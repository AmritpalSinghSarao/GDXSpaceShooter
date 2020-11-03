package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.awt.*;
import java.util.Iterator;
import java.util.Random;

public class GameSecondLevel implements Screen {

    public OrthographicCamera camera;
    int i = 0;
    private GameMain gameMain;
    private SpriteBatch batch;
    private Sprite backTexture;
    private Music mainTheme;
    private Music shipMenu;
    private Music bossEntryMusic;
    private Sound soundshoot;
    private Sound soundHitEnemy;
    private Array<Scroll> backStar;

    private Random random;
    public Sprite spaceShip;

    public Array<Bullet> bullet;
    public Array<FireRealistic> fireAni;
    private Sound explosionSound;
    private Sound energyBoosted;
    public GameScore gameScore;
    private BitmapFont font;
    private String menuText;
    private boolean enemystart = false;
    private GameLevelTwoEnemy gameLevelTwoEnemy;
    private int asteroidCounter = 0; //whenever any asteroid is destroyed by bullet this value is increased by 1


    public int score;
    public float health = 1; // 1 means full health and 0 means dead
    private Sprite blankSprite;

    private GamePlayer gamePlayer;

    private long lastTime; //used to create the asteroids in certain interval of time

    private boolean playerDestroyed =false; // whenever player is killed this is changed to true
    private long overTime;// used to wait for next screen when game player is killed
    private long completedTime;
    private long healthTime;
    private long bossKilled;
    private long changeScreen;

    private Array<Scroll>  healthGain;
    public Array<TextureAnimation> explosionAnimation;

    private boolean playerAliveLevelTwo=true;



    public GameSecondLevel(GameMain gameMain) throws NullPointerException {
        this.gameMain = gameMain;
        create();
    }

    /**
     * This method is called in constructor and used to upload all the needed to objects before starting the game
     */

    public void create() {

        //>
        //> SpriteBatch could be thought as sheet where it is possible to draw different elements
        //

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 600, 600);

        //>
        //> Sprite is used to draw on the SpriteBach, all the image should be set up on Sprite in order to be drawn.
        //> BackTexture is set to the background black image
        //>
        backTexture = new Sprite(new Texture(Gdx.files.internal("black.jpg")));
        backTexture.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //>
        //> Spaceship Sprite contains a image of the my ship then its size and position is set up
        //>
        spaceShip = new Sprite(new Texture(Gdx.files.internal("spaceship.png")));
        spaceShip.setBounds(Gdx.graphics.getWidth()/2, 40, 68, 68);

        //>
        //> Initialization of background music when game starts, it keeps playing as is set up on Loop
        //>
        mainTheme = Gdx.audio.newMusic(Gdx.files.internal("03 The Secret Base.mp3"));
        mainTheme.setLooping(true);

        bossEntryMusic = Gdx.audio.newMusic(Gdx.files.internal("13_Boss.mp3"));
        bossEntryMusic.setLooping(true);

        shipMenu = Gdx.audio.newMusic(Gdx.files.internal("shipmenu.mp3"));

        //>
        //> Uploading of shooting sound by the space ship
        //>
        soundshoot = Gdx.audio.newSound(Gdx.files.internal("Laser-Ricochet.mp3"));

