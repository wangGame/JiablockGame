package com.tony.dominoes.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.kw.gdx.asset.Asset;

public class GameView extends Group {
    private Array<PartActor> parts;
    private PartActor tempTouchActor;
    private Vector2 touchTempV2;
    private Vector2 touchV2;
    private float distance ;
    private float partWidth;
    private float partHeight;

    public GameView(){
        this.distance = 10;
        this.touchTempV2 = new Vector2();
        this.touchV2 = new Vector2();
        this.parts = new Array<>();
        int uSplitNum = 4;
        int vSplitNum = 4;
        Texture texture = Asset.getAsset().getTexture("filePic.png");
        float offsetu = distance/texture.getWidth();
        float offsetv = distance/texture.getHeight();
        this.partWidth = texture.getWidth()/uSplitNum;
        this.partHeight = texture.getHeight()/vSplitNum;
        setSize(texture.getWidth(),texture.getHeight());
        setDebug(true);
        for (int i = 0; i < uSplitNum; i++) {
            for (int i1 = 0; i1 < vSplitNum; i1++) {
                TextureRegion region = new TextureRegion();
                region.setRegion(texture);

                float uSplitN = 1.f/uSplitNum;
                float vSplitN = 1.f/vSplitNum;

                float uStart = uSplitN * i + offsetu;
                float uEnd = uSplitN *(i+1) - offsetu;

                float vStart = 1.0f - (i1+1)*vSplitN + offsetv;
                float vEnd = 1.0f - (i1*vSplitN) - offsetv;


                region.setRegion(
                        uStart,
                        vStart,
                        uEnd,
                        vEnd);
                
                //原来的   当前的  
                PartActor partActor = new PartActor(region);
                addActor(partActor);
                partActor.setWidth(partWidth);
                partActor.setHeight(partHeight);
                partActor.setCurrentX(i);
                partActor.setCurrentY(i1);
                partActor.setDistance(distance);
                partActor.setStdX(i);
                partActor.setStdY(i1);
                partActor.setPartPosition();
                parts.add(partActor);

            }
        }
        addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                for (PartActor part : parts) {
                    touchV2.set(x,y);
                    part.parentToLocalCoordinates(touchV2);
                    Actor hit = (PartActor) part.hit(touchV2.x, touchV2.y);
                    if (hit!=null){
                        touchTempV2.set(touchV2.x,touchV2.y);
                        tempTouchActor = (PartActor) hit;
                        tempTouchActor.setColor(Color.BLACK);
                        break;
                    }
                }
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                super.touchDragged(event, x, y, pointer);
                if (tempTouchActor!=null) {
                    tempTouchActor.setPosition(x-touchTempV2.x, y-touchTempV2.y);
                }
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                if (tempTouchActor!=null){
                    int convert = convert(x);

                    tempTouchActor.setPartPosition();
                }
                tempTouchActor = null;
            }
        });
    }

    public int convert(float pos){
        return (int) (pos / partWidth);
    }
}
