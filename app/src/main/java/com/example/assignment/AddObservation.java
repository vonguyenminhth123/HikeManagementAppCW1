package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AddObservation extends AppCompatActivity {
    TextView tvObservationTime;
    Button btnObservationTime;
    EditText edtObservationName, edtObservationComment;
    Button btnObservationAdd;
    DBHandle dbHandle;
    boolean isAllFieldsFilled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_observation);

        tvObservationTime = findViewById(R.id.showObservationTime);
        btnObservationTime = findViewById(R.id.btnObservationTime);
        edtObservationName = findViewById(R.id.edtObservationName);
        edtObservationComment = findViewById(R.id.edtObservationComment);
        btnObservationAdd = findViewById(R.id.btnObservationAdd);

        dbHandle = new DBHandle(this);

        // Retrieve the selected Hike data
        Intent intent = getIntent();
        HikeModal selectedHike = (HikeModal) intent.getSerializableExtra("SELECTED_HIKE");

        btnObservationTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        btnObservationAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAllFieldsFilled = CheckAllFields();
                if (isAllFieldsFilled) {
                    try {
                        if (selectedHike != null) {
                            int hikeID = selectedHike.getId();
                            String observationName = edtObservationName.getText().toString();
                            String observationTime = tvObservationTime.getText().toString();
                            String observationComment = edtObservationComment.getText().toString();

                            final String TITLE = "Details Entered";
                            final String MESSAGE_FORMAT = "Information: \nName: %s\nTime: %s\nComment: %s\n";

                            String message = String.format(MESSAGE_FORMAT, observationName, observationTime, observationComment);

                            new AlertDialog.Builder(AddObservation.this)
                                    .setTitle(TITLE)
                                    .setMessage(message)
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    })
                                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dbHandle.addObservation(hikeID, observationName, observationTime, observationComment);
                                            Toast t = Toast.makeText(AddObservation.this, "New Observation has been added", Toast.LENGTH_SHORT);
                                            t.show();

                                            // Intent to navigate back to Details activity
                                            Intent intentBackToDetails = new Intent(AddObservation.this, Details.class);
                                            intentBackToDetails.putExtra("SELECTED_HIKE", selectedHike); // Pass the selected hike data
                                            startActivity(intentBackToDetails);
                                        }
                                    })
                                    .show();
                        } else {
                            Toast.makeText(AddObservation.this, "Selected Hike is null", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("AddObservation", "Error: " + e.getMessage());
                    }
                }
            }
        });
    }
    private void openDialog(){
        // Get the current time
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
                String formattedTime = String.format("%02d:%02d", hours, minutes);
                tvObservationTime.setText(formattedTime);
            }
        }, currentHour, currentMinute, true);
        dialog.show();
    }
    public boolean CheckAllFields() {
        if (TextUtils.isEmpty(edtObservationName.getText())) {
            edtObservationName.setError("This field is required");
            return false;
        }
        if (TextUtils.isEmpty(tvObservationTime.getText())) {
            tvObservationTime.setError("This field is required");
            return false;
        }
        // after all validation return true.
        return true;
    }
}