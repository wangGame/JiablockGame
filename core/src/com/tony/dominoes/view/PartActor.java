package com.tony.dominoes.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;

public class PartActor extends Group {
    private int index = 0;
    private Texture region;
    private Image allImage;
    private Image partImage;
    public PartActor(Texture region){
        this.region = region;
        allImage = new Image(region);
        addActor(allImage);
        setSize(allImage.getWidth(),allImage.getHeight());

        partImage = new Image(region);

    }
}
