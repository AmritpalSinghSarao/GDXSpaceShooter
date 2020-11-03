package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

public class GamePlayer {
    private Sprite gamePlayer;
    private Array<Bullet> bulletplayer;
    private Sound shootSound;
    private boolean playerAlive;


    public GamePlayer(Sprite sprite, Array<Bullet> bullet, Sound shoot,boolean playerAlive){
        this.gamePlayer=sprite;
        this.bulletplayer=bullet;
        this.shootSound=shoot;
        this.playerAlive=playerAlive;
    }


    /**
     *  Game player input
     */
    public void inputUser() {
        // following
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && gamePlayer.getX()>0)
            gamePlayer.translateX(-5);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && gamePlayer.getX()<Gdx.graphics.getWidth()-gamePlayer.getWidth())
            gamePlayer.translateX(5);

        //Whenever space key is pressed then new bullet is created
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && playerAlive ) {
            bulletplayer.add(new Bullet(gamePlayer.getX() + gamePlayer.getWidth() / 2 , gamePlayer.getY() + gamePlayer.getHeight()));
            shootSound.play();
        }
    }
}
