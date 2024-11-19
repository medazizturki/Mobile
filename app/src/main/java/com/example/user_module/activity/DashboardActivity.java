package com.example.user_module.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.user_module.MainActivity;
import com.example.user_module.MainActivity4;
import com.example.user_module.R;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard); // The layout with the included navbar

        // Access the navbar elements
        ImageView backArrowButton = findViewById(R.id.backArrowButton);
        TextView navbarTitle = findViewById(R.id.navbarTitle);
        ImageView menuButton = findViewById(R.id.menuButton);

        // Set up the back button action
        backArrowButton.setOnClickListener(v -> finish()); // Navigates back to the previous screen

        // Customize the navbar title
        navbarTitle.setText("Dashboard"); // Customize the title for the specific page

        // Set up the menu button action
        menuButton.setOnClickListener(v -> showMenu()); // Opens the menu when clicked
    }

    private void showMenu() {
        PopupMenu popupMenu = new PopupMenu(this, findViewById(R.id.menuButton));
        popupMenu.getMenuInflater().inflate(R.menu.dashboard_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_profile) {
                // Navigate to ProfileActivity
                startActivity(new Intent(DashboardActivity.this, ProfileActivity.class));
                return true;
            }else if (item.getItemId() == R.id.menu_tournoi) {
                // Navigate to ProfileActivity
                startActivity(new Intent(DashboardActivity.this, MainActivity.class));
                return true;
            }else if (item.getItemId() == R.id.menu_cart) {
                // Navigate to ProfileActivity
                startActivity(new Intent(DashboardActivity.this, MainActivity4.class));
                return true;
            }
            return false;
        });
        popupMenu.show();
    }
}
