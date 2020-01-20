package com.qwhiteorangeofficial.myaccountant;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    NoteAdapter mListAdapter;
    RecyclerView mRecyclerView;
    NoteDao noteDao;
    CategoryDao categoryDao;
    AppDatabase db;
    Calendar dateAndTime = Calendar.getInstance();
    Button datePick;
    TextView currentDate;

    @Override
    protected void onResume() {
        super.onResume();

        mListAdapter = new NoteAdapter(db.noteDao().getAllNotes());
        mRecyclerView.setAdapter(mListAdapter);

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

        db = AppDatabase.getInstance(getApplicationContext());
        noteDao = db.noteDao();
        categoryDao = db.catDao();

        setInitialDate();


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

        mListAdapter = new NoteAdapter(db.noteDao().getItemsByDate(dateAndTime.getTimeInMillis()));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mListAdapter);
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

        return super.onOptionsItemSelected(item);
    }

    public void addNote(View view) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }
}
