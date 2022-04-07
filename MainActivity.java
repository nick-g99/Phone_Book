package com.example.phonebook;
/*
This app will allow a user to type in a First and Last name, and a phone number
and press "add" to add it to a phone book and "delete" that deletes a name and
phone number if present. The data stored in the phone book will be shared with
another application which will allow it to use the phone number to make a call.
@author Nickolas Gallegos
@version 4/6/2021
 */
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //initialize variables
    EditText firstNameBox, lastNameBox, phoneNumberBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstNameBox = findViewById(R.id.first_name);
        lastNameBox = findViewById(R.id.last_name);
        phoneNumberBox = findViewById(R.id.phone_number);
    }

    public void show(View view){
        firstNameBox.setText("");
        lastNameBox.setText("");
        phoneNumberBox.requestFocus();
        Intent intent = new Intent(this,DisplayActivity.class);
        startActivity(intent);
    }

    public void newContact(View view) {
        String phoneNumber = phoneNumberBox.getText().toString();
        if ((phoneNumber.length() == 10) && !phoneNumber.contains("-")) {
            phoneNumber = phoneNumber.substring(0,3) + "-" +
                    phoneNumber.substring(3,6) + "-" +
                    phoneNumber.substring(6,10);
        }
        else if ((phoneNumber.length() == 7) && !phoneNumber.contains("-")) {
            phoneNumber = phoneNumber.substring(0,3) + "-" +
                    phoneNumber.substring(3,7);
        }

        Contact contact = new Contact(firstNameBox.getText().toString(),
                lastNameBox.getText().toString(),
                phoneNumber);
        ContentValues values = new ContentValues();
        values.put(MyContentProvider.COLUMN_FN,contact.getFirstName());
        values.put(MyContentProvider.COLUMN_LN,contact.getLastName());
        values.put(MyContentProvider.COLUMN_PN,contact.getPhoneNumber());
        ContentResolver contentResolver = getContentResolver();
        Uri uri = contentResolver.insert(MyContentProvider.CONTENT_URI, values);
        firstNameBox.setText("");
        lastNameBox.setText("");
        phoneNumberBox.setText("");
        Toast.makeText(this,"1 Contact Added Successfully",
                Toast.LENGTH_LONG).show();
    }

    /*   This method is unnecessary since we do not have a "lookUpButton"
    public void lookupContact(View view) {
        String [] projection = {MyContentProvider.COLUMN_FN,
                MyContentProvider.COLUMN_LN,
                MyContentProvider.COLUMN_PN};
        String selection = "firstName = \"" +
                firstNameBox.getText().toString() + "\"";
        // access our table through a ContentResolver instance
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(MyContentProvider.CONTENT_URI,
                projection ,selection ,null,null );
        Contact contact = new Contact();
        if(cursor.moveToFirst()) {
            contact.setFirstName(cursor.getString(0));
            contact.setLastName(cursor.getString(1));
            contact.setPhoneNumber(cursor.getString(2));
            cursor.close();
        }
        else
            contact = null;
        if(contact != null){
            firstNameBox.setText(String.valueOf(contact.getFirstName()));
            lastNameBox.setText(String.valueOf(contact.getLastName()));
            phoneNumberBox.setText(String.valueOf(contact.getPhoneNumber()));
        }
        else
            Toast.makeText(this,"Contact Not Found",
                    Toast.LENGTH_LONG).show();
    }
    */

    public void removeContact (View view){
        String selection = "firstName = \""
                + firstNameBox.getText().toString() + "\"";
        ContentResolver contentResolver = getContentResolver();
        int result = contentResolver.delete(MyContentProvider.CONTENT_URI,
                selection,null );
        if(result > 0){
            firstNameBox.setText("");
            lastNameBox.setText("");
            phoneNumberBox.setText("");
            Toast.makeText(this,"1 Contact Deleted",
                    Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this,"Contact Not Found",
                    Toast.LENGTH_LONG).show();

    }

    public void clearBoxes (View view) {
        firstNameBox.setText("");
        lastNameBox.setText("");
        phoneNumberBox.setText("");
    }
}