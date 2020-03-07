package com.qwhiteorangeofficial.pocketbudjet.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.qwhiteorangeofficial.pocketbudjet.Database.AppDatabase;
import com.qwhiteorangeofficial.pocketbudjet.Dao.CategoryDao;
import com.qwhiteorangeofficial.pocketbudjet.Entity.CategoryEntity;
import com.qwhiteorangeofficial.pocketbudjet.Entity.Note;
import com.qwhiteorangeofficial.pocketbudjet.Dao.NoteDao;
import com.qwhiteorangeofficial.pocketbudjet.R;
import com.qwhiteorangeofficial.pocketbudjet.databinding.AddCategoryBinding;

import java.util.List;

public class AddCategoryActivity extends AppCompatActivity {

    Long mId;
    AddCategoryBinding mAddCategoryBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddCategoryBinding = AddCategoryBinding.inflate(getLayoutInflater());
        setContentView(mAddCategoryBinding.getRoot());


        Intent intent = getIntent();
        mId = intent.getLongExtra("Id", 0L);


        mAddCategoryBinding.enterTheTextCategory.setText(intent.getStringExtra("Name"));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.income_expense, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAddCategoryBinding.enterTheType.setAdapter(adapter);

        int spinnerPosition = adapter.getPosition(intent.getStringExtra("Type"));
        mAddCategoryBinding.enterTheType.setSelection(spinnerPosition);

        if (mId == 0L) {
            mAddCategoryBinding.btnDelete.setVisibility(View.GONE);
            mAddCategoryBinding.existOrNot.setText(R.string.text_creating_category);
        } else {
            mAddCategoryBinding.btnDelete.setVisibility(View.VISIBLE);
            mAddCategoryBinding.existOrNot.setText(R.string.text_editing_category);

        }
    }

    public void create(View view) {
        if (checkForFilling()) {
            CategoryDao categoryDao = AppDatabase.getInstance(getApplicationContext()).catDao();
            CategoryEntity category = new CategoryEntity();
            category.category_name_entity = mAddCategoryBinding.enterTheTextCategory.getText().toString();
            category.category_debit_credit_entity = mAddCategoryBinding.enterTheType.getSelectedItem().toString();
            categoryDao.insert(category);

            finish();
        }
    }

    public boolean checkForFilling() {
        if (mAddCategoryBinding.enterTheTextCategory.getText().toString().equals("")) {
            Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void delete(View view) {
        deleteACategory();

        finish();
    }

    public void deleteACategory() {
        CategoryDao catDao = AppDatabase.getInstance(getApplicationContext()).catDao();
        CategoryEntity category = catDao.getCategoryById(Long.valueOf(mId.toString()));

        boolean resChecking = checkAllNotes();

        if (resChecking) {
            catDao.delete(category);
        } else {
            Toast.makeText(this, R.string.error_deleting_category, Toast.LENGTH_LONG).show();
        }

    }

    public boolean checkAllNotes() {
        NoteDao noteDao = AppDatabase.getInstance(getApplicationContext()).noteDao();
        List<Note> list = noteDao.getItemsByIdOfCat(mId);

        return list.isEmpty();
    }
}
