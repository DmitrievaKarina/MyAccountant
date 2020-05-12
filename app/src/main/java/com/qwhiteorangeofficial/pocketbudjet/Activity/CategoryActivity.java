package com.qwhiteorangeofficial.pocketbudjet.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qwhiteorangeofficial.pocketbudjet.Adapter.CategoryAdapter;
import com.qwhiteorangeofficial.pocketbudjet.Database.AppDatabase;
import com.qwhiteorangeofficial.pocketbudjet.Dao.CategoryDao;
import com.qwhiteorangeofficial.pocketbudjet.R;
import com.qwhiteorangeofficial.pocketbudjet.databinding.ActivityCategoryBinding;

public class CategoryActivity extends AppCompatActivity {

    CategoryDao categoryDao;

    CategoryAdapter mListAdapter;
    RecyclerView mRecyclerView;
    ActivityCategoryBinding mCategoryBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategoryBinding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(mCategoryBinding.getRoot());

        categoryDao = AppDatabase.getInstance(getApplicationContext()).catDao();

        mRecyclerView = findViewById(R.id.list_of_categories);
        mListAdapter = new CategoryAdapter(categoryDao.getAllCategories());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mCategoryBinding.listOfCategories.setLayoutManager(linearLayoutManager);
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
