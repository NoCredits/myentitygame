package com.mygdx.entitygame.loader;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class B2dAssetManager {
    public  final AssetManager manager = new AssetManager();

    // Music
    public final String playingSong = "music/Rolemusic_-_pl4y1ng.mp3";

    public final String playerImage = "images/player.png";
    public final String enemyImage = "images/enemy.png";

    // Textures
    public final String gameImages = "images/game.atlas";
    public final String loadingImages = "images/loading.atlas";


    // Sounds
    public final String boingSound = "sounds/boing.wav";
    public final String pingSound = "sounds/ping.wav";

    // Music
    // Skin
    public final String skin = "skin/glassy-ui.json";

    // Textures
    // Particle Effects
    public final String smokeEffect = "particles/smoke.pe";
    public final String waterEffect = "particles/water.pe";
    public final String fireEffect = "particles/fire.pe";
    public void queueAddImages(){
        //manager.load(playerImage, Texture.class);
        //manager.load(enemyImage, Texture.class);
        manager.load(gameImages, TextureAtlas.class);
        manager.load(loadingImages, TextureAtlas.class);
    }
    public void queueAddMusic(){
        manager.load(playingSong, Music.class);
    }

    public void queueAddFonts(){
    }

    public void queueAddParticleEffects(){
    }
    // Texture
    //
    public void queueAddSounds(){
        manager.load(boingSound, Sound.class);
        manager.load(pingSound, Sound.class);
    }


}
