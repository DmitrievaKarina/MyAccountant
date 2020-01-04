package com.qwhiteorangeofficial.myaccountant;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    NoteAdapter mListAdapter;
    RecyclerView mRecyclerView;
    NoteDao noteDao;
    CategoryDao categoryDao;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = AppDatabase.getInstance(getApplicationContext());
        noteDao = db.noteDao();
        categoryDao = db.catDao();

//        Category example1 = new Category();
//        example1.category_name = "Food";
//        example1.debit_credit = "credit";
//        categoryDao.insert(example1);
//
//        Note example = new Note();
//        example.name_of_note = "something";
//        example.note_date = System.currentTimeMillis();
////        example.category_id_of_note = example1.category_id;
//        example.sum = 154.5f;
//        noteDao.insert(example);

        mRecyclerView = findViewById(R.id.list_of_notes);
        mListAdapter = new NoteAdapter(db.noteDao().getAllNotes());
        mRecyclerView.setAdapter(mListAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
