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
import android.widget.Toast;

public class DeleteRecord extends AppCompatActivity {

    EditText id;
    Button del;
    SQLiteDatabase database;
    String sql = "CREATE TABLE IF NOT EXISTS employee(ID VARCHAR,Name VARCHAR,Salary int);";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_record);

        database = openOrCreateDatabase("Department", Context.MODE_PRIVATE, null);
        database.execSQL(sql);

        id = findViewById(R.id.delete_id);
        del = findViewById(R.id.delete);

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id.getText().toString().trim().length() == 0) {
                    showMessage("Error", "Please enter ID");
                    return;
                }
                Cursor cursor = database.rawQuery("SELECT * FROM employee WHERE ID='" + id.getText() + "'", null);
                if (cursor.moveToFirst()) {
                    database.execSQL("DELETE FROM employee WHERE ID='" + id.getText() + "'");
                    showMessage("Success", "Record Deleted");
                } else {
                    showMessage("Error", "Invalid Rollno");
                }
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
        id.requestFocus();
    }
}
