package com.example.week2;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.example.week2.MainActivity.listViewItemList;
import static com.example.week2.Page2Fragment.recyclerView;
import static com.google.android.material.internal.ContextUtils.getActivity;

public class ListViewAdapter extends BaseAdapter {

    Filter listFilter;
    static ImageView iconImageView;

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

        iconImageView = (ImageView) convertView.findViewById(R.id.imageView1);
        TextView nameTextView = (TextView) convertView.findViewById(R.id.textView1);
        TextView numberTextView = (TextView) convertView.findViewById(R.id.textView2);

        User listViewItem = listViewItemList.get(position);

        nameTextView.setText(listViewItem.getName());
        numberTextView.setText(listViewItem.getUser_profile());
        Glide.with(recyclerView)
                .load(listViewItem.user_profile_photo)
                .circleCrop()
                .into(iconImageView);
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

    public void addItem(Bitmap icon, String title, String desc, String mail, String profile, String profile_photo) {
        User item = new User();
        item.setName(title);
        item.setPhone(desc);
        item.setEmail(mail);
        item.setUser_profile(profile);
        item.setUser_profile_photo(profile_photo);
        listViewItemList.add(item);
    }

    public void addFront(Bitmap icon, String title, String desc, String mail, String profile, String profile_photo) {

        User item = new User();


        item.setName(title);
        item.setPhone(desc);
        item.setEmail(mail);
        item.setUser_profile(profile);
        item.setUser_profile_photo(profile_photo);

        listViewItemList.add(0, item);
    }

    public void setFront(Bitmap icon, String title, String desc, String mail, String profile, String profile_photo) {

        User item = new User();
        item.setName(title);
        item.setPhone(desc);
        item.setEmail(mail);
        item.setUser_profile(profile);
        item.setUser_profile_photo(profile_photo);

        listViewItemList.set(0, item);
    }

    public void clear(){
        listViewItemList=new ArrayList<User>();
    }

}
