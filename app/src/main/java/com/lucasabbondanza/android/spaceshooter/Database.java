package com.lucasabbondanza.android.spaceshooter;

import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Database implements Serializable {

    private static Database database;

    public static Database getDatabase(){
        if(database == null) {
            database = new Database();
        }
        return database;
    }

    public static void load(File f) throws IOException, ClassNotFoundException {
        ObjectInputStream oin = null;
        try {
            oin = new ObjectInputStream(new FileInputStream(f));
            database = (Database) oin.readObject();
        } finally {
            if(oin != null) {
                try {
                    oin.close();
                } catch (IOException e) {
                    //Ignore
                }
            }
        }
    }

    public static void createSample() {
        database = new Database();
    }

    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        score += points;
    }

    public void resetScore() {
        score = 0;
    }

    public boolean getMusicSetting() {
        return music;
    }

    public void setMusicSetting(boolean setting) {
        music = setting;
    }

    public boolean isEndless() { return endless; }

    public void  setEndless(boolean bool) { endless = bool; }

    public int getHighscore() {
        return highscore;
    }

    public int getHighscore_endless() {
        return highscore_endless;
    }

    public int getStat_enemies_destroyed() {
        return stat_enemies_destroyed;
    }

    public int getStat_stars_collected() {
        return stat_stars_collected;
    }

    public int getTimes_played() {
        return times_played;
    }

    public int getTimes_won() {
        return times_won;
    }

    public void updateStats(boolean win, int enemies, int stars) {
        if(score > highscore_endless && endless)
            highscore_endless = score;
        else if(!endless && score > highscore)
            highscore = score;
        if(win)
            times_won++;
        times_played++;
        stat_enemies_destroyed += enemies;
        stat_stars_collected += stars;
    }

    private int score;
    private boolean music;
    private boolean endless;
    private int highscore;
    private int highscore_endless;
    private int times_won;
    private int times_played;
    private int stat_enemies_destroyed;
    private int stat_stars_collected;

    private Database() {
        score = 0;
        highscore = 0;
        highscore_endless = 0;
        music = true;
        times_played = 0;
        times_won = 0;
        stat_stars_collected = 0;
        stat_enemies_destroyed = 0;
    }

    public void save(File f) throws IOException {
        ObjectOutputStream oout = null;
        try {
            oout = new ObjectOutputStream(new FileOutputStream(f));
            oout.writeObject(this);
            oout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(oout != null) {
                try {
                    oout.close();
                } catch (IOException e) {
                    //Ignore
                }
            }
        }
    }



}
