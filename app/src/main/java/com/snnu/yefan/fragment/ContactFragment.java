package com.snnu.yefan.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.snnu.yefan.adapter.ContactAdapter;
import com.snnu.yefan.bean.Contact;
import com.snnu.yefan.socialization.R;
import com.snnu.yefan.view.IndexBar;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2016/7/20.
 */
public class ContactFragment extends Fragment {

    private static ContactFragment fragment = null;
    private ListView lvContact;
    private IndexBar ibIndicator;
    private TextView tvIndicator;
    private List<Contact> mContactList = new ArrayList<Contact>();
    private Context mContext;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact,container,false);
        mContext = view.getContext();
        readContact();
        init(view);

        return view;
    }

    public static ContactFragment instance(){
        if(fragment==null){
            fragment = new ContactFragment();
        }
        return fragment;
    }

    private void init(View view) {
        lvContact = (ListView) view.findViewById(R.id.lv_contact);
        ibIndicator = (IndexBar) view.findViewById(R.id.ib_indicator);
        tvIndicator = (TextView) view.findViewById(R.id.tv_indicator);
        lvContact.setAdapter(new ContactAdapter(mContactList));
        ibIndicator.setData(mContactList,lvContact,tvIndicator);
    }

    private void readContact() {
        try {
            InputStream is = mContext.getAssets().open("contact.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer);

            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonArray jArray = parser.parse(json).getAsJsonArray();
            for (JsonElement obj : jArray) {
                Contact contact = gson.fromJson(obj, Contact.class);
                mContactList.add(contact);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
