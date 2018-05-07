package com.lucasabbondanza.android.spaceshooter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.TextureView;

import java.util.ArrayList;
import java.util.List;

class World {
    private List<Sprite> sprites;
    private List<Sprite> delete;
    private PlayerSprite player;
    private TextureView view;

    private int shootTick;

    public World(TextureView textureView) {
        view = textureView;
        sprites = new ArrayList<>();
        delete = new ArrayList<>();
        shootTick = 0;
        sprites.add(new EnemySprite(new Vec2d(100, 100)));
        sprites.add(new EnemySprite(new Vec2d(1000, 100)));
        //The player needs to be the last sprite added so it is drawn on top of most things
        sprites.add(player = new PlayerSprite(new Vec2d(500,2000), view));
    }

    public void tick(double dt) {
        if(player.isShooting() && !player.isDead()) {
            shootTick++;
            if(shootTick%2 == 0) {
                Bullet pBullet = new Bullet(new Vec2d(0,0));
                float x = player.getPosition().getX()+(player.getBoundingBox().width()/2)-pBullet.getBoundingBox().width();
                float y = player.getPosition().getY();
                sprites.add(new Bullet(new Vec2d(x, y)));
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
        for(Sprite s: sprites) {
            if(s instanceof Bullet && s.isDead()) {
                delete.add(s);
            }else if(s.getPosition().getY() < player.getPosition().getY()-3000) {
                delete.add(s);
            }else if (s.getPosition().getY() > player.getPosition().getY()+1000){
                delete.add(s);
            }
        }
        if(!delete.isEmpty() && !player.isDead()) {
            for (Sprite s : delete) {
                Log.d("Sprite Cleanup", "Removing " + s);
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
        if(e.getY() > view.getHeight()/2) {
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

    public void draw(Canvas c) {
        Bitmap bg = BitmapRepo.getInstance().getImage(R.drawable.galaxy1);
        float y = player.getPosition().getY();
        if(!player.isDead())
            c.translate(0, -y+2000);
        int backgroundNumber = (int)(y / bg.getHeight());
        c.drawBitmap(bg, 0, bg.getHeight()*(backgroundNumber-3),  null);
        c.drawBitmap(bg, 0, bg.getHeight()*(backgroundNumber-2),  null);
        c.drawBitmap(bg, 0, bg.getHeight()*(backgroundNumber-1),  null);
        c.drawBitmap(bg, 0, bg.getHeight()*backgroundNumber,  null);
        c.drawBitmap(bg, 0, bg.getHeight()*(backgroundNumber+1),  null);
        for(Sprite s: sprites)
            s.draw(c);
    }
}
