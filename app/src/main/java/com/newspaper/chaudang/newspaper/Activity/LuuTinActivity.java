package com.newspaper.chaudang.newspaper.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.newspaper.chaudang.newspaper.Adapter.ThoiSuAdapter;
import com.newspaper.chaudang.newspaper.Model.ThoiSu;
import com.newspaper.chaudang.newspaper.R;

import java.util.ArrayList;

public class LuuTinActivity extends AppCompatActivity {

    private ListView lv;
    ThoiSu thoiSu = new ThoiSu();
    private String tenTin = thoiSu.getTenThoiSu();

    private ArrayList<ThoiSu> arrayTinLuu = new ArrayList<>();
    private ThoiSuAdapter adapter;
    private Toolbar toolbar;
    private DatabaseReference mDatabase;
    private EditText edtSearch;
    private Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luu_tin);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        init();
        actionBar();


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(LuuTinActivity.this, WebViewThoiSu.class);
                intent.putExtra("linkThoiSu", arrayTinLuu.get(i).getLink());
                startActivity(intent);
            }
        });

        mDatabase.child("TinLưu").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                ThoiSu thoisu = dataSnapshot.getValue(ThoiSu.class);

                arrayTinLuu.add(new ThoiSu(thoisu.getTenThoiSu(), thoisu.getHinhanh(), thoisu.getLink()));
                adapter = new ThoiSuAdapter(getApplicationContext(), arrayTinLuu);
                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Lưu tin");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.left);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.ToolLuuTin);
        lv = (ListView) findViewById(R.id.listLuuTin);
    }
}
