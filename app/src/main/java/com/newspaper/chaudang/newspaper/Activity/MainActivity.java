package com.newspaper.chaudang.newspaper.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.newspaper.chaudang.newspaper.Fragment.FragmentGiaDinh;
import com.newspaper.chaudang.newspaper.Fragment.FragmentGiaiTri;
import com.newspaper.chaudang.newspaper.Fragment.FragmentGiaoDuc;
import com.newspaper.chaudang.newspaper.Fragment.FragmentGocNhin;
import com.newspaper.chaudang.newspaper.Fragment.FragmentKinhDoanh;
import com.newspaper.chaudang.newspaper.Fragment.FragmentPhapLuat;
import com.newspaper.chaudang.newspaper.Fragment.FragmentSucKhoe;
import com.newspaper.chaudang.newspaper.Fragment.FragmentTheGioi;
import com.newspaper.chaudang.newspaper.Fragment.FragmentTheThao;
import com.newspaper.chaudang.newspaper.Fragment.FragmentThoiSu;
import com.newspaper.chaudang.newspaper.R;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtTitle;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        init();
        actionBar();
        setTabLayout();
        naviListener();

    }

    private void naviListener() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.trangchu:
                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                        break;

                    case R.id.tingui:
                        Intent intenttingui = new Intent(MainActivity.this, GuiTinActivity.class);
                        startActivity(intenttingui);
                        break;

                    case R.id.luutin:
                        Intent intentluutin = new Intent(MainActivity.this, LuuTinActivity.class);
                        startActivity(intentluutin);
                        break;

                    case R.id.gopy:
                        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                        emailIntent.setType("text/plain");
                        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"dangminhchau15@gmail.com"});
                        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
                        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Text");
                        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                        break;

                    case R.id.lienhe:
                        Dialog dialog = new Dialog(MainActivity.this);
                        dialog.setTitle("");
                        dialog.setCancelable(false);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.custom_dialog);
                        TextView txtTitle = dialog.findViewById(R.id.dialogTitle);
                        TextView txtDate = dialog.findViewById(R.id.dialogDate);
                        TextView txtAuthor = dialog.findViewById(R.id.dialogAuthor);
                        TextView txtTel = dialog.findViewById(R.id.dialogTel);
                        TextView txtClose = dialog.findViewById(R.id.dialogClose);

                        txtClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(MainActivity.this, MainActivity.class));
                            }
                        });

                        dialog.show();
                        break;

                }

                drawerLayout.closeDrawer(Gravity.START);
                return false;

            }
        });
    }

    private void setTabLayout() {

        ViewPagerColorAdapter adapter = new ViewPagerColorAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentThoiSu());
        adapter.addFragment(new FragmentGocNhin());
        adapter.addFragment(new FragmentTheGioi());
        adapter.addFragment(new FragmentKinhDoanh());
        adapter.addFragment(new FragmentGiaiTri());
        adapter.addFragment(new FragmentTheThao());
        adapter.addFragment(new FragmentPhapLuat());
        adapter.addFragment(new FragmentGiaoDuc());
        adapter.addFragment(new FragmentSucKhoe());
        adapter.addFragment(new FragmentGiaDinh());

        adapter.getPageTitle(0);
        adapter.getPageTitle(1);
        adapter.getPageTitle(2);
        adapter.getPageTitle(3);
        adapter.getPageTitle(4);
        adapter.getPageTitle(5);
        adapter.getPageTitle(6);
        adapter.getPageTitle(7);
        adapter.getPageTitle(8);
        adapter.getPageTitle(9);

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.button);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.guitinaction) {
            startActivity(new Intent(MainActivity.this, GuiTinActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.myTool);
        drawerLayout = (DrawerLayout) findViewById(R.id.myDrawer);
        navigationView = (NavigationView) findViewById(R.id.myNavi);
        tabLayout = (TabLayout) findViewById(R.id.myTabLayout);
        viewPager = (ViewPager) findViewById(R.id.myViewPager);
    }
}
