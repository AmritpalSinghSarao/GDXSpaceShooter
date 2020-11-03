package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import sun.text.resources.cldr.ext.FormatData_xog;

import java.util.Iterator;
import java.util.Random;

import static com.badlogic.gdx.utils.TimeUtils.millis;

public class GameLevelTwoEnemy {

    public Array<Enemy> enemies;
    public Array<Enemy> enemiesTwo;
    public Array<Enemy> enemiesThree;
    public Array<Enemy> enemiesFour;
    public Enemy boss;
    public Array<BulletEnemy> bulletEnemies;
    public Array<TextureAnimation> bulletAnimation;


    private boolean enemyMovementPositive = true; //Enemy movement towards left
    private boolean enemyMovementNegative = false;  //Enemy movement towards right


    private int counter = 0; // This counter is used to resolve a bug in movement of enemies
    private int counterEnemy = 0;

    private BulletEnemy bulletEnemy;

    // Time variables for Enemy Shoots
    private long lastTimeFirstEnemy;
    private long lastTimeSecondEnemy;

    // Enemy speed
    final private int firstEnemiesSpeed = 70;
    final private int thirdEnemySpeed = 25;

    // Which kind of enemy group is being displayed
    private boolean firstEnemies = true;
    private boolean secondEnemies = false;
    private boolean thirdEnemies = false;
    private boolean forthEnemies = false;
    private boolean bossEnemy = false;


    private long bossEntry;

    public int enemyNumber = 0;

    // Boss shooting timing variables
    private long firstShoot;
    private long secondShoot;
    private long thirdShoot;
    private long changeShootOne;
    private long changeShootTwo;
    private long changeShootThree;
    private long waitChangeOne;
    private long waitChangeTwo;
    private long waitChangeThree;

    private Random random;
    private Sprite ship;


    final private String[] enemyShipImage = {"Spaceship_05_YELLOW.png", "Spaceship_05_GREEN.png", "Spaceship_05_BLUE.png"};


    public GameLevelTwoEnemy(Sprite ship) {
        this.ship = ship;
        create();
    }


    /**
     * All the enemies objects are generated here and their Array too
     */
    public void create() {

        random = new Random();
        //>
        //> Uploading of first couple of enemies
        //>
        enemies = new Array<Enemy>();
        enemies.add(new Enemy(0, 10, 60, "Spaceship_06_GREEN.png", 80, 90));
        enemies.add(new Enemy(Gdx.graphics.getWidth() , 2, 60, "Spaceship_06_GREEN.png", 80, 90));


        enemiesTwo = new Array<Enemy>();
        enemiesTwo.add(new Enemy(0, 10, firstEnemiesSpeed, "Spaceship_06_PURPLE.png", 80, 90));
        enemiesTwo.add(new Enemy(Gdx.graphics.getWidth() - 60, 20, firstEnemiesSpeed, "Spaceship_06_PURPLE.png", 80, 90));
        bulletEnemies = new Array<BulletEnemy>();

        enemiesThree = new Array<Enemy>();

        enemiesThree.add(new Enemy(-200,
                10, thirdEnemySpeed, enemyShipImage[random.nextInt(enemyShipImage.length)], 80, 90));
        enemiesThree.add(new Enemy(-100, 15, thirdEnemySpeed, enemyShipImage[random.nextInt(enemyShipImage.length)], 80, 90));

        enemiesFour = new Array<Enemy>();

        enemiesFour.add(new Enemy(Gdx.graphics.getWidth() + 200,
                10, thirdEnemySpeed, enemyShipImage[random.nextInt(enemyShipImage.length)], 80, 90));
        enemiesFour.add(new Enemy(Gdx.graphics.getWidth() + 100, 15, thirdEnemySpeed, enemyShipImage[random.nextInt(enemyShipImage.length)], 80, 90));


        boss = new Enemy(Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 12, 30, thirdEnemySpeed, "Ship6.png", 110, 140);
        bulletAnimation = new Array<TextureAnimation>();


    }


    /**
     * Update the movement of the enemies
     *
     * @param batch
     */

