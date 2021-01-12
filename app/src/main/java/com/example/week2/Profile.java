package com.example.week2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javax.sql.DataSource;

import static com.example.week2.MainActivity.listViewItemList;
import static com.example.week2.MainActivity.testlist;
import static com.example.week2.Page2Fragment.recyclerView;
import static com.example.week2.RecyclerAdapter.listData;

public class Profile extends AppCompatActivity {

    MyGridAdapter adapter = null;
    static ArrayList<GalleryImage> img = new ArrayList<>();
    GridView gv;
    JSONArray posting_list;
    ArrayList<String> str = new ArrayList<String>();

    static Photo data;
    static AlertDialog.Builder builder;
    static AlertDialog dlg;
    static ImageView d_profile;
    static TextView d_names;
    static TextView d_tagged;
    static TextView d_date;
    static TextView d_desc;
    static ImageView d_imageView;
    static ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        Intent intent = getIntent();
        int pos = intent.getExtras().getInt("position");

        User tmp = listViewItemList.get(pos);

        ImageView myphoto = (ImageView) findViewById(R.id.myphoto);
        Glide.with(getBaseContext())
                .asBitmap()
                .load(tmp.getUser_profile_photo())
                .circleCrop()
                .into(myphoto);

        TextView name = (TextView) findViewById(R.id.myname);
        name.setText(tmp.getName());
        TextView profile = (TextView) findViewById(R.id.myprofile);
        profile.setText(tmp.getUser_profile());
        TextView phone = (TextView) findViewById(R.id.myphone);
        phone.setText(tmp.getPhone());
        TextView mail = (TextView) findViewById(R.id.mymail);
        mail.setText(tmp.getEmail());
        gv = (GridView) this.findViewById(R.id.myposting);

        img.clear();
        posting_list = listViewItemList.get(pos).posting;

        str.clear();
        for(int i=0; i<posting_list.length(); i++) {
            try {
                 str.add(posting_list.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        adapter = new MyGridAdapter(
                getApplicationContext(),
                R.layout.row_in_contact,       // GridView 항목의 레이아웃 row.xml
                str);    // 데이터
        adapter.notifyDataSetChanged();

        gv.setAdapter(adapter);  // 커스텀 아답타를 GridView 에 적용
    }

    public class MyGridAdapter extends BaseAdapter {
        Context context;
        int layout;
        ArrayList<String> str;
        LayoutInflater inf;

        public MyGridAdapter(Context context, int layout, ArrayList<String> str) {
            this.context = context;
            this.layout = layout;
            this.str = str;
            inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return str.size();
        }

        public Object getItem(int position) {
            return str.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            imageView = new ImageView(context);

            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getRealSize(size); // or getSize(size)
            int width = size.x;

            width /= 4;
            width -= 20;
            Glide.with(gv)
                    .asBitmap()
                    .load("http://192.249.18.249:3000/" + str.get(position))
                    .centerCrop()
                    .into(imageView);

            imageView.setLayoutParams(new GridView.LayoutParams(width, width));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(7, 7, 7, 7);

            //imageView.setImageBitmap(img.get(position).getD());
            final int pos = position;

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    data = new Photo();
                    forDialog(context, str.get(position));
                }

            });
        return imageView;
        }
    }

    public void forDialog(Context context, String path){
        builder = new AlertDialog.Builder(this);
//        dlg = new Dialog(context);
//        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dlg.setContentView(R.layout.recyclerview_item);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.recyclerview_item, null);
        builder.setView(view);

        d_profile = (ImageView) view.findViewById(R.id.myprofile);
        d_names = (TextView) view.findViewById(R.id.names);
        d_tagged = (TextView) view.findViewById(R.id.names2);
        d_date = (TextView) view.findViewById(R.id.posted_date);
        d_desc = (TextView) view.findViewById(R.id.textView2);
        d_imageView = (ImageView) view.findViewById(R.id.imageView);

        data = new Photo();
        String Url = "http://192.249.18.249:3000/getphoto/";
        NetworkTask networkTask = new NetworkTask(Url, null, "GET", path, context, view);
        networkTask.execute();

    }

    public static class NetworkTask extends AsyncTask<Void, Void, String> {
        private String url;
        private ContentValues values;
        private String method;
        private String path;
        private Context context;
        private View view;

        public NetworkTask(String url, ContentValues values, String method, String path, Context context, View view) {
            this.url = url;
            this.values = values;
            this.method = method;
            this.path = path;
            this.context = context;
            this.view = view;
        }
        @Override
        protected String doInBackground(Void... params) {
            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            if(method == "GET"){
                result = requestHttpURLConnection.request_get(url, values); // 해당 URL로 부터 결과물을 얻어온다.
            }
            else{
                result = requestHttpURLConnection.request_post(url, null); // 해당 URL로 POST 보내기.
            }
            return result;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String getImageurl = "";
            //System.out.println(s);

            if (method == "GET"){
                ArrayList<Photo> tmp = new ArrayList<Photo>();
                try{
                    //Json parsing
                    JSONArray jsonArray = new JSONArray(s);
                    for(int i = 0; i< jsonArray.length();i++) {
                        JSONObject photoObject = jsonArray.getJSONObject(i);
                        Photo posting = new Photo();

                        posting.setExplain(photoObject.getString("explain"));

                        JSONArray userList = new JSONArray();
                        posting.setUserList(userList);

                        getImageurl = "http://192.249.18.249:3000/" + photoObject.getString("server_place");
                        posting.setServer_place(getImageurl);

                        String from = photoObject.getString("time");
                        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date time = transFormat.parse(from);
                        posting.setTime(time);

                        posting.setUserList(photoObject.getJSONArray("userList"));

                        if (posting.getServer_place().equals("http://192.249.18.249:3000/" + path)) {
                            data = posting;
                            break;
                        }
                    }

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
                        String stmp = ja.getString(0);
                        d_names.setText(stmp);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (namestr.length() > 0) d_tagged.setText(namestr);
                    else d_tagged.setText("None");

                    Date from2 = data.getTime();
                    SimpleDateFormat transFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String to = transFormat2.format(from2);
                    d_date.setText(to);

                    d_desc.setText(data.getExplain());
                    Glide.with(context).load(data.getServer_place())
                            .centerCrop()
                            .into(d_imageView);

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
                    Glide.with(context).load(path)
                            .circleCrop()
                            .into(d_profile);


                }catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            else if(method == "POST"){
                if(s == "fail"){
                    //Log.e("fail","fail....");
                }
                else{
                    //Log.e("success",s);
                }
            }
            dlg = builder.create();
            dlg.show();
        }

    }
}
