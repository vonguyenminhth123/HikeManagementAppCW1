package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class Add extends AppCompatActivity {
    private TextView tvDate;
    private Button btnDate, btnAdd, btnView;
    private EditText name, location, length, time, description, alert;
    private Switch switchParking;
    private Spinner spinner;
    DBHandle dbHandler;
    boolean isAllFieldsFilled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        tvDate = findViewById(R.id.showDate);
        btnDate = findViewById(R.id.btnDate);
        name = findViewById(R.id.edtName);
        location = findViewById(R.id.edtLocation);
        length = findViewById(R.id.edtLength);
        time = findViewById(R.id.edtTime);
        description = findViewById(R.id.edtDescription);
        alert = findViewById(R.id.edtAlert);
        switchParking = findViewById(R.id.swtSpace);
        spinner = findViewById(R.id.spnLevel);
        btnAdd = findViewById(R.id.btnAdd);
        btnView = findViewById(R.id.btnView);

        dbHandler = new DBHandle(this);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAllFieldsFilled = CheckAllFields();
                if (isAllFieldsFilled) {
                    displayAlert();
                }
            }
        });
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToView = new Intent(Add.this, ViewFunction.class);
                startActivity(intentToView);
            }
        });
    }
    //creates and displays a DatePickerDialog allowing the user to select a date
    private void openDialog(){
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // updating the tvDate TextView with the chosen date
                tvDate.setText(String.valueOf(day)+"-"+String.valueOf(month+1)+"-"+String.valueOf(year));
            }
        }, 2023, 10, 1);
        dialog.show();
    }
    //constructs an AlertDialog showing various hike details fetched from user inputs
    public void displayAlert() {
        String hikelevel = spinner.getSelectedItem().toString();
        String hikename = name.getText().toString();
        String hikelocation = location.getText().toString();
        String hikedate = tvDate.getText().toString();
        String hikelength = length.getText().toString();
        String hiketime = time.getText().toString();
        String hikedescription = description.getText().toString();
        String hikealert = alert.getText().toString();
        String parking = switchParking.isChecked() ? "Parking available" : "No parking available";
        boolean isParkingAvailable = switchParking.isChecked();

        final String TITLE = "Details Entered";
        final String MESSAGE_FORMAT = "Information: \nDate: %s\nLevel: %s\nName: %s\nLocation: %s\nLength: %s\nTime: %s\nDescription: %s\nAlert: %s\n%s\n";

        String message = String.format(MESSAGE_FORMAT, hikedate, hikelevel, hikename, hikelocation, hikelength, hiketime, hikedescription, hikealert, parking);
        //prompting for confirmation to add a new hike
        new AlertDialog.Builder(this)
                .setTitle(TITLE)
                .setMessage(message)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                //upon confirmation, triggers an addition in the database and displays a toast confirming the hike addition
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dbHandler.addNewHike(hikename, hikelocation, hikedate, isParkingAvailable, hikelength, hikelevel, hiketime, hikedescription, hikealert);
                        Toast t = Toast.makeText(Add.this, "New Hike has been added", Toast.LENGTH_SHORT);
                        t.show();
                    }
                })
                .show();
    }
    //validates essential fields for the hike creation
    public boolean CheckAllFields() {
        if (TextUtils.isEmpty(name.getText())) {
            //highlights the empty fields with error messages
            name.setError("This field is required");
            return false;
        }
        if (TextUtils.isEmpty(location.getText())) {
            location.setError("This field is required");
            return false;
        }
        if (TextUtils.isEmpty(length.getText())) {
            length.setError("This field is required");
            return false;
        }
        if (TextUtils.isEmpty(time.getText())) {
            time.setError("This field is required");
            return false;
        }
        // after all validation return true.
        return true;
    }
}