    public void update(SpriteBatch batch) {

        if (firstEnemies)
            enemyFirstGroup(batch, enemies);


        if (enemies.size == 0 && firstEnemies) {
            secondEnemies = true;
            firstEnemies = false;
            enemyMovementPositive=true;
            enemyMovementNegative=false;
        }

        if (secondEnemies)
            enemyFirstGroup(batch, enemiesTwo);


        if (enemiesTwo.size == 0  && secondEnemies) {
            secondEnemies = false;
            thirdEnemies = true;
        }


        if (thirdEnemies)
            enemyThirdGroupOne(batch, enemiesThree);

        if (forthEnemies)
            enemyThirdGroupTwo(batch, enemiesFour);


        if (bossEnemy && TimeUtils.millis()-bossEntry>2000 && boss.enemyDestroy()==false)
            bossEnemy(batch, boss);

        //>
        //> Loop of enemy bullets
        //>
        for (BulletEnemy bulletEnemy : bulletEnemies) {
            bulletEnemy.render(batch);
        }


        for (TextureAnimation bulletEnemy : bulletAnimation)
            bulletEnemy.renderFirst(batch);

        //>
        //> Generation of enemy bullets
        //>
        generateEnemyBullet();


    }


    /**
     * This medhod generate the bullets for all the enemies by checking which
     * group of enemies is being displayed
     */


