package com.arkadiuszzimny.elevatorcontrolsystemapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.arkadiuszzimny.elevatorcontrolsystemapp.R;
import com.arkadiuszzimny.elevatorcontrolsystemapp.data.entities.ElevatorItem;
import com.arkadiuszzimny.elevatorcontrolsystemapp.databinding.ElevatorCardLayoutBinding;
import java.util.ArrayList;
import java.util.List;

public class ElevatorRecyclerAdapter extends RecyclerView.Adapter<ElevatorRecyclerAdapter.ViewHolder> {

    /**
     * A public variable because it must be accessed by simulation button listeners etc.
     */
    public List<ElevatorItem> elevators = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        return new ViewHolder(ElevatorCardLayoutBinding.inflate(li, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        /**
         * an animation for circle FrameLayout
         */
        holder.circleFrameNumber.setAnimation(AnimationUtils.loadAnimation(holder.circleFrameNumber.getContext(), R.anim.fade_transition_animation));

        /**
         * an animation for the whole card
         */
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(holder.cardView.getContext(), R.anim.fade_scale_animation));

        ElevatorItem currentElevator = elevators.get(position);
        holder.textViewNumber.setText(String.valueOf(currentElevator.getId()));
        holder.textViewCurrentFloor.setText(String.valueOf(currentElevator.getCurrentFloor()));
        switch (currentElevator.getState()) {
            case 0:
                holder.textViewState.setText(R.string.empty);
                break;
            case 1:
                holder.textViewState.setText(R.string.up);
                break;
            case -1:
                holder.textViewState.setText(R.string.down);
                break;
        }
        if(String.valueOf(currentElevator.getTargetFloors().get(0)).equals("-1")) {
            holder.textViewOrders.setText(R.string.lack);
        } else {
            holder.textViewOrders.setText(String.valueOf(currentElevator.getTargetFloors()));
        }
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
        private FrameLayout circleFrameNumber;
        private CardView cardView;

        public ViewHolder(ElevatorCardLayoutBinding b) {
            super(b.getRoot());
            textViewNumber = b.idElev;
            textViewCurrentFloor = b.currentFloor;
            textViewState = b.state;
            textViewOrders = b.orders;
            circleFrameNumber = b.circleNumber;
            cardView = b.cardView;
        }
    }
}
