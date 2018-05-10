package com.lucasabbondanza.android.spaceshooter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainMenuFragment extends Fragment {

    private Context context;
    private Button start_button;
    private Button scores_button;
    private Button options_button;
    private Button endless_button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(android.view.LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_main_menu, container, false);
        view.setBackgroundResource(R.drawable.menu_background);
        AnimationDrawable background = (AnimationDrawable) view.getBackground();
        background.start();
        start_button = view.findViewById(R.id.start_button);
        scores_button = view.findViewById(R.id.scores_button);
        options_button = view.findViewById(R.id.options_button);
        endless_button = view.findViewById(R.id.endless_button);

        start_button.setOnClickListener((View v) -> {
            Database.getDatabase().setEndless(false);
            ((MainActivity) view.getContext())
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new GameFragment(), "Game")
                    .commit();
        });

        endless_button.setOnClickListener((View v) -> {
            Database.getDatabase().setEndless(true);
            ((MainActivity) view.getContext())
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new GameFragment(), "Game")
                    .commit();
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        context = null;
    }

}
