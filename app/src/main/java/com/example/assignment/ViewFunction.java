package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
public class ViewFunction extends AppCompatActivity implements HikeAdapter.OnItemClickListener {
    // Declaring variables
    private ArrayList<HikeModal> hikeModalArrayList;
    private DBHandle dbHandler;
    private HikeAdapter hikeAdapter;
    private RecyclerView hikesRV;
    private Button btnClearAll, btnAdd;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_hike);
        // Initializing UI elements
        btnClearAll = findViewById(R.id.btnClearAll);
        btnAdd = findViewById(R.id.btnAdd);
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        // Initializing variables and retrieving hike data from the database
        hikeModalArrayList = new ArrayList<>();
        dbHandler = new DBHandle(ViewFunction.this);
        hikeModalArrayList = dbHandler.readHike();

        // Setting up RecyclerView
        hikesRV = findViewById(R.id.idRVHikes);
        hikesRV.setLayoutManager(new LinearLayoutManager(this));
        hikeAdapter = new HikeAdapter(hikeModalArrayList, this, this);
        hikesRV.setAdapter(hikeAdapter);

        btnClearAll.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                // Confirmation dialog for clearing all hikes
                new AlertDialog.Builder(ViewFunction.this)
                        .setTitle("Delete Confirmation")
                        .setMessage("Are you sure you want to clear all?")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Deleting all hikes and refreshing RecyclerView
                                dbHandler.deleteAllHikes();
                                refreshRecyclerView();
                            }
                        })
                        .show();
            }
        });
        btnAdd.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                // Redirecting to the Add activity to add a new hike
                Intent intentToAdd = new Intent(ViewFunction.this, Add.class);
                startActivity(intentToAdd);
            }
        });

        // Click listener for items in the RecyclerView
        hikeAdapter.setOnItemClickListener(new HikeAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(int position) {
                // Retrieve the selected Hike
                HikeModal selectedHike = hikeModalArrayList.get(position);

                // Pass the selected hike data to the EditActivity
                Intent detailsIntent = new Intent(ViewFunction.this, Details.class);
                detailsIntent.putExtra("SELECTED_HIKE", selectedHike);
                startActivity(detailsIntent);
            }
        });
    }
    // Method to filter the hike list based on the search query
    private void filterList(String text) {
        refreshRecyclerView();
        ArrayList<HikeModal> filteredList = new ArrayList<>();
        for (HikeModal hike : hikeModalArrayList){
            // Filtering hikes based on name, location, length, and date
            if (hike.getHikeName().toLowerCase().contains(text.toLowerCase()) ||
            hike.getHikeLocation().toLowerCase().contains(text.toLowerCase()) ||
            hike.getHikeLength().toLowerCase().contains(text.toLowerCase()) ||
            hike.getHikeDate().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(hike);
            }
        }
        if (filteredList.isEmpty()){
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        } else {
            hikeAdapter.setFilteredList(filteredList);
        }
    }
    @Override
    public void onItemClick(int position) {
        // Not handling item click in this activity
    }
    @Override
    protected void onResume() {
        super.onResume();
        refreshRecyclerView();
    }
    // Method to refresh the RecyclerView with updated hike data
    private void refreshRecyclerView() {
        ArrayList<HikeModal> hikes = dbHandler.readHike();
        hikeAdapter.UpdateList(hikes);
    }
}