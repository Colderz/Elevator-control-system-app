package com.arkadiuszzimny.elevatorcontrolsystemapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.arkadiuszzimny.elevatorcontrolsystemapp.data.entities.ElevatorItem;
import com.arkadiuszzimny.elevatorcontrolsystemapp.databinding.ElevatorCardLayoutBinding;
import java.util.ArrayList;
import java.util.List;

public class ElevatorRecyclerAdapter extends RecyclerView.Adapter<ElevatorRecyclerAdapter.ViewHolder> {
    public List<ElevatorItem> elevators = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        return new ViewHolder(ElevatorCardLayoutBinding.inflate(li, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ElevatorItem currentElevator = elevators.get(position);
        holder.textViewNumber.setText(String.valueOf(currentElevator.getId()));
        holder.textViewCurrentFloor.setText(String.valueOf(currentElevator.getCurrentFloor()));
        switch (currentElevator.getState()) {
            case 0:
                holder.textViewState.setText("Empty");
                break;
            case 1:
                holder.textViewState.setText("UP");
                break;
            case -1:
                holder.textViewState.setText("DOWN");
                break;
        }
        /*if(currentElevator.getTargetFloors().get(0).equals("-1")) {
            holder.textViewOrders.setText("LACK");
        } else {
            holder.textViewOrders.setText(String.valueOf(currentElevator.getTargetFloors()));
        }*/
        holder.textViewOrders.setText(String.valueOf(currentElevator.getTargetFloors()));
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
        private TextView textViewCurrentFloor;
        private TextView textViewState;
        private TextView textViewOrders;

        public ViewHolder(ElevatorCardLayoutBinding b) {
            super(b.getRoot());
            textViewNumber = b.idElev;
            textViewCurrentFloor = b.currentFloor;
            textViewState = b.state;
            textViewOrders = b.orders;
        }
    }
}
