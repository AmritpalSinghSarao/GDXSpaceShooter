package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

public class BulletEnemy implements Disposable {

    public int bulletEnemyX,bulletEnemyY;
    private final int speedBullet=250;
    private Sprite spriteBullet;
    private String pathTexture;
    private int bulletEnemyWidth=15,
            bulletEnemyHeight=20;

    public BulletEnemy(int bulletX,int bulletY,String path){
        this.pathTexture=path;
        this.bulletEnemyX=bulletX;
        this.bulletEnemyY=bulletY;
        create();
    }

    /**
     *  load the Enemy bullet on Sprite
     */
    public void create(){
            spriteBullet=new Sprite(new Texture(pathTexture));
            spriteBullet.setBounds(bulletEnemyX,bulletEnemyY,bulletEnemyWidth,bulletEnemyHeight);
    }

    /**
     * Changes the vertical position of Bullet depending on the speed
     * @param batch
     */
    public void render(SpriteBatch batch){
        bulletEnemyY-=speedBullet* Gdx.graphics.getDeltaTime();
        spriteBullet.setPosition(bulletEnemyX-spriteBullet.getWidth(),bulletEnemyY);
        spriteBullet.draw(batch);
    }


    /**
     * @return Bullet horizontal position
     */
    public int getBulletEnemyX() {
        return bulletEnemyX;
    }

    /**
     *
     * @return Bullet vertical position
     */
    public int getBulletEnemyY() {
        return bulletEnemyY;
    }

    /**
     * @return Enemy bullet width
     */

    public int getBulletEnemyHeight() {
        return bulletEnemyHeight;
    }

    /**
     * @return Enemy bullet height
     */

    public int getBulletEnemyWidth() {
        return bulletEnemyWidth;
    }


    @Override
    public void dispose() {
        spriteBullet.getTexture().dispose();
    }
}
