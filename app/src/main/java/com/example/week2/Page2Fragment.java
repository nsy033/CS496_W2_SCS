package com.example.week2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.week2.MainActivity.testlist;
import static java.sql.DriverManager.println;

public class Page2Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    static RecyclerAdapter adapter = null;
    static RecyclerView recyclerView = null;


    public Page2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Page1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Page2Fragment newInstance(String param1, String param2) {
        Page2Fragment fragment = new Page2Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String Url = "http://192.249.18.249:3000/getphoto/";
        NetworkTask networkTask = new NetworkTask(Url, null, "GET");
        networkTask.execute();
        View view = inflater.inflate(R.layout.page2fragment, null);
        Intent intent = new Intent(getActivity(), AddImageActivity.class);
        recyclerView = view.findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerAdapter();

        ImageButton imgAddButton =  view.findViewById(R.id.addImageButton);
        imgAddButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
        /*ArrayList<RecyclerViewItem> list = networkTask.recyclerViewItems;
        adapter.addItem(list.get(0));
        adapter.addItem(list.get(1));*/

        recyclerView.setAdapter(adapter);


        return view;
    }
    public static class NetworkTask extends AsyncTask<Void, Void, String> {
        private String url;
        private ContentValues values;
        private String method;
        static ArrayList<RecyclerViewItem> recyclerViewItems = new ArrayList<RecyclerViewItem>();

        public NetworkTask(String url, ContentValues values, String method) {
            this.url = url;
            this.values = values;
            this.method = method;
        }
        @Override
        protected String doInBackground(Void... params) {
            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            if(method == "GET"){
                result = requestHttpURLConnection.request_get(url, values); // 해당 URL로 부터 결과물을 얻어온다.
            }
            else{
                result = requestHttpURLConnection.request_post(url, values); // 해당 URL로 POST 보내기.
            }
            return result;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String getImageurl = "";
            //System.out.println(s);
            if (method == "GET"){
                try{
                    //Json parsing

                    JSONArray jsonArray = new JSONArray(s);

                    for(int i = 0; i< jsonArray.length();i++){
                        JSONObject photoObject = jsonArray.getJSONObject(i);
                        RecyclerViewItem posting = new RecyclerViewItem();

                        posting.setContent(photoObject.getString("explain"));
                        posting.setTitle(photoObject.getString("userList"));

                        getImageurl = "http://192.249.18.249:3000/" + photoObject.getString("server_place");
                        adapter.addItem(posting);
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
               // ImageLoadTask task = new ImageLoadTask(getImageurl,recyclerViewImageView);
               // task.execute();
                recyclerView.setAdapter(adapter);
//            System.out.println("CHECK : " + contactItems.size());
//            for(int i = 0; i< contactItems.size() ; i++){
//                Bitmap sampleBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.person);
//                ContactItem ci = contactItems.get(i);
//                adapter.addItem(sampleBitmap, ci.getUser_name(), ci.getUser_phNumber(),
//                        ci.getMail(), "sample address");
//            }
//            listview.setAdapter(adapter);
            }
            else if(method == "POST"){
                if(s == "fail"){
                    //Log.e("fail","fail....");
                }
                else{
                    //Log.e("success",s);
                }
            }
        }
    }

}



