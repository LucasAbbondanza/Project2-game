package com.lucasabbondanza.android.spaceshooter;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ScoresActivity extends AppCompatActivity {

    TextView scoreText;
    TextView endlessText;
    TextView enemies;
    TextView stars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        scoreText = findViewById(R.id.highscore);
        endlessText = findViewById(R.id.highscore_endless);
        enemies = findViewById(R.id.enemies_destroyed);
        stars = findViewById(R.id.stars_collected);

        scoreText.setText("Highscore: " + Database.getDatabase().getHighscore());
        endlessText.setText("Endless Highscore: " + Database.getDatabase().getHighscore_endless());
        enemies.setText("Enemies Destroyed: " + Database.getDatabase().getStat_enemies_destroyed());
        stars.setText("Stars Collected: " + Database.getDatabase().getStat_stars_collected());
    }

}
