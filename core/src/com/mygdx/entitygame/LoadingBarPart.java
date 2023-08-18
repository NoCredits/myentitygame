package com.mygdx.entitygame;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class LoadingBarPart extends Actor {

    private final TextureAtlas.AtlasRegion image;

    Texture currentFrame;
    private final Animation flameAnimation;
    private float stateTime;

    @Override
    public void draw(Batch batch, float parentAlpha) {
        
        super.draw(batch, parentAlpha);
        batch.draw(image, getX(),getY(), 30, 30);
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);

        batch.draw(currentFrame, getX()-5,getY(), 40, 40);
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta; // Accumulate elapsed animation time
        currentFrame = (Texture) flameAnimation.getKeyFrame(stateTime, true);
    }

    public LoadingBarPart(TextureAtlas.AtlasRegion ar, Animation an) {
        super();
        image = ar;
        flameAnimation = an;
        this.setWidth(30);
        this.setHeight(25);
        this.setVisible(false);
    }

}
