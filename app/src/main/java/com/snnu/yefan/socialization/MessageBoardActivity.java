package com.snnu.yefan.socialization;

import android.os.Bundle;
import android.support.annotation.AnimatorRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.snnu.yefan.adapter.MsgBorderAdapter;
import com.snnu.yefan.bean.MsgBorder;
import com.snnu.yefan.utils.Constants;
import com.snnu.yefan.view.NoSrcollListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2016/7/29.
 */
public class MessageBoardActivity extends AppCompatActivity {

    private NoSrcollListView list_msgBorder;
    private MsgBorderAdapter msgBorderAdapter;
    private ImageView imageSelect;
    private RelativeLayout mRelativeSelect;
    private List<MsgBorder> msgList;
    private boolean isOn = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_border);
        initData();
        init();
    }

    private void init() {

        list_msgBorder = (NoSrcollListView) findViewById(R.id.messsage_listview);
        imageSelect = (ImageView) findViewById(R.id.image_select);
        mRelativeSelect = (RelativeLayout) findViewById(R.id.relative);
        imageSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = null;
                if(isOn){

                    //mRelativeSelect.setAnimation();
                    mRelativeSelect.setVisibility(View.GONE);
                    isOn = false;
                }else{
                    //mRelativeSelect.setAnimation();
                    mRelativeSelect.setVisibility(View.VISIBLE);
                    isOn = true;
                }
            }
        });
        msgBorderAdapter = new MsgBorderAdapter(this,msgList);
        list_msgBorder.setAdapter(msgBorderAdapter);

    }

    private void initData() {
        msgList = new ArrayList<MsgBorder>();
        MsgBorder msg1 = new MsgBorder();
        msg1.setType(Constants.WORD);
        msg1.setWordContent("shfl hklsdf lhsl fklshfd khs fdk ");
        MsgBorder msg2 = new MsgBorder();
        msg2.setType(Constants.WORD);
        msg2.setWordContent("shfl aslfdj slafjdl aflsd ");
        MsgBorder msg3 = new MsgBorder();
        msg3.setType(Constants.VOICE);
        msg3.setVoice(24);
        MsgBorder msg4 = new MsgBorder();
        msg4.setType(Constants.VOICE);
        msg4.setVoice(32);
        MsgBorder msg5 = new MsgBorder();
        msg5.setType(Constants.PICTRUE);
        msg5.setPictrue(R.drawable.img03);
        MsgBorder msg6 = new MsgBorder();
        msg6.setType(Constants.PICTRUE);
        msg6.setPictrue(R.drawable.img04);
        MsgBorder msg7 = new MsgBorder();
        msg7.setType(Constants.VIDEO);
        msg7.setVideo(30);
        MsgBorder msg8 = new MsgBorder();
        msg8.setType(Constants.VIDEO);
        msg8.setVideo(40);
        msgList.add(msg1);
        msgList.add(msg2);
        msgList.add(msg3);
        msgList.add(msg4);
        msgList.add(msg5);
        msgList.add(msg6);
        msgList.add(msg7);
        msgList.add(msg8);
    }
}
