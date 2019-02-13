package com.example.asus.community.comment;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.asus.baseurl.BaseUrl;
import com.example.asus.myapplication.R;
import com.example.vo.Comment;
import com.example.vo.User;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CommentAdapter extends BaseAdapter {
    private static final String TAG = "CommentAdapter";

    private LayoutInflater layoutInflater;

    private Context context;

    private List<Comment> commentList;

    private User user;

    //构造方法
    public CommentAdapter(Context context, List<Comment> commentList) {
        this.context = context;
        this.commentList = commentList;
        layoutInflater=LayoutInflater.from(context);
    }

    //内部持有类
    private static class ViewHolder{
        public CircleImageView circleImageView;

        public TextView textView_user;
        private Handler textHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String post_source_name= (String)msg.obj;
                textView_user.setText(post_source_name);
            }
        };

        public TextView textView_comment;


    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=layoutInflater.inflate(R.layout.community_post_item_comment_list_item,null);
            //获取UI
            viewHolder.circleImageView=convertView.findViewById(R.id.circle_comment_user);
            viewHolder.textView_user=convertView.findViewById(R.id.textView_user);
            viewHolder.textView_comment=convertView.findViewById(R.id.textView_comment);

            convertView.setTag(viewHolder);

        }else{
             viewHolder=(ViewHolder)convertView.getTag();
        }

        Comment comment=commentList.get(position);
        viewHolder.circleImageView.setImageResource(R.drawable.pear);
        Log.i(TAG, "getView: "+comment.getComment_user_id());
        doHttpGetPostSourceName(viewHolder.textHandler,Integer.toString(comment.getComment_user_id()));
        viewHolder.textView_comment.setText(comment.getComment_content());

        return convertView;


    }


    //辅助方法：通过user_phone获取user
    public void doHttpGetPostSourceName(final Handler textHandler,String user_id) {
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody formBody = new FormBody.Builder().add("user_id", user_id).build();
        Request request = new Request.Builder().get().url(BaseUrl.BASE_URL + "findUserById").post(formBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result=response.body().string();
                Gson gson=new Gson();
                user=gson.fromJson(result,User.class);
                Message message=new Message();
                message.obj=user.getUser_name();
                textHandler.sendMessage(message);
            }
        });
    }
}
