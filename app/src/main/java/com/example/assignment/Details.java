package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Details extends AppCompatActivity implements ObservationAdapter.OnItemClickListener{
    // Declaring variables
    private HikeModal hikeModal;
    private DBHandle dbHandle;
    private ArrayList<Observation> observationArrayList;
    private RecyclerView observationsRV;
    private ObservationAdapter observationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Retrieve the selected Hike data
        Intent intent = getIntent();
        HikeModal selectedHike = (HikeModal) intent.getSerializableExtra("SELECTED_HIKE");

        hikeModal = new HikeModal();
        dbHandle = new DBHandle(Details.this);
        hikeModal = dbHandle.readHikeById(selectedHike.getId());

        // Initializing UI elements
        TextView name = findViewById(R.id.tvName);
        TextView location = findViewById(R.id.tvLocation);
        TextView date = findViewById(R.id.tvDate);
        TextView parking = findViewById(R.id.tvParkingAvailability);
        TextView length = findViewById(R.id.tvLength);
        TextView level = findViewById(R.id.tvLevel);
        TextView time = findViewById(R.id.tvTime);
        TextView desc = findViewById(R.id.tvDescription);
        TextView alert = findViewById(R.id.tvAlert);
        // Setting TextViews with hike details
        name.setText(hikeModal.getHikeName());
        location.setText(hikeModal.getHikeLocation());
        date.setText(hikeModal.getHikeDate());
        parking.setText(hikeModal.getHikeParkingAvailable()? "Parking Available" : "Parking Not Available");
        length.setText(hikeModal.getHikeLength());
        level.setText(hikeModal.getHikeLevel());
        time.setText(hikeModal.getHikeTime());
        desc.setText(hikeModal.getHikeDescription());
        alert.setText(hikeModal.getHikeAlert());

        Button edit = findViewById(R.id.btnEdit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirecting to the Edit activity to modify hike details
                Intent intentToEdit = new Intent(Details.this, Edit.class);
                intentToEdit.putExtra("SELECTED_HIKE", hikeModal); // Pass the selected hike details
                startActivity(intentToEdit);
            }
        });

        Button delete = findViewById(R.id.btnDelete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Displaying a confirmation dialog before deleting the hike
                showDeleteConfirmationDialog(hikeModal.getId());
            }
        });

        Button addObservation = findViewById(R.id.btnObservation);
        addObservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirecting to AddObservation activity to add an observation for the hike
                Intent intentToAddObservation = new Intent(Details.this, AddObservation.class);
                intentToAddObservation.putExtra("SELECTED_HIKE", hikeModal); // Pass the selected hike
                startActivity(intentToAddObservation);
            }
        });
        /*
        // Fetch observations for the selected hike
        observationArrayList = dbHandle.readObservationsForHike(hikeModal.getId());

        // Log the size of observations retrieved for debugging
        Log.d("ObservationSize", "Observation size: " + observationArrayList.size());

        // Find RecyclerView
        observationsRV = findViewById(R.id.idRVObservations);
        observationsRV.setLayoutManager(new LinearLayoutManager(this));

        // Set up the adapter with the retrieved observations
        observationAdapter = new ObservationAdapter(observationArrayList, this, this);
        observationsRV.setAdapter(observationAdapter);
        observationAdapter.setOnItemClickListener(this);*/

        // Populating dummy observations
        populateDummyObservations();
    }
    // Method to display a confirmation dialog before deleting the hike
    private void showDeleteConfirmationDialog(final int hikeId){
        new AlertDialog.Builder(this)
                .setTitle("Confirm Deletion")
                .setMessage("Are you sure you want to delete this hike?")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Call the DBHandler to delete the hike
                        dbHandle.deleteHike(hikeId);
                        Toast.makeText(Details.this, "Hike deleted successfully", Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }
    @Override
    public void onItemClick(int position) {

    }
    // Method to populate dummy observations for testing
    public void populateDummyObservations() {
        observationArrayList = new ArrayList<>();

        // Create dummy observations
        observationArrayList.add(new Observation(1, "Observation 1", "10:00 AM", "First observation"));
        observationArrayList.add(new Observation(2, "Observation 2", "11:30 AM", "Second observation"));
        observationArrayList.add(new Observation(3, "Observation 3", "01:45 PM", "Third observation"));

        // Set up RecyclerView and Adapter
        observationsRV = findViewById(R.id.idRVObservations);
        observationsRV.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter with the dummy data
        observationAdapter = new ObservationAdapter(observationArrayList, this, this);
        observationsRV.setAdapter(observationAdapter);
        observationAdapter.setOnItemClickListener(this);
    }
}