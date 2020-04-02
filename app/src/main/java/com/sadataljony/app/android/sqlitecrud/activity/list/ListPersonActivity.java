package com.sadataljony.app.android.sqlitecrud.activity.list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sadataljony.app.android.sqlitecrud.R;
import com.sadataljony.app.android.sqlitecrud.activity.add.AddPersonActivity;
import com.sadataljony.app.android.sqlitecrud.adapter.AdapterPerson;
import com.sadataljony.app.android.sqlitecrud.db.SQLiteHelper;
import com.sadataljony.app.android.sqlitecrud.db.SQLiteTables;
import com.sadataljony.app.android.sqlitecrud.model.Person;

import java.util.ArrayList;

/**
 * Created by Muhammad Sadat Al-Jony on 02-April-2020.
 */

public class ListPersonActivity extends AppCompatActivity implements View.OnClickListener, AdapterPerson.AdapterListener {
    SQLiteDatabase SQLITEDATABASE;
    public static SQLiteHelper sqLiteHelper;
    private ImageView mImgViewNoDateFound;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFloatingActionButton;
    private AdapterPerson mAdapter;
    private ArrayList<Person> mArrayList;

    public static ListPersonActivity newInstance() {
        ListPersonActivity activity = new ListPersonActivity();
        return activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBCreate();// create db
        setContentView(R.layout.activity_list_person);
        initUi();// initialize all ui components
    }

    // create db
    public void DBCreate() {
        SQLiteTables object = new SQLiteTables();
        SQLITEDATABASE = openOrCreateDatabase("person.db", Context.MODE_PRIVATE, null);// create db
        sqLiteHelper = new SQLiteHelper(this, "person.db", null, 1);
        sqLiteHelper.queryData(object.person);// create table
    }

    // initialize all ui components
    private void initUi() {
        mArrayList = new ArrayList<>();
        mAdapter = new AdapterPerson(mArrayList, this.getApplicationContext(), this, ListPersonActivity.this);

        mImgViewNoDateFound = findViewById(R.id.imageViewNoDataFound);
        mRecyclerView = findViewById(R.id.recyclerView);
        mFloatingActionButton = findViewById(R.id.floatingActionButtonAdd);
        mFloatingActionButton.setOnClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
    // get all person
        loadAllPerson();
    }

    // get all person
    public void loadAllPerson() {
        Cursor cursor = ListPersonActivity.sqLiteHelper.getData("SELECT * FROM  person ORDER BY name ASC");
        mArrayList.clear();
        if (cursor.getCount() <= 0) {
            mImgViewNoDateFound.setVisibility(View.VISIBLE);// if no data found show No Data Found image
            mAdapter.notifyDataSetChanged();
            cursor.close();
        } else {
            mImgViewNoDateFound.setVisibility(View.GONE);// id data found hide No Data Found image
            while (cursor.moveToNext()) {
                try {
                    int intId = cursor.getInt(0);//getInt() for int value
                    String strName = cursor.getString(1);//getString() for String value
                    int intAge = cursor.getInt(2);
                    String strPhone = cursor.getString(3);
                    String strEmail = cursor.getString(4);
                    String strAddress = cursor.getString(5);
                    double doubleSalary = cursor.getDouble(6);//getDouble() for double value

                    // add individual person in ArrayList
                    mArrayList.add(new Person(intId, strName, intAge, strPhone, strEmail, strAddress, doubleSalary));
                    mAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            cursor.close();
            // set adapter in RecyclerView
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    // all on click event
    @Override
    public void onClick(View v) {
        if (v == mFloatingActionButton) {
            startActivity(new Intent(ListPersonActivity.this, AddPersonActivity.class));// go to AddPersonActivity
        }
    }

    // search option in action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchQuery(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                searchQuery(query);
                return false;
            }
        });
        return true;
    }

    // query for person name
    public void searchQuery(String searchQuery) {
        mAdapter.getFilter().filter(searchQuery);
    }

    @Override
    public void onContactSelected(Person person) {

    }

    // resume all person
    @Override
    protected void onResume() {
        super.onResume();
        loadAllPerson();
    }

}
