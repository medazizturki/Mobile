package com.example.user_module;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.user_module.Database.TournoiDatabase;
import com.example.user_module.entity.Tournoi;

import java.util.List;

public class YourCustomAdapter extends RecyclerView.Adapter<YourCustomAdapter.ViewHolder> {

    private List<Tournoi> TournoiList;
    private TournoiDatabase db;  // Add TournoiDatabase instance
    private Context context;  // Add a Context field

    // Single constructor
    public YourCustomAdapter(Context context, List<Tournoi> TournoiList, TournoiDatabase db) {
        this.TournoiList = TournoiList;
        this.context = context;  // Initialize the Context
        this.db = db;  // Initialize the database instance
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tournoi Tournoi = TournoiList.get(position);
        holder.title.setText(Tournoi.title);
        holder.description.setText(Tournoi.description);
        holder.prix.setText(Tournoi.prix);
        holder.nombrePlace.setText(Tournoi.nombrePlace);

        holder.deleteButton.setOnClickListener(v -> {
            // Perform deletion on a background thread
            new Thread(() -> {
                db.TournoiDao().delete(Tournoi); // Delete from the database

                // Update the list and notify the adapter on the main thread
                TournoiList.remove(position);
                ((Activity) context).runOnUiThread(() -> {  // Use context to run on the UI thread
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, TournoiList.size());
                });
            }).start();
        });
    }


    @Override
    public int getItemCount() {
        return TournoiList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, prix,nombrePlace;
        ImageButton deleteButton;  // Add the delete button reference
        ImageView image;
        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.b_title);
            description = itemView.findViewById(R.id.btn_desc);
            prix = itemView.findViewById(R.id.btn_auth);
            nombrePlace = itemView.findViewById(R.id.btn_nombreplace);

            deleteButton = itemView.findViewById(R.id.delete_button);  // Bind the delete button
        }
    }
}
