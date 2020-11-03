package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

import java.awt.*;

public class Bullet implements Disposable {
    public float bulletX,bulletY;
    private final int speed=400;
    public Sprite fire;



    public Bullet(float x,float y){
        this.bulletX=x;
        this.bulletY=y;
        create();
    }

    /**
     * Load the texture of player bullet
     */
    public void create(){

        fire=new Sprite(new Texture(Gdx.files.internal("laser2.png")));
        fire.setBounds(bulletX,bulletY - 25/2,25,20);

    }

    /**
     *  Changes the vertical position of Bullet depending on the speed
     * @param batch
     */
    public void render(SpriteBatch batch){
        bulletY+=speed*Gdx.graphics.getDeltaTime();
        fire.setPosition(bulletX - fire.getWidth()/2,bulletY);
        fire.draw(batch);
    }

    /**
     *
     */
    public void getRectangle() {
        Rectangle bulletRectangle=fire.getBoundingRectangle();
    }

    @Override
    public void dispose() {
        fire.getTexture().dispose();
        fire = null;
    }
}
