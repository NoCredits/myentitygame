package com.mygdx.entitygame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.entitygame.loader.B2dAssetManager;
import com.mygdx.entitygame.screens.EndScreen;
import com.mygdx.entitygame.screens.LoadingScreen;
import com.mygdx.entitygame.screens.MainScreen;
import com.mygdx.entitygame.screens.MenuScreen;
import com.mygdx.entitygame.screens.PreferencesScreen;

public class MyGame extends Game {
	private LoadingScreen loadingScreen;
	private PreferencesScreen preferencesScreen;
	private MenuScreen menuScreen;
	private MainScreen mainScreen;
	private EndScreen endScreen;
	private AppPreferences preferences;
	public B2dAssetManager assMan = new B2dAssetManager();

	public Music playingSong;
		
	public final static int MENU = 0;
	public final static int PREFERENCES = 1;
	public final static int APPLICATION = 2;
	public final static int ENDGAME = 3;
//	SpriteBatch batch;
//	Texture img;
//
//	@Override
//	public void create () {
//		batch = new SpriteBatch();
//		img = new Texture("badlogic.jpg");
//	}
//
//	@Override
//	public void render () {
//		ScreenUtils.clear(1, 0, 0, 1);
//		batch.begin();
//		batch.draw(img, 0, 0);
//		batch.end();
//	}
//
//	@Override
//	public void dispose () {
//		batch.dispose();
//		img.dispose();
//	}

	public void changeScreen(int screen){
		switch(screen){
			case MENU:
				if(menuScreen == null) menuScreen = new MenuScreen(this); // added (this)
				this.setScreen(menuScreen);
				break;
			case PREFERENCES:
				if(preferencesScreen == null) preferencesScreen = new PreferencesScreen(this); // added (this)
				this.setScreen(preferencesScreen);
				break;
			case APPLICATION:
				if(mainScreen == null) mainScreen = new MainScreen(this); //added (this)
				this.setScreen(mainScreen);
				break;
			case ENDGAME:
				if(endScreen == null) endScreen = new EndScreen(this);  // added (this)
				this.setScreen(endScreen);
				break;
		}
	}

	@Override
	public void create() {

		System.out.println("Create 1");
		loadingScreen = new LoadingScreen(this);
		System.out.println("Create 2");
		preferences = new AppPreferences();

		setScreen(loadingScreen);
		//setScreen(preferencesScreen);
		System.out.println("Create 3");
		// tells our asset manger that we want to load the images set in loadImages method
		assMan.queueAddMusic();
		// tells the asset manager to load the images and wait until finished loading.
		assMan.manager.finishLoading();
		// loads the 2 sounds we use
		playingSong = assMan.manager.get("music/Rolemusic_-_pl4y1ng.mp3");

		playingSong.play();
	}
	@Override
	public void dispose(){
		playingSong.dispose();
		assMan.manager.dispose();
	}
	public AppPreferences getPreferences(){
		return this.preferences;
	}
}
