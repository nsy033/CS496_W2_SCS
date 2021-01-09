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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.google.android.material.internal.ContextUtils.getActivity;

public class ListViewAdapter extends BaseAdapter {
    public static ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>() ;
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

        ListViewItem listViewItem = listViewItemList.get(position);

        iconImageView.setImageBitmap(listViewItem.getIcon());
        nameTextView.setText(listViewItem.getTitle());
        numberTextView.setText(listViewItem.getDesc());
/*
        convertView.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Profile.class);
                context.startActivity(intent);
            }
        });
*/
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

    public void addItem(Bitmap icon, String title, String desc, String mail, String address) {
        ListViewItem item = new ListViewItem();

        item.setIcon(icon);
        item.setTitle(title);
        item.setDesc(desc);
        item.setMail(mail);
        item.setAddress(address);

        listViewItemList.add(item);
    }

    public void addFront(Bitmap icon, String title, String desc, String mail, String address) {
        ListViewItem item = new ListViewItem();

        item.setIcon(icon);
        item.setTitle(title);
        item.setDesc(desc);
        item.setMail(mail);
        item.setAddress(address);

        listViewItemList.add(0, item);
    }

    public void clear(){
        listViewItemList=new ArrayList<ListViewItem>();
    }

}
