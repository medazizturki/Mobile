package com.example.user_module;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.Toast;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.room.Room;


import com.example.user_module.Database.CardDatabase;
import com.example.user_module.entity.Card;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

//import javax.mail.Transport;
//import javax.mail.internet.MimeMessage;


public class Publish4 extends AppCompatActivity {

    private CardDatabase db;
    private Uri imageUri;
    private ImageView imageView;
    private int cardId = -1;  // Utilisé pour identifier si on est en mode ajout ou mise à jour

    // Declare constants
    private static final int REQUEST_CODE_PERMISSIONS = 101;
    private static final int REQUEST_CODE_SELECT_IMAGE = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish4);
        db = Room.databaseBuilder(getApplicationContext(), CardDatabase.class, "card_database")
                .allowMainThreadQueries()  // Pour les tests uniquement
                .build();

        imageView = findViewById(R.id.image_tmb);
        EditText titleField = findViewById(R.id.b_title);
        EditText descField = findViewById(R.id.btn_desc);
        EditText amountField = findViewById(R.id.btn_auth);


        // Get reference to the back arrow ImageView
        ImageView backArrow = findViewById(R.id.backArrowButton);  // Assuming you added the ImageView for the back arrow in XML
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();  // This will take the user back to the previous activity or fragment
            }
        });

        // Vérifier si un card_id est passé pour la mise à jour
        Intent intent = getIntent();
        if (intent.hasExtra("card_id")) {
            cardId = intent.getIntExtra("card_id", -1);
            loadCardData(cardId);  // Charger les données du card existant
        }

        Button btnSelectImage = findViewById(R.id.b_selectimage);
        btnSelectImage.setOnClickListener(v -> checkPermissionsAndSelectImage());

        // Gérer l'action du bouton publier
        Button btnPublish = findViewById(R.id.btn_pub);
        btnPublish.setText(cardId != -1 ? "UPDATE" : "PUBLISH");  // Changer le texte du bouton en fonction du mode
        btnPublish.setOnClickListener(v -> {
            if (validateFields()) {  // Vérifier la validité du formulaire avant de publier
                if (cardId != -1) {
                    updateCard(titleField.getText().toString(), descField.getText().toString(), amountField.getText().toString());
                } else {
                    insertCard(titleField.getText().toString(), descField.getText().toString(), amountField.getText().toString());
                }
                returnToHome();  // Retourner à l'écran d'accueil
            }
        });


    }

    private void checkPermissionsAndSelectImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_CODE_PERMISSIONS);
            } else {
                selectImage();
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSIONS);
            } else {
                selectImage();
            }
        } else {
            selectImage(); // Pas de permissions nécessaires
        }
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            selectImage();
        } else {
            Toast.makeText(this, "Permission denied. Cannot select images.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            if (imageUri != null) {
                getContentResolver().takePersistableUriPermission(
                        imageUri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                );
                try {
                    InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    imageView.setVisibility(View.VISIBLE);
                    imageView.setImageBitmap(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void loadCardData(int id) {
        Card card = db.cardDao().getCardById(id);
        if (card != null) {
            ((EditText) findViewById(R.id.b_title)).setText(card.title);
            ((EditText) findViewById(R.id.btn_desc)).setText(card.description);
            ((EditText) findViewById(R.id.btn_auth)).setText(card.amount);
            if (card.imageUri != null) {
                imageUri = Uri.parse(card.imageUri);
                imageView.setImageURI(imageUri);
                imageView.setVisibility(View.VISIBLE);
            }
        }
    }

    private void insertCard(String title, String description, String amount) {
        Card card = new Card();
        card.title = title;
        card.description = description;
        card.amount = amount;
        card.imageUri = imageUri != null ? imageUri.toString() : null;
        db.cardDao().insert(card);
        Toast.makeText(this, "card published successfully", Toast.LENGTH_SHORT).show();


    }

    private void updateCard(String title, String description, String amount) {
        Card card = db.cardDao().getCardById(cardId);
        if (card != null) {
            card.title = title;
            card.description = description;
            card.amount = amount;
            if (imageUri != null) {
                card.imageUri = imageUri.toString();
            }
            db.cardDao().update(card);
            Toast.makeText(this, "Card updated successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateFields() {
        EditText titleField = findViewById(R.id.b_title);
        EditText descField = findViewById(R.id.btn_desc);
        EditText amountField = findViewById(R.id.btn_auth);

        if (titleField.getText().toString().isEmpty()) {
            titleField.setError("Title is required");
            return false;
        }
        if (descField.getText().toString().isEmpty()) {
            descField.setError("Description is required");
            return false;
        }
        if (amountField.getText().toString().isEmpty()) {
            amountField.setError("Amount is required");
            return false;
        }
        return true;
    }

    private void returnToHome() {
        Intent intent = new Intent(this, MainActivity4.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }





}
