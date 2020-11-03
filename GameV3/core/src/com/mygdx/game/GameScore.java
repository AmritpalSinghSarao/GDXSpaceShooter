package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;

import java.sql.Time;

public class GameScore implements Disposable {
    public int score=0,missed=0,lives=2;
    private BitmapFont font;
    private String scoreText,missedText,livesText;

    public GameScore(){
        font = new BitmapFont();
        scoreText = "Score : "+score;
        font.getData().setScale(2f);
        font.setColor(Color.DARK_GRAY);
    }

    public void render(SpriteBatch batch){

        scoreText = "Score : "+score;
        font.draw(batch,scoreText,10, Gdx.graphics.getHeight()-10);
    }

    /**
     *  Used to add the score
     * @param scorePlus Score added to score
     */
    public void scoreUpdater(int scorePlus){
        score+=scorePlus;
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
