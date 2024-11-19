package com.example.blogappll;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.blogappll.Database.ParticipantDatabase;
import com.example.blogappll.Entity.Participant;
import java.util.concurrent.Executors;

public class AddParticipation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_participation);

        ImageView backArrow = findViewById(R.id.back_arrow3);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        EditText teamName = findViewById(R.id.nomequipe);
        EditText leaderName = findViewById(R.id.leadername);
        EditText numberOfMembers = findViewById(R.id.nombremembres);


        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String equipe = teamName.getText().toString().trim();
                String leader = leaderName.getText().toString().trim();
                String membersString = numberOfMembers.getText().toString().trim();

                // Validate input
                if (equipe.isEmpty() || leader.isEmpty() || membersString.isEmpty()) {
                    Toast.makeText(AddParticipation.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                int members;
                try {
                    members = Integer.parseInt(membersString);
                } catch (NumberFormatException e) {
                    Toast.makeText(AddParticipation.this, "Invalid number of members", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create a Participant and add it to the database
                Participant participant = new Participant(equipe, leader, members);
                Executors.newSingleThreadExecutor().execute(() -> {
                    ParticipantDatabase.getInstance(AddParticipation.this).participantDao().insertParticipant(participant);
                    runOnUiThread(() -> {
                        Toast.makeText(AddParticipation.this, "Participation added successfully!", Toast.LENGTH_SHORT).show();

                        // Navigate back to Home Fragment
                        Intent intent = new Intent(AddParticipation.this, MainActivity.class);  // Assuming MainActivity hosts the Home fragment
                        intent.putExtra("navigate_to_home", true); // Optional: signal to navigate to Home
                        startActivity(intent);
                        finish();  // Close AddParticipation activity
                    });
                });

                // Optionally clear the form
                teamName.setText("");
                leaderName.setText("");
                numberOfMembers.setText("");
            }
        });
    }
}
