package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class GameScreen<Imgage> implements Screen {

    private GameMain gameM;
    private GameStart gameS;
    private Stage gameScreenStage;
    private Music gameOverMusic;
    private Music gameClearMusic;
    private Table gameScreenTable;
    private TextureRegion myTextureRegion;
    private TextureRegionDrawable myTexRegionDrawable;
    private String starOne,starTwo,starThree;
    private BitmapFont fontScreen;
    private int score;
    private int screenLevel;
    private Image background,window;

    private ImageButton replayButton,closeButton,nextButton,playButton;
    static private String[] screen={"GameOverOne","GameOverTwo","GameCompleted","GameWin"};

    public GameScreen(GameMain g,int score,int screenLevel){
        this.gameM=g;
        this.score=score;
        this.screenLevel=screenLevel;
        create();
    }

    public void create(){

        gameScreenStage = new Stage();
        Gdx.input.setInputProcessor(gameScreenStage);



        myTextureRegion = new TextureRegion(new Texture(Gdx.files.internal("7.png")));
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        background=new Image(myTexRegionDrawable);
        gameScreenStage.addActor(background);





       switch (screenLevel){
           case 1: {
               gameOverFirstLevel();
               break;
           }

           case 2: // gameOver second level
           {
              gameOverSecondLevel();
              break;
           }

           case 3:
           {
                gameLevelCompleted();
                break;

           }

           case 4:
           {
                gameWinner();
                break;
           }
       }


    }

    public void gameOverFirstLevel(){

        gameScreenTable=new Table();
        gameScreenTable.setWidth(gameScreenStage.getWidth());
        gameScreenTable.align(Align.center|Align.left);
        gameScreenTable.setPosition(0,Gdx.graphics.getHeight());

        myTextureRegion = new TextureRegion(new Texture(Gdx.files.internal("18.jpg")));
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        background=new Image(myTexRegionDrawable);
        background.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        gameScreenStage.addActor(background);

        myTextureRegion = new TextureRegion(new Texture(Gdx.files.internal("Table.png")));
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        window=new Image(myTexRegionDrawable);
        window.setBounds(160,310,290,50);
        gameScreenStage.addActor(window);

        myTextureRegion = new TextureRegion(new Texture(Gdx.files.internal("Replay_BTN.png")));
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        replayButton = new ImageButton(myTexRegionDrawable); //Set the button up

        myTextureRegion = new TextureRegion(new Texture(Gdx.files.internal("Close_BTN.png")));
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        closeButton = new ImageButton(myTexRegionDrawable); //Set the button up

        gameScreenTable.padTop(800);
        gameScreenTable.padLeft(150);
        gameScreenTable.add(replayButton).width(Value.percentWidth(.15F,gameScreenTable)).space(125);
        gameScreenTable.add(closeButton).width(Value.percentWidth(.15F,gameScreenTable));

        replayButton.addListener(new ClickListener() {

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                gameM.setScreen(new GameStart(gameM));
                gameOverMusic.dispose();
            }
        });

        closeButton.addListener(new ClickListener() {

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                dispose();
                gameOverMusic.dispose();
            }
        });

       gameScreenStage.addActor(gameScreenTable);

       screenTitle("GAME OVER",170,500,3);
       screenTitle("     SCORE     "+score,160,330,2);

        //>
        //> Initialization of background music when game Over, it keeps playing as is set up on Loop
        //>
        gameOverMusic = Gdx.audio.newMusic(Gdx.files.internal("GameOverSound.wav"));
        gameOverMusic.setLooping(true);
        gameOverMusic.play();



    }

    public void gameOverSecondLevel(){

        gameScreenTable=new Table();
        gameScreenTable.setWidth(gameScreenStage.getWidth());
        gameScreenTable.align(Align.center|Align.left);
        gameScreenTable.setPosition(0,Gdx.graphics.getHeight());

        myTextureRegion = new TextureRegion(new Texture(Gdx.files.internal("18.jpg")));
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        background=new Image(myTexRegionDrawable);
        background.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        gameScreenStage.addActor(background);

        myTextureRegion = new TextureRegion(new Texture(Gdx.files.internal("Table.png")));
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        window=new Image(myTexRegionDrawable);
        window.setBounds(160,310,290,50);
        gameScreenStage.addActor(window);

        myTextureRegion = new TextureRegion(new Texture(Gdx.files.internal("Replay_BTN.png")));
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        replayButton = new ImageButton(myTexRegionDrawable); //Set the button up

        myTextureRegion = new TextureRegion(new Texture(Gdx.files.internal("Close_BTN.png")));
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        closeButton = new ImageButton(myTexRegionDrawable); //Set the button up

        gameScreenTable.padTop(800);
        gameScreenTable.padLeft(150);
        gameScreenTable.add(replayButton).width(Value.percentWidth(.15F,gameScreenTable)).space(125);
        gameScreenTable.add(closeButton).width(Value.percentWidth(.15F,gameScreenTable));

        replayButton.addListener(new ClickListener() {

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                gameOverMusic.dispose();
                gameM.setScreen(new GameSecondLevel(gameM));

            }
        });

        closeButton.addListener(new ClickListener() {

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                dispose();
                gameOverMusic.dispose();
            }
        });

        gameScreenStage.addActor(gameScreenTable);

        screenTitle("GAME OVER",170,500,3);
        screenTitle("     SCORE     "+score,160,330,2);

        gameOverMusic = Gdx.audio.newMusic(Gdx.files.internal("GameOverSound.wav"));
        gameOverMusic.setLooping(true);
        gameOverMusic.play();



    }

    public void gameLevelCompleted(){


            gameScreenTable=new Table();
            gameScreenTable.setWidth(gameScreenStage.getWidth());
            gameScreenTable.align(Align.center|Align.left);
            gameScreenTable.setPosition(0,Gdx.graphics.getHeight());

        myTextureRegion = new TextureRegion(new Texture(Gdx.files.internal("18.jpg")));
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        background=new Image(myTexRegionDrawable);
        background.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        gameScreenStage.addActor(background);

        myTextureRegion = new TextureRegion(new Texture(Gdx.files.internal("Table.png")));
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        window=new Image(myTexRegionDrawable);
        window.setBounds(160,310,290,50);
        gameScreenStage.addActor(window);

            myTextureRegion = new TextureRegion(new Texture(Gdx.files.internal("Replay_BTN.png")));
            myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
            replayButton = new ImageButton(myTexRegionDrawable); //Set the button up

            myTextureRegion = new TextureRegion(new Texture(Gdx.files.internal("Close_BTN.png")));
            myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
            closeButton = new ImageButton(myTexRegionDrawable); //Set the button up

            myTextureRegion = new TextureRegion(new Texture(Gdx.files.internal("Forward_BTN.png")));
            myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
            nextButton = new ImageButton(myTexRegionDrawable); //Set the button up

            gameScreenTable.padTop(800);
            gameScreenTable.padLeft(120);
            gameScreenTable.add(nextButton).width(Value.percentWidth(.15F,gameScreenTable)).space(50);
            gameScreenTable.add(replayButton).width(Value.percentWidth(.15F,gameScreenTable)).space(50);
            gameScreenTable.add(closeButton).width(Value.percentWidth(.15F,gameScreenTable));


        nextButton.addListener(new ClickListener() {

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                gameClearMusic.dispose();
                gameM.setScreen(new GameSecondLevel(gameM));

            }
        });

            replayButton.addListener(new ClickListener() {

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    dispose();
                    gameClearMusic.dispose();
                    gameM.setScreen(new GameStart(gameM));

                }
            });

            closeButton.addListener(new ClickListener() {

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    Gdx.app.exit();
                    dispose();
                    gameClearMusic.dispose();
                }
            });

            gameScreenStage.addActor(gameScreenTable);

            screenTitle("LEVEL COMPLETED",100,500,3);
            screenTitle("     SCORE     "+score,160,330,2);

        gameClearMusic = Gdx.audio.newMusic(Gdx.files.internal("16_Unused.mp3"));
        gameClearMusic.setLooping(true);
        gameClearMusic.play();




    }

    public void gameWinner(){

        gameScreenTable=new Table();
        gameScreenTable.setWidth(gameScreenStage.getWidth());
        gameScreenTable.align(Align.center|Align.left);
        gameScreenTable.setPosition(0,Gdx.graphics.getHeight());

        myTextureRegion = new TextureRegion(new Texture(Gdx.files.internal("18.jpg")));
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        background=new Image(myTexRegionDrawable);
        background.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        gameScreenStage.addActor(background);

        myTextureRegion = new TextureRegion(new Texture(Gdx.files.internal("Table.png")));
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        window=new Image(myTexRegionDrawable);
        window.setBounds(160,310,290,50);
        gameScreenStage.addActor(window);

        myTextureRegion = new TextureRegion(new Texture(Gdx.files.internal("Replay_BTN.png")));
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        replayButton = new ImageButton(myTexRegionDrawable); //Set the button up

        myTextureRegion = new TextureRegion(new Texture(Gdx.files.internal("Close_BTN.png")));
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        closeButton = new ImageButton(myTexRegionDrawable); //Set the button up

        myTextureRegion = new TextureRegion(new Texture(Gdx.files.internal("Play_BTN.png")));
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        playButton = new ImageButton(myTexRegionDrawable); //Set the button up

        gameScreenTable.padTop(800);
        gameScreenTable.padLeft(120);
        gameScreenTable.add(playButton).width(Value.percentWidth(.15F,gameScreenTable)).space(50);
        gameScreenTable.add(replayButton).width(Value.percentWidth(.15F,gameScreenTable)).space(50);
        gameScreenTable.add(closeButton).width(Value.percentWidth(.15F,gameScreenTable));


        playButton.addListener(new ClickListener() {

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                gameClearMusic.dispose();
                gameM.setScreen(new GameStart(gameM));
            }
        });

        replayButton.addListener(new ClickListener() {

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                gameClearMusic.dispose();
                gameM.setScreen(new GameSecondLevel(gameM));

            }
        });

        closeButton.addListener(new ClickListener() {

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                dispose();
            }
        });

        gameScreenStage.addActor(gameScreenTable);

        screenTitle("CONGRATULATION! YOU WON",100,500,2);
        screenTitle("     SCORE     "+score,160,330,2);

        gameClearMusic = Gdx.audio.newMusic(Gdx.files.internal("16_Unused.mp3"));
        gameClearMusic.setLooping(true);
        gameClearMusic.play();
    }

    public void screenTitle(String screenTitle,int x,int y,int scale){
        fontScreen = new BitmapFont();
        String text = screenTitle;
        Label.LabelStyle style = new Label.LabelStyle( fontScreen, Color.GOLD);
        Label instructions = new Label( text, style );
        instructions.setFontScale(scale);
        instructions.setPosition(x, y);  //x=170 ,y=500
        // repeating color pulse effect
        instructions.addAction(
                Actions.forever(
                        Actions.sequence(
                                Actions.color( new Color(1, 1, 0, 1), 0.5f ),
                                Actions.delay( 0.5f ),
                                Actions.color( new Color(0.5f, 0.5f, 0, 1), 0.5f )
                        )
                )
        );
        gameScreenStage.addActor(instructions);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {


        // update
        gameScreenStage.act(delta);
        // draw graphics
        Gdx.gl.glClearColor(0.8f, 0.8f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameScreenStage.draw();
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


        gameScreenStage.dispose();
        fontScreen.dispose();


    }
}
