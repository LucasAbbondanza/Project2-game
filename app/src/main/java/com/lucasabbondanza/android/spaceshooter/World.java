package com.lucasabbondanza.android.spaceshooter;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class World {
    private List<Sprite> sprites;
    private List<BlueEnemySprite> blues;
    private List<Sprite> delete;
    private PlayerSprite player;
    private GameMessageSprite message;
    private final TextureView view;
    private SoundPool soundPool;

    int[] sounds;

    private int shootTick;
    private int enemySpawnTimer;
    private int starSpawnTimer;
    private int redSpawnTimer;
    private int blueSpawnTimer;
    private int bluesLeft;
    private int enemiesSpawned;
    private int enemiesDestroyed;
    private int starsSpawned;
    private int starsGrabbed;
    private int level;
    private int timer;
    private boolean music;
    private boolean endless;
    private boolean win;
    private boolean gameOver;
    private float playerY;

    public World(TextureView textureView) {
        view = textureView;
        sprites = new ArrayList<>();
        delete = new ArrayList<>();
        blues = new ArrayList<>();
        shootTick = 0;
        enemiesSpawned = 0;
        enemySpawnTimer = 0;
        enemiesDestroyed = 0;
        starsSpawned = 0;
        starsGrabbed = 0;
        redSpawnTimer = 0;
        blueSpawnTimer = 0;
        starSpawnTimer = 0;
        bluesLeft = 0;
        level = 0;
        timer = 0;
        win = false;
        gameOver = false;
        music = Database.getDatabase().getMusicSetting();
        endless = Database.getDatabase().isEndless();
        sprites.add(player = new PlayerSprite(new Vec2d(500, 2000), view));
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(4)
                .setAudioAttributes(attributes)
                .build();
        sounds = new int[4];
        sounds[0] = soundPool.load(view.getContext(), R.raw.fire, 1);
        sounds[1] = soundPool.load(view.getContext(), R.raw.explosion, 1);
        sounds[2] = soundPool.load(view.getContext(), R.raw.collect, 1);
        sounds[3] = soundPool.load(view.getContext(), R.raw.opening, 1);
        if(music)
            soundPool.play(sounds[3], 1, 1, 1, 0, 1);
    }

    public void tick(double dt) {
        timer++;
        if(player.isShooting() && !player.isDead() && !player.isDone()) {
            shootTick++;
            if(shootTick%6 == 0) {
                BulletSprite pBullet = new BulletSprite(new Vec2d(0,0));
                float x = player.getPosition().getX()+(player.getBoundingBox().width()/2)-pBullet
                        .getBoundingBox().width();
                float y = player.getPosition().getY();
                if(Database.getDatabase().getMusicSetting())
                    soundPool.play(sounds[0], 1, 1, 1, 0, 1);
                sprites.add(new BulletSprite(new Vec2d(x, y)));
                shootTick = 0;
            }
        }
        MotionEvent e = TouchEventQueue.getInstance().dequeue();
        if (e != null)
            handleMotionEvent(e);
        for(Sprite s: sprites) {
            s.tick(dt);
        }
        resolveCollisions();
        enemySpawnTimer++;
        starSpawnTimer++;
        redSpawnTimer++;
        blueSpawnTimer++;
        if(endless && !player.isDead()) {
            //Endless Mode
            if (enemySpawnTimer > 10) {
                enemySpawnTimer = 0;
                spawn_gray();
            }
            if (starSpawnTimer > 30) {
                starSpawnTimer = 0;
                spawn_star();
            }
            if (redSpawnTimer > 60) {
                redSpawnTimer = 0;
                spawn_red();
            }
            if (blueSpawnTimer > 200) {
                blueSpawnTimer = 0;
                spawn_blue();
            }
        } else {
            switch (level) {
                case 0:
                    GameMessageSprite forSize = new GameMessageSprite(new Vec2d(0, 0), view, 1);
                    sprites.add(message = new GameMessageSprite(
                            new Vec2d(0,
                                    player.getPosition().getY()
                                            -(view.getHeight())+forSize.getBoundingBox().height()), view, 1));
                    level++;
                case 1: level1();
                        break;
                case 2: level2();
                        break;
                case 3: level3();
                        break;
                case 4: win();
                        break;
            }
        }
        for(Sprite s: sprites) {
            if(s instanceof BlueEnemySprite) {
                blues.add((BlueEnemySprite) s);
            }
            if(s.isDead() && s.getDeadTime() < 1) {
                if(s instanceof PlayerSprite || s instanceof EnemySprite) {
                    soundPool.play(sounds[1], 1, 1, 1, 0, 1);
                } else if(s instanceof StarSprite) {
                    soundPool.play(sounds[2], 2, 2, 1, 0, 1);
                }
            }
            if(s.isRemoveTime()) {
                delete.add(s);
            }else if(s.getPosition().getY() < player.getPosition().getY()-3000) {
                delete.add(s);
            }else if (s.getPosition().getY() > player.getPosition().getY()+1000){
                delete.add(s);
            }
        }
        for(Sprite s: blues) {
            if(blueSpawnTimer%20 == 0 && !player.isDead()) {
                sprites.add(new EnemyBulletSprite(
                        new Vec2d(s.getPosition().getX() + (s.getBoundingBox().width() / 2) - (new EnemyBulletSprite(new Vec2d(0, 0)).getBoundingBox().width() / 2),
                                s.getPosition().getY())));
                soundPool.play(sounds[0], 1, 1, 1, 0, 2);
            }
        }
        blues.clear();
        if(!delete.isEmpty()) {
            for (Sprite s : delete) {
                if (s instanceof PlayerSprite) {
                    //Game Over
                    playerY = player.getPosition().getY();
                    GameMessageSprite forSize = new GameMessageSprite(new Vec2d(0, 0), view, 0);
                    sprites.add(message = new GameMessageSprite(
                            new Vec2d(0,
                                    player.getPosition().getY()
                                            -(view.getHeight()/2)+forSize.getBoundingBox().height()), view, 0));
                    Database.getDatabase().updateStats(false, enemiesDestroyed, starsGrabbed);
                    try {
                        Database.getDatabase().save(new File(view.getContext().getFilesDir(), "database"));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        Toast.makeText(view.getContext(), "Error saving data", Toast.LENGTH_SHORT);
                    }
                    gameOver = true;
                }
                if(s.isDead() && !player.isDead()) {
                    if(s instanceof EnemySprite) {
                        enemiesDestroyed++;
                    } else if (s instanceof StarSprite) {
                        starsGrabbed++;
                    }
                }
                if(s instanceof BlueEnemySprite)
                    bluesLeft--;
                sprites.remove(s);
            }
            delete.clear();
        }
    }

    private void resolveCollisions() {
        ArrayList<Collision> collisions = new ArrayList<>();
        for(int i=0; i < sprites.size()-1; i++)
            for(int j=i+1; j < sprites.size(); j++) {
                Sprite s1 = sprites.get(i);
                Sprite s2 = sprites.get(j);

                if (s1.collidesWith(s2))
                    collisions.add(new Collision(s1, s2));
            }

        for(Collision c: collisions) c.resolve();
    }

    private void handleMotionEvent(MotionEvent e) {
        if(!player.isDead()) {
            if (e.getY() > view.getHeight() / 2) {
                if (e.getActionMasked() == MotionEvent.ACTION_UP)
                    player.stop();
                else {
                    player.move(e.getX());
                }
            } else {
                if (e.getActionMasked() == MotionEvent.ACTION_DOWN)
                    player.fire(!player.isShooting());
            }
        }
    }

    private void spawn_gray(){
        Random rand = new Random();
        float x = rand.nextInt(view.getWidth()-(int)(new EnemySprite(new Vec2d(0,0)).getBoundingBox().width()));
        float y = player.getPosition().getY()-view.getHeight();
        sprites.add(new EnemySprite(new Vec2d(x,  y)));
        enemiesSpawned++;
    }

    private void spawn_red(){
        Random rand = new Random();
        float x = rand.nextInt(view.getWidth()-(int)(new EnemySprite(new Vec2d(0,0)).getBoundingBox().width()));
        float y = player.getPosition().getY()-view.getHeight();
        sprites.add(new RedEnemySprite(new Vec2d(x,  y), view, rand.nextBoolean()));
        enemiesSpawned++;
    }

    private void spawn_blue(){
        Random rand = new Random();
        boolean side = rand.nextBoolean();
        float x;
        if(side) {
            x = 0 - (new EnemySprite(new Vec2d(0, 0)).getBoundingBox().width());
        } else {
            x = view.getWidth();
        }
        float y = player.getPosition().getY()-view.getHeight() + (rand.nextInt(view.getHeight()/2) + 350);
        sprites.add(new BlueEnemySprite(new Vec2d(x,  y), view, side));
        enemiesSpawned++;
        bluesLeft++;
    }

    private void spawn_star(){
        Random rand = new Random();
        float x = rand.nextInt(view.getWidth()-(int)(new StarSprite(new Vec2d(0,0)).getBoundingBox().width()));
        float y = player.getPosition().getY()-view.getHeight();
        sprites.add(new StarSprite(new Vec2d(x,  y)));
        starsSpawned++;
    }

    private void level1() {
        if(!player.isDead() && timer > 80 && enemiesSpawned < 30) {
            if (enemySpawnTimer > 10) {
                enemySpawnTimer = 0;
                spawn_gray();
            }
            if (starSpawnTimer > 30) {
                starSpawnTimer = 0;
                spawn_star();
            }
            if (enemiesSpawned > 29) {
                GameMessageSprite forSize = new GameMessageSprite(new Vec2d(0, 0), view, 2);
                sprites.add(message = new GameMessageSprite(
                        new Vec2d(0,
                                player.getPosition().getY()
                                        -(view.getHeight())+forSize.getBoundingBox().height()), view, 2));
                level++;
                timer = 0;
                enemySpawnTimer = 0;
                starSpawnTimer = 0;
                redSpawnTimer = 0;
                blueSpawnTimer = 0;
            }
        }
    }

    private void level2() {
        if(!player.isDead() && timer > 80 && enemiesSpawned < 100) {
            if (enemySpawnTimer > 10) {
                enemySpawnTimer = 0;
                spawn_gray();
            }
            if (starSpawnTimer > 30) {
                starSpawnTimer = 0;
                spawn_star();
            }
            if (redSpawnTimer > 60) {
                redSpawnTimer = 0;
                spawn_red();
            }
            if (enemiesSpawned > 99) {
                GameMessageSprite forSize = new GameMessageSprite(new Vec2d(0, 0), view, 3);
                sprites.add(message = new GameMessageSprite(
                        new Vec2d(0,
                                player.getPosition().getY()
                                        -(view.getHeight())+forSize.getBoundingBox().height()), view, 3));
                level++;
                timer = 0;
                enemySpawnTimer = 0;
                starSpawnTimer = 0;
                redSpawnTimer = 0;
                blueSpawnTimer = 0;
            }
        }
    }

    private void level3() {
        if(!player.isDead() && timer > 80 && enemiesSpawned < 160) {
            if (starSpawnTimer > 30) {
                starSpawnTimer = 0;
                spawn_star();
            }
            if (redSpawnTimer > 40) {
                redSpawnTimer = 0;
                spawn_red();
            }
            if (blueSpawnTimer > 100) {
                blueSpawnTimer = 0;
                spawn_blue();
            }
        }
        if (bluesLeft < 1 && enemiesSpawned > 159) {
            level++;
            timer = 0;
            enemySpawnTimer = 0;
            starSpawnTimer = 0;
            redSpawnTimer = 0;
            blueSpawnTimer = 0;
        }
    }

    private void win() {
        if(!player.isDead() && !win && timer > 80) {
            player.win();
            win = true;
            GameMessageSprite forSize = new GameMessageSprite(new Vec2d(0, 0), view, 4);
            sprites.add(message = new GameMessageSprite(
                    new Vec2d(0,
                            player.getPosition().getY()
                                    -(view.getHeight()/2)+forSize.getBoundingBox().height()), view, 4));
            Database.getDatabase().addScore(10000);
            Database.getDatabase().updateStats(true, enemiesDestroyed, starsGrabbed);
            try {
                Database.getDatabase().save(new File(view.getContext().getFilesDir(), "database"));
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(view.getContext(), "Error saving data", Toast.LENGTH_SHORT);
            }
            gameOver = true;
        }
    }

    public void draw(Canvas c) {
        Bitmap bg = BitmapRepo.getInstance().getImage(R.drawable.galaxy1);
        c.translate(0, -(player.getPosition().getY()+100)+view.getHeight()-200);
        int backgroundNumber = (int)((player.getPosition().getY()+100) / bg.getHeight());
        c.drawBitmap(bg, 0, bg.getHeight()*(backgroundNumber-3),  null);
        c.drawBitmap(bg, 0, bg.getHeight()*(backgroundNumber-2),  null);
        c.drawBitmap(bg, 0, bg.getHeight()*(backgroundNumber-1),  null);
        c.drawBitmap(bg, 0, bg.getHeight()*backgroundNumber,  null);
        c.drawBitmap(bg, 0, bg.getHeight()*(backgroundNumber+1),  null);
        for(Sprite s: sprites)
            s.draw(c);
        if(gameOver) {
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.FILL);
            paint.setTextSize(100);
            c.drawText("Score: " + Database.getDatabase().getScore(), 400, player.getPosition().getY() - 700, paint);
        }
    }
}
