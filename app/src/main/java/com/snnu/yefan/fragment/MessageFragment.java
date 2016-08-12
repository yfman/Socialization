package com.snnu.yefan.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.readystatesoftware.viewbadger.BadgeView;
import com.snnu.yefan.bean.Message;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.snnu.yefan.bean.Person;
import com.snnu.yefan.socialization.DetailMsgActivity;
import com.snnu.yefan.socialization.R;
import com.snnu.yefan.view.NoSrcollListView;
import com.snnu.yefan.view.RoundImageView;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by zero on 2016/7/9.
 */
public class MessageFragment extends Fragment {

    private static MessageFragment fm;
    private NoSrcollListView mListView;
    private NoSrcollListView msgListView;
    private MyAdapter adapter;
    private MsgAdapter msgAdapter;
    private List<Person> list = new ArrayList<Person>();
    private List<Message> msgList = new ArrayList<Message>();
    private Context mContext;
    private String msgContent[] = {"最近干嘛了？","中午要吃啥?","撸串去","帅哥有时间吗？","火影更新了，快去看","走，去看电影呗"};
    private int img[] = {R.drawable.head_1,R.drawable.head_12,R.drawable.head_3,R.drawable.head_8,R.drawable.head_5,R.drawable.head_6,R.drawable.head_11};
    private int Img[] = {R.drawable.head_2,R.drawable.head_9,R.drawable.head_10,R.drawable.head_13,R.drawable.head_16};
    public static MessageFragment instance(){
        if(fm==null){
            fm = new MessageFragment();
        }
        return fm;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main,container,false);
        mContext = view.getContext();
        initData();
        init(view);
        return view;
    }

    private void initData() {

        Person person = null;
        for(int i = 0 ;i<5;i++){
            person = new Person();
            person.setResId(Img[i]);
            person.setName("Jonathan McCall");
            person.setInfo("Produce Manager at  OXCO,Inc");
            list.add(person);
        }
        Message msg = null;
        for(int i = 0 ;i<5;i++){
            msg = new Message();
            msg.setResId(img[i]);
            msg.setMessage(msgContent[i]);
            msgList.add(msg);
        }

    }

    private void init(View view) {

        mListView = (NoSrcollListView) view.findViewById(R.id.list_view);
        msgListView = (NoSrcollListView) view.findViewById(R.id.list_msg);

        adapter = new MyAdapter();
        mListView.setAdapter(adapter);
        msgAdapter = new MsgAdapter();
        msgListView.setAdapter(msgAdapter);

        msgListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, DetailMsgActivity.class);
                startActivity(intent);
            }
        });

//        for(int i = 0 ;i < msgList.size() ; i++){
//            BadgeView badgeView = new BadgeView(mContext,mListView.getChildAt(i));
//            badgeView.setText("4");
//            badgeView.show();
//        }


    }


    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if(convertView==null){
                convertView = LayoutInflater.from(mContext).inflate(R.layout.mian_item,parent,false);
                final View view = convertView;
                holder = new ViewHolder();
                holder.img = (RoundImageView) convertView.findViewById(R.id.head);
                holder.tv_name = (TextView) convertView.findViewById(R.id.name);
                holder.tv_info = (TextView) convertView.findViewById(R.id.info);
                holder.img_del = (ImageView) convertView.findViewById(R.id.img_del);
                holder.img_add = (ImageView) convertView.findViewById(R.id.img_add);
                holder.img_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deletePattern(view,position);
                    }
                });
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            Person person = list.get(position);
            holder.img.setImageResource(person.getResId());
            holder.tv_name.setText(person.getName());
            holder.tv_info.setText(person.getInfo());
            return convertView;
        }
    }


    private void deletePattern(final View view, final int position) {

        Animation.AnimationListener al = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                list.remove(position);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
        collapse(view, al);

    }

    private void collapse(final View view, Animation.AnimationListener al) {
        final int originHeight = view.getMeasuredHeight();

        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1.0f) {
                    view.setVisibility(View.GONE);
                } else {
                    view.getLayoutParams().height = originHeight - (int) (originHeight * interpolatedTime);
                    view.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        if (al != null) {
            animation.setAnimationListener(al);
        }
        animation.setDuration(300);
        view.startAnimation(animation);
    }

    private class ViewHolder{
        public RoundImageView img;
        public TextView tv_name;
        public TextView tv_info;
        public ImageView img_del;
        public ImageView img_add;
    }


    private class MsgAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return msgList.size();
        }

        @Override
        public Object getItem(int position) {
            return msgList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder = null;

            if(convertView==null){

                convertView = LayoutInflater.from(mContext).inflate(R.layout.message_item,parent,false);
                holder = new Holder();
                holder.img = (RoundImageView) convertView.findViewById(R.id.head);
                holder.tv_msg = (TextView) convertView.findViewById(R.id.tv_msg);
                holder.tv_del = (TextView) convertView.findViewById(R.id.tv_del);
                holder.tv_scan = (TextView) convertView.findViewById(R.id.tv_scan);
                convertView.setTag(holder);
            }else{
                holder = (Holder) convertView.getTag();
            }
            final Message msg = msgList.get(position);
//            BadgeView badgeView = new BadgeView(mContext,holder.img);
//            badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
//            badgeView.setText("12");
//            badgeView.show();
            holder.img.setImageResource(msg.getResId());
            holder.tv_msg.setText(msg.getMessage());
            holder.tv_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(mContext)
                            .setTitle("提示")
                            .setMessage("确认删除")
                            .setNegativeButton("取消", null)
                            .setPositiveButton("确定",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0,
                                                            int arg1) {

                                        }
                                    }).show();
                }
            });

            holder.tv_scan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            return convertView;
        }
    }

    private class Holder{
        public TextView tv_msg;
        public TextView tv_del;
        public TextView tv_scan;
        public RoundImageView img;
    }
}
