package com.lucasabbondanza.android.spaceshooter;

public class GameOverSprite extends Sprite {
    
    public GameOverSprite(Vec2d v) {
        super(v);
        loadBitmaps();
    }

    private void loadBitmaps() {
        BitmapRepo r = BitmapRepo.getInstance();
        BitmapSequence s = new BitmapSequence();
        s.addImage(r.getImage(R.drawable.over_frame_00), 0.001);
        s.addImage(r.getImage(R.drawable.over_frame_01), 0.001);
        s.addImage(r.getImage(R.drawable.over_frame_02), 0.001);
        s.addImage(r.getImage(R.drawable.over_frame_03), 0.001);
        s.addImage(r.getImage(R.drawable.over_frame_04), 0.001);
        s.addImage(r.getImage(R.drawable.over_frame_05), 0.001);
        s.addImage(r.getImage(R.drawable.over_frame_06), 0.001);
        s.addImage(r.getImage(R.drawable.over_frame_07), 0.001);
        s.addImage(r.getImage(R.drawable.over_frame_08), 0.001);
        s.addImage(r.getImage(R.drawable.over_frame_09), 0.001);
        //Removed frames that made the animation a bit awkward
        s.addImage(r.getImage(R.drawable.over_frame_12), 0.001);
        s.addImage(r.getImage(R.drawable.over_frame_13), 0.001);
        s.addImage(r.getImage(R.drawable.over_frame_14), 0.001);
        s.addImage(r.getImage(R.drawable.over_frame_15), 0.001);
        s.addImage(r.getImage(R.drawable.over_frame_17), 0.001);
        s.addImage(r.getImage(R.drawable.over_frame_19_final), 100000);
        setBitmaps(s);
    }

    @Override
    public boolean isDangerous() {
        return false;
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void makeDead() {

    }
}
