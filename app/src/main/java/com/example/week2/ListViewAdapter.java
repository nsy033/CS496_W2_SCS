package com.example.week2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.example.week2.Page2Fragment.recyclerView;
import static com.google.android.material.internal.ContextUtils.getActivity;

public class ListViewAdapter extends BaseAdapter {
    public static ArrayList<User> listViewItemList = new ArrayList<User>() ;
    Filter listFilter;

    public ListViewAdapter() {}

    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView1);
        TextView nameTextView = (TextView) convertView.findViewById(R.id.textView1);
        TextView numberTextView = (TextView) convertView.findViewById(R.id.textView2);

        User listViewItem = listViewItemList.get(position);

        nameTextView.setText(listViewItem.getName());
        numberTextView.setText(listViewItem.getPhone());
        Glide.with(recyclerView).load(listViewItem.user_profile_photo).into(iconImageView);
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    public void addItem(Bitmap icon, String title, String desc, String mail, String profile) {
        User item = new User();


        item.setName(title);
        item.setPhone(desc);
        item.setEmail(mail);
        item.setUser_profile(profile);

        listViewItemList.add(item);
    }

    public void addFront(Bitmap icon, String title, String desc, String mail, String profile) {

        User item = new User();


        item.setName(title);
        item.setPhone(desc);
        item.setEmail(mail);
        item.setUser_profile(profile);

        listViewItemList.add(0, item);
    }

    public void clear(){
        listViewItemList=new ArrayList<User>();
    }

}
