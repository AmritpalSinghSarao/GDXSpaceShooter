package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameMain extends Game {

	public final static int width=600;
	public final static  int height=600;
	public final static String title="Space shooter";
	int x=0;

	@Override
	public void create () {
       if(x==1)
		setScreen(new GameScreen(this,2,4));
       else
       	setScreen(new GameMenu(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
	}
}
