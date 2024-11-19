package com.example.blogappll;

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

import com.example.blogappll.Database.TournoiDatabase;
import com.example.blogappll.Entity.Tournoi;

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

        return view;
    }

    private void loadTournois() {
        Log.d(TAG, "Fetching Tournoi from the database...");

        new Thread(() -> {
            try {
                List<Tournoi> Tournois = db.TournoiDao().getAllTournois();
                Log.d(TAG, "Tournois loaded: " + Tournois.size());

                getActivity().runOnUiThread(() -> {
                    if (adapter == null) {
                        // Initialiser l'adaptateur et gérer les clics pour mise à jour
                        adapter = new TournoiAdapter(getActivity(), Tournois, db, Tournoi -> {
                            // Créer une intention pour ouvrir Publish en mode mise à jour
                            Intent intent = new Intent(getActivity(), Publish.class);
                            intent.putExtra("Tournoi_id", Tournoi.id);  // Passer l'ID du Tournoi pour mise à jour
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
