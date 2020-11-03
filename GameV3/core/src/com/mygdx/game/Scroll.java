package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

import java.util.Random;

public class Scroll implements Disposable {

    private int speed; // speed of stars
    public int scrollWidth,scrollHeight;
    private  Texture textureSprite;
    public  int scrollX,scrollY;
    private Sprite scrollSprite;
    private String name;
    private Random random;


    public Scroll(int width,int height,int speed,String name,int y){
        this.scrollWidth=width;
        this.scrollHeight=height;
        this.scrollY=y;
        this.speed=speed;
        this.name=name;
        create();
    }
    public void create(){

        textureSprite=new Texture(Gdx.files.internal(name));
        scrollSprite=new Sprite(textureSprite);
        scrollSprite.setSize(scrollHeight,scrollWidth);
        //>
        //> Creation  of random numbers
        //>
        random=new Random();
        scrollX=20 + (int)(Math.random() * (((Gdx.graphics.getWidth()-20) - 20) + 1));
        scrollY=Gdx.graphics.getHeight();

    }

    public void render(SpriteBatch batch){

        //Drawing
        scrollY-=speed*Gdx.graphics.getDeltaTime();
        scrollSprite.setPosition(scrollX,scrollY);
        scrollSprite.draw(batch);

    }

    @Override
    public void dispose() {
textureSprite.dispose();
        scrollSprite.getTexture().dispose();
    }
}
