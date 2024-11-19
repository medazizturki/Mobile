package com.example.user_module;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.user_module.Database.TournoiDatabase;
import com.example.user_module.entity.Tournoi;
import java.util.List;

public class TournoiAdapter extends RecyclerView.Adapter<TournoiAdapter.TournoiViewHolder> {

    private List<Tournoi> TournoiList;
    private TournoiDatabase db;
    private Context context;
    private OnUpdateClickListener onUpdateClickListener;

    // Interface to handle the click on the "Update" button
    public interface OnUpdateClickListener {
        void onUpdateClick(Tournoi Tournoi);
    }

    // Constructor
    public TournoiAdapter(Context context, List<Tournoi> TournoiList, TournoiDatabase db, OnUpdateClickListener onUpdateClickListener) {
        this.TournoiList = TournoiList;
        this.db = db;
        this.context = context;
        this.onUpdateClickListener = onUpdateClickListener;
    }

    @NonNull
    @Override
    public TournoiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row1, parent, false);
        return new TournoiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TournoiViewHolder holder, int position) {
        Tournoi Tournoi = TournoiList.get(position);

        holder.title.setText(Tournoi.title);
        holder.description.setText(Tournoi.description);
        holder.prix.setText(Tournoi.prix);
        holder.nombrePlace.setText(Tournoi.nombrePlace);

        if (Tournoi.imageUri != null && !Tournoi.imageUri.isEmpty()) {
            Uri imageUri = Uri.parse(Tournoi.imageUri);
            Glide.with(context)
                    .load(imageUri)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.image);
        } else {
            holder.image.setImageResource(R.drawable.ic_launcher_background);
        }

        holder.itemView.setOnClickListener(v -> {
            Log.d("TournoiAdapter", "Item clicked: " + Tournoi.title);
            Intent intent = new Intent(holder.itemView.getContext(), TournoiDetails.class);
            intent.putExtra("title", Tournoi.title);
            intent.putExtra("description", Tournoi.description);
            intent.putExtra("prix", Tournoi.prix);
            intent.putExtra("Nombre Place", Tournoi.nombrePlace);
            intent.putExtra("imageUri", Tournoi.imageUri);
            holder.itemView.getContext().startActivity(intent);
        });

        holder.deleteButton.setOnClickListener(v -> {
            Log.d("TournoiAdapter", "Delete button clicked for: " + Tournoi.title);
            deleteTournoi(Tournoi, position);
        });

        // Update button now launches PublishActivity for updating
        holder.updateButton.setOnClickListener(v -> {
            Intent updateIntent = new Intent(holder.itemView.getContext(), Publish.class);
            updateIntent.putExtra("Tournoi_id", Tournoi.id); // Pass the Tournoi ID
            holder.itemView.getContext().startActivity(updateIntent);
        });
    }

    // Helper method to delete a Tournoi from the database and update the RecyclerView
    private void deleteTournoi(Tournoi Tournoi, int position) {
        new Thread(() -> {
            // Delete Tournoi from database
            db.TournoiDao().delete(Tournoi);
            TournoiList.remove(position);

            // Update the RecyclerView on the main thread
            ((Activity) context).runOnUiThread(() -> {
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, TournoiList.size());
            });
        }).start();
    }

    public void updateTournoiList(List<Tournoi> newTournoiList) {
        Log.d("TournoiAdapter", "Updating list with " + newTournoiList.size() + " items.");
        TournoiList.clear();
        TournoiList.addAll(newTournoiList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return TournoiList.size();
    }

    // ViewHolder for list items
    public static class TournoiViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, prix, nombrePlace;
        ImageView image;
        ImageButton deleteButton, updateButton;

        public TournoiViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.b_title);
            description = itemView.findViewById(R.id.btn_desc);
            prix = itemView.findViewById(R.id.btn_auth);
            image = itemView.findViewById(R.id.image_tmb);
            nombrePlace = itemView.findViewById(R.id.btn_nombreplace);
            deleteButton = itemView.findViewById(R.id.delete_button);
            updateButton = itemView.findViewById(R.id.update_button);
        }
    }
}
