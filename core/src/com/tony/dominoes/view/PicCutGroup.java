package com.tony.dominoes.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.kw.gdx.asset.Asset;

public class PicCutGroup extends Group {
    public PicCutGroup(){
        Texture texture = Asset.getAsset().getTexture("filePic.png");
        int uSplitNum = 4;
        int vSplitNum = 4;
        float offsetu = 10f/texture.getWidth();
        float offsetv = 10f/texture.getHeight();


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


                PartActor partActor = new PartActor(region);
                addActor(partActor);
                partActor.setPosition(i * (texture.getWidth()/4.0f),i1*(texture.getHeight()/4.0f));

            }
        }
    }
}
