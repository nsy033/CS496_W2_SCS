package com.example.week2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static com.example.week2.MainActivity.test;
import static com.example.week2.Page2Fragment.recyclerView;
import static com.example.week2.MainActivity.testlist;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {

    // adapter에 들어갈 list 입니다.
    //private ArrayList<Photo> listData = new ArrayList<>();
    public static ArrayList<Photo> listData = new ArrayList<>();

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size();
    }

    void addItem(Photo data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView profile;
        private TextView names;
        private TextView tagged;
        private TextView date;
        private TextView desc;
        private ImageView imageView;

        ItemViewHolder(View itemView) {
            super(itemView);

            profile = itemView.findViewById(R.id.myprofile);
            names = itemView.findViewById(R.id.names);
            tagged = itemView.findViewById(R.id.names2);
            date = itemView.findViewById(R.id.posted_date);
            desc = itemView.findViewById(R.id.textView2);
            imageView = itemView.findViewById(R.id.imageView);
        }

        void onBind(Photo data) {

            JSONArray ja = data.getUserList();
            String namestr = "";
            for (int i = 1; i < ja.length(); i++) {
                try {
                    namestr = namestr + ja.getString(i);
                    if (i < ja.length() - 1) namestr = namestr + " ";
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            try {
                names.setText(ja.getString(0));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (namestr.length() > 0) tagged.setText(namestr);
            else tagged.setText("None");

            Date from = data.getTime();
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String to = transFormat.format(from);
            date.setText(to);

            desc.setText(data.getExplain());
            Glide.with(recyclerView).load(data.getServer_place())
                    .centerCrop()
                    .into(this.imageView);

            try {
                String owner = ja.getString(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String path = "";
            for (int i = 0; i < testlist.size(); i++) {
                try {
                    if (testlist.get(i).getName().equals(ja.getString(0))) {
                        path = testlist.get(i).getUser_profile_photo();
                        break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            Glide.with(recyclerView).load(path)
                    .circleCrop()
                    .into(this.profile);
        }
    }
}