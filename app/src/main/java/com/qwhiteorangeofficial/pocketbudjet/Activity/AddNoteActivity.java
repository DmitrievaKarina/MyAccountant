package com.qwhiteorangeofficial.pocketbudjet.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.qwhiteorangeofficial.pocketbudjet.Dao.CategoryDao;
import com.qwhiteorangeofficial.pocketbudjet.Database.AppDatabase;
import com.qwhiteorangeofficial.pocketbudjet.Dao.ResultDao;
import com.qwhiteorangeofficial.pocketbudjet.Entity.Note;
import com.qwhiteorangeofficial.pocketbudjet.Dao.NoteDao;
import com.qwhiteorangeofficial.pocketbudjet.Entity.ResultDay;
import com.qwhiteorangeofficial.pocketbudjet.R;
import com.qwhiteorangeofficial.pocketbudjet.databinding.AddNoteBinding;

import java.util.Calendar;
import java.util.List;


public class AddNoteActivity extends AppCompatActivity {

    Long mId;

    Calendar dateAndTime = Calendar.getInstance();

    ArrayAdapter mAdapter;
    List<String> listCategory;
    Long idCategory;
    Long gettingTime;
    AddNoteBinding mAddNoteBinding;

    AppDatabase db;
    CategoryDao catDao;
    ResultDao resDao;
    NoteDao noteDao;

    @Override
    protected void onResume() {
        super.onResume();

        initializeDbAndDao();

        listCategory = catDao.getAllCategoriesAsMassiv();

        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listCategory);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAddNoteBinding.pickTheCategoryNote.setAdapter(mAdapter);

        //read category for an existing note and setup info into the spinner
        Intent intent = getIntent();
        mId = intent.getLongExtra("Id", 0L);

        idCategory = intent.getLongExtra("Category", 0L);
        if (idCategory != 0L) {
            int spinnerPosition = mAdapter.getPosition(catDao.getNameById(idCategory));
            mAddNoteBinding.pickTheCategoryNote.setSelection(spinnerPosition);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddNoteBinding = AddNoteBinding.inflate(getLayoutInflater());
        setContentView(mAddNoteBinding.getRoot());

        initializeDbAndDao();

        listCategory = catDao.getAllCategoriesAsMassiv();

        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listCategory);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAddNoteBinding.pickTheCategoryNote.setAdapter(mAdapter);

        Intent intent = getIntent();
        mId = intent.getLongExtra("Id", 0L);


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
            dateAndTime.setTimeInMillis(gettingTime);
        }

        //read category for an existing note and setup info into the spinner
        idCategory = intent.getLongExtra("Category", 0L);
        if (idCategory != 0L) {
            int spinnerPosition = mAdapter.getPosition(catDao.getNameById(idCategory));
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


    private void initializeDbAndDao() {
        db = AppDatabase.getInstance(this);
        noteDao = db.noteDao();
        catDao = db.catDao();
        resDao = db.resDao();
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

    DatePickerDialog.OnDateSetListener d = (view, year, monthOfYear, dayOfMonth) -> {
        dateAndTime.set(Calendar.YEAR, year);
        dateAndTime.set(Calendar.MONTH, monthOfYear);
        dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        setInitialDate();
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

    private void createNewNote() {
        if (checkForFilling()) {
            Note note = new Note();
            note.sum = Float.valueOf(mAddNoteBinding.sumNote.getText().toString());
            note.name_of_note = mAddNoteBinding.enterTheTextNote.getText().toString();

            dateAndTime.set(Calendar.MILLISECOND, 0);
            dateAndTime.set(Calendar.SECOND, 0);
            dateAndTime.set(Calendar.MINUTE, 0);
            dateAndTime.set(Calendar.HOUR_OF_DAY, 0);

            note.note_date = dateAndTime.getTimeInMillis();
            note.category_id_of_note = catDao.getIdByName(mAddNoteBinding.pickTheCategoryNote.getSelectedItem().toString());
            if (mId != 0L) {
                note.note_id = mId;
            }
            noteDao.insert(note);

            if (gettingTime != 0L) {
                recountResultsInDay(gettingTime);
            }
            recountResultsInDay(dateAndTime.getTimeInMillis());

            finish();
        }
    }

    private boolean checkForFilling() {
        boolean errrors = false;
        if (mAddNoteBinding.enterTheTextNote.getText().toString().equals("")) {
            Toast.makeText(this, R.string.error_empty_the_text, Toast.LENGTH_LONG).show();
            errrors = true;
        }
        else if (mAddNoteBinding.enterTheDateNote.getText().toString().contentEquals(getText(R.string.hint_pick_the_date))) {
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

        return !errrors;
    }

    private void deleteANote() {
        Note note = noteDao.getItemById(Long.valueOf(mId.toString()));

        noteDao.delete(note);

        if (gettingTime != 0L) {
            recountResultsInDay(gettingTime);
        }
        recountResultsInDay(dateAndTime.getTimeInMillis());

    }

    private void recountResultsInDay(Long timeInMillis) {

        List<Note> listOfNotes = noteDao.getItemsByDate(timeInMillis);
        Float currentIncome = 0f;
        Float currentExpense = 0f;
        String expenseInstant = getResources().getStringArray(R.array.income_expense)[0];
        String incomeInstant = getResources().getStringArray(R.array.income_expense)[1];

        for (Note item : listOfNotes) {
            String categoryIdItem = catDao.getTypeById(item.category_id_of_note);
            if (categoryIdItem.equals(expenseInstant)) {
                currentIncome += item.sum;
            } else if (categoryIdItem.equals(incomeInstant)) {
                currentExpense += item.sum;
            }
        }

        ResultDay resultDay = new ResultDay();
        resultDay.result_day_date_entity = timeInMillis;

        resultDay.result_day_income_entity = Math.round(currentIncome * 100.0f) / 100.0f;
        resultDay.result_day_expense_entity = Math.round(currentExpense * 100.0f) / 100.0f;

        resDao.insert(resultDay);
    }

    public void addCategory(View view) {
        Intent intent = new Intent(this, AddCategoryActivity.class);
        startActivity(intent);
    }
}
