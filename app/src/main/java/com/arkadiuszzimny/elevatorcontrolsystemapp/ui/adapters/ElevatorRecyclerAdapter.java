package com.arkadiuszzimny.elevatorcontrolsystemapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arkadiuszzimny.elevatorcontrolsystemapp.R;
import com.arkadiuszzimny.elevatorcontrolsystemapp.data.entities.ElevatorItem;
import com.arkadiuszzimny.elevatorcontrolsystemapp.databinding.ElevatorCardLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class ElevatorRecyclerAdapter extends RecyclerView.Adapter<ElevatorRecyclerAdapter.ViewHolder> {
    private List<ElevatorItem> elevators = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        return new ViewHolder(ElevatorCardLayoutBinding.inflate(li));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ElevatorItem currentElevator = elevators.get(position);
        holder.textViewNumber.setText(String.valueOf(currentElevator.getId()));
    }

    @Override
    public int getItemCount() {
        return elevators.size();
    }

    public void setElevators(List<ElevatorItem> elevators) {
        this.elevators = elevators;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNumber;
        private TextView textViewPassenger;

        public ViewHolder(ElevatorCardLayoutBinding b) {
            super(b.getRoot());
            textViewNumber = b.idElev;
            textViewPassenger = b.passengers;
        }
    }
}
