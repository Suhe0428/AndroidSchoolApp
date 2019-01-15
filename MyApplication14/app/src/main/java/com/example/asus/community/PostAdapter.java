package com.example.asus.community;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.baseurl.BaseUrl;
import com.example.asus.myapplication.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PostAdapter extends BaseAdapter {
    private static final String TAG = "PostAdapter";

    private List<Post> postList;
    private Context context;
    private LayoutInflater layoutInflater;

    private Bitmap bitmap;

    private static class ViewHolder {
        //      Item主体
        public TextView textView_post_source;
        public CircleImageView circleImageView_post_source_img;
        public TextView textView__post_content;
        public ImageView imageView_post_content_img;
        //      底部操作
        public ImageButton imageButton_com;
        public TextView textView_com_num;
        public ImageButton imageButton_sha;
        public TextView textView_sha_num;

        public Handler handler=new Handler(){
            public void handleMessage(Message message){
                Bitmap bitmap=(Bitmap)message.obj;
                imageView_post_content_img.setImageBitmap(bitmap);
            }
        };
    }

    public PostAdapter(Context context, List<Post> postList) {
        this.postList = postList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return postList.size();
    }

    @Override
    public Object getItem(int position) {
        return postList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.fragment_two_community_list_item, null);
            /*贴子内容相关
             * 发布人
             * 文字
             * 图片*/
            viewHolder.circleImageView_post_source_img = convertView.findViewById(R.id.circleImageView_post_source_img);
            viewHolder.textView_post_source = convertView.findViewById(R.id.textView__post_source);
            viewHolder.textView__post_content = convertView.findViewById(R.id.textView__post_content);
            viewHolder.imageView_post_content_img = convertView.findViewById(R.id.imageView_post_content_img);

            /*帖子操作相关
             * 点赞
             * 评论
             * 分享*/
            viewHolder.imageButton_com = convertView.findViewById(R.id.imageButton_com);
            viewHolder.imageButton_sha = convertView.findViewById(R.id.imageButton_sha);
            viewHolder.textView_com_num = convertView.findViewById(R.id.textView_com_num);
            viewHolder.textView_sha_num = convertView.findViewById(R.id.textView_sha_num);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        /*1.主体布局*/
        final Post post = postList.get(position);
        //头像
        viewHolder.circleImageView_post_source_img.setImageResource(R.drawable.pear);
        //昵称
        SharedPreferences sharedPreferences=context.getSharedPreferences("post_source_names",Context.MODE_PRIVATE);
        String post_source_name=sharedPreferences.getString(""+post.getPost_id(),"");
        viewHolder.textView_post_source.setText(post_source_name);
        //内容
        viewHolder.textView__post_content.setText(post.getPost_content());
        //图片
        doHttpGetPostImg(viewHolder.handler,viewHolder.imageView_post_content_img,post.getPost_content_img());

        /*2.主体操作监听*/
        viewHolder.textView__post_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PostItemActivity.class);
                context.startActivity(intent);
            }
        });
        viewHolder.imageView_post_content_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PostItemActivity.class);
                context.startActivity(intent);
            }
        });

        /*3.底部操作监听*/
        //评论
        viewHolder.imageButton_com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentBottomDialog(context);
            }
        });
        //分享
        viewHolder.imageButton_sha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareBottomDialog(context);
            }
        });

        return convertView;
    }

    //辅助方法：分享底部弹窗
    private void shareBottomDialog(final Context context) {
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

    //辅助方法：评论底部弹窗
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
        dialog.findViewById(R.id.editText_comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        dialog.findViewById(R.id.button_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "评论成功", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

    }

    //辅助方法：获取post的content_img
    public void doHttpGetPostImg(final Handler handler,final ImageView imageView,String post_img_name){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(BaseUrl.IMG_BASE_URL + post_img_name)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();//得到图片的流
                bitmap = BitmapFactory.decodeStream(inputStream);
                Message message=new Message();
                message.obj=bitmap;
                handler.sendMessage(message);
            }
        });
    }


}