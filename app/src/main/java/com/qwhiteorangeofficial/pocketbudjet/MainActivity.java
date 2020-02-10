package com.qwhiteorangeofficial.pocketbudjet;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    NoteAdapter mListAdapter;
    RecyclerView mRecyclerView;
    NoteDao noteDao;
    CategoryDao categoryDao;
    ResultDao resultDao;
    AppDatabase db;
    Calendar dateAndTime = Calendar.getInstance();
    Button datePick;
    TextView currentDate;
    TextView currentDebit;
    TextView currentCredit;

    @Override
    protected void onResume() {
        super.onResume();

        mRecyclerView = findViewById(R.id.list_of_notes);
        mListAdapter = new NoteAdapter(db.noteDao().getItemsByDate(dateAndTime.getTimeInMillis()));
        mRecyclerView.setAdapter(mListAdapter);

        setInitialDate();
        setDebitCredit();


       }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = findViewById(R.id.list_of_notes);
        datePick = findViewById(R.id.select);
        currentDate = findViewById(R.id.date);

        currentCredit = findViewById(R.id.credit_per_day);
        currentDebit = findViewById(R.id.debit_per_day);


        db = AppDatabase.getInstance(getApplicationContext());
        noteDao = db.noteDao();
        categoryDao = db.catDao();
        resultDao = db.resDao();

        setInitialDate();
        setDebitCredit();

        mRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInitialDate();
            }
        });
    }
    public void setDate(View view) {
        Long mills;
        if (view.getId() == R.id.next)
        {
            mills = dateAndTime.getTimeInMillis() + 86400000;
        }
        else
        {
            mills = dateAndTime.getTimeInMillis() - 86400000;
        }
        dateAndTime.setTimeInMillis(mills);
        currentDate.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));

        dateAndTime.set(Calendar.MILLISECOND,0);
        Date mDate = new Date(dateAndTime.getTimeInMillis());
        mDate.setHours(0);
        mDate.setMinutes(0);
        mDate.setSeconds(0);

        mListAdapter = new NoteAdapter(db.noteDao().getItemsByDate(mDate.getTime()));
        mRecyclerView.setAdapter(mListAdapter);
        setDebitCredit();
    }


    public void selectDate(View v) {
        new DatePickerDialog(MainActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();

        dateAndTime.set(Calendar.MILLISECOND,0);
        Date mDate = new Date(dateAndTime.getTimeInMillis());
        mDate.setHours(0);
        mDate.setMinutes(0);
        mDate.setSeconds(0);

        mListAdapter = new NoteAdapter(db.noteDao().getItemsByDate(mDate.getTime()));
        mRecyclerView.setAdapter(mListAdapter);
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDate();
        }
    };


    // установка начальных даты и времени
    private void setInitialDate() {

        currentDate.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));

        dateAndTime.set(Calendar.MILLISECOND,0);
        Date mDate = new Date(dateAndTime.getTimeInMillis());
        mDate.setHours(0);
        mDate.setMinutes(0);
        mDate.setSeconds(0);

        mListAdapter = new NoteAdapter(db.noteDao().getItemsByDate(mDate.getTime()));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mListAdapter);

    }

    //
    private void setDebitCredit() {
        dateAndTime.set(Calendar.MILLISECOND,0);
        Date mDate = new Date(dateAndTime.getTimeInMillis());
        mDate.setHours(0);
        mDate.setMinutes(0);
        mDate.setSeconds(0);

        ResultDay resultDay = resultDao.getObjectByDate(mDate.getTime());

        try {
            if (resultDay.result_day_income_entity == 0f)
            {
                currentDebit.setText(R.string.default_for_result);
            }
            else
            {
                currentDebit.setText(getResources().getStringArray(R.array.income_expense)[0]+"\n"+String.valueOf(resultDay.result_day_income_entity));
            }
            if (resultDay.result_day_expense_entity == 0f)
            {
                currentCredit.setText(R.string.default_for_result);
            }
            else
            {
                currentCredit.setText(getResources().getStringArray(R.array.income_expense)[1] + "\n" + String.valueOf(resultDay.result_day_expense_entity));
            }
        }
        catch(Exception e){
            currentDebit.setText(R.string.default_for_result);
            currentCredit.setText(R.string.default_for_result);
            Log.e("Error", e.getMessage());
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.list_of_categories) {
            Intent intent = new Intent(this, CategoryActivity.class);
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
        startActivity(intent);
        setDebitCredit();
    }
}
