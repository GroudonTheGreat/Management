package com.example.management;

import android.app.AlertDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddRecord extends AppCompatActivity {

    SQLiteDatabase database;
    String sql = "CREATE TABLE IF NOT EXISTS employee(ID VARCHAR,Name VARCHAR,Salary int);";

    EditText id, name, sal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        database = openOrCreateDatabase("Department", Context.MODE_PRIVATE, null);
        database.execSQL(sql);

        id = findViewById(R.id.add_id);
        name = findViewById(R.id.add_name);
        sal = findViewById(R.id.add_salary);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (id.getText().toString().trim().length() == 0 ||
                        name.getText().toString().trim().length() == 0 ||
                        sal.getText().toString().trim().length() == 0) {
                    Toast.makeText(AddRecord.this, "ERROR", Toast.LENGTH_SHORT).show();
                    showMessage("Error", "Please enter all values");
                    return;
                }

                //SQL query
                database.execSQL("INSERT INTO employee VALUES('" + id.getText() + "','" + name.getText() + "','" + sal.getText() + "');");
                showMessage("Success", "Record added");
                clearText();
            }
        });
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void clearText() {
        id.setText("");
        name.setText("");
        sal.setText("");
        id.requestFocus();
    }
}
