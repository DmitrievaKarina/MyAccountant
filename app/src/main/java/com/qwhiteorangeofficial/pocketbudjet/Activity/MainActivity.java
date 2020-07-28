package com.qwhiteorangeofficial.pocketbudjet.Activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.qwhiteorangeofficial.pocketbudjet.Adapter.NoteAdapter;
import com.qwhiteorangeofficial.pocketbudjet.Database.AppDatabase;
import com.qwhiteorangeofficial.pocketbudjet.Dao.CategoryDao;
import com.qwhiteorangeofficial.pocketbudjet.Dao.NoteDao;
import com.qwhiteorangeofficial.pocketbudjet.Dao.ResultDao;
import com.qwhiteorangeofficial.pocketbudjet.Entity.CategoryEntity;
import com.qwhiteorangeofficial.pocketbudjet.Entity.ResultDay;
import com.qwhiteorangeofficial.pocketbudjet.R;
import com.qwhiteorangeofficial.pocketbudjet.Utils.Categories;
import com.qwhiteorangeofficial.pocketbudjet.databinding.ActivityMainBinding;

import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    NoteAdapter mListAdapter;
    Calendar dateAndTime = Calendar.getInstance();

    ActivityMainBinding mActivityMainBinding;

    SharedPreferences sp;

    NoteDao noteDao;
    CategoryDao categoryDao;
    ResultDao resultDao;
    AppDatabase db;

    @Override
    protected void onResume() {
        super.onResume();

        setListAdapter();
        initializeDbAndDao();
        setInitialDate();
        setDebitCredit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mActivityMainBinding.getRoot());
        setSupportActionBar(mActivityMainBinding.toolbar);
        initializeDbAndDao();
        setInitialDate();
        setDebitCredit();
        mActivityMainBinding.listOfNotes.setOnClickListener(v -> setInitialDate());
        checkFirstLaunch();
    }

    public void initializeDbAndDao() {
        db = AppDatabase.getInstance(this.getApplicationContext());
        noteDao = db.noteDao();
        categoryDao = db.catDao();
        resultDao = db.resDao();
    }

    public void checkFirstLaunch() {
        sp = getSharedPreferences("my_settings",
                Context.MODE_PRIVATE);
        // check for first launch
        boolean hasVisited = sp.getBoolean("firstLaunch", false);

        if (!hasVisited) {
            SharedPreferences.Editor e = sp.edit();
            e.putBoolean("firstLaunch", true);
            e.apply();//save changes

            //add several items to list of category
            for (String cat : getResources().getStringArray(R.array.categories_default_expenses)) {
                CategoryEntity category = new CategoryEntity();
                category.category_name_entity = cat;
                category.category_debit_credit_entity = getResources().getStringArray(R.array.income_expense)[1];
                categoryDao.insert(category);
            }
            for (String cat : getResources().getStringArray(R.array.categories_default_incomes)) {
                CategoryEntity category = new CategoryEntity();
                category.category_name_entity = cat;
                category.category_debit_credit_entity = getResources().getStringArray(R.array.income_expense)[0];
                categoryDao.insert(category);
            }


        }
    }

    public void setDate(View view) {
        long mills;
        if (view.getId() == R.id.next) {
            mills = dateAndTime.getTimeInMillis() + 86400000;
        } else {
            mills = dateAndTime.getTimeInMillis() - 86400000;
        }
        dateAndTime.setTimeInMillis(mills);
        mActivityMainBinding.date.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));

        setListAdapter();
        setDebitCredit();
    }

    /**
     * to show|hidden filter
     * @param v
     */
    public void showFilter(View v) {
        sp = getSharedPreferences("my_settings",
                Context.MODE_PRIVATE);
        boolean showFilter = sp.getBoolean("showFilter", false);
        if (showFilter) {
            showFilter = false;
            mActivityMainBinding.showFilter.setBackground(getResources().getDrawable(R.drawable.cursors_up));
            mActivityMainBinding.filter.setVisibility(View.VISIBLE);
        }
        else {
            showFilter = true;
            mActivityMainBinding.showFilter.setBackground(getResources().getDrawable(R.drawable.cursors_down));
            mActivityMainBinding.filter.setVisibility(View.GONE);
        }
        SharedPreferences.Editor e = sp.edit();
        e.putBoolean("showFilter", showFilter);
        e.apply();//save changes
    }

    public void checkFilter(View v) {
        if (mActivityMainBinding.checkboxExpenses.isChecked() && mActivityMainBinding.checkboxIncomes.isChecked())
            setListAdapter();
        else if (mActivityMainBinding.checkboxIncomes.isChecked())
            setListAdapterWithFilter(Categories.INCOME);
        else if (mActivityMainBinding.checkboxExpenses.isChecked())
            setListAdapterWithFilter(Categories.EXPENSE);
        else
            setListAdapterWithFilter(Categories.NONE);
    }

    /**
     * "Select date" button listener
     */
    public void selectDate(View v) {
        new DatePickerDialog(MainActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();

        setListAdapter();
    }

    DatePickerDialog.OnDateSetListener d = (view, year, monthOfYear, dayOfMonth) -> {
        dateAndTime.set(Calendar.YEAR, year);
        dateAndTime.set(Calendar.MONTH, monthOfYear);
        dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        setInitialDate();
    };


    /**
     * setup starting date
     */
    private void setInitialDate() {
        mActivityMainBinding.date.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));

        setListAdapter();
    }

    private void setListAdapter() {
        setListAdapterWithFilter(Categories.ALL);
    }

    private void setListAdapterWithFilter(Categories currentCategory) {
        resetTime();
        if (currentCategory == Categories.NONE)
            mListAdapter = new NoteAdapter(Collections.emptyList());
        else if (currentCategory == Categories.ALL)
            mListAdapter = new NoteAdapter(db.noteDao().getItemsByDate(dateAndTime.getTimeInMillis()));
        else if (currentCategory == Categories.INCOME)
            mListAdapter = new NoteAdapter(db.noteDao().getItemsByDate(dateAndTime.getTimeInMillis(), 0L));
        else if (currentCategory == Categories.EXPENSE)
            mListAdapter = new NoteAdapter(db.noteDao().getItemsByDate(dateAndTime.getTimeInMillis(), 1L));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityMainBinding.listOfNotes.setLayoutManager(linearLayoutManager);
        mActivityMainBinding.listOfNotes.setAdapter(mListAdapter);
    }

    private void resetTime() {
        dateAndTime.set(Calendar.MILLISECOND, 0);
        dateAndTime.set(Calendar.SECOND, 0);
        dateAndTime.set(Calendar.MINUTE, 0);
        dateAndTime.set(Calendar.HOUR, 0);
    }

    private void setDebitCredit() {
        resetTime();

        ResultDay resultDay = resultDao.getObjectByDate(dateAndTime.getTimeInMillis());
        try {
            if (resultDay.result_day_income_entity == 0f) {
                mActivityMainBinding.incomePerDayValue.setText(R.string.label_default_for_result);
            } else {
                mActivityMainBinding.incomePerDayValue.setText(String.valueOf(resultDay.result_day_income_entity));
            }
            if (resultDay.result_day_expense_entity == 0f) {
                mActivityMainBinding.expensePerDayValue.setText(R.string.label_default_for_result);
            } else {
                mActivityMainBinding.expensePerDayValue.setText(String.valueOf(resultDay.result_day_expense_entity));
            }
        } catch (Exception e) {
            mActivityMainBinding.incomePerDayValue.setText(R.string.label_default_for_result);
            mActivityMainBinding.expensePerDayValue.setText(R.string.label_default_for_result);
            Log.e("Error", Objects.requireNonNull(e.getMessage()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.list_of_categories) {
            Intent intent = new Intent(this, CategoryActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.reports) {
            Intent intent = new Intent(this, ReportActivity.class);
            startActivity(intent);
        }
//        else if (id == R.id.list_of_result) {
//            Intent intent = new Intent(this, ResultActivity.class);
//            startActivity(intent);
//        }

        return super.onOptionsItemSelected(item);
    }

    public void addNote(View view) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        intent.putExtra("Time", dateAndTime.getTimeInMillis());
        startActivity(intent);
        setDebitCredit();
    }
}
