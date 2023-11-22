package com.example.assignment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HikeAdapter extends RecyclerView.Adapter<HikeAdapter.ViewHolder> {
    private ArrayList<HikeModal> hikeModalArrayList;
    private Context context;
    private OnItemClickListener mItemClickListener;
    public HikeAdapter(ArrayList<HikeModal> hikeModalArrayList, Context context, OnItemClickListener itemClickListener) {
        this.hikeModalArrayList = hikeModalArrayList;
        this.context = context;
        this.mItemClickListener = itemClickListener;
    }
    public void setFilteredList(ArrayList<HikeModal> filteredList){
        //updates the dataset with a new filtered list of HikeModal objects
        this.hikeModalArrayList = filteredList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_view, parent, false);
        return new ViewHolder(view, mItemClickListener);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HikeModal modal = hikeModalArrayList.get(position);
        holder.hikeName.setText(modal.getHikeName());
        holder.hikeLocation.setText(modal.getHikeLocation());
        holder.hikeDate.setText(modal.getHikeDate());

        //set text for a view (hikeParkingAvailable) based on the boolean value returned
        //if the value is true, it sets the text to "Parking Available"; otherwise, it sets it to "Parking Not Available"
        holder.hikeParkingAvailable.setText(modal.getHikeParkingAvailable() ? "Parking Available" : "Parking Not Available");

        holder.hikeLength.setText(modal.getHikeLength());
        holder.hikeLevel.setText(modal.getHikeLevel());
        holder.hikeTime.setText(modal.getHikeTime());
        holder.hikeDescription.setText(modal.getHikeDescription());
        holder.hikeAlert.setText(modal.getHikeAlert());

        holder.itemView.setTag(modal);
    }
    @Override
    public int getItemCount() {
        // returning the size of our array list
        return hikeModalArrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // creating variables for our text views.
        private TextView hikeName, hikeLocation, hikeDate, hikeParkingAvailable, hikeLength, hikeLevel,
                hikeTime, hikeDescription, hikeAlert;
        private OnItemClickListener mListener;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            // initializing our text views
            hikeName = itemView.findViewById(R.id.tvName);
            hikeLocation = itemView.findViewById(R.id.tvLocation);
            hikeDate = itemView.findViewById(R.id.tvDate);
            hikeParkingAvailable = itemView.findViewById(R.id.tvParkingAvailability);
            hikeLength = itemView.findViewById(R.id.tvLength);
            hikeLevel = itemView.findViewById(R.id.tvLevel);
            hikeTime = itemView.findViewById(R.id.tvTime);
            hikeDescription = itemView.findViewById(R.id.tvDescription);
            hikeAlert = itemView.findViewById(R.id.tvAlert);

            this.mListener = listener;
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v){
            if (mListener != null){
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                }
            }
        }
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }
    // Method to update the dataset and notify the adapter
    public void UpdateList(ArrayList<HikeModal> newHikeList) {
        hikeModalArrayList = newHikeList;
        notifyDataSetChanged();
    }
}