package com.example.bai5nguyentatthang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import com.example.bai5nguyentatthang.model.Order;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView revView;
    private RecyclerViewAdapter adapter;
    private FloatingActionButton fabAdd;
    private Button btnGetAll;
    private SQLiteHelper sqlHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        adapter = new RecyclerViewAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        revView.setLayoutManager(manager);
        revView.setAdapter(adapter);
        sqlHelper = new SQLiteHelper(this);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAdd = new Intent(MainActivity.this, ActivityAdd.class);
                startActivity(intentAdd);
            }
        });

        btnGetAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Order> list =  sqlHelper.getAll();
                adapter.setListOrder(list);
                revView.setAdapter(adapter);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search,menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.mSearch).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                List<Order> list = sqlHelper.getByName(newText);
                adapter.setListOrder(list);
                revView.setAdapter(adapter);
                return true;
            }
        });
        return true;
    }

    @Override
    protected void onResume() {
        List<Order> list =  sqlHelper.getAll();
        adapter.setListOrder(list);
        revView.setAdapter(adapter);
        super.onResume();
    }

    private void init() {
        revView = findViewById(R.id.revViewMain);
        fabAdd = findViewById(R.id.btnAddMain);
        btnGetAll = findViewById(R.id.btnGetAll);
    }
}