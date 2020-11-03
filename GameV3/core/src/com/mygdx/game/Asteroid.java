package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

import java.awt.*;
import java.util.Random;

public class Asteroid implements Disposable {
    private Animation<TextureRegion> asteroidAnimation;
    private Texture asteroid;
    private float stateTime;
    public int asteroidX;
    public int asteroidY;
    private int speed;
    public boolean remove = false;


    // Constant rows and columns of the sprite sheet
    private static final int FRAME_COLS = 8, FRAME_ROWS = 8;

    public Asteroid(int speed) {
        this.speed = speed;
        create();
    }

    /**
     *  This method upload a sprite sheet which is split in order to have animation effect
     */
    public void create() {
        // Load the sprite sheet as a Texture

        asteroid = new Texture(Gdx.files.internal("asteroid-sheet.png"));

        //>
        //> Use the split utility method to create a 2D array of TextureRegions. This is
        //> possible because this sprite sheet contains frames of equal size and they are
        //>all aligned.
        //>
        TextureRegion[][] tmp = TextureRegion.split(asteroid,
                asteroid.getWidth() / FRAME_COLS,
                asteroid.getHeight() / FRAME_ROWS);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] asteroid = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                asteroid[index++] = tmp[i][j];
            }
        }

        // Initialize the Animation with the frame interval and array of frames
        asteroidAnimation = new Animation<TextureRegion>(0.025f, asteroid);


    //Random Value of X in order to create asteriod
    Random random = new Random();
    asteroidX=20 + (int)(Math.random() * (((Gdx.graphics.getWidth()-20) - 20) + 1));
    asteroidY=Gdx.graphics.getHeight();
}

    public void render(SpriteBatch batch){

        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

        // Get current frame of animation for the current stateTime
        TextureRegion currentFrame = asteroidAnimation.getKeyFrame(stateTime,true);

        asteroidY-=speed*Gdx.graphics.getDeltaTime();

        if(asteroidY>0) {
            batch.draw(currentFrame, asteroidX, asteroidY, 25, 25);
            Rectangle asteroidRectangle = new Rectangle(asteroidX, asteroidY, 25, 25);
        }
        if(asteroidY<0)
            remove=true;

    }

    @Override
    public void dispose() {
        asteroid.dispose();
    }
}
