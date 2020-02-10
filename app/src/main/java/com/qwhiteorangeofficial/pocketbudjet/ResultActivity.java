package com.qwhiteorangeofficial.pocketbudjet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ResultActivity extends AppCompatActivity {

    ResultDao categoryDao;
    AppDatabase db;

    ResultAdapter mListAdapter;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        db = AppDatabase.getInstance(getApplicationContext());
        categoryDao = db.resDao();

        mRecyclerView = findViewById(R.id.list_of_results);
        mListAdapter = new ResultAdapter(categoryDao.getAllResults());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mListAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mListAdapter = new ResultAdapter(categoryDao.getAllResults());
        mRecyclerView.setAdapter(mListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mListAdapter = new ResultAdapter(categoryDao.getAllResults());
        mRecyclerView.setAdapter(mListAdapter);
    }

}
