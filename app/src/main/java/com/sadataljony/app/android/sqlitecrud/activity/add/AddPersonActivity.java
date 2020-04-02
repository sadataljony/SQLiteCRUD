package com.sadataljony.app.android.sqlitecrud.activity.add;

import android.app.Activity;
import android.content.DialogInterface;
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


public class AddPersonActivity extends Activity implements View.OnClickListener {
    private EditText mEditTextName, mEditTextAge, mEditTextPhone, mEditTextEmail, mEditTextAddress, mEditTextSalary;
    private Button mButtonAdd, mButtonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
        this.setFinishOnTouchOutside(false);// prevent closing add person dialog when touch outside
        initUi();// initialize all ui components
    }

    // initialize all ui components
    private void initUi() {
        mEditTextName = findViewById(R.id.name);
        mEditTextAge = findViewById(R.id.age);
        mEditTextPhone = findViewById(R.id.phone);
        mEditTextEmail = findViewById(R.id.email);
        mEditTextAddress = findViewById(R.id.address);
        mEditTextSalary = findViewById(R.id.salary);
        mButtonAdd = findViewById(R.id.add);
        mButtonCancel = findViewById(R.id.cancel);
        // implement on click on button
        mButtonAdd.setOnClickListener(this);
        mButtonCancel.setOnClickListener(this);
    }

    // all on click event
    @Override
    public void onClick(View v) {

        if (v == mButtonAdd) {
            if (mEditTextName.getText().toString().trim().equalsIgnoreCase("")) {
                mEditTextName.setError("This field can not be blank");
            } else {
                try {
                    if (checkNameIsPresent(mEditTextName.getText().toString())) {// check duplicate person name
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
                        // insert person
                        ListPersonActivity.sqLiteHelper.insert_person(
                                mEditTextName.getText().toString().trim(),
                                intAge,
                                mEditTextPhone.getText().toString().trim(),
                                mEditTextEmail.getText().toString().trim(),
                                mEditTextAddress.getText().toString().trim(),
                                doubleSalary
                        );
                        Toast.makeText(getApplicationContext(), "Insert Successful", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        showAlertDialog("Error!", "Duplicate Name");
                    }

                } catch (Exception e) {
                    showAlertDialog("Error!", e.toString());
                }
            }
        }

        if (v == mButtonCancel) {
            finish();
        }

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
                                finish();
                            }
                        });
        alertDialogBuilder.show();
    }

    // check duplicate person name
    public boolean checkNameIsPresent(String strName) {
        Cursor cursor = ListPersonActivity.sqLiteHelper.getData("SELECT * FROM  person WHERE name='" + strName + "'");
        if (cursor.getCount() > 0) {
            cursor.close();
            return false;
        } else {
            cursor.close();
            return true;
        }
    }

    // handle back button press
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
