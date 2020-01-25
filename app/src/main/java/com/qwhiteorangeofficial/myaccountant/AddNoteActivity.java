package com.qwhiteorangeofficial.myaccountant;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddNoteActivity extends AppCompatActivity {

    TextView name;
    Spinner category;
    TextView sum;

    Calendar dateAndTime = Calendar.getInstance();

    ArrayAdapter<String> adapter;
    List<String> listCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note);

        name = findViewById(R.id.enter_the_text_note);
        category = findViewById(R.id.pick_the_category_note);
        sum = findViewById(R.id.sum_note);
        listCategory = AppDatabase.getInstance(getApplicationContext()).catDao().getAllCategoriesInText();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listCategory);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);

    }

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

    // установка начальных даты и времени
    private void setInitialDate() {
        TextView textName = findViewById(R.id.enter_the_date_note);
        textName.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    public void create(View view) {

        createNewNote();

        finish();
    }

    public void createNewNote() {
        NoteDao noteDao = AppDatabase.getInstance(getApplicationContext()).noteDao();
        Note note = new Note();
        note.sum = Float.valueOf(sum.getText().toString());
        note.name_of_note = name.getText().toString();
        dateAndTime.set(Calendar.MILLISECOND, 0);
        Date mDate = new Date(dateAndTime.getTimeInMillis());
        mDate.setHours(0);
        mDate.setMinutes(0);
        mDate.setSeconds(0);
//        LocalDate mDate1 = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            mDate1 = mDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        }
        note.note_date = mDate.getTime();
        note.category_id_of_note = AppDatabase.getInstance(getApplicationContext()).catDao().getIdByName(category.getSelectedItem().toString());
        noteDao.insert(note);

        recountResultsInDay(note);
    }

    public void recountResultsInDay(Note note) {
        List<Note> list_of_notes = AppDatabase.getInstance(getApplicationContext()).noteDao().getItemsByDate(dateAndTime.getTimeInMillis());
        Float currentIncome = 0f;
        Float currentExpense = 0f;
        for (Note item : list_of_notes) {
            if (AppDatabase.getInstance(getApplicationContext()).catDao().getTypeById(item.category_id_of_note) == getResources().getStringArray(R.array.income_expense)[0])
            {
                currentIncome += item.sum;
            }
            else if (AppDatabase.getInstance(getApplicationContext()).catDao().getTypeById(item.category_id_of_note) == getResources().getStringArray(R.array.income_expense)[1])
            {
                currentExpense += item.sum;
            }
        }
        ResultDao resDao = AppDatabase.getInstance(getApplicationContext()).resDao();
        ResultDay resultDay = new ResultDay();
        resultDay.result_day_date_entity = note.note_date;
        resultDay.result_day_income_entity = currentIncome;
        resultDay.result_day_expense_entity = currentExpense;


        resDao.insert(resultDay);
    }
}
