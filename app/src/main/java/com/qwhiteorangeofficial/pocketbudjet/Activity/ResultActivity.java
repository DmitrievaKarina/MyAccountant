package com.qwhiteorangeofficial.pocketbudjet.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qwhiteorangeofficial.pocketbudjet.Adapter.ResultAdapter;
import com.qwhiteorangeofficial.pocketbudjet.Database.AppDatabase;
import com.qwhiteorangeofficial.pocketbudjet.R;
import com.qwhiteorangeofficial.pocketbudjet.Dao.ResultDao;

public class ResultActivity extends AppCompatActivity {

    ResultDao categoryDao;
    AppDatabase db;

    ResultAdapter mListAdapter;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        db = AppDatabase.getInstance(this.getApplicationContext());
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
