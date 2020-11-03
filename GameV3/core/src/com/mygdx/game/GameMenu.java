package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class GameMenu implements Screen{

    private GameMain gameM;
    private Stage menuStage;
    private Music menuMusic;
    private BitmapFont fontMenu;
    private Table menuTable;
    private OrthographicCamera camera;
    private TextureRegion myTextureRegion;
    private TextureRegionDrawable myTexRegionDrawable;
    private ImageButton playButton,exitButton,facebookButton;
    private Image background;


    public GameMenu(GameMain g) {
        this.gameM=g;
        create();
    }


    public  void create() {
        menuStage = new Stage();
        Gdx.input.setInputProcessor(menuStage);



        myTextureRegion = new TextureRegion(new Texture(Gdx.files.internal("7.png")));
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        background=new Image(myTexRegionDrawable);
        menuStage.addActor(background);

        myTextureRegion = new TextureRegion(new Texture(Gdx.files.internal("Start_BTN.png")));
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        playButton = new ImageButton(myTexRegionDrawable); //Set the button up



        myTextureRegion = new TextureRegion(new Texture(Gdx.files.internal("EXIT_BTN.png")));
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        exitButton = new ImageButton(myTexRegionDrawable); //Set the button up


        myTextureRegion = new TextureRegion(new Texture(Gdx.files.internal("Facebook_BTN.png")));
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        facebookButton = new ImageButton(myTexRegionDrawable); //Set the button up



        menuStage.addActor( background );

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 600, 600);


        menuTable=new Table();
        menuTable.setWidth(menuStage.getWidth());
        menuTable.align(Align.center|Align.top);
        menuTable.setPosition(0,Gdx.graphics.getHeight());



        playButton.addListener(new ClickListener() {

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("TouchUp");
             // gameM.setScreen(new GameStart(gameM));
              gameM.setScreen(new GameStart(gameM));
                dispose();
            }
        });



        exitButton.addListener(new ClickListener() {

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("TouchUp");
                Gdx.app.exit();
                dispose();

            }
        });


        menuTable.padTop(200);
        menuTable.add(playButton).width(Value.percentWidth(.25F,menuTable));
        menuTable.row();
        menuTable.add(exitButton).width(Value.percentWidth(.25F,menuTable));
        menuTable.row();
        menuTable.add(facebookButton).width(Value.percentWidth(.10F,menuTable)).height(Value.percentHeight(.10F,menuTable));


        menuStage.addActor(menuTable);


        //>
        //> Initialization of background music when game starts, it keeps playing as is set up on Loop
        //>
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("Star-wars.mp3"));
        menuMusic.setLooping(true);



         fontMenu = new BitmapFont();
        String text = " SPACE SHOOTER";
        LabelStyle style = new LabelStyle( fontMenu, Color.SKY);
        Label instructions = new Label( text, style );
        instructions.setFontScale(3);
        instructions.setPosition(90, 500);
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
        menuStage.addActor( instructions );

    }
    @Override
    public void dispose() {
    menuStage.dispose();
    menuMusic.dispose();
    fontMenu.dispose();


    }

    @Override
    public void hide() {


    }

    @Override
    public void pause() {


    }


    @Override
    public void render(float dt) {

        // update
        menuStage.act(dt);



        // draw graphics
        Gdx.gl.glClearColor(0.8f, 0.8f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        menuStage.draw();

    }

    @Override
    public void resize(int x, int y) {



    }

    @Override
    public void resume() {


    }

    @Override
    public void show() {

  menuMusic.play();
    }

}

