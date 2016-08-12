package com.snnu.yefan.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huewu.pla.lib.MultiColumnListView;
import com.huewu.pla.lib.internal.PLA_AdapterView;
import com.snnu.yefan.bean.Bean;
import com.snnu.yefan.socialization.DetailActivity;
import com.snnu.yefan.socialization.R;
import com.snnu.yefan.view.RoundImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zero on 2016/7/9.
 */
public class HomeFragment extends Fragment {

    private static HomeFragment fm;

    private MultiColumnListView mListView;
    private PLAAdapter mAdapter;
    private Context mContext;
    private List<Bean> list = new ArrayList<Bean>();
    private int imgId[] = {R.drawable.img_1, R.drawable.img_2, R.drawable.img_3, R.drawable.img_4, R.drawable.img_5, R.drawable.img_6, R.drawable.img_7,
            R.drawable.img_8, R.drawable.img_9, R.drawable.img_10, R.drawable.img_1, R.drawable.img_2, R.drawable.img_3, R.drawable.img_4, R.drawable.img_5,
            R.drawable.img_6, R.drawable.img_7, R.drawable.img_8, R.drawable.img_9, R.drawable.img_10};
    private int pohotoId[] = {R.drawable.head_1,R.drawable.head_2,R.drawable.head_3,R.drawable.head_4,R.drawable.head_5,R.drawable.head_6,R.drawable.head_7,R.drawable.head_8,R.drawable.head_9,R.drawable.head_10,
            R.drawable.head_1,R.drawable.head_2,R.drawable.head_3,R.drawable.head_4,R.drawable.head_5,R.drawable.head_6,R.drawable.head_7,R.drawable.head_8,R.drawable.head_9,R.drawable.head_10};
    private String content[] = {"我给你，早在你出生前多年的一个傍晚，看到的一朵黄玫瑰的记忆。", "看着你的照片，\n" +
            "　　就像别人田野里滋长的春天\n" +
            "　　时常\n" +
            "　　烂漫了爸爸的泪行", "我想在有生之年，许你一处没有围墙的庭院。", "你说你要与我共白头\n" +
            "   染完之后你又说我非主流", "今天做了新美甲，靓靓哒！", "从宇宙的深渊,\n" +
            "　　一只不带珍珠的贝壳,\n" +
            "　　被抛上了你的海岸。", "唯美食不可辜负。", "酥软香嫩，齿有余香。", "赴你一场星光之约。", "匠人有什么不好呢？一生只做一件事，细节处满是匠心。", "有个人，从齐耳短发到长发及腰，从校服到婚纱，从青春年少到苍苍白发——致初恋。", "在休闲的下午，就该如此享受美食。", "我不止喜欢这杯咖啡，还有在我旁边的你。", "There are no hopeless situations;there are only men who have grown hopeless abput them.", "还记得你说家是唯一的城堡，随着稻香河流继续奔跑", "重要的不是别人怎么看你，重要的是自己怎么看自己。", "汤汁清爽，萝卜白净，辣油红艳，香菜翠绿，面条黄亮，你值得拥有。", "今天威少那个补扣居然没进当日十佳球，我想不通啊……", "我科走后，再无信仰。", "你带给大家的期望永远不会让人失望 因为你是一个言出必行的男人 你用行动证明了你不是一个只会说空话的人\n" +
            "你的情商真的很低很低 你的榆木脑袋总是转不过弯 却也傻得可爱 也只有这样的你才更讨人喜爱不是吗？",};

    public static HomeFragment instance() {
        if (fm == null) {
            fm = new HomeFragment();
        }
        return fm;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mContext = view.getContext();
        initData();
        init(view);
        return view;
    }

    private void initData() {
        Bean bean = null;
        for (int i = 0; i < 20; i++) {
            bean = new Bean();
            bean.setId(imgId[i]);
            bean.setContent(content[i]);
            list.add(bean);
        }

    }

    private void init(View view) {
        mListView = (MultiColumnListView) view.findViewById(R.id.list);
        mAdapter = new PLAAdapter();

        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new PLA_AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(PLA_AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("imgId",imgId[position]);
                intent.putExtra("content",content[position]);
                startActivity(intent);
            }
        });
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
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.sample_item, parent, false);
                holder = new ViewHolder();
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            holder.img = (ImageView) convertView.findViewById(R.id.thumbnail);
            holder.tv = (TextView) convertView.findViewById(R.id.tv);
            holder.photo = (RoundImageView) convertView.findViewById(R.id.photo);
            Bean bean = list.get(position);
            holder.img.setImageResource(bean.getId());
            holder.tv.setText(bean.getContent());
            holder.photo.setImageResource(pohotoId[position]);
            return convertView;
        }
    }

    private class ViewHolder {
        ImageView img;
        TextView tv;
        RoundImageView photo;
    }

}
