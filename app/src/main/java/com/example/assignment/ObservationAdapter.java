package com.example.assignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ObservationAdapter extends RecyclerView.Adapter<ObservationAdapter.ViewHolder> {
    private ArrayList<Observation> observationArrayList;
    private Context context;
    private ObservationAdapter.OnItemClickListener mItemClickListener;

    public ObservationAdapter(ArrayList<Observation> observationArrayList, Context context, ObservationAdapter.OnItemClickListener mItemClickListener) {
        this.observationArrayList = observationArrayList;
        this.context = context;
        this.mItemClickListener = mItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.observation_item, parent, false);
        return new ViewHolder(view, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Observation observation = observationArrayList.get(position);
        holder.observationName.setText(observation.getObservationName());
        holder.observationTime.setText(observation.getObservationTime());
        holder.observationComment.setText(observation.getObservationComment());

        holder.itemView.setTag(observation);
    }

    @Override
    public int getItemCount() {
        return observationArrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // creating variables for our text views.
        private TextView observationName, observationTime, observationComment;
        private ObservationAdapter.OnItemClickListener mListener;

        public ViewHolder(@NonNull View itemView, ObservationAdapter.OnItemClickListener listener) {
            super(itemView);
            // initializing our text views
            observationName = itemView.findViewById(R.id.tvObservationName);
            observationTime = itemView.findViewById(R.id.tvObservationTime);
            observationComment = itemView.findViewById(R.id.tvObservationComment);

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
    public void setOnItemClickListener(ObservationAdapter.OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }
    public void updateObservationList(ArrayList<Observation> newObservationList) {
        observationArrayList = newObservationList;
        notifyDataSetChanged();
    }
}