package com.snnu.yefan.socialization;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huewu.pla.lib.NoScrollMultiColumnListView;
import com.snnu.yefan.bean.Bean;
import com.snnu.yefan.view.RoundImageView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 029call on 2016/7/12.
 */
public class DetailActivity extends AppCompatActivity {



    private List<Bean> list = new ArrayList<Bean>();
    private NoScrollMultiColumnListView noScrollMultiColumnListView;
    private PLAAdapter mAdapter;
    private RoundImageView imageView;
    private TextView tv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_item);
        initData();
        init();
    }

    private void init() {

        imageView = (RoundImageView) findViewById(R.id.thumbnail);
        tv = (TextView) findViewById(R.id.tv);
        imageView.setImageResource(getIntent().getIntExtra("imgId",0));
        tv.setText(getIntent().getStringExtra("content"));
        noScrollMultiColumnListView = (NoScrollMultiColumnListView) findViewById(R.id.listView);
        mAdapter = new PLAAdapter();

        noScrollMultiColumnListView.setAdapter(mAdapter);


    }


    private void initData() {
        Bean bean = null;
        for(int i = 0 ; i < 20 ;i++){
            bean = new Bean();
            if(i%2==0){
                bean.setId(R.drawable.img02);
            }else if(i%3==0){
                bean.setId(R.drawable.img03);
            }else{
                bean.setId(R.drawable.img04);
            }
            bean.setContent("Retro football poster on Behance Retro football poster on Behance Retro football poster on Behance");
            list.add(bean);
        }

    }



    private class PLAAdapter extends BaseAdapter {

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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView==null){
                convertView = LayoutInflater.from(getBaseContext()).inflate(R.layout.sample_item,parent,false);
                holder = new ViewHolder();
                convertView.setTag(holder);
            }
            holder = (ViewHolder) convertView.getTag();
            holder.img = (ImageView) convertView.findViewById(R.id.thumbnail);
            holder.tv = (TextView) convertView.findViewById(R.id.tv);
            Bean bean = list.get(position);
            holder.img.setImageResource(bean.getId());
            holder.tv.setText(bean.getContent());

            return convertView;
        }
    }

    private class ViewHolder{
        ImageView img;
        TextView tv;
    }
}
