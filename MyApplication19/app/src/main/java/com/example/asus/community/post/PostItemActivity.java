package com.example.asus.community.post;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.baseurl.BaseUrl;
import com.example.vo.Comment;
import com.example.asus.community.comment.CommentAdapter;
import com.example.asus.myapplication.R;
import com.example.vo.Post;
import com.example.vo.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PostItemActivity extends AppCompatActivity {
    private static final String TAG = "PostItemActivity";

    private int post_id;

    private Post post;
    private User user;

    private List<Comment> commentList;

    private TextView textView_post_source_name;
    private Handler textHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String post_source_name = (String) msg.obj;
            textView_post_source_name.setText(post_source_name);
        }
    };

    private TextView textView_post_content;

    private ImageView imageView_post;
    private Bitmap bitmap;
    private Handler imgHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            bitmap = (Bitmap) msg.obj;
            imageView_post.setImageBitmap(bitmap);
        }
    };

    private ImageButton imageButton_comment;
    private ImageButton imageButton_share;

    private ListView listView_comment;
    private Handler listHandler = new Handler();


    //评论 v.
    private User user_myself;

    private int user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        //1.获取点击焦点的数据
        Intent intent = getIntent();
        post_id = intent.getIntExtra("post_id", 1);
        //2.设置内容
        doHttpGetPostById(Integer.toString(post_id));
        doHttpGetPostComment(Integer.toString(post_id));
        //3.操作监听
        imageButton_comment = findViewById(R.id.imageButton_comment);
        imageButton_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentBottomDialog(PostItemActivity.this);
            }
        });
        imageButton_share = findViewById(R.id.imageButton_share);
        imageButton_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareBottomDialog(PostItemActivity.this);
            }
        });
    }

    //1.辅助方法：根据ID获取post,并设置布局内容（发帖人、贴文、贴图）
    public void doHttpGetPostById(String post_id) {
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody formBody = new FormBody.Builder().add("post_id", post_id).build();
        Request request = new Request.Builder().get().url(BaseUrl.BASE_URL + "findPostById").post(formBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.i(TAG, "onResponse: " + result);
                Gson gson = new Gson();
                post = gson.fromJson(result, Post.class);
                //设置发帖人
                textView_post_source_name = findViewById(R.id.textView_post_source_name);
                doHttpGetPostSourceName(Integer.toString(post.getPost_source_id()));//

                //贴文
                textView_post_content = findViewById(R.id.textView__post_content);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        textView_post_content.setText(post.getPost_content());
                    }
                }).start();

                //贴图
                imageView_post = findViewById(R.id.imageView_post_content_img);
                doHttpGetPostImg(post.getPost_content_img());

            }
        });
    }

    //1.1 辅助方法：获取post所属用户昵称（在 1 中调用）
    public void doHttpGetPostSourceName(String post_source_id) {
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody formBody = new FormBody.Builder().add("user_id", post_source_id).build();
        Request request = new Request.Builder().get().url(BaseUrl.BASE_URL + "findUserById").post(formBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Gson gson = new Gson();
                user = gson.fromJson(result, User.class);
                Message message = new Message();
                message.obj = user.getUser_name();
                textHandler.sendMessage(message);
            }
        });
    }

    //1.2 辅助方法：获取图片资源（在 1 中调用）
    public void doHttpGetPostImg(String post_content_img) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(BaseUrl.POST_IMG_BASE_URL + post_content_img).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                Message message = new Message();
                message.obj = bitmap;
                imgHandler.sendMessage(message);
            }
        });
    }

    //1.3 辅助方法：用于获取此post相关评论,并添加到listview
    public void doHttpGetPostComment(String comment_post_id) {
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody formBody = new FormBody.Builder().add("comment_post_id", comment_post_id).build();
        Request request = new Request.Builder().get().url(BaseUrl.BASE_URL + "selectCommentByPostId").post(formBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                commentList = new Gson().fromJson(result, new TypeToken<List<Comment>>() {
                }.getType());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        listHandler.post(runnableUi);
                    }
                }).start();
            }
        });
    }

    Runnable runnableUi = new Runnable() {
        @Override
        public void run() {
            listView_comment = findViewById(R.id.listView_comment);
            listView_comment.setAdapter(new CommentAdapter(PostItemActivity.this, commentList));
        }
    };

    //2.辅助方法：分享底部弹窗
    private void shareBottomDialog(Context context) {
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(context, R.style.BottomDialogTheme);
        //2、设置布局
        View view = View.inflate(context, R.layout.fragment_two_share_dialog, null);
        dialog.setContentView(view);

        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.DialogAnimStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        //3.设置监听
        dialog.findViewById(R.id.circle_share_QQ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.circle_share_WeChat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.circle_share_QQSpace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.circle_share_WeChatC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    //3.辅助方法：评论底部弹窗
    private void commentBottomDialog(final Context context) {
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(context, R.style.BottomDialogTheme);
        //2、设置布局
        View view = View.inflate(context, R.layout.fragment_two_comment_dialog, null);
        dialog.setContentView(view);

        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.DialogAnimStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        //3.设置监听
        final EditText editText = dialog.findViewById(R.id.editText_comment);
        dialog.findViewById(R.id.button_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = editText.getText().toString();
                SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                String user_phone = sharedPreferences.getString("user_phone", "");
                doHttpGetUserIdByPhone(user_phone, comment);

                dialog.dismiss();
            }
        });

    }

    //3.1 辅助方法：通过user_phone 获取user_id
    public void doHttpGetUserIdByPhone(String user_phone, final String comment) {
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody formBody = new FormBody.Builder().add("user_phone", user_phone).build();
        Request request = new Request.Builder().get().url(BaseUrl.BASE_URL + "findUserByPhone").post(formBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                user_myself = new Gson().fromJson(result, User.class);
                user_id = user_myself.getUser_id();
                Comment newComment = new Comment(user_id, post_id, comment);
                if (!"".equals(comment)) {
                    doHttpCommitComment(newComment);
                }
            }
        });
    }

    //3.2 辅助方法：提交评论
    public void doHttpCommitComment(Comment comment) {
        String json = new Gson().toJson(comment);
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody formBody = new FormBody.Builder().add("json", json).build();
        Request request = new Request.Builder().get().url(BaseUrl.BASE_URL + "addComment").post(formBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                if ("true".equals(result)) {
                    Looper.prepare();
                    Toast.makeText(PostItemActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                } else {
                    Looper.prepare();
                    Toast.makeText(PostItemActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        });

    }
}
