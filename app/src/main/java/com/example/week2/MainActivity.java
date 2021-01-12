package com.example.week2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    static ArrayList<User> listViewItem = new ArrayList<User>();
    public static String test= "";
    public static ArrayList<User> testlist = new ArrayList<User>();
    public static ArrayList<User> listViewItemList = new ArrayList<User>() ;
    public static ArrayList<Photo> recyclerViewItems = new ArrayList<Photo>();
    Bitmap user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String Url = "http://192.249.18.249:3000/getuser/";
        Page1NetworkTask networkTask = new Page1NetworkTask(Url, null, "GET");
        networkTask.execute();

        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

        Bitmap tmp =  BitmapFactory.decodeResource(getResources(), R.drawable.iconuser);
        user = tmp;
    }

    private long backKeyPressedTime = 0;
    private Toast toast;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 1000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "Press the back key again to exit the app", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 1000) {
            toast.cancel();
            finishAffinity();
        }
    }

}