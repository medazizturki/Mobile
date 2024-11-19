package com.example.user_module;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.example.user_module.Database.TournoiDatabase;
import com.example.user_module.activity.DashboardActivity;
import com.example.user_module.entity.Tournoi;
import java.util.List;

public class Home extends Fragment {

    private static final String TAG = "HomeFragment";
    private TournoiDatabase db;
    private RecyclerView recyclerView;
    private TournoiAdapter adapter;

    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: Home fragment is created.");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize Room database
        db = Room.databaseBuilder(getContext(), TournoiDatabase.class, "Tournoi_database")
                .fallbackToDestructiveMigration()
                .build();

        // Load Tournoi from the database and update UI
        loadTournois();

        // Find the "Plus" button and set the click listener
        ImageButton buttonPlus = view.findViewById(R.id.button_plus);
        buttonPlus.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Publish.class);
            startActivity(intent);
        });

        // Locate the "retour" button and set the click listener
        ImageView retourButton = view.findViewById(R.id.backArrowButton);
        retourButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), DashboardActivity.class);
            startActivity(intent);
        });

        return view;
    }

    private void loadTournois() {
        Log.d(TAG, "Fetching Tournoi from the database...");

        new Thread(() -> {
            try {
                List<Tournoi> Tournois = db.TournoiDao().getAllTournois();
                Log.d(TAG, "Tournois loaded: " + Tournois.size());

                if (Tournois.isEmpty()) {
                    Log.d(TAG, "No Tournois found in the database.");
                }

                getActivity().runOnUiThread(() -> {
                    if (adapter == null) {
                        // Initialize the adapter and handle the update button clicks
                        adapter = new TournoiAdapter(getActivity(), Tournois, db, Tournoi -> {
                            // Create an intent to open the Publish activity in update mode
                            Intent intent = new Intent(getActivity(), Publish.class);
                            intent.putExtra("Tournoi_id", Tournoi.id);  // Pass the Tournoi ID for updating
                            startActivity(intent);
                        });
                        recyclerView.setAdapter(adapter);
                    } else {
                        adapter.updateTournoiList(Tournois);
                    }
                });
            } catch (Exception e) {
                Log.e(TAG, "Error fetching Tournois", e);
            }
        }).start();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: Home fragment resumed.");
        loadTournois();
    }
}
