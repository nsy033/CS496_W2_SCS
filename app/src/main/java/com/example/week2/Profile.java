package com.example.week2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;

import static com.example.week2.MainActivity.testlist;

public class Profile extends AppCompatActivity {

    static MyGridAdapter adapter = null;
    static ArrayList<GalleryImage> img = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        Intent intent = getIntent();
        int pos = intent.getExtras().getInt("position");

        TextView tx1 = (TextView) findViewById(R.id.textView1);
        tx1.setText(testlist.get(pos).getTitle());
        TextView tx2 = (TextView) findViewById(R.id.textView2);
        tx2.setText(testlist.get(pos).getDesc());

        for(int i=0;i<3;i++) {
            GalleryImage gi = new GalleryImage();
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.iconuser);
            gi.setD(bitmap);
            img.add(gi);
        }

        adapter = new MyGridAdapter(
                getApplicationContext(),
                R.layout.row_in_contact,       // GridView 항목의 레이아웃 row.xml
                img);    // 데이터
        adapter.notifyDataSetChanged();

        GridView gv = (GridView) this.findViewById(R.id.gridView1);
        gv.setAdapter(adapter);  // 커스텀 아답타를 GridView 에 적용
    }

    public class MyGridAdapter extends BaseAdapter {
        Context context;
        int layout;
        ArrayList<GalleryImage> img;
        LayoutInflater inf;

        public MyGridAdapter(Context context, int layout, ArrayList<GalleryImage> img) {
            this.context = context;
            this.layout = layout;
            this.img = img;
            inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return img.size();
        }

        public Object getItem(int position) {
            return img.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            ImageView imageView = new ImageView(context);

            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getRealSize(size); // or getSize(size)
            int width = size.x;

            width /= 5;
            width -= 30;

            imageView.setLayoutParams(new GridView.LayoutParams(width, width));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(5, 5, 5, 5);

            imageView.setImageBitmap(img.get(position).getD());
            final int pos = position;

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            return imageView;
        }

    }
}