    public void generateEnemyBullet() {

        //>
        //>  Bullet generation of first group of enemy
        //>
        if (firstEnemies) {
            counterEnemy = 0;
            for (Enemy enemy : enemies) {
                if (TimeUtils.millis() - lastTimeFirstEnemy > 1000 && counterEnemy == 0) {
                    spawnBullet(enemy.getEnemyX() + enemy.getEnemyWidth() / 2 + 10, enemy.getEnemyY() + enemy.getEnemyHeight() / 8);
                    lastTimeFirstEnemy = TimeUtils.millis();
                }
                if (TimeUtils.millis() - lastTimeSecondEnemy > 1000 && counterEnemy == 1) {
                    spawnBullet(enemy.getEnemyX() + enemy.getEnemyWidth() / 2 + 10, enemy.getEnemyY() + enemy.getEnemyHeight() / 8);
                    lastTimeSecondEnemy = TimeUtils.millis();
                }
                counterEnemy++;
            }
            counterEnemy = 0;
        }
        //>
        //>  Bullet generation of second group of enemy
        //>
        if (secondEnemies) {
            counterEnemy = 0;
            for (Enemy enemy : enemiesTwo) {
                if (TimeUtils.millis() - lastTimeFirstEnemy > 1000 && counterEnemy == 0) {
                    spawnBullet(enemy.getEnemyX() + enemy.getEnemyWidth() / 2 + 10, enemy.getEnemyY() + enemy.getEnemyHeight() / 8);
                    lastTimeFirstEnemy = TimeUtils.millis();
                }
                if (TimeUtils.millis() - lastTimeSecondEnemy > 1000 && counterEnemy == 1) {
                    spawnBullet(enemy.getEnemyX() + enemy.getEnemyWidth() / 2 + 10, enemy.getEnemyY() + enemy.getEnemyHeight() / 8);
                    lastTimeSecondEnemy = TimeUtils.millis();
                }
                counterEnemy++;
            }
            counterEnemy = 0;
        }


        if (thirdEnemies) {
            counterEnemy = 0;
            for (Enemy enemy : enemiesThree) {
                if (TimeUtils.millis() - lastTimeFirstEnemy > 1000 && counterEnemy == 0) {
                    bulletAnimation.add(new TextureAnimation(4, "shot2_",
                            250, (int) enemy.getEnemyX(),
                            (int) enemy.getEnemyY(), 15, 25, false));
                    bulletAnimation.add(new TextureAnimation(4, "shot2_",
                            250, (int) enemy.getEnemyX() + enemy.getEnemyWidth() - 20,
                            (int) enemy.getEnemyY(), 15, 25, false));
                    lastTimeFirstEnemy = TimeUtils.millis();
                }

                if (TimeUtils.millis() - lastTimeSecondEnemy > 1000 && counterEnemy == 1) {
                    bulletAnimation.add(new TextureAnimation(4, "shot2_",
                            250, (int) enemy.getEnemyX(),
                            (int) enemy.getEnemyY(), 15, 25, false));
                    bulletAnimation.add(new TextureAnimation(4, "shot2_",
                            250, (int) enemy.getEnemyX() + enemy.getEnemyWidth() - 20,
                            (int) enemy.getEnemyY(), 15, 25, false));
                    lastTimeSecondEnemy = TimeUtils.millis();
                }
                counterEnemy++;
            }
            counterEnemy = 0;
        }

        if (forthEnemies){counterEnemy=0;
            for (Enemy enemy : enemiesFour) {
                if (TimeUtils.millis() - lastTimeFirstEnemy > 1000 && counterEnemy == 0) {
                    bulletAnimation.add(new TextureAnimation(4, "shot2_",
                            250, (int) enemy.getEnemyX(),
                            (int) enemy.getEnemyY(), 15, 25, false));
                    bulletAnimation.add(new TextureAnimation(4, "shot2_",
                            200, (int) enemy.getEnemyX() + enemy.getEnemyWidth() - 20,
                            (int) enemy.getEnemyY(), 15, 25, false));
                    lastTimeFirstEnemy = TimeUtils.millis();
                }

                if (TimeUtils.millis() - lastTimeSecondEnemy > 1000 && counterEnemy == 1) {
                    bulletAnimation.add(new TextureAnimation(4, "shot2_",
                            250, (int) enemy.getEnemyX(),
                            (int) enemy.getEnemyY(), 15, 25, false));
                    bulletAnimation.add(new TextureAnimation(4, "shot2_",
                            200, (int) enemy.getEnemyX() + enemy.getEnemyWidth() - 20,
                            (int) enemy.getEnemyY(), 15, 25, false));
                    lastTimeSecondEnemy = TimeUtils.millis();
                }
                counterEnemy++;
            }
            counterEnemy=0;
    }




        if (bossEnemy && TimeUtils.millis()-bossEntry>2500 && boss.enemyDestroy()==false) {

            if (changeShootOne == 0){
                changeShootOne = TimeUtils.millis();
                waitChangeOne=0;
                changeShootTwo=0;
                changeShootThree=0;
                waitChangeThree=0;
            }
            if (TimeUtils.millis() - changeShootOne < 6000) {
                if (TimeUtils.millis() - firstShoot > 2000 / 3 && changeShootOne != 0) {
                    bulletAnimation.add(new TextureAnimation(4, "shotRed",
                            250, (int) boss.getEnemyX() + 10,
                            (int) boss.getEnemyY(), 15, 25, false));

                    bulletAnimation.add(new TextureAnimation(4, "shotRed",
                            250, (int) boss.getEnemyX() + boss.enemyWidth - 30,
                            (int) boss.getEnemyY(), 15, 25, false));
                    firstShoot = TimeUtils.millis();

                }

            } else if (waitChangeOne == 0) {
                waitChangeOne = TimeUtils.millis();
            }

            if (changeShootTwo == 0 && TimeUtils.millis() - changeShootOne > 6000 && TimeUtils.millis() - waitChangeOne > 2000) {
                changeShootTwo = TimeUtils.millis();
                secondShoot = TimeUtils.millis();
            }

            if (TimeUtils.millis() - changeShootTwo < 6000) {
                if (TimeUtils.millis() - secondShoot > 2000 / 3 && changeShootTwo != 0) {
                    bulletAnimation.add(new TextureAnimation(4, "shot4_",
                            250, (int) boss.getEnemyX() + 10,
                            (int) boss.getEnemyY(), 15, 25, false));

                    bulletAnimation.add(new TextureAnimation(4, "shot4_",
                            250, (int) boss.getEnemyX() + boss.enemyWidth - 30,
                            (int) boss.getEnemyY(), 15, 25, false));
                    secondShoot = TimeUtils.millis();

                }


            } else if (waitChangeTwo == 0) {
                waitChangeTwo = TimeUtils.millis();
                waitChangeOne=0;
            }

            if (changeShootThree == 0 && TimeUtils.millis() - changeShootTwo > 6000 && changeShootTwo!=0 && TimeUtils.millis() - waitChangeTwo > 2000) {
                changeShootThree = TimeUtils.millis();
                thirdShoot = TimeUtils.millis();
            }
            if (TimeUtils.millis() - changeShootThree < 6000) {
                if (TimeUtils.millis() - thirdShoot > 2000 / 3 && changeShootThree != 0) {
                    bulletAnimation.add(new TextureAnimation(5, "shot6_",
                            250, (int) boss.getEnemyX() + 10,
                            (int) boss.getEnemyY(), 15, 25, false));

                    bulletAnimation.add(new TextureAnimation(5, "shot6_",
                            250, (int) boss.getEnemyX() + boss.enemyWidth - 30,
                            (int) boss.getEnemyY(), 15, 25, false));
                    thirdShoot = TimeUtils.millis();

                }

            }
            else if(waitChangeThree==0 && changeShootThree!=0){
                waitChangeThree=TimeUtils.millis();
            }

            if (TimeUtils.millis()-waitChangeThree>2000 &&waitChangeThree!=0)
                changeShootOne=0;
        }
    }
    /**
     * It is used for generating new bullets
     */

    public void spawnBullet(float x, float y) {
        bulletEnemy = new BulletEnemy((int) x, (int) y, "laser.png");
        bulletEnemies.add(bulletEnemy);
    }


