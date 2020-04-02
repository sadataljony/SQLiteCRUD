package com.sadataljony.app.android.sqlitecrud.activity.edit;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.sadataljony.app.android.sqlitecrud.R;
import com.sadataljony.app.android.sqlitecrud.activity.list.ListPersonActivity;

/**
 * Created by Muhammad Sadat Al-Jony on 02-April-2020.
 */


public class EditPersonActivity extends Activity implements View.OnClickListener {
    private EditText mEditTextName, mEditTextAge, mEditTextPhone, mEditTextEmail, mEditTextAddress, mEditTextSalary;
    private Button mButtonCancel, mButtonUpdate;
    private int mIntId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_person);
        this.setFinishOnTouchOutside(false);// prevent closing add person dialog when touch outside
        getIntentValue();// get person id from DialogEditDeleteCloseActivity
        initUi();// initialize all ui components
    }

    // get person id from DialogEditDeleteCloseActivity
    private void getIntentValue() {
        Intent intent = getIntent();
        mIntId = intent.getIntExtra("id", 0);// get int id from DialogEditDeleteCloseActivity
    }

    // initialize all ui components
    private void initUi() {
        mEditTextName = findViewById(R.id.name);
        mEditTextAge = findViewById(R.id.age);
        mEditTextPhone = findViewById(R.id.phone);
        mEditTextEmail = findViewById(R.id.email);
        mEditTextAddress = findViewById(R.id.address);
        mEditTextSalary = findViewById(R.id.salary);
        mButtonUpdate = findViewById(R.id.update);
        mButtonCancel = findViewById(R.id.cancel);
        // implement on click on buttons
        mButtonUpdate.setOnClickListener(this);
        mButtonCancel.setOnClickListener(this);
        // load individual person based on id
        loadIndividualPerson();
    }

    // all on click event
    @Override
    public void onClick(View v) {

        if (v == mButtonUpdate) {// update button
            try {
                int intAge = 0;
                String strAge = mEditTextAge.getText().toString().trim();
                if (strAge != null && !strAge.isEmpty()) {
                    intAge = Integer.parseInt(strAge);
                }
                double doubleSalary = 0.0;
                String strSalary = mEditTextSalary.getText().toString().trim();
                if (strAge != null && !strAge.isEmpty()) {
                    doubleSalary = Double.parseDouble(strSalary);
                }
                // update person
                ListPersonActivity.sqLiteHelper.update_person(
                        mIntId,
                        mEditTextName.getText().toString().trim(),
                        intAge,
                        mEditTextPhone.getText().toString().trim(),
                        mEditTextEmail.getText().toString().trim(),
                        mEditTextAddress.getText().toString().trim(),
                        doubleSalary
                );
                Toast.makeText(getApplicationContext(), "Update Successful", Toast.LENGTH_LONG).show();
                finish();
            } catch (Exception e) {
                showAlertDialog("Error!", e.toString());// show error dialog
            }
        }

        if (v == mButtonCancel) {// cancel button
            finish();// finish current activity
        }

    }

    // load individual person based on id
    public void loadIndividualPerson() {
        Cursor cursor = ListPersonActivity.sqLiteHelper.getData("SELECT * FROM  person WHERE id =" + mIntId);
        while (cursor.moveToNext()) {
            int intId = cursor.getInt(0);//getInt() for int value
            String strName = cursor.getString(1);//getString() for String value
            int intAge = cursor.getInt(2);
            String strPhone = cursor.getString(3);
            String strEmail = cursor.getString(4);
            String strAddress = cursor.getString(5);
            double doubleSalary = cursor.getDouble(6);//getDouble() for double value

            // set all values in EditText
            this.mEditTextName.setText(strName);
            this.mEditTextAge.setText(String.valueOf(intAge));//int value need to convert before set in EditText
            this.mEditTextPhone.setText(strPhone);
            this.mEditTextEmail.setText(strEmail);
            this.mEditTextAddress.setText(strAddress);
            this.mEditTextSalary.setText(String.valueOf(doubleSalary));//double value need to convert before set in EditText
        }
        cursor.close();
    }

    // show alert dialog
    public void showAlertDialog(String title, String msg) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setMessage(msg)
                .setCancelable(true)
                .setNegativeButton("CLOSE",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
        alertDialogBuilder.show();
    }

    // handle back button press
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
