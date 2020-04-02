package com.sadataljony.app.android.sqlitecrud.activity.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.sadataljony.app.android.sqlitecrud.R;
import com.sadataljony.app.android.sqlitecrud.activity.edit.EditPersonActivity;
import com.sadataljony.app.android.sqlitecrud.activity.list.ListPersonActivity;

/**
 * Created by Muhammad Sadat Al-Jony on 02-April-2020.
 */

public class DialogEditDeleteCloseActivity extends Activity implements View.OnClickListener {
    private Button mBtnEdit, mBtnDelete, mBtnCancel;
    private Context mContext;
    private int mIntId;
    private String mStrName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_edit_delete_close);
        this.setFinishOnTouchOutside(false);// prevent closing dialog when touch outside
        getIntentValues();// get person id and name from AdapterPerson
        initUi();// initialize all ui components
    }

    // get person id and name from adapter
    private void getIntentValues() {
        Intent intent = getIntent();
        mIntId = intent.getIntExtra("id", 0);// get int value
        mStrName = intent.getStringExtra("name");// get String value
        mContext = DialogEditDeleteCloseActivity.this;
    }

    // initialize all ui components
    private void initUi() {
        TextView textViewName = findViewById(R.id.textViewName);
        textViewName.setText(mStrName);// set person name in TextView
        mBtnEdit = findViewById(R.id.btnEdit);
        mBtnDelete = findViewById(R.id.btnDelete);
        mBtnCancel = findViewById(R.id.btnCancel);
        // implement on click listener on buttons
        mBtnEdit.setOnClickListener(this);
        mBtnDelete.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
    }

    // all on click event
    @Override
    public void onClick(View v) {

        if (v == mBtnEdit) {// edit button
            Intent intent = new Intent(v.getContext(), EditPersonActivity.class);// go to EditPersonActivity
            intent.putExtra("id", mIntId);
            v.getContext().startActivity(intent);
            finish();
        }
        if (v == mBtnDelete) {// delete button
            alertDialogDeleteItem("Alert!!!", "Want to delete this item?");// show delete person alert dialog
        }
        if (v == mBtnCancel) {// cancel button
            finish();// finish current activity
        }
    }

    // show alert dialog
    public void alertDialogDeleteItem(String title, String msg) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setMessage(msg)
                .setCancelable(true)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ListPersonActivity.sqLiteHelper.delete_person(mIntId);// delete person based on id
                        dialog.dismiss();
                        ((Activity) mContext).finish();

                    }
                })
                .setNegativeButton("CLOSE",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                ((Activity) mContext).finish();
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
