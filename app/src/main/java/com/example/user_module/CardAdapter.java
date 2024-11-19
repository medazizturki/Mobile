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
import com.example.user_module.Database.CardDatabase;
import com.example.user_module.entity.Card;


import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private List<Card> cardList;
    private CardDatabase db;
    private Context context;
    private OnUpdateClickListener onUpdateClickListener;

    // Interface pour gérer le clic sur le bouton "Update"
    public interface OnUpdateClickListener {
        void onUpdateClick(Card card);
    }

    // Constructeur
    public CardAdapter(Context context, List<Card> cardList, CardDatabase db, OnUpdateClickListener onUpdateClickListener) {
        this.cardList = cardList;
        this.db = db;
        this.context = context;
        this.onUpdateClickListener = onUpdateClickListener;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row6, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        // Get the current card item
       Card card = cardList.get(position);

        // Set card details in the UI components
        holder.title.setText(card.title);
        holder.description.setText(card.description);
        holder.amount.setText(card.amount);

        // Load image with Glide, showing a placeholder if the imageUri is invalid or missing
        if (card.imageUri != null && !card.imageUri.isEmpty()) {
            Uri imageUri = Uri.parse(card.imageUri);
            Glide.with(context)
                    .load(imageUri)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.image);
        } else {
            holder.image.setImageResource(R.drawable.ic_launcher_background);
        }

        // Set a click listener on the entire item to open the CardDetails activity
        holder.itemView.setOnClickListener(v -> {
            Log.d("CardAdapter", "Item clicked: " + card.title);
            Intent intent = new Intent(holder.itemView.getContext(), CardDetails.class);
            intent.putExtra("title", card.title);
            intent.putExtra("description", card.description);
            intent.putExtra("amount", card.amount);
            intent.putExtra("imageUri", card.imageUri);
            holder.itemView.getContext().startActivity(intent);
        });

        // Handle the delete button click
        holder.deleteButton.setOnClickListener(v -> {
            Log.d("CardAdapter", "Delete button clicked for: " + card.title);
            deleteCard(card, position);
        });

        // Handle the update button click
        holder.updateButton.setOnClickListener(v -> onUpdateClickListener.onUpdateClick(card));
    }

    // Helper method to delete a car from the database and update the RecyclerView
    private void deleteCard(Card card, int position) {
        new Thread(() -> {
            // Delete card from database
            db.cardDao().delete(card);
            cardList.remove(position);

            // Update the RecyclerView on the main thread
            ((Activity) context).runOnUiThread(() -> {
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, cardList.size());
            });
        }).start();
    }


    public void updateCardList(List<Card> newCardList) {
        cardList.clear();
        cardList.addAll(newCardList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    // ViewHolder pour les éléments de la liste
    public static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, amount;
        ImageView image;
        ImageButton deleteButton, updateButton;

        public CardViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.b_title);
            description = itemView.findViewById(R.id.btn_desc);
            amount = itemView.findViewById(R.id.btn_auth);
            image = itemView.findViewById(R.id.image_tmb);
            deleteButton = itemView.findViewById(R.id.delete_button);
            updateButton = itemView.findViewById(R.id.update_button);
        }
    }
}
