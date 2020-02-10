package com.qwhiteorangeofficial.pocketbudjet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryActivity extends AppCompatActivity {

    CategoryDao categoryDao;
    AppDatabase db;

    CategoryAdapter mListAdapter;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        db = AppDatabase.getInstance(getApplicationContext());
        categoryDao = db.catDao();

        mRecyclerView = findViewById(R.id.list_of_categories);
        mListAdapter = new CategoryAdapter(categoryDao.getAllCategories());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mListAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mListAdapter = new CategoryAdapter(categoryDao.getAllCategories());
        mRecyclerView.setAdapter(mListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mListAdapter = new CategoryAdapter(categoryDao.getAllCategories());
        mRecyclerView.setAdapter(mListAdapter);
    }

    public void addCategory(View view) {
        Intent intent = new Intent(this, AddCategoryActivity.class);
        startActivity(intent);
    }
}
