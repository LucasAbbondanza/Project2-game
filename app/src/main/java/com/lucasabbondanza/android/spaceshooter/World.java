package com.lucasabbondanza.android.spaceshooter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

class World {
    private List<Sprite> sprites;
    private PlayerSprite player;

    public World() {
        sprites = new ArrayList<>();
        sprites.add(new EnemySprite(new Vec2d(100, 100)));
        sprites.add(new EnemySprite(new Vec2d(1000, 100)));
        //The player needs to be the last sprite added so it is drawn on top of most things
        sprites.add(player = new PlayerSprite(new Vec2d(500,2000)));
    }

    public void tick(double dt) {
        MotionEvent e = TouchEventQueue.getInstance().dequeue();
        if (e != null)
            handleMotionEvent(e);
        for(Sprite s: sprites) {
            s.tick(dt);
        }
        resolveCollisions();
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
        if(e.getY() > 1200) {
            if (e.getActionMasked() == MotionEvent.ACTION_UP)
                player.stop();
            else {
                player.move(e.getX());
            }
        } else if (e.getY() <= 1200){
            player.fire(!player.isShooting());
            while(e.getActionMasked() == MotionEvent.ACTION_DOWN){
                float x = player.getPosition().getX() + 50;
                float y = player.getPosition().getY();
                sprites.add(new Bullet(new Vec2d(x, y)));
            }


        }
    }

    public void draw(Canvas c) {
        Bitmap bg = BitmapRepo.getInstance().getImage(R.drawable.background);
        float y = player.getPosition().getY();
        if(!player.isDead())
            c.translate(0, -y+2000);
        int backgroundNumber = (int)(y / bg.getHeight());
        c.drawBitmap(bg, 0, bg.getHeight()*(backgroundNumber-1),  null);
        c.drawBitmap(bg, 0, bg.getHeight()*backgroundNumber,  null);
        c.drawBitmap(bg, 0, bg.getHeight()*(backgroundNumber+1),  null);
        for(Sprite s: sprites)
            s.draw(c);
    }
}
