package com.lucasabbondanza.android.spaceshooter;

public class EnemySprite extends Sprite {

    private static BitmapSequence deadSequence;

    private int xVelocity;
    private int yVelocity;
    private int deadTime;
    private boolean dead;


    EnemySprite(Vec2d p) {
        super(p);
        loadBitmaps();
        xVelocity = 0;
        yVelocity = 1000;
        dead = false;
        deadTime = 0;
    }

    private void loadBitmaps() {
        BitmapRepo r = BitmapRepo.getInstance();
        BitmapSequence s = new BitmapSequence();
        s.addImage(r.getImage(R.drawable.enemy), 0.1);

        deadSequence = new BitmapSequence();
        deadSequence.addImage(r.getImage(R.drawable.boom_frame_00), 0.01);
        deadSequence.addImage(r.getImage(R.drawable.boom_frame_01), 0.01);
        deadSequence.addImage(r.getImage(R.drawable.boom_frame_02), 0.01);
        deadSequence.addImage(r.getImage(R.drawable.boom_frame_03), 0.01);
        deadSequence.addImage(r.getImage(R.drawable.boom_frame_04), 0.01);
        deadSequence.addImage(r.getImage(R.drawable.boom_frame_05), 0.01);
        deadSequence.addImage(r.getImage(R.drawable.boom_frame_06), 0.01);
        deadSequence.addImage(r.getImage(R.drawable.boom_frame_07), 0.01);
        deadSequence.addImage(r.getImage(R.drawable.boom_frame_08), 0.01);
        deadSequence.addImage(r.getImage(R.drawable.boom_frame_09), 0.01);
        deadSequence.addImage(r.getImage(R.drawable.boom_frame_10), 0.01);
        deadSequence.addImage(r.getImage(R.drawable.boom_frame_11), 0.01);
        deadSequence.addImage(r.getImage(R.drawable.blank), 10);
        setBitmaps(s);
    }

    private void path() {

    }

    @Override
    public void tick(double dt) {
        super.tick(dt);
        setPosition(getPosition().add(new Vec2d(xVelocity *dt, yVelocity *dt)));
        if(dead) {
            deadTime++;
            if(deadTime > 10)
                removeTime = true;
        }
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void resolve(Collision collision, Sprite other) {
        if(!dead && other instanceof BulletSprite && !(other instanceof EnemyBulletSprite)) {
            makeDead();
            other.makeDead();
        }
    }

    public void makeDead() {
        dead = true;
        xVelocity = 0;
        yVelocity = 0;
        setBitmaps(deadSequence);
    }

    @Override
    public boolean isDead() {
        return dead;
    }

    @Override
    public int getDeadTime() { return deadTime;}

}
