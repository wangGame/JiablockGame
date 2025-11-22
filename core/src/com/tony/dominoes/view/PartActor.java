package com.tony.dominoes.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;

public class PartActor extends Group {
    private int index = 0;
    private TextureRegion region;
    public PartActor(TextureRegion region){
        this.region = region;
        Image image = new Image(region);
        addActor(image);
        setSize(image.getWidth(),image.getHeight());
        setDebug(true);
    }
}
