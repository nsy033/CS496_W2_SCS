package com.example.week2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.week2.LogIn.user_name;

public class Page3Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    static ArrayList<PlayList> playLists = new ArrayList<PlayList>();

    private static final String API_KEY = "AIzaSyANYM5TIbJohkt1_z0P48A4WB8IEr2cVe0";
    private static String VIDEO_ID = "STwHSJSA86c";


    public Page3Fragment() {
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
    public static Page3Fragment newInstance(String param1, String param2) {
        Page3Fragment fragment = new Page3Fragment();
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
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        Page3NetworkTask page3NetworkTask = new Page3NetworkTask("", "여기에 user", "여기에 explain", "여기에 time", "GET");
        page3NetworkTask.execute();
        String url = "http://www.youtube.com";

        PlayList song = new PlayList();
        Date date = new Date();
        song.setKeys("Ib5ec71QIc8");
        song.setTime(date);
        song.setExplain("Ed Sheeran - Afterglow");
        playLists.add(song);

        PlayList song2 = new PlayList();
        Date date2 = new Date();
        song2.setKeys("DvC3MdUzjmM");
        song2.setTime(date2);
        song2.setExplain("뉴홉클");

        WebView view = rootView.findViewById(R.id.home_webview);
        view.setWebViewClient(new WebViewClient());
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(url);
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //This is the filter
                if (event.getAction()!=KeyEvent.ACTION_DOWN)
                    return true;

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (view.canGoBack()) {
                        view.goBack();
                    } else {
                        ((MainActivity)getActivity()).onBackPressed();
                    }
                    return true;
                }
                return false;
            }
        });
        ImageButton newPlayList = (ImageButton) rootView.findViewById(R.id.newplaylist);
        newPlayList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LinearLayout linear = (LinearLayout) View.inflate(getActivity(), R.layout.new_playlist_dialog, null);
                AlertDialog.Builder adb = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
                adb.setView(linear)
                        .setTitle("Add new song")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText edt = (EditText) linear.findViewById(R.id.et1);
                                String desc = edt.getText().toString();

                                String str = view.getUrl();
                                String key = "";
                                int index = 0;
                                boolean flag = true;
                                for(int i=str.length()-1; i>=0; i--) {
                                    if(str.charAt(i)=='=' && str.charAt(i-1)=='v') {
                                        index = i+1;
                                        flag = false;
                                        break;
                                    }
                                }
                                if(flag) Toast.makeText(getContext(), "Wrong page", Toast.LENGTH_SHORT).show();
                                else {
                                    if (!str.contains("&t=")) {
                                        key = key + str.substring(index);
                                        System.out.println(key);
                                    } else {
                                        int end = 0;
                                        for (int j = str.length() - 1; j >= 0; j--) {
                                            if (str.charAt(j) == '&' && str.charAt(j + 1) == 't') {
                                                end = j - 1;
                                                break;
                                            }
                                        }
                                        key = str.substring(index, end);
                                    }

                                    Date date = new Date();
                                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    String time = format.format(date);

                                    String name = user_name;

                                    //key, desc, time, name
                                    System.out.println(key + " " + desc + " " + time + " " + name);
                                  
                                    Page3NetworkTask page3NetworkTask = new Page3NetworkTask(key, user_name, "여기에 explain", "여기에 time", "POST");
                                    page3NetworkTask.execute();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = adb.create();
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface arg0) {
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#6E6557"));
                        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#6E6557"));
                    }

                });
                dialog.show();

            }
        });

        ImageButton showPlayList = (ImageButton) rootView.findViewById(R.id.showplaylist);
        showPlayList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Youtube.class);
                startActivity(intent);
            }
        });

        return rootView ;
    }

}
