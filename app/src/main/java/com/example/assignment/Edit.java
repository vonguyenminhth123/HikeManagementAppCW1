package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Edit extends AppCompatActivity {
    // Declaring variables
    String[] levels = {"Easy", "Medium", "Hard", "Challenging"};
    Button btnUpdate, btnView,  btnDate;
    private ArrayList<HikeModal> hikeModalArrayList;
    private DBHandle dbHandler;
    private HikeAdapter hikeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Initializing UI elements and variables
        btnUpdate = findViewById(R.id.btnUpdate);
        btnView = findViewById(R.id.btnView);
        btnDate = findViewById(R.id.btnDate);
        hikeModalArrayList = new ArrayList<>();
        dbHandler = new DBHandle(Edit.this);

        // Retrieve the selected Hike data
        Intent intent = getIntent();
        HikeModal selectedHike = (HikeModal) intent.getSerializableExtra("SELECTED_HIKE");

        if (selectedHike != null) {
            int hikeid = selectedHike.getId();
            // Use the data to pre-fill the fields for editing
            EditText nameEdit = findViewById(R.id.updateName);
            nameEdit.setText(selectedHike.getHikeName());
            EditText locationEdit = findViewById(R.id.updateLocation);
            locationEdit.setText(selectedHike.getHikeLocation());
            TextView dateEdit = findViewById(R.id.updateDate);
            dateEdit.setText(selectedHike.getHikeDate());
            // Set onClickListener for the date picker button
            btnDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Display a date picker dialog
                    DatePickerDialog dialog = new DatePickerDialog(Edit.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            dateEdit.setText(String.valueOf(day)+"-"+String.valueOf(month+1)+"-"+String.valueOf(year));
                        }
                    }, 2023, 10, 1);
                    dialog.show();
                }
            });
            Switch parkingEdit = findViewById(R.id.updateSpace);
            parkingEdit.setChecked(selectedHike.getHikeParkingAvailable());
            EditText lengthEdit = findViewById(R.id.updateLength);
            lengthEdit.setText(selectedHike.getHikeLength());

            Spinner levelSpinner = findViewById(R.id.updateLevel);
            String selectedLevel = selectedHike.getHikeLevel();
            int position = indexOf(levels, selectedLevel);
            levelSpinner.setSelection(position);

            EditText timeEdit = findViewById(R.id.updateTime);
            timeEdit.setText(selectedHike.getHikeTime());
            EditText descEdit = findViewById(R.id.updateDescription);
            descEdit.setText(selectedHike.getHikeDescription());
            EditText alertEdit = findViewById(R.id.updateAlert);
            alertEdit.setText(selectedHike.getHikeAlert());

            // After receiving the modified Hike object
            DBHandle dbHandler = new DBHandle(Edit.this);
            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Display a confirmation dialog before updating the hike
                    new AlertDialog.Builder(Edit.this)
                            .setTitle("Update Confirmation")
                            .setMessage("Are you sure you want to update this hike?")
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String hikelevel = levelSpinner.getSelectedItem().toString();
                                    String hikename = nameEdit.getText().toString();
                                    String hikelocation = locationEdit.getText().toString();
                                    String hikedate = dateEdit.getText().toString();
                                    String hikelength = lengthEdit.getText().toString();
                                    String hiketime = timeEdit.getText().toString();
                                    String hikedescription = descEdit.getText().toString();
                                    String hikealert = alertEdit.getText().toString();
                                    String parking = parkingEdit.isChecked() ? "Parking available" : "No parking available";
                                    boolean isParkingAvailable = parkingEdit.isChecked();
                                    // Call DBHandler to update the hike details
                                    dbHandler.updateHike(hikeid, hikename, hikelocation, hikedate, isParkingAvailable, hikelength,  hikelevel, hiketime, hikedescription, hikealert); // Pass the modified Hike object to update
                                    Toast t = Toast.makeText(Edit.this, "Hike updated successfully", Toast.LENGTH_SHORT);
                                    t.show();
                                }
                            })
                            .show();
                }
            });
            btnView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Redirect to ViewFunction activity
                    Intent intentToView = new Intent(Edit.this, ViewFunction.class);
                    startActivity(intentToView);
                }
            });
        }
    }
    // Method to find the index of a value in the array
    private int indexOf(String[] array, String value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(value)) {
                return i;
            }
        }
        return -1;  // Not found
    }
}