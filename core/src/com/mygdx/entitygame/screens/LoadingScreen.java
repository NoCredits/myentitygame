package com.mygdx.entitygame.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.entitygame.MyGame;

public class LoadingScreen implements Screen {

    private final SpriteBatch sb;
    private MyGame parent; // a field to store our or
    private TextureAtlas atlas;
    private TextureAtlas.AtlasRegion title;
    private Animation flameAnimation;

    public final int IMAGE = 0;		// loading images
    public final int FONT = 1;		// loading fonts
    public final int PARTY = 2;		// loading particle effects
    public final int SOUND = 3;		// loading sounds
    public final int MUSIC = 4;		// loading music

    private int currentLoadingStage = 0;

    // timer for exiting loading screen
    public float countDown = 0.1f;
    private TextureAtlas.AtlasRegion dash;
    private Stage stage;
    private Table table;
    private Image titleImage;
    private TextureAtlas.AtlasRegion copyright;
    private Image copyrightImage;
    private Table loadingTable;
    private TextureAtlas.AtlasRegion background;// chestrator
    private float stateTime;

    // our constructor with a Box2DTutorial argument
    public LoadingScreen(MyGame game){
        parent = game;
        sb = new SpriteBatch();
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);// setting the argument to our field.
    }

    @Override
    public void show() {
        // load loading images and wait until finished
        parent.assMan.queueAddImages();
        parent.assMan.manager.finishLoading();

// get images used to display loading progress
        atlas = parent.assMan.manager.get("images/loading.atlas");
        title = atlas.findRegion("staying-alight-logo");
        dash = atlas.findRegion("loading-dash");

// initiate queueing of images but don't start loading
        flameAnimation = new Animation(0.07f, atlas.findRegions("flames/flames"), com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP);  //new

// initiate queueing of images but don't start loading
        //parent.assMan.queueAddImages();
        //System.out.println("Loading images....");

        stateTime = 0f;

        System.out.println("After show");
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0,0,0,1); //  clear the screen
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // start SpriteBatch and draw the logo
//        sb.begin();
//        sb.draw(title, 135, 250);
//        sb.end();

        stateTime += delta; // Accumulate elapsed animation time  // new

        TextureRegion currentFrame = (TextureRegion) flameAnimation.getKeyFrame(stateTime, true);  //new

        sb.begin();
        drawLoadingBar(currentLoadingStage * 2, currentFrame);  // new
        sb.draw(title, 135, 250);
        sb.end();
        // check if the asset manager has finished loading
        if (parent.assMan.manager.update()) { // Load some, will return true if done loading
            currentLoadingStage+= 1;
            switch(currentLoadingStage){
                case FONT:
                    System.out.println("Loading fonts....");
                    parent.assMan.queueAddFonts(); // first load done, now start fonts
                    break;
                case PARTY:
                    System.out.println("Loading Particle Effects....");
                    parent.assMan.queueAddParticleEffects(); // fonts are done now do party effects
                    break;
                case SOUND:
                    System.out.println("Loading Sounds....");
                    parent.assMan.queueAddSounds();
                    break;
                case MUSIC:
                    System.out.println("Loading fonts....");
                    parent.assMan.queueAddMusic();
                    break;
                case 5:
                    System.out.println("Finished"); // all done
                    break;
            }
            if (currentLoadingStage >5){
                countDown -= delta;  // timer to stay on loading screen for short preiod once done loading
                currentLoadingStage = 5;  // cap loading stage to 5 as will use later to display progress bar anbd more than 5 would go off the screen
                if(countDown < 0){ // countdown is complete
                    parent.changeScreen(MyGame.MENU);  /// go to menu screen
                }
            }
        }


    }
    private void drawLoadingBar(int stage, TextureRegion currentFrame){
        for(int i = 0; i < stage;i++){
            sb.draw(currentFrame, 50 + (i * 50), 150, 50, 50);
            sb.draw(dash, 35 + (i * 50), 140, 80, 80);
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

    }
}
