package com.example.user_module;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.user_module.Database.CardDatabase;
import com.example.user_module.entity.Card;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UserCreditsActivity extends AppCompatActivity {

    private TextView creditsTextView;
    private int currentCredits = 0;

    // Executor to perform database operations on a background thread
    private Executor executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_credits);

        creditsTextView = findViewById(R.id.creditsTextView);
        Button redeemButton = findViewById(R.id.redeemButton);
        Button goToBackOfficeButton = findViewById(R.id.BackOfficeButton);

        redeemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRedeemDialog();
            }
        });

        // Set click listener for the Back Office button
        goToBackOfficeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Explicit intent to go to BackOfficeActivity
                Intent intent = new Intent(UserCreditsActivity.this, Home4.class);
                startActivity(intent);
            }
        });
    }

    private void openRedeemDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Redeem Card");

        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_redeem_card, null);
        builder.setView(customLayout);

        builder.setPositiveButton("Submit", (dialog, which) -> {
            TextView codeInput = customLayout.findViewById(R.id.codeInput);
            String code = codeInput.getText().toString().trim();
            redeemCard(code);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void redeemCard(String code) {
        // Perform the database query on a background thread using Executor
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Query the database for the card by description (code)
                Card card = CardDatabase.getInstance(UserCreditsActivity.this).cardDao().getCardByDescription(code);

                if (card != null) {
                    // Check if the card is already redeemed
                    if (!card.isRedeemed()) {
                        // Add credits and mark the card as redeemed
                        currentCredits += Integer.parseInt(card.getAmount());
                        card.setRedeemed(true);

                        // Update the card in the database
                        CardDatabase.getInstance(UserCreditsActivity.this).cardDao().update(card);

                        // Update the UI with the new credits and success message
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                creditsTextView.setText("Credits: " + currentCredits);
                                Toast.makeText(UserCreditsActivity.this, "Card redeemed successfully!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        // Show error if the card is already redeemed
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(UserCreditsActivity.this, "Card has already been redeemed.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    // Show error if the card is invalid (not found in the database)
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(UserCreditsActivity.this, "Invalid code.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
