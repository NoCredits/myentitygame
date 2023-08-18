package com.mygdx.entitygame.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.mygdx.entitygame.MyGame;
import com.mygdx.entitygame.controller.KeyboardController;
import com.mygdx.entitygame.model.B2dModel;

public class MainScreen implements Screen {

    private final B2dModel model;
    private final OrthographicCamera cam;
    private final Box2DDebugRenderer debugRenderer;
    private final KeyboardController controller;
    private final SpriteBatch sb;

    TextureAtlas.AtlasRegion playerTex;

    private MyGame parent; // a field to store our orchestrator
    // our constructor with a Box2DTutorial argument
    public MainScreen(MyGame game){
        parent = game;
        controller = new KeyboardController();
        cam = new OrthographicCamera(32,24);

        model = new B2dModel(controller,cam);

        debugRenderer = new Box2DDebugRenderer(true,true,true,true,false,true);

// tells our asset manger that we want to load the images set in loadImages method
        parent.assMan.queueAddImages();
// tells the asset manager to load the images and wait until finsihed loading.
        parent.assMan.manager.finishLoading();
// gets the images as a texture
       // playerTex = parent.assMan.manager.get("images/player.png");
        //playerTex = parent.assMan.manager.get("images/player.png");


        sb = new SpriteBatch();
        sb.setProjectionMatrix(cam.combined);

        TextureAtlas atlas = parent.assMan.manager.get("images/game.atlas"); // new
        playerTex = atlas.findRegion("player"); // updated


    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(controller);
    }

    @Override
    public void render(float delta) {
        model.logicStep(delta);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        debugRenderer.render(model.world, cam.combined);
        SpriteBatch sb = new SpriteBatch();
        sb.setProjectionMatrix(cam.combined);

        sb.begin();

        sb.draw(playerTex,model.player.getPosition().x -1,model.player.getPosition().y -1,2,2);

        sb.end();

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

