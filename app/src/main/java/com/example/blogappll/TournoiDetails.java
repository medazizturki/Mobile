package com.example.blogappll;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TournoiDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_details);


        // Get references to the UI components
        TextView title = findViewById(R.id.blog_details_title);
        TextView description = findViewById(R.id.blog_details_description);
        TextView prix = findViewById(R.id.blog_details_author);
        ImageView image = findViewById(R.id.blog_details_image);

        // Get reference to the back arrow ImageView
        ImageView backArrow = findViewById(R.id.back_arrow);  // Assuming you added the ImageView for the back arrow in XML

        // Set click listener for the back arrow
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();  // This will take the user back to the previous activity or fragment
            }

        });

        // Receive data from the intent
        Intent intent = getIntent();
        String TournoiTitle = intent.getStringExtra("title");
        String TournoiDescription = intent.getStringExtra("description");
        String Tournoiprix = intent.getStringExtra("prix");
        String imageUri = intent.getStringExtra("imageUri");

        // Set the data in UI components
        title.setText(TournoiTitle != null ? TournoiTitle : "No Title");
        description.setText(TournoiDescription != null ? TournoiDescription : "No Description");
        prix.setText(Tournoiprix != null ? Tournoiprix : "No Prix");

        // Display the image, if available
        if (imageUri != null && !imageUri.isEmpty()) {
            Uri uri = Uri.parse(imageUri);
            image.setImageURI(uri);  // Set the image URI to the ImageView
        } else {
            image.setImageResource(R.drawable.ic_launcher_background);  // Default image if no URI
        }

        // Find the "Participate" button and set the click listener
        Button participateButton = findViewById(R.id.btn_participate); // Make sure this ID matches your layout
        participateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TournoiDetails.this, AddParticipation.class);

                // Pass data to the AddParticipation activity
                intent.putExtra("title", TournoiTitle);
                intent.putExtra("description", TournoiDescription);
                intent.putExtra("prix", Tournoiprix);
                intent.putExtra("imageUri", imageUri);

                startActivity(intent);
            }
        });
    }
}
