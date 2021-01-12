package com.example.week2;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static com.example.week2.MainActivity.listViewItemList;
import static com.example.week2.MainActivity.test;
import static com.example.week2.MainActivity.testlist;
import static com.example.week2.Page2Fragment.recyclerView;

public class Page1Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static int ChangeFlag = 1;
    static String username;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    MenuItem mSearch;
    static ListView listview = null;
    static ArrayList<User> listViewItem = new ArrayList<User>();
    static ListViewAdapter adapter = new ListViewAdapter();

    public Page1Fragment() {
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
    public static Page1Fragment newInstance(String param1, String param2) {
        Page1Fragment fragment = new Page1Fragment();
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

        View view = inflater.inflate(R.layout.page1fragment, null);
        setHasOptionsMenu(true);

        listview = (ListView) view.findViewById(R.id.listview);

        String Url = "http://192.249.18.249:3000/getuser/";


        if(ChangeFlag == 1){
            Page1NetworkTask networkTask = new Page1NetworkTask(Url, null, "GET");
            networkTask.execute();
            ChangeFlag = 0;
        }
        else{
            User tmpuser = listViewItemList.get(0);
            adapter.setFront(null,tmpuser.getName(), tmpuser.getPhone(), tmpuser.getEmail(),tmpuser.getUser_profile(), "http://192.249.18.249:3000/"+tmpuser.getUser_profile_photo() );
            adapter.notifyDataSetChanged();
            listview.setAdapter(adapter);
        }


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position > 0) {
                    Intent intent = new Intent(getActivity(), Profile.class);
                    intent.putExtra("position", position);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(getActivity(), EditProfile.class);
                    startActivity(intent);
                }
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment1_menu, menu);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) menu.findItem(R.id.search).getActionView();
        ArrayList<User> tmpList = new ArrayList<User>();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(newText.length()>0) {
                    tmpList.clear();
                    for (int i = 0; i < testlist.size(); i++) {
                        if (testlist.get(i).getName().toLowerCase().contains(newText)) {
                            tmpList.add(testlist.get(i));
                        }
                    }
                    listViewItemList = tmpList;
                    adapter.notifyDataSetChanged();
                    return false;
                }
                else{
                    listViewItemList = testlist;
                    adapter.notifyDataSetChanged();
                }
                return false;
            }

        });
    }

}
