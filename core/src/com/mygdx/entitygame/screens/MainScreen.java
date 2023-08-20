package com.mygdx.entitygame.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entitygame.B2dContactListener;
import com.mygdx.entitygame.BodyFactory;
import com.mygdx.entitygame.MyGame;
import com.mygdx.entitygame.components.B2dBodyComponent;
import com.mygdx.entitygame.components.CollisionComponent;
import com.mygdx.entitygame.components.PlayerComponent;
import com.mygdx.entitygame.components.StateComponent;
import com.mygdx.entitygame.components.TextureComponent;
import com.mygdx.entitygame.components.TransformComponent;
import com.mygdx.entitygame.components.TypeComponent;
import com.mygdx.entitygame.controller.KeyboardController;
import com.mygdx.entitygame.model.B2dModel;
import com.mygdx.entitygame.systems.AnimationSystem;
import com.mygdx.entitygame.systems.CollisionSystem;
import com.mygdx.entitygame.systems.PhysicsDebugSystem;
import com.mygdx.entitygame.systems.PhysicsSystem;
import com.mygdx.entitygame.systems.PlayerControlSystem;
import com.mygdx.entitygame.systems.RenderingSystem;

public class MainScreen implements Screen {

    //private final B2dModel model;
    private final OrthographicCamera cam;
   // private final Box2DDebugRenderer debugRenderer;
    private final KeyboardController controller;
    private final SpriteBatch sb;
    private final PooledEngine engine;
    private final World world;

    TextureAtlas.AtlasRegion playerTex;

    private MyGame parent; // a field to store our orchestrator
    // our constructor with a Box2DTutorial argument
    public MainScreen(MyGame game){
//        parent = game;
//        controller = new KeyboardController();
//        cam = new OrthographicCamera(32,24);
//
//        model = new B2dModel(controller,cam);
//
//        debugRenderer = new Box2DDebugRenderer(true,true,true,true,false,true);
//
//// tells our asset manger that we want to load the images set in loadImages method
//        parent.assMan.queueAddImages();
//// tells the asset manager to load the images and wait until finsihed loading.
//        parent.assMan.manager.finishLoading();
//// gets the images as a texture
//       // playerTex = parent.assMan.manager.get("images/player.png");
//        //playerTex = parent.assMan.manager.get("images/player.png");
//
//
//        sb = new SpriteBatch();
//        sb.setProjectionMatrix(cam.combined);
//
//        TextureAtlas atlas = parent.assMan.manager.get("images/game.atlas"); // new
//        playerTex = atlas.findRegion("player"); // updated
//

            parent = game;
            controller = new KeyboardController();
              world = new World(new Vector2(0, -10f), true);
            world.setContactListener(new B2dContactListener());
            BodyFactory bodyFactory = BodyFactory.getInstance(world);

            parent.assMan.queueAddSounds();
            parent.assMan.manager.finishLoading();
            TextureAtlas atlas = parent.assMan.manager.get("images/game.atlas", TextureAtlas.class);
            Sound ping = parent.assMan.manager.get("sounds/ping.wav", Sound.class);
            Sound boing = parent.assMan.manager.get("sounds/boing.wav", Sound.class);


            sb = new SpriteBatch();
            // Create our new rendering system
            RenderingSystem renderingSystem = new RenderingSystem(sb);
            cam = renderingSystem.getCamera();
            sb.setProjectionMatrix(cam.combined);

            //create a pooled engine
            engine = new PooledEngine();

            // add all the relevant systems our engine should run
            engine.addSystem(new AnimationSystem());
            engine.addSystem(renderingSystem);
            engine.addSystem(new PhysicsSystem(world));
            engine.addSystem(new PhysicsDebugSystem(world, renderingSystem.getCamera()));
            engine.addSystem(new CollisionSystem());
            engine.addSystem(new PlayerControlSystem(controller));

            // create some game objects
            createPlayer();
            createPlatform(2,2);
            createPlatform(2,7);
            createPlatform(7,2);
            createPlatform(7,7);

            createFloor();

    }
    private void createPlayer(){

        // Create the Entity and all the components that will go in the entity
        Entity entity = engine.createEntity();
        B2dBodyComponent b2dbody = engine.createComponent(B2dBodyComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        PlayerComponent player = engine.createComponent(PlayerComponent.class);
        CollisionComponent colComp = engine.createComponent(CollisionComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        StateComponent stateCom = engine.createComponent(StateComponent.class);

        // create the data for the components and add them to the components
        BodyFactory bodyFactory = BodyFactory.getInstance(world);
        b2dbody.body = bodyFactory.makeCirclePolyBody(10,10,1, BodyFactory.STONE, BodyDef.BodyType.DynamicBody,true);
        // set object position (x,y,z) z used to define draw order 0 first drawn
        position.position.set(10,10,0);

        TextureAtlas atlas = parent.assMan.manager.get("images/game.atlas");
        texture.region = atlas.findRegion("player");
        type.type = TypeComponent.PLAYER;
        stateCom.set(StateComponent.STATE_NORMAL);
        b2dbody.body.setUserData(entity);

        // add the components to the entity
        entity.add(b2dbody);
        entity.add(position);
        entity.add(texture);
        entity.add(player);
        entity.add(colComp);
        entity.add(type);
        entity.add(stateCom);

        // add the entity to the engine
        engine.addEntity(entity);

    }
    private void createPlatform(float x, float y){
        BodyFactory bodyFactory = BodyFactory.getInstance(world);
        Entity entity = engine.createEntity();
        B2dBodyComponent b2dbody = engine.createComponent(B2dBodyComponent.class);
        b2dbody.body = bodyFactory.makeBoxPolyBody(x, y, 3, 0.2f, BodyFactory.STONE, BodyDef.BodyType.StaticBody);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        TextureAtlas atlas = parent.assMan.manager.get("images/game.atlas");
        texture.region = atlas.findRegion("player");
        TypeComponent type = engine.createComponent(TypeComponent.class);
        type.type = TypeComponent.SCENERY;
        b2dbody.body.setUserData(entity);

        entity.add(b2dbody);
        entity.add(texture);
        entity.add(type);

        engine.addEntity(entity);

    }

    private void createFloor(){
        BodyFactory bodyFactory = BodyFactory.getInstance(world);
        Entity entity = engine.createEntity();
        B2dBodyComponent b2dbody = engine.createComponent(B2dBodyComponent.class);
        b2dbody.body = bodyFactory.makeBoxPolyBody(0, 0, 100, 0.2f, BodyFactory.STONE, BodyDef.BodyType.StaticBody);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        TextureAtlas atlas = parent.assMan.manager.get("images/game.atlas");
        texture.region = atlas.findRegion("player");
        TypeComponent type = engine.createComponent(TypeComponent.class);
        type.type = TypeComponent.SCENERY;

        b2dbody.body.setUserData(entity);

        entity.add(b2dbody);
        entity.add(texture);
        entity.add(type);

        engine.addEntity(entity);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(controller);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        engine.update(delta);
//        model.logicStep(delta);
//        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//       // debugRenderer.render(model.world, cam.combined);
//        SpriteBatch sb = new SpriteBatch();
//        sb.setProjectionMatrix(cam.combined);
//
//        sb.begin();
//
//        sb.draw(playerTex,model.player.getPosition().x -1,model.player.getPosition().y -1,2,2);
//
//        sb.end();

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

