package com.lucasabbondanza.android.spaceshooter;

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

    private int score;
    private boolean music;

    private Database() {
        score = 0;
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
