package com.snnu.yefan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.snnu.yefan.bean.MsgBorder;
import com.snnu.yefan.socialization.R;
import com.snnu.yefan.utils.Constants;

import java.util.List;

/**
 * Created by zero on 2016/8/2.
 */
public class MsgBorderAdapter extends BaseAdapter {

    private Context mContext;
    private List<MsgBorder> list;
    public static final int WORD = 0;
    public static final int VOICE = 1;
    public static final int PICTRUE = 2;
    public static final int VIDEO = 3;

    public MsgBorderAdapter(Context context, List<MsgBorder> list){
        mContext = context;
        this.list = list;
    }

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
    public int getItemViewType(int position) {
        MsgBorder msgBorder = list.get(position);
        if(msgBorder.getType()== Constants.WORD){
            return WORD;
        }else if(msgBorder.getType()==Constants.VOICE){
            return VOICE;
        }else if(msgBorder.getType()==Constants.PICTRUE){
            return PICTRUE;
        }else{
            return VIDEO;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        switch (getItemViewType(position)){
            case WORD:
                WordViewHolder holder = null;
                if(convertView==null){
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.msg_border__word_item,parent,false);
                    holder = new WordViewHolder();
                    holder.imageView = (ImageView) convertView.findViewById(R.id.imageWord);
                    holder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
                    convertView.setTag(holder);
                }else{
                    holder = (WordViewHolder) convertView.getTag();
                }
                MsgBorder msgBorder = list.get(position);
                holder.imageView.setImageResource(R.mipmap.word);
                holder.tvContent.setText(msgBorder.getWordContent());
                break;
            case VOICE:
                VoiceViewHolder voiceViewHolder = null;
                if(convertView==null){
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.msg_border__voice_item,parent,false);
                    voiceViewHolder = new VoiceViewHolder();
                    voiceViewHolder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
                    convertView.setTag(voiceViewHolder);
                }else{
                    voiceViewHolder = (VoiceViewHolder) convertView.getTag();
                }
                MsgBorder msgBorder1 = list.get(position);
                voiceViewHolder.tvTime.setText(msgBorder1.getVoice()+"''");
                break;
            case PICTRUE:
                PictrueViewHolder pictrueViewHolder = null;
                if(convertView==null){
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.msg_border__pictrue_item,parent,false);
                    pictrueViewHolder = new PictrueViewHolder();
                    pictrueViewHolder.imageView = (ImageView) convertView.findViewById(R.id.img_pictrue);
                    convertView.setTag(pictrueViewHolder);
                }else{
                    pictrueViewHolder = (PictrueViewHolder) convertView.getTag();
                }
                MsgBorder msgBorder2 = list.get(position);
                pictrueViewHolder.imageView.setImageResource(msgBorder2.getPictrue());
                break;
            case VIDEO:
                VideoViewHolder videoViewHolder = null;
                if(convertView==null){
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.msg_border__video_item,parent,false);
                    videoViewHolder = new VideoViewHolder();
                    videoViewHolder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
                    convertView.setTag(videoViewHolder);
                }else{
                    videoViewHolder = (VideoViewHolder) convertView.getTag();
                }
                MsgBorder msgBorder3 = list.get(position);
                videoViewHolder.tvTime.setText(msgBorder3.getVideo()+"'");
                break;
        }

        return convertView;
    }

    class WordViewHolder{

        ImageView imageView;
        TextView tvContent;
    }

    class VoiceViewHolder{
        TextView tvTime;
    }

    class PictrueViewHolder{
        ImageView imageView;
    }

    class VideoViewHolder{
        TextView tvTime;
    }
}
