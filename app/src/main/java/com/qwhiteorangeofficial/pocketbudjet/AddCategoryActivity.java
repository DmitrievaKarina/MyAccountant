package com.qwhiteorangeofficial.pocketbudjet;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AddCategoryActivity extends AppCompatActivity {

    TextView name;
    Spinner debit_credit;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_category);

        name = findViewById(R.id.enter_the_text_category);
        debit_credit = findViewById(R.id.enter_the_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.income_expense, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        debit_credit.setAdapter(adapter);
    }

    public void create(View view) {
        CategoryDao categoryDao = AppDatabase.getInstance(getApplicationContext()).catDao();
        Category category = new Category();
        category.category_name_entity = name.getText().toString();
        category.category_debit_credit_entity = debit_credit.getSelectedItem().toString();
        categoryDao.insert(category);

        finish();
    }
}
