package walker;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.lucasabbondanza.android.project2.walker.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static walker.GameActivity.gameOnsound;
import static walker.PlayerSprite.dead;

class World {

    private List<Sprite> sprites;
    private PlayerSprite player;
    private int position_y;
    private double timer;

    private BitmapSequence game_over;

    private int enemy_count = 2;
    private int enemy_killed = 0;

    public World() {
        sprites = new ArrayList<>();
        sprites.add(player = new PlayerSprite(new Vec2d(200,500)));
        sprites.add( new HeartSprite(new Vec2d(2600,0)));
        sprites.add( new HeartSprite(new Vec2d(2400,0)));
        sprites.add( new HeartSprite(new Vec2d(2200,0)));

        //spawnEnemy();
    }

    private void create_enemy(int enemy_count){
        Random rand = new Random();
        float x = player.getPosition().getX();
        int  position_y = rand.nextInt(2000) + 500;
        for (int i=1 ;i<= enemy_count; i++){
            sprites.add(new AlienSprite(new Vec2d(x + 3000,  (position_y * i) /2)));
        }
    }

    private void create_stars(){
        int stars_count = 2;
        Random rand = new Random();
        float x = player.getPosition().getX();
        int  position_y = rand.nextInt(2000) + 500;
        for (int i=1 ;i<= stars_count; i++){
            sprites.add(new StarSpirte(new Vec2d(x + 3000,  (position_y * i) /2)));
        }
    }

    private void spawnEnemy() {
        float x = player.getPosition().getX();
        sprites.add(new AlienSprite(new Vec2d(x + 3000 , 100)));
        sprites.add(new AlienSprite(new Vec2d(x + 3000 , 550)));
        sprites.add(new AlienSprite(new Vec2d(x + 3000 , 1000)));
    }

    public void tick(double dt) {
        MotionEvent e = TouchEventQueue.getInstance().dequeue();
        if (e != null)
            handleMotionEvent(e);
        for(Sprite s: sprites)
            s.tick(dt);
        timer += dt;
        if (timer > 0){
            timer = -1;
            create_stars();
            create_enemy(enemy_count);
            //spawnEnemy();
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


    /**
     * When the user touches the screen, this message is sent.  Probably you
     * want to tell the player to do something?
     *
     * @param e the MotionEvent corresponding to the touch
     */
    private void handleMotionEvent(MotionEvent e) {


    }

    public void draw(Canvas c) {
        Bitmap bg = BitmapRepo.getInstance().getImage(R.drawable.background);
        float x = player.getPosition().getX();
        c.translate(-x+100, 0);
        int backgroundNumber = (int)(x / bg.getWidth());
        c.drawBitmap(bg, bg.getWidth()*(backgroundNumber-1), 0,  null);
        c.drawBitmap(bg, bg.getWidth()*backgroundNumber, 0,  null);
        c.drawBitmap(bg, bg.getWidth()*(backgroundNumber+1), 0,  null);
        for(Sprite s: sprites)
            s.draw(c);

        //position_y = (int) x;
        if(dead){
            BitmapRepo r = BitmapRepo.getInstance();
            game_over = new BitmapSequence();
            game_over.addImage(r.getImage(R.drawable.gameover), 100);
            player.setBitmaps(game_over);
            gameOnsound.stop();


            //gameOversound.start();
        }
    }

}

