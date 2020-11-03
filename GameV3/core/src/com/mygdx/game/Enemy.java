package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class Enemy implements Disposable {

    private Sprite enemyShip;
    private Texture enemyTexture;
    private float enemyX; //initial position of enemy
    private float enemyY = GameMain.height+20; //Starting vertical position of Enemy
    private int speedEnemy; //Vertical speed of enemy
    private int enemyHealth;
    public  int enemyWidth = 80;
    public  int enemyHeight = 90;
    private boolean stopVerticalMovementDown; // to stop vertical movement
    private boolean stopVerticalMovementUp;
    private String nameShip;


    public Enemy(int enemyX, int enemyHealth,int speedEnemy,String nameShip,int enemyWidth,int enemyHeight) {
        this.nameShip=nameShip;
        this.speedEnemy=speedEnemy;
        this.enemyX = enemyX;
        this.enemyHealth = enemyHealth;
        this.enemyWidth=enemyWidth;
        this.enemyHeight=enemyHeight;
        create();

    }


    public void create() {
        enemyTexture = new Texture(nameShip);
        enemyShip = new Sprite(enemyTexture);
        enemyShip.setBounds(enemyX, enemyY, enemyWidth, enemyHeight);
    }

    /**
     * Enemy vertical down movement
     * @param batch
     */

    public void updateDown(SpriteBatch batch) {

        // when stopVerticalMovementDown is true then enemy stop moving vertically

            if(stopVerticalMovementDown!=true && enemyY>=Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/12)
        enemyY -= speedEnemy*3 * Gdx.graphics.getDeltaTime();
        else if(stopVerticalMovementDown!=true && enemyY>=Gdx.graphics.getHeight()/2)
            enemyY -= speedEnemy*Gdx.graphics.getDeltaTime();

        enemyShip.setPosition(enemyX, enemyY);
        enemyShip.draw(batch);

    }

    /**
     * Enemy vertical up Movement
     * @param batch
     */
    public void updateUp(SpriteBatch batch) {
        if(stopVerticalMovementUp!=true)
        enemyY += speedEnemy * Gdx.graphics.getDeltaTime();
        enemyShip.setPosition(enemyX, enemyY);
        enemyShip.draw(batch);

    }

    /**
     * This boolean method is used to know Whether enemy should be destroyed or not
     * @return
     */

    public boolean enemyDestroy() {
        if (enemyHealth <= 0)
            return true;
        return false;
    }


    public boolean isStopVerticalMovementDown(boolean b) {
        return this.stopVerticalMovementDown=b;
    }

    public boolean isStopVerticalMovementDown() {
        return stopVerticalMovementDown;
    }

    public boolean isStopVerticalMovementUp() {
        return stopVerticalMovementUp;
    }

    public void setEnemyHealth(int enemyHealth) {
        this.enemyHealth = enemyHealth;
    }

    public int getEnemyHealth() {
        return enemyHealth;
    }

    public Sprite getEnemyShip() {
        return enemyShip;
    }


    public float getEnemyX() {
        return enemyX;
    }

    public void setEnemyX(float enemyX) {
        this.enemyX = enemyX;
    }

    public int getEnemyWidth() {
        return enemyWidth;
    }

    public int getEnemyHeight() {
        return enemyHeight;
    }


    public float getEnemyY() {
        return enemyY;
    }



    @Override
    public void dispose() {
        enemyTexture.dispose();
        enemyShip.getTexture().dispose();
    }

}
