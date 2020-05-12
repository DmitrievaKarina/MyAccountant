package com.qwhiteorangeofficial.pocketbudjet.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.qwhiteorangeofficial.pocketbudjet.Dao.NoteDao;
import com.qwhiteorangeofficial.pocketbudjet.Dao.ResultDao;
import com.qwhiteorangeofficial.pocketbudjet.Database.AppDatabase;
import com.qwhiteorangeofficial.pocketbudjet.Entity.ResultDay;
import com.qwhiteorangeofficial.pocketbudjet.R;
import com.qwhiteorangeofficial.pocketbudjet.databinding.ActivityReportBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReportActivity extends AppCompatActivity {

    ActivityReportBinding mActivityReportBinding;
    ArrayAdapter<CharSequence> adapterM;
    ArrayAdapter<Integer> adapterY;

    AppDatabase db;
    NoteDao mNoteDao;
    ResultDao mResultDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityReportBinding = ActivityReportBinding.inflate(getLayoutInflater());
        setContentView(mActivityReportBinding.getRoot());

        db = AppDatabase.getInstance(this);
        mNoteDao = db.noteDao();
        mResultDao = db.resDao();

        adapterM = ArrayAdapter.createFromResource(this, R.array.months, android.R.layout.simple_spinner_item);
        adapterM.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mActivityReportBinding.spinnerMonths.setAdapter(adapterM);

        List<Integer> years = new ArrayList<>();
        mResultDao.getDates().stream()
                .filter(d -> !years.contains(new Date(d).getYear()+1900))
                .forEach(d -> years.add(new Date(d).getYear()+1900));

        adapterY = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);
        adapterY.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mActivityReportBinding.spinnerYears.setAdapter(adapterY);

        //set actual month and year
        int spinnerPosition1 = new Date().getMonth();
        mActivityReportBinding.spinnerMonths.setSelection(spinnerPosition1);
        int spinnerPosition2 = years.indexOf(Calendar.YEAR);
        mActivityReportBinding.spinnerYears.setSelection(spinnerPosition2);

    }

    public void select(View view) {
        Float incomes=0f;
        Float expenses=0f;

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set((int)mActivityReportBinding.spinnerYears.getSelectedItem(), mActivityReportBinding.spinnerMonths.getSelectedItemPosition(), 1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set((int)mActivityReportBinding.spinnerYears.getSelectedItem(), mActivityReportBinding.spinnerMonths.getSelectedItemPosition(), calendar1.getMaximum(Calendar.DAY_OF_MONTH));

        List<ResultDay> list = mResultDao.getItemsForMonth(calendar1.getTimeInMillis(), calendar2.getTimeInMillis());
        for (ResultDay item : list) {
            incomes += item.result_day_income_entity;
            expenses += item.result_day_expense_entity;
        }

        mActivityReportBinding.textIncomes.setText(String.valueOf(incomes));
        mActivityReportBinding.textExpenses.setText(String.valueOf(expenses));
    }
}