        //>
        //> Uploading of the explosion sound between bullet and asteroid
        //>
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("Explosion+3.wav"));

        //>
        //> Enemy hit Sound
        //>
        soundHitEnemy = Gdx.audio.newSound(Gdx.files.internal("sfx_sounds_damage1.wav"));

        //>
        //> Creation of bullet array
        //>
        bullet = new Array<Bullet>();


        //>
        //> Creation of array to have background moving stars effect
        //>
        backStar = new Array<Scroll>();
        random = new Random();

        //>
        //> Whenever collision between bullet and asteroid occurs fire effect is created
        //>
        fireAni = new Array<FireRealistic>();

        //>
        //>Score object which will update a score on the screen
        //>
        gameScore = new GameScore();


        //>
        //> Goal of BitmapFont is to show on the screen Timer and Score of player
        //>
        font = new BitmapFont();
        font.getData().setScale(2f);
        font.setColor(Color.MAGENTA);

        //
        blankSprite = new Sprite(new Texture(Gdx.files.internal("blank.png")));

        //>
        //> Game Input player
        //>
        gamePlayer = new GamePlayer(spaceShip, bullet, soundshoot,playerAliveLevelTwo);

        //>
        //> Uploading of the ending game assets
        //>
        gameLevelTwoEnemy = new GameLevelTwoEnemy(spaceShip);

         explosionAnimation=new Array<TextureAnimation>();

        //>
        //>
        //>
        healthGain=new Array<Scroll>();
        energyBoosted=Gdx.audio.newSound(Gdx.files.internal("HealthCollected.wav"));


    }


    @Override
    public void show() {
         mainTheme.play();
    }

    /**
     * @param delta
     */
    @Override
    public void render(float delta) {

        //>
        //> Clear the screen
        //>
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //>
        //> Tell the camera to update its matrices.
        //>
        camera.update();

        //>
        //> Tell the SpriteBatch to render in the
        //> Coordinate system specified by the camera.
        //>
        batch.setProjectionMatrix(camera.combined);

        //>
        //> Only 100 stars are visible on the screen at the time
        //> The generation of smaller stars is greater than bigger
        //>
        if (backStar.size < 100) {
            if (random.nextInt(15) == 1) {
                backStar.add(new Scroll(3, 3, 150,"star.png",Gdx.graphics.getHeight()));
            }
            if (random.nextInt(10) == 1) {
                backStar.add(new Scroll(2, 2, 70,"star.png",Gdx.graphics.getHeight()));
            }
            if (random.nextInt(5) == 1) {
                backStar.add(new Scroll(1, 1, 60,"star.png",Gdx.graphics.getHeight()));
            }


        }


        //>
        //> Interval 10 seconds
        //>
        if(TimeUtils.millis()-healthTime>20000){
            healthGain.add(new Scroll(20,20,40,"HP_Icon.png",Gdx.graphics.getHeight()));
            healthTime=TimeUtils.millis();
            System.out.println("health");
        }



        //>
        //> Check Whether player is still alive or level is completed successfully
        //>
        if(health<0 && playerDestroyed!=true)
            playerKilled();

        if (gameLevelTwoEnemy.boss.enemyDestroy())
            bossKilled();

        // when player is destroyed or Boss, screen waits almost 3 seconds before displaying GameOver screen
        if (playerDestroyed!=false && TimeUtils.millis()-overTime>3000 && overTime!=0
        ) { dispose();
            gameMain.setScreen(new GameScreen(gameMain, gameScore.score,2)); }


        if (gameLevelTwoEnemy.boss.enemyDestroy() && TimeUtils.millis()-changeScreen>3000 && bossKilled!=0){
            bossEntryMusic.dispose();
            gameMain.setScreen(new GameScreen(gameMain, gameScore.score,4)); }


        if (gameLevelTwoEnemy.isBossEnemy() && mainTheme.isPlaying()) {
            mainTheme.stop();
            bossEntryMusic.play();
        }


        //>
        //> SpriteBatch Batch drawing method, This method update the screen over and over again
        //>

        batchDrawing();



        //>
        //> In the following method, all the arrays are cleaned
        //>
        clearArraysElements();
    }

    /**
     *  Create explosion effect and initialize waiting time for gameOver Screen
     */

    public void playerKilled(){

        fireAni.add(new FireRealistic((int)spaceShip.getX(),(int)spaceShip.getY()));
        fireAni.add(new FireRealistic((int)spaceShip.getX()+(int)spaceShip.getWidth()/2,(int)spaceShip.getY()-
                (int)spaceShip.getHeight()/2));
        fireAni.add(new FireRealistic((int)spaceShip.getX()+(int)spaceShip.getWidth(),(int)spaceShip.getY()
                -(int)spaceShip.getHeight()));
        playerDestroyed=true;
        explosionSound.play();
        overTime=TimeUtils.millis();
        playerAliveLevelTwo=false;
    }


    public void bossKilled(){

          if (bossKilled==0)
              changeScreen=TimeUtils.millis();

        if (TimeUtils.millis()-bossKilled>500){
            System.out.println("Boss killed");
            explosionSound.play();
            explosionAnimation.add(new TextureAnimation(10,"shot6_exp",0,
                    (int)gameLevelTwoEnemy.boss.getEnemyX()
            ,(int)gameLevelTwoEnemy.boss.getEnemyY(),gameLevelTwoEnemy.boss.enemyWidth,gameLevelTwoEnemy.boss.enemyHeight,true));
            bossKilled= TimeUtils.millis();}


    }


    /**
     *  Draw all the sprites on the SpriteBatch.
     */
    public void batchDrawing() {

        //>
        //> All the sprites for drawing must start from this point
        //>
        batch.begin();




        //>
        //> By using backtexture object of Sprite class back image is drawn on batch
        //>
        backTexture.draw(batch);


        //>
        //> Following loops are needed to have moving effect of all the objects
        //> Loop of back stars
        //>

        for (Scroll star : backStar)
            star.render(batch);

        //>
        //>Drawing of ship
        //>
        if (playerDestroyed!=true)
            spaceShip.draw(batch);

        //>
        //>Bullet loop
        //>
        for (Bullet b : bullet)
            b.render(batch);

        //Input from user for playing a game
        if (playerDestroyed!=true)
        gamePlayer.inputUser();

        System.out.println(healthGain.size);

        //>
        //>FireRealistic Render
        //>
        for (FireRealistic fireR : fireAni)
            fireR.render(batch);


        for (TextureAnimation expAnimation: explosionAnimation)
            expAnimation.renderFirst(batch);

        //>
        //> When the score is greater than 200, enemies are called
        //>

            gameLevelTwoEnemy.update(batch);


        //>
        //>
        //>
        for (Scroll energy : healthGain)
            energy.render(batch);

        //>
        //> During the game playing state, score is updated through this method
        //>
        gameScore.render(batch);

        //>
        //> Health of player, collision between player ship and any other kind of enemy object
        //> will reduce health of player
        //>
        blankSprite.setBounds(0, 0, Gdx.graphics.getWidth() * health, 5);
        blankSprite.setColor(Color.GREEN);
        blankSprite.draw(batch);


        //>
        //> End the drawing on the screen
        //>
        batch.end();
    }


    /**
     * In the following method, all the arrays are cleaned
     * //> which are either destroyed or not needed anymore
     * //> This setup is necessary to use less RAM as possible
     */

    public void clearArraysElements() {

        //>
        //> When moving stars reach the bottom, they must be removed from our array
        //>
        Iterator<Scroll> starsIterator = backStar.iterator();
        while (starsIterator.hasNext()) {
            Scroll star = starsIterator.next();
            if (star.scrollY < 0) {
                star.dispose();
                starsIterator.remove();
            }
        }

        //>
        //> Removing of energy booster
        //>
        Iterator<Scroll> energyIterator = healthGain.iterator();
        while (energyIterator.hasNext()) {
            Scroll energy = energyIterator.next();
            if (energy.scrollY < 10) {
                energy.dispose();
                energyIterator.remove();
            }
        }

        Iterator<Scroll> energyIt = healthGain.iterator();
        while (energyIt.hasNext()) {
            Scroll energy = energyIt.next();
            Rectangle shipRectangle = spaceShip.getBoundingRectangle();
            Rectangle energyRectangle = new Rectangle(energy.scrollX, energy.scrollY, energy.scrollWidth, energy.scrollHeight);
            if (shipRectangle.overlaps(energyRectangle)) {

                energy.dispose();
                energyIt.remove();
                if(health<1)
                    health += 0.1;
                gameScore.scoreUpdater(50);
                energyBoosted.play();


            }
        }

        //>
        //> Bullet must be removed when it is no longer visible on the screen
        //>

        Iterator<Bullet> bulletIterator = bullet.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();

            if (bullet.bulletY > Gdx.graphics.getHeight()) {

                bullet.dispose();
                bulletIterator.remove();
            }
        }


        //>
        //>Removing Fire realistic
        //>
        Iterator<FireRealistic> fireiter = fireAni.iterator();
        while (fireiter.hasNext()) {
            FireRealistic fireAni = fireiter.next();
            if (fireAni.remove) {
                fireAni.dispose();
                fireiter.remove();
            }
        }


        //>
        //> Removing of enemy objects
        //>

        enemyRemove();

    }

    /**
     * Enemies of Second level are removed and related actions
     */
    public void enemyRemove() {

        //>
        //> Whenever enemyBullets reach the ending of the screen, following iterator remove the objects
        //>
        Iterator<BulletEnemy> bulletEnemyIt = gameLevelTwoEnemy.bulletEnemies.iterator();
        while (bulletEnemyIt.hasNext()) {
            BulletEnemy e = bulletEnemyIt.next();
            if (e.bulletEnemyY<10) {
                bulletEnemyIt.remove();
                e.dispose();
            }

        }

        //>
        //> Whenever enemyBullets Animation reach the ending of the screen, following iterator remove the objects
        //>
        Iterator<TextureAnimation> bulletIt = gameLevelTwoEnemy.bulletAnimation.iterator();
        while (bulletIt.hasNext()) {
            TextureAnimation e = bulletIt.next();
            if (e.isRemove()) {
                bulletIt.remove();
                e.dispose();
            }

        }

        //>
        //> Removing of explosion effect on player ship when bullet animation hit the player ship
        //>
        Iterator<TextureAnimation> bulletIter = explosionAnimation.iterator();
        while (bulletIter.hasNext()) {
            TextureAnimation e = bulletIter.next();
            if (e.isRemove()) {
                bulletIter.remove();
                e.dispose();
            }

        }



            enemyFirstRemove(gameLevelTwoEnemy.enemies);


            enemyFirstRemove(gameLevelTwoEnemy.enemiesTwo);


            thirdGroupRemove(gameLevelTwoEnemy.enemiesThree);



        //>
        //> If enemies of third group are not destroyed then they must be removed ones they are not visible
        //> on the screen
        //>
        Iterator<Enemy> enemyIter1 = gameLevelTwoEnemy.enemiesThree.iterator();
        while (enemyIter1.hasNext()) {
            Enemy e = enemyIter1.next();
            if (e.getEnemyX()>Gdx.graphics.getWidth()+e.getEnemyWidth()) {
                enemyIter1.remove();
                e.dispose();
            }
        }



            thirdGroupRemove(gameLevelTwoEnemy.enemiesFour);

        //>
        //> If enemies of third group are not destroyed then they must be removed ones they are not visible
        //> on the screen
        //>
        Iterator<Enemy> enemyIter2 = gameLevelTwoEnemy.enemiesFour.iterator();
        while (enemyIter2.hasNext()) {
            Enemy e = enemyIter2.next();
            if (e.getEnemyX()<20) {
                enemyIter2.remove();
                e.dispose();
            }
        }

        //>
        //> whenever collision between bullet and Boss occurs then  Boss
        //>  is removed and fireRealistc effect is created with sound
        //>

            //initialize the bullet iterator each time here

            Iterator<Bullet> iterBul = bullet.iterator();
            while (iterBul.hasNext()) {
                Bullet b = iterBul.next();

                // By using Rectangle class, area and position of enemy and bullet is obtained
                Rectangle enemyRectangle = new Rectangle(gameLevelTwoEnemy.boss.getEnemyX(),gameLevelTwoEnemy.boss.getEnemyY()
                ,gameLevelTwoEnemy.boss.enemyWidth,gameLevelTwoEnemy.boss.enemyHeight);
                Rectangle bulletRectangle = b.fire.getBoundingRectangle();

                if (bulletRectangle.overlaps(enemyRectangle)) {
                    gameLevelTwoEnemy.boss.setEnemyHealth(gameLevelTwoEnemy.boss.getEnemyHealth() - 1);
                    b.dispose();
                    iterBul.remove();
                    soundHitEnemy.play(7);
                    explosionAnimation.add(new TextureAnimation(7 ,"shot5_exp",0,(int) b.bulletX,
                            (int) b.bulletY,
                            15,25,true));
                    if (gameLevelTwoEnemy.boss.enemyDestroy()) {
                        gameScore.scoreUpdater(1000);
                    }
                }
            }
        }




  public void enemyFirstRemove(Array<Enemy> enemies){

          //>
          //> whenever collision between bullet and enemy occurs then  enemy
          //>  is removed and fireRealistc effect is created with sound
          //>
          Iterator<Enemy> iterEnemy = enemies.iterator();
          while (iterEnemy.hasNext()) {
              Enemy e = iterEnemy.next();

              //initialize the bullet iterator each time here

              Iterator<Bullet> iterBul = bullet.iterator();

              while (iterBul.hasNext()) {
                  Bullet b = iterBul.next();

                  // By using Rectangle class, area and position of enemy and bullet is obtained
                  Rectangle enemyRectangle = e.getEnemyShip().getBoundingRectangle();
                  Rectangle bulletRectangle = b.fire.getBoundingRectangle();

                  if (bulletRectangle.overlaps(enemyRectangle)) {
                      e.setEnemyHealth(e.getEnemyHealth() - 1);
                      b.dispose();
                      iterBul.remove();
                      soundHitEnemy.play(7);
                      explosionAnimation.add(new TextureAnimation(4 ,"shot3_exp",0,(int)b.bulletX,
                              (int) b.bulletY,
                              15,25,true));
                      if (e.enemyDestroy()) {
                          fireAni.add(new FireRealistic((int) e.getEnemyX(), (int) e.getEnemyY()));
                          fireAni.add(new FireRealistic((int) e.getEnemyX() - 10, (int) e.getEnemyY() + 10));
                          e.dispose();
                          iterEnemy.remove();
                          explosionSound.play();
                          gameScore.scoreUpdater(200);
                      }
                  }
              }
          }

      //>
      //> Removing of enemy bullet when it collide with Space ship
      //>
      Iterator<BulletEnemy> bulletEnemyIt1 = gameLevelTwoEnemy.bulletEnemies.iterator();
      while (bulletEnemyIt1.hasNext()) {
          BulletEnemy bulletEnemy = bulletEnemyIt1.next();
          Rectangle shipRectangle = spaceShip.getBoundingRectangle();
          Rectangle bulletRectangle = new Rectangle(bulletEnemy.bulletEnemyX, bulletEnemy.bulletEnemyY,
                  bulletEnemy.getBulletEnemyWidth(), bulletEnemy.getBulletEnemyHeight());
          if (shipRectangle.overlaps(bulletRectangle)) {
              bulletEnemyIt1.remove();
              bulletEnemy.dispose();
              health -= 0.2;
              explosionAnimation.add(new TextureAnimation(4 ,"shot3_exp",0,bulletEnemy.bulletEnemyX,
                      bulletEnemy.bulletEnemyY,
                      15,25,true));

          }
      }

      }


      public void thirdGroupRemove(Array<Enemy> enemiesThree){

          //>
          //> whenever collision between bullet and enemy occurs then  enemy
          //>  is removed and fireRealistc effect is created with sound
          //>

          Iterator<Enemy> iterEnemy = enemiesThree.iterator();
          while (iterEnemy.hasNext()) {
              Enemy e = iterEnemy.next();

              //initialize the bullet iterator each time here

              Iterator<Bullet> iterBul = bullet.iterator();

              while (iterBul.hasNext()) {
                  Bullet b = iterBul.next();

                  // By using Rectangle class, area and position of enemy and bullet is obtained
                  Rectangle enemyRectangle = e.getEnemyShip().getBoundingRectangle();
                  Rectangle bulletRectangle = b.fire.getBoundingRectangle();

                  if (bulletRectangle.overlaps(enemyRectangle)) {
                      e.setEnemyHealth(e.getEnemyHealth() - 1);
                      iterBul.remove();
                      b.dispose();
                      explosionAnimation.add(new TextureAnimation(4 ,"shot5_exp",0,(int) b.bulletX,
                              (int)  b.bulletY,
                              15,25,true));
                      soundHitEnemy.play(7);
                      if (e.enemyDestroy()) {
                          fireAni.add(new FireRealistic((int) e.getEnemyX(), (int) e.getEnemyY()));
                          fireAni.add(new FireRealistic((int) e.getEnemyX() - 10, (int) e.getEnemyY() + 10));
                          iterEnemy.remove();
                          e.dispose();
                          explosionSound.play();
                          gameScore.scoreUpdater(200);
                      }
                  }
              }
          }

          //>
          //> Removing of enemy bullet when it collide with Space ship
          //>
          Iterator<TextureAnimation> bulletEnemyIt1 = gameLevelTwoEnemy.bulletAnimation.iterator();
          while (bulletEnemyIt1.hasNext()) {
              TextureAnimation bulletEnemy = bulletEnemyIt1.next();
              Rectangle shipRectangle = spaceShip.getBoundingRectangle();
              Rectangle bulletRectangle = new Rectangle(bulletEnemy.getTextureX(), bulletEnemy.getTextureY(),
                      bulletEnemy.getTextureWidth(), bulletEnemy.getTextureHeight());
              if (shipRectangle.overlaps(bulletRectangle)) {
                 explosionAnimation.add(new TextureAnimation(4, "exp2_",
                         0, (int) bulletEnemy.getTextureX(),
                         bulletEnemy.getTextureY(),15,25,true));
                  bulletEnemyIt1.remove();
                  bulletEnemy.dispose();
                  health -= 0.2;
              }
          }





      }


    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        //>
        //> All the sprites, textures, music, sound and BitFont are disposed when the screen is no longer visible
        //>
        soundshoot.dispose();
        shipMenu.dispose();
        spaceShip.getTexture().dispose();
        explosionSound.dispose();
        backTexture.getTexture().dispose();
        batch.dispose();
        font.dispose();
        mainTheme.dispose();
        energyBoosted.dispose();
        bossEntryMusic.dispose();
        blankSprite.getTexture().dispose();
        soundHitEnemy.dispose();
    }

}

