package com.qwhiteorangeofficial.pocketbudjet.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.qwhiteorangeofficial.pocketbudjet.Database.AppDatabase;
import com.qwhiteorangeofficial.pocketbudjet.Adapter.CustomSpinnerAdapter;
import com.qwhiteorangeofficial.pocketbudjet.Dao.ResultDao;
import com.qwhiteorangeofficial.pocketbudjet.Entity.Note;
import com.qwhiteorangeofficial.pocketbudjet.Dao.NoteDao;
import com.qwhiteorangeofficial.pocketbudjet.Entity.ResultDay;
import com.qwhiteorangeofficial.pocketbudjet.R;
import com.qwhiteorangeofficial.pocketbudjet.databinding.AddNoteBinding;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.view.View.NO_ID;

public class AddNoteActivity extends AppCompatActivity {

    Long mId;

    Calendar dateAndTime = Calendar.getInstance();

    CustomSpinnerAdapter mCustomSpinnerAdapter;
    ArrayAdapter<String> mAdapter;
    List<String> listCategory;
    Long id_categ;
    Long gettingTime;
    AddNoteBinding mAddNoteBinding;

    @Override
    protected void onResume() {
        super.onResume();


        listCategory = AppDatabase.getInstance(getApplicationContext()).catDao().getAllCategoriesAsMassiv();
//        mCustomSpinnerAdapter = new CustomSpinnerAdapter(this, android.R.layout.simple_spinner_item, listCategory);
//        mCustomSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        mAddNoteBinding.pickTheCategoryNote.setAdapter(mCustomSpinnerAdapter);

        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listCategory);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAddNoteBinding.pickTheCategoryNote.setAdapter(mAdapter);

        Intent intent = getIntent();
        mId = intent.getLongExtra("Id", 0L);

        mAddNoteBinding.pickTheCategoryNote.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                CustomSpinnerAdapter.flag = mId != 0L;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        id_categ = intent.getLongExtra("Category", 0L);
        if (id_categ != 0L) {
            int spinnerPosition = mAdapter.getPosition(AppDatabase.getInstance(this).catDao().getNameById(id_categ));
            mAddNoteBinding.pickTheCategoryNote.setSelection(spinnerPosition);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddNoteBinding = AddNoteBinding.inflate(getLayoutInflater());
        setContentView(mAddNoteBinding.getRoot());

        listCategory = AppDatabase.getInstance(getApplicationContext()).catDao().getAllCategoriesAsMassiv();

//        mCustomSpinnerAdapter = new CustomSpinnerAdapter(this, android.R.layout.simple_spinner_item, listCategory);
//        mCustomSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        mAddNoteBinding.pickTheCategoryNote.setAdapter(mCustomSpinnerAdapter);

        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listCategory);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAddNoteBinding.pickTheCategoryNote.setAdapter(mAdapter);

        Intent intent = getIntent();
        mId = intent.getLongExtra("Id", 0L);

