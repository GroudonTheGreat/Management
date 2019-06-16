package com.example.management;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FindRecord extends AppCompatActivity {

    EditText id;
    Button find,viewAll;
    TextView name, sal;
    SQLiteDatabase database;
    String sql = "CREATE TABLE IF NOT EXISTS employee(ID VARCHAR,Name VARCHAR,Salary int);";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_record);

        database = openOrCreateDatabase("Department", Context.MODE_PRIVATE, null);
        database.execSQL(sql);

        id = findViewById(R.id.find_id);
        name = findViewById(R.id.find_name);
        sal = findViewById(R.id.find_salary);

        find = findViewById(R.id.find);
        viewAll = findViewById(R.id.viewAll);

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id.getText().toString().trim().length() == 0) {
                    showMessage("Error", "Please enter ID");
                    return;
                }
                Cursor cursor = database.rawQuery("SELECT * FROM employee WHERE id='" + id.getText() + "'", null);
                if (cursor.moveToFirst()) {
                    name.setText(cursor.getString(1));
                    sal.setText(cursor.getString(2));
                } else {
                    showMessage("Error", "Invalid ID");
                    clearText();
                }
            }
        });

        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = database.rawQuery("SELECT * FROM employee;", null);
                if (cursor.getCount() == 0) {
                    showMessage("Error", "No records found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (cursor.moveToNext()) {
                    buffer.append("ID :\t\t" + cursor.getString(0) + "\n");
                    buffer.append("Name :\t" + cursor.getString(1) + "\n");
                    buffer.append("Salary :\t" + cursor.getInt(2) + "\n\n");
                }
                showMessage("Employee Details", buffer.toString());
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
        id.requestFocus();
    }
}
