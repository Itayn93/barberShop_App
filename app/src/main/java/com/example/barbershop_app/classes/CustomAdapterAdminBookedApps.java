package com.example.barbershop_app.classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbershop_app.R;

//import java.util.ArrayList;
import java.util.List;

public class CustomAdapterAdminBookedApps extends RecyclerView.Adapter {

    List<Appointment> appointmentList;

    public CustomAdapterAdminBookedApps(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booked_apps_cards_layout,parent,false);
        ViewHolderClass viewHolderClass = new ViewHolderClass(view);

        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass viewHolderClass = (ViewHolderClass)holder;
        Appointment appointment = appointmentList.get(position);

        viewHolderClass.textViewName.setText(appointment.getUserName());
        viewHolderClass.textViewDate.setText(appointment.getDate());
        viewHolderClass.textViewHour.setText(appointment.getHour());
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public static class ViewHolderClass extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView textViewName;
        TextView textViewDate;
        TextView textViewHour;
        ImageView imageViewIcon;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardViewAppCard);
            textViewName = itemView.findViewById(R.id.textViewUserName);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewHour = itemView.findViewById(R.id.textViewHour);
            imageViewIcon = itemView.findViewById(R.id.imageViewScissors);
        }
    }







}
