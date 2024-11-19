package com.example.user_module.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.user_module.R;

public class ProfileActivity extends AppCompatActivity {
    private TextView profileName, profileSurname, profilePhone;
    private EditText editProfileName, editProfileSurname, editProfilePhone;
    private Button editButton, saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize views
        profileName = findViewById(R.id.profileName);
        profileSurname = findViewById(R.id.profileSurname);
        profilePhone = findViewById(R.id.profilePhone);

        editProfileName = findViewById(R.id.editProfileName);
        editProfileSurname = findViewById(R.id.editProfileSurname);
        editProfilePhone = findViewById(R.id.editProfilePhone);

        editButton = findViewById(R.id.editButton);
        saveButton = findViewById(R.id.saveButton);

        editButton.setOnClickListener(v -> {
            toggleEditMode(true);
        });

        saveButton.setOnClickListener(v -> {
            profileName.setText("Name: " + editProfileName.getText().toString());
            profileSurname.setText("Surname: " + editProfileSurname.getText().toString());
            profilePhone.setText("Phone: " + editProfilePhone.getText().toString());

            toggleEditMode(false);
        });
    }

    private void toggleEditMode(boolean isEditing) {
        profileName.setVisibility(isEditing ? View.GONE : View.VISIBLE);
        profileSurname.setVisibility(isEditing ? View.GONE : View.VISIBLE);
        profilePhone.setVisibility(isEditing ? View.GONE : View.VISIBLE);

        editProfileName.setVisibility(isEditing ? View.VISIBLE : View.GONE);
        editProfileSurname.setVisibility(isEditing ? View.VISIBLE : View.GONE);
        editProfilePhone.setVisibility(isEditing ? View.VISIBLE : View.GONE);

        editButton.setVisibility(isEditing ? View.GONE : View.VISIBLE);
        saveButton.setVisibility(isEditing ? View.VISIBLE : View.GONE);
    }
}

