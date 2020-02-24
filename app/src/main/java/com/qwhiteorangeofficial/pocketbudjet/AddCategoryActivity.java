package com.qwhiteorangeofficial.pocketbudjet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;
import java.util.List;

public class AddCategoryActivity extends AppCompatActivity {

    TextView name;
    Spinner debit_credit;
    Long mId;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_category);


        Intent intent = getIntent();
        mId = intent.getLongExtra("Id", 0L);


        name = findViewById(R.id.enter_the_text_category);
        name.setText(intent.getStringExtra("Name"));
        debit_credit = findViewById(R.id.enter_the_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.income_expense, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        debit_credit.setAdapter(adapter);

        int spinnerPosition = adapter.getPosition(intent.getStringExtra("Type"));
        debit_credit.setSelection(spinnerPosition);
    }

    public void create(View view) {
        CategoryDao categoryDao = AppDatabase.getInstance(getApplicationContext()).catDao();
        Category category = new Category();
        category.category_name_entity = name.getText().toString();
        category.category_debit_credit_entity = debit_credit.getSelectedItem().toString();
        categoryDao.insert(category);

        finish();
    }

    public void delete(View view) {
        deleteACategory();

        finish();
    }

    public void deleteACategory() {
        CategoryDao catDao = AppDatabase.getInstance(getApplicationContext()).catDao();
        Category category = catDao.getCategoryById(Long.valueOf(mId.toString()));

        boolean resChecking = checkAllNotes(mId);

        if (resChecking) {
            catDao.delete(category);
        }
        else {
            Toast.makeText(this, R.string.error_deleting_category, Toast.LENGTH_LONG).show();
        }

    }

    public boolean checkAllNotes(Long CatId){
        NoteDao noteDao = AppDatabase.getInstance(getApplicationContext()).noteDao();
        List<Note> list = noteDao.getItemsByIdOfCat(mId);

        return list.isEmpty();
    }
}