    /**
     * This method is used to show first two enemies when level 2 starts
     * Enemies follow the movement of player to hitting it
     *
     * @param batch
     * @param enemies
     */
    public void enemyFirstGroup(SpriteBatch batch, Array<Enemy> enemies) {

        //>
        //> The first group of enemy
        //>



        for (Enemy enemy : enemies) {
            if (enemy.getEnemyY() > Gdx.graphics.getHeight() / 2) {
                enemy.updateDown(batch);
                if (enemy.getEnemyY() > Gdx.graphics.getHeight() / 2 && enemyNumber == 0)
                    enemy.setEnemyX(enemy.getEnemyX() + 1);
                if (enemy.getEnemyY() > Gdx.graphics.getHeight() / 2 && enemyNumber == 1)
                    enemy.setEnemyX(enemy.getEnemyX() - 1);
            } else {
                enemy.isStopVerticalMovementDown(true);
                enemy.updateDown(batch);




            if ((enemyMovementPositive && enemy.isStopVerticalMovementDown())
            ) {
                //if movement is positive then enemies move to left side
                if (enemies.size == 1)
                    enemyNumber = 1;
                enemy.setEnemyX(enemy.getEnemyX() + 2);
                if (enemy.getEnemyX() + enemy.getEnemyWidth() > Gdx.graphics.getWidth() && enemyNumber == 1 ||
                        enemy.getEnemyX() > ship.getX() && enemyNumber == 1) {
                    enemyMovementNegative = true;
                    enemyMovementPositive = false;
                }
            } else if (enemyMovementNegative && enemy.isStopVerticalMovementDown()) {
                enemy.setEnemyX(enemy.getEnemyX() - 2);
                if (enemy.getEnemyX() <= 0 || counter > 0 || enemy.getEnemyX() < ship.getX() && enemyNumber == 0) {
                    counter++;
                    if (counter == 2) {

                        enemyMovementPositive = true;
                        enemyMovementNegative = false;
                        counter = 0;
                    }
                }
            }


        }
            enemyNumber++;}
        enemyNumber = 0;

    }


    /**
     * This method updates the movement of enemies of third group
     * When the the size of enemiesThree is 0 then next enemy group is called by changing boolean value of third one
     * @param batch
     * @param enemiesThree
     */
    public void enemyThirdGroupOne(SpriteBatch batch, Array<Enemy> enemiesThree) {


        for (Enemy enemy : enemiesThree) {
            enemy.setEnemyX(enemy.getEnemyX() + 1);
            enemy.updateDown(batch);
        }

        if (enemiesThree.size == 0) {
            thirdEnemies = false;
            forthEnemies = true;

        }


    }

    /**
     * This Method updates enemy group of forth group
     * When its size is 0 then Boss is called
     * @param batch
     * @param enemiesFour
     */

    public void enemyThirdGroupTwo(SpriteBatch batch, Array<Enemy> enemiesFour) {


        for (Enemy enemy : enemiesFour) {
            enemy.setEnemyX(enemy.getEnemyX() - 1);
            enemy.updateDown(batch);
        }

        System.out.println("Forth Size: "+enemiesFour.size);

        if (enemiesFour.size == 0) {
            forthEnemies = false;
            bossEnemy = true;
            bossEntry=TimeUtils.millis();

        }


    }

    /**
     *  Boss Render , after reaching vertical fixing value its stop moving down
     *   Boss moves even moves horizontally
     * @param batch
     * @param bossEnemy Enemy class object
     */
    public void bossEnemy(SpriteBatch batch, Enemy bossEnemy) {

        bossEnemy.updateDown(batch);
        if (enemyMovementPositive){
        bossEnemy.setEnemyX(bossEnemy.getEnemyX() + 1);
        if (bossEnemy.getEnemyX() + bossEnemy.getEnemyWidth() >= Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/4) {
            enemyMovementNegative = true;
            enemyMovementPositive = false;
        }}
     else if(enemyMovementNegative ){


        bossEnemy.setEnemyX(bossEnemy.getEnemyX() - 1);

        if (bossEnemy.getEnemyX() <= Gdx.graphics.getWidth()/4 )
             {

                enemyMovementPositive = true;
                enemyMovementNegative = false;
            }

    }

     if (bossEnemy.getEnemyY()<Gdx.graphics.getHeight()-bossEnemy.enemyWidth-50)
         bossEnemy.isStopVerticalMovementDown(true);

}

    public boolean isBossEnemy() {
        return bossEnemy;
    }

}