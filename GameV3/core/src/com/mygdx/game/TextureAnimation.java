package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

import java.awt.*;

public class TextureAnimation implements Disposable {

    private Animation<TextureRegion> animationOne;

    private int frameNumberOne;
    private float stateTime;
    private int textureSpeedY;
    private int textureX;
    private int textureY;
    private int counter=0;
    private int textureWidth;
    private int textureHeight;

    private String nameFrame;

    private TextureRegion[] framesOne;


    private Texture texture;

    private boolean frameOne=true;

    private boolean remove=false;
    private boolean stop;




    public TextureAnimation(int frameNumberOne,String nameFrame,int textureSpeedY,int textureX,int textureY
    ,int textureWidth,int textureHeight, boolean stop){
        this.frameNumberOne=frameNumberOne;
        this.nameFrame=nameFrame;
        this.textureX=textureX;
        this.textureY=textureY;
        this.textureSpeedY=textureSpeedY;
        this.textureWidth=textureWidth;
        this.textureHeight=textureHeight;
        this.stop=stop;
        create();
    }


    public void create() {
        framesOne = new TextureRegion[frameNumberOne];


        for (int n = 0; n < frameNumberOne; n++) {
            String fileName = nameFrame + "" + n + ".png";
            texture = new Texture(fileName);
            framesOne[n] = new TextureRegion(texture);
        }


        animationOne = new Animation<TextureRegion>(0.05f, framesOne);



    }


    public void renderFirst(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

        // Get current frame of animation for the current stateTime
        TextureRegion currentFrameOne = animationOne.getKeyFrame(stateTime,true);

        textureY-=textureSpeedY*Gdx.graphics.getDeltaTime();
        batch.draw(currentFrameOne, textureX,textureY, textureWidth, textureHeight);
        Rectangle textureRectangle = new Rectangle(textureX, textureY, textureWidth, textureHeight);
        if (textureY<10 && stop!=true)
            remove=true;

        counter++;
        if (counter==frameNumberOne && stop)
            remove=true;

    }




    public boolean isFrameOne() {
        return frameOne;
    }



    public int getTextureX() {
        return textureX;
    }


    public int getTextureY() {
        return textureY;
    }



    public boolean isRemove() {
        return remove;
    }


    public int getTextureHeight() {
        return textureHeight;
    }



    public int getTextureWidth() {
        return textureWidth;
    }


    @Override
    public void dispose() {
        texture.dispose();
    }
}
