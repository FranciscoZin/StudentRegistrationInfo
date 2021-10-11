package com.example.studentregistrationinfo;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {


    EditText editName;
    EditText editAge;
    EditText editGrade;

    EditText editContact;
    EditText editParentName;
    EditText editEmail;

    Button btnAdd;
    Button btnDelete;
    Button btnModify;
    Button btnView;
    Button btnViewAll;
    Button btnShowInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// Initializing controls


        editName = (EditText) findViewById(R.id.e1);
        editAge = (EditText) findViewById(R.id.e2);
        editGrade = (EditText) findViewById(R.id.e3);
        editParentName = (EditText) findViewById(R.id.e4);
        editContact = (EditText) findViewById(R.id.e5);
        editEmail = (EditText) findViewById(R.id.e6);

        btnAdd = (Button) findViewById(R.id.b1);
        btnDelete = (Button) findViewById(R.id.b2);
        btnModify = (Button) findViewById(R.id.b3);
        btnView = (Button) findViewById(R.id.b4);
        btnViewAll = (Button) findViewById(R.id.b5);
        btnShowInfo = (Button) findViewById(R.id.b6);

        // Creating database and table

        final SQLiteDatabase db = openOrCreateDatabase("studentregistrationinfo", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student(grade VARCHAR,name VARCHAR,age VARCHAR,contact VARCHAR,parentName VARCHAR,email VARCHAR);");


// Registering event handlers

        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Checking empty fields
                if(editName.getText().toString().trim().length()==0||editAge.getText().toString().trim().length()==0||editGrade.getText().toString().trim().length()==0||
                editParentName.getText().toString().trim().length()==0||editContact.getText().toString().trim().length()==0||editEmail.getText().toString().trim().length()==0)
                {
                    showMessage("Error", "Please enter all values");
                    return;
                }
                // Inserting record
                db.execSQL("INSERT INTO student VALUES('" + editName.getText() + "','" +
                        editAge.getText() + "','" + editGrade.getText() + editParentName.getText() + "','" +
                        editContact.getText() + "','" + editEmail.getText()+"');");
                showMessage("Success", "Record added");
                clearText();
            }
        });
        btnView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Checking empty roll number
                if(editGrade.getText().toString().trim().length()==0)
                {
                    showMessage("Error", "Please enter Class");
                    return;
                }
                // Searching roll number
                Cursor c=db.rawQuery("SELECT * FROM student WHERE grade='"+editGrade.getText()+"'", null);
                if(c.moveToFirst())
                {

                    editName.setText(c.getString(1));
                    editAge.setText(c.getString(2));
                    editParentName.setText(c.getString(3));
                    editContact.setText(c.getString(4));
                    editEmail.setText(c.getString(5));


                }
                else
                {
                    showMessage("Error", "Invalid Class ");
                    clearText();
                }


            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                // Checking empty roll number
                if(editGrade.getText().toString().trim().length()==0)
                {
                    showMessage("Error", "Enter Class");
                    return;
                }
                // Searching roll number
                Cursor c=db.rawQuery("SELECT * FROM student WHERE grade='"+editGrade.getText()+"'", null);
                if(c.moveToFirst())
                {
                    // Deleting record if found
                    db.execSQL("DELETE FROM student WHERE grade='"+editGrade.getText()+"'");
                    showMessage("Success", "Record Deleted");

                }
                else
                {
                    showMessage("Error", "Invalid Class");
                }
                clearText();

            }



        });
        btnModify.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                if(editAge.getText().toString().trim().length()==0)
                {
                    showMessage("Error", "Enter Class");
                    return;
                }
                // Searching roll number
                Cursor c=db.rawQuery("SELECT * FROM student WHERE grade='"+editGrade.getText()+"'", null);
                if(c.moveToFirst())
                {
                    // Modifying record if found
                    db.execSQL("UPDATE student SET name='"+editName.getText()+"',age='"+editAge.getText()+ "',contact='"+editContact.getText()+ "'," +
                            "parentName= '"+editParentName.getText()+ "',email='"+editEmail.getText()+
                            "' WHERE class='"+editGrade.getText()+"'");
                    showMessage("Success", "Record Modified");

                }
                else
                {
                    showMessage("Error", "Invalid Class");
                }
                clearText();


            }
        });

        btnViewAll.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                // Checking empty roll number
                Cursor c=db.rawQuery("SELECT * FROM student", null);
                if(c.getCount()==0)
                {
                    showMessage("Error", "No records found");
                    return;
                }
                StringBuffer buffer=new StringBuffer();
                while(c.moveToNext())
                {
                    buffer.append("Grade: "+c.getString(0)+"\n");
                    buffer.append("Name: "+c.getString(1)+"\n");
                    buffer.append("Age: "+c.getString(2)+"\n\n");

                    buffer.append("Contact: "+c.getString(3)+"\n");
                    buffer.append("Parent Name: "+c.getString(4)+"\n");
                    buffer.append("email: "+c.getString(5)+"\n\n");
                }
                showMessage("Student Details", buffer.toString());
            }
        });
        btnShowInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showMessage("Students Information Handling", "https://byui-cs.github.io/CS246/week-03/team-2.html");

            }
        });

    }
    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void clearText()
    {
        editGrade.setText("");
        editName.setText("");
        editAge.setText("");

        editContact.setText("");
        editParentName.setText("");
        editEmail.setText("");

        editGrade.requestFocus();
    }

}