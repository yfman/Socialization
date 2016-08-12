/**
 * 2015-4-1
 */
package com.snnu.yefan.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.snnu.yefan.bean.Contact;
import com.snnu.yefan.socialization.R;

import java.util.List;



public class ContactAdapter extends BaseAdapter {
    public static final int TYPE_TITLE = 0;
    public static final int TYPE_COMPANY = 1;
    private List<Contact> mContactList;
    private int imgId[] = {R.drawable.head_1,R.drawable.head_2,R.drawable.head_3,R.drawable.head_4,R.drawable.head_5,R.drawable.head_6,R.drawable.head_7,R.drawable.head_8,
            R.drawable.head_9,R.drawable.head_10,R.drawable.head_11,R.drawable.head_12,R.drawable.head_13,R.drawable.head_14,R.drawable.head_15,R.drawable.head_16,R.drawable.head_17,
            R.drawable.head_18,R.drawable.head_19,R.drawable.head_20,R.drawable.head_21,R.drawable.head_22,R.drawable.head_23,R.drawable.head_24,R.drawable.head_25,R.drawable.head_26,
            R.drawable.head_27,R.drawable.head_28,R.drawable.head_29,R.drawable.head_30,R.drawable.head_1,R.drawable.head_2,R.drawable.head_3,R.drawable.head_4,R.drawable.head_5,R.drawable.head_6,R.drawable.head_7,R.drawable.head_8,
            R.drawable.head_9,R.drawable.head_10,R.drawable.head_11,R.drawable.head_12,R.drawable.head_13,R.drawable.head_14,R.drawable.head_15,R.drawable.head_16,R.drawable.head_17,
            R.drawable.head_18,R.drawable.head_19,R.drawable.head_20,R.drawable.head_21,R.drawable.head_22};
    public ContactAdapter(List<Contact> contactList) {
        mContactList = contactList;
    }

    @Override
    public int getCount() {
        return mContactList.size();
    }

    @Override
    public Object getItem(int position) {
        return mContactList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (TextUtils.isEmpty(mContactList.get(position).getCode())) {
            return TYPE_TITLE;
        } else {
            return TYPE_COMPANY;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public boolean isEnabled(int position) {
        return !TextUtils.isEmpty(mContactList.get(position).getCode());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        IndexViewHolder indexHolder;
        ContactViewHolder contactHolder;
        switch (getItemViewType(position)) {
            case TYPE_TITLE:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.view_holder_company_index, parent, false);
                    indexHolder = new IndexViewHolder();
                    indexHolder.tvIndex = (TextView) convertView.findViewById(R.id.tv_index);
                    convertView.setTag(indexHolder);
                } else {
                    indexHolder = (IndexViewHolder) convertView.getTag();
                }
                indexHolder.tvIndex.setText(mContactList.get(position).getName());
                break;
            case TYPE_COMPANY:

                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.view_holder_company, parent, false);
                    contactHolder = new ContactViewHolder();
                    contactHolder.ivLogo = (ImageView) convertView.findViewById(R.id.iv_logo);
                    contactHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
                    convertView.setTag(contactHolder);
                } else {
                    contactHolder = (ContactViewHolder) convertView.getTag();
                }
                contactHolder.ivLogo.setImageResource(imgId[position]);
//                Glide.with(context)
//                        .load(Utils.formatLogoUrl(mContactList.get(position).getLogo()))
//                        .dontAnimate()
//                        .placeholder(R.drawable.default_logo)
//                        .into(contactHolder.ivLogo);
                contactHolder.tvName.setText(mContactList.get(position).getName());
                Log.i("TAG", "getView: "+mContactList.size());
                break;
        }
        return convertView;
    }

    public static class IndexViewHolder {

        public TextView tvIndex;

    }

    public static class ContactViewHolder {

        public ImageView ivLogo;

        public TextView tvName;

    }
}
