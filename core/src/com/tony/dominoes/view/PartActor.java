package com.tony.dominoes.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Null;

public class PartActor extends Group {
    private int stdX;
    private int stdY;
    private int currentX;
    private int currentY;
    private float distance;
    private TextureRegion region;
    public PartActor(TextureRegion region){
        this.region = region;
        Image image = new Image(region);
        addActor(image);
        setTouchable(Touchable.disabled);
    }

    private Vector2 touchV2 = new Vector2();
    public Actor hit (float x, float y) {
        touchV2.set(x,y);
        return touchV2.x >= 0 && touchV2.x < getWidth() && touchV2.y >= 0 && touchV2.y < getHeight() ? this : null;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    public void setStdX(int stdX) {
        this.stdX = stdX;
    }

    public void setStdY(int stdY) {
        this.stdY = stdY;
    }

    public void setPartPosition(){
        setPosition(currentX*getWidth()+distance + getWidth() / 2,currentY*getHeight()+distance + getHeight() / 2, Align.center);
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }
}