        mAddNoteBinding.pickTheCategoryNote.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CustomSpinnerAdapter.flag = mId != 0L;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //read text, sum and date for an existing note and setup into fitting views
        mAddNoteBinding.enterTheTextNote.setText(intent.getStringExtra("Name"));
        float gettingSum = intent.getFloatExtra("Sum", 0);
        if (gettingSum != 0f) {
            mAddNoteBinding.sumNote.setText(String.valueOf(gettingSum));
        }
        gettingTime = intent.getLongExtra("Time", 0L);
        if (gettingTime != 0L) {
            mAddNoteBinding.enterTheDateNote.setText(DateUtils.formatDateTime(this, gettingTime,
                    DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
        }

        //read category for an existing note and setup info into the spinner
        id_categ = intent.getLongExtra("Category", 0L);
        if (id_categ != 0L) {
            int spinnerPosition = mAdapter.getPosition(AppDatabase.getInstance(this).catDao().getNameById(id_categ));
            mAddNoteBinding.pickTheCategoryNote.setSelection(spinnerPosition);
        }

        //hiding button "Delete" for a new note
        if (mId == 0L) {
            mAddNoteBinding.deleteNote.setVisibility(View.GONE);
            mAddNoteBinding.existOrNot.setText(R.string.text_creating_note);
        } else {
            mAddNoteBinding.deleteNote.setVisibility(View.VISIBLE);
            mAddNoteBinding.existOrNot.setText(R.string.text_editing_note);

        }

    }

    /**
     * when presses a button "Pick a date"
     *
     * @param view standard param
     */
    public void pickDate(View view) {
        new DatePickerDialog(this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDate();
        }
    };

    /**
     * setup start date
     */
    private void setInitialDate() {
        TextView textName = findViewById(R.id.enter_the_date_note);
        textName.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    public void create(View view) {
        createNewNote();
    }

    public void delete(View view) {

        deleteANote();

        finish();
    }

    public void createNewNote() {
        if (checkForFilling()) {
            NoteDao noteDao = AppDatabase.getInstance(getApplicationContext()).noteDao();
            Note note = new Note();
            note.sum = Float.valueOf(mAddNoteBinding.sumNote.getText().toString());
            note.name_of_note = mAddNoteBinding.enterTheTextNote.getText().toString();

            dateAndTime.set(Calendar.MILLISECOND, 0);
            Date mDate = new Date(dateAndTime.getTimeInMillis());
            mDate.setHours(0);
            mDate.setMinutes(0);
            mDate.setSeconds(0);
            note.note_date = mDate.getTime();
            note.category_id_of_note = AppDatabase.getInstance(getApplicationContext()).catDao().getIdByName(mAddNoteBinding.pickTheCategoryNote.getSelectedItem().toString());
            if (mId != 0L) {
                note.note_id = mId;
            }
            noteDao.insert(note);

            if (gettingTime != 0L) {
                recountResultsInDay(new Date(gettingTime));
            }
            recountResultsInDay(mDate);

            finish();
        }

    }

    public boolean checkForFilling() {
        boolean errrors = false;
        if (mAddNoteBinding.enterTheTextNote.getText().toString().equals("")) {
            Toast.makeText(this, R.string.error_empty_the_text, Toast.LENGTH_LONG).show();
            errrors = true;
        }
        else if (mAddNoteBinding.enterTheDateNote.getText().toString().equals(getText(R.string.hint_pick_the_date))) {
            Toast.makeText(this, R.string.error_empty_the_date, Toast.LENGTH_LONG).show();
            errrors = true;
        }
        else if (mAddNoteBinding.sumNote.getText().toString().equals("")) {
            Toast.makeText(this, R.string.error_empty_the_sum, Toast.LENGTH_LONG).show();
            errrors = true;
        }
        else if (mAdapter.getCount() == 0) {
            Toast.makeText(this, R.string.error_empty_count_categories, Toast.LENGTH_LONG).show();
            errrors = true;
        }

        if (errrors) return false;
        return true;
    }

    public void deleteANote() {
        NoteDao noteDao = AppDatabase.getInstance(getApplicationContext()).noteDao();
        Note note = noteDao.getItemById(Long.valueOf(mId.toString()));

        noteDao.delete(note);

        if (gettingTime != 0L) {
            recountResultsInDay(new Date(gettingTime));
        }
        recountResultsInDay(new Date(dateAndTime.getTimeInMillis()));

    }

    public void recountResultsInDay(Date date) {
        Date mDate = date;
        mDate.setHours(0);
        mDate.setMinutes(0);
        mDate.setSeconds(0);
        List<Note> list_of_notes = AppDatabase.getInstance(getApplicationContext()).noteDao().getItemsByDate(mDate.getTime());
        Float currentIncome = 0f;
        Float currentExpense = 0f;
        for (Note item : list_of_notes) {
            if (AppDatabase.getInstance(getApplicationContext()).catDao().getTypeById(item.category_id_of_note).equals(String.valueOf(getResources().getStringArray(R.array.income_expense)[0]))) {
                currentIncome += item.sum;
            } else if (AppDatabase.getInstance(getApplicationContext()).catDao().getTypeById(item.category_id_of_note).equals(String.valueOf(getResources().getStringArray(R.array.income_expense)[1]))) {
                currentExpense += item.sum;
            }
        }

        ResultDao resDao = AppDatabase.getInstance(getApplicationContext()).resDao();
        ResultDay resultDay = new ResultDay();
        resultDay.result_day_date_entity = mDate.getTime();

        resultDay.result_day_income_entity = Math.round(currentIncome * 100.0f) / 100.0f;
        resultDay.result_day_expense_entity = Math.round(currentExpense * 100.0f) / 100.0f;

        resDao.insert(resultDay);


    }

    public void addCategory(View view) {
        Intent intent = new Intent(this, AddCategoryActivity.class);
        startActivity(intent);
    }
}
