package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

import java.util.Random;

public class FireRealistic implements Disposable {

    private Animation<TextureRegion> fireAnimation;
    private Texture fire;
    private float stateTime;
    public int x;
    public int y;
    public boolean remove=false;
    private int counter=0;

    // Constant rows and columns of the sprite sheet
    private static final int FRAME_COLS = 7, FRAME_ROWS = 7;

    public FireRealistic(int x,int y){
        this.x=x;
        this.y=y;
        create();
    }

    public void create(){
        // Load the sprite sheet as a Texture

        fire = new Texture(Gdx.files.internal("realistic-fire.png"));

        // Use the split utility method to create a 2D array of TextureRegions. This is
        // possible because this sprite sheet contains frames of equal size and they are
        // all aligned.
        TextureRegion[][] tmp = TextureRegion.split(fire,
                fire.getWidth() / FRAME_COLS,
                fire.getHeight() / FRAME_ROWS);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] fire = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                fire[index++] = tmp[i][j];
            }
        }

        // Initialize the Animation with the frame interval and array of frames
        fireAnimation = new Animation<TextureRegion>(0.025f, fire);

    }

    public void render(SpriteBatch batch){

        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

        // Get current frame of animation for the current stateTime
        TextureRegion currentFrame = fireAnimation.getKeyFrame(stateTime,false);



            batch.draw(currentFrame,x,y,25,25);


            // after counting 50 frames sprite must be removed
        if(counter>50)
            remove=true;

        counter++;
    }

    @Override
    public void dispose() {
        fire.dispose();
    }
}

