package com.qwhiteorangeofficial.myaccountant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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
        mRecyclerView.setAdapter(mListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mListAdapter = new CategoryAdapter(categoryDao.getAllCategories());
        mRecyclerView.setAdapter(mListAdapter);

        Toast.makeText(this, String.valueOf(categoryDao.getAllCategories().size()), Toast.LENGTH_SHORT).show();
    }

    public void addCategory(View view) {
        Intent intent = new Intent(this, AddCategoryActivity.class);
        startActivity(intent);
    }
}
