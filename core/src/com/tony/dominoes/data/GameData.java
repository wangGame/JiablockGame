package com.tony.dominoes.data;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.kw.gdx.asset.Asset;
import com.tony.dominoes.group.PartPicActor;

public class GameData {
    private int widthSplit;
    private int heightSplit;
    private int offset;
    private float picWidth;
    private float picHight;
    private Array<PartData> partData;

    public GameData(){
        this.partData = new Array<>();
        this.widthSplit = 4;
        this.heightSplit = 4;
        this.offset = 4;
    }

    public Array<PartData> getPartData(){
        return partData;
    }



    public float getPicWidth() {
        return picWidth;
    }

    public void setPicWidth(float picWidth) {
        this.picWidth = picWidth;
    }

    public float getPicHight() {
        return picHight;
    }

    public void setPicHight(float picHight) {
        this.picHight = picHight;
    }

    public void initData() {
        this.partData.clear();
        Texture texture = Asset.getAsset().getTexture("filePic.png");
        this.picWidth =  texture.getWidth();
        this.picHight = texture.getHeight();
        int perH = texture.getHeight() / heightSplit;
        int perW = texture.getWidth() / widthSplit;
        for (int i = 0; i < widthSplit; i++) {
            for (int j = 0; j < widthSplit; j++) {
                PartData data = new PartData();
                data.setSplitX(widthSplit);
                data.setSplitY(heightSplit);
                data.setOffset(offset);
                data.setPerW(perW);
                data.setPerH(perH);
                data.setPosX(i);
                data.setPosY(j);
                data.setCurrentX(i);
                data.setCurrentY(j);
                partData.add(data);
            }
        }
    }

    public void shuffleAll() {
        Array<PartData> partDataTemp = new Array<>(partData);
        int size = partDataTemp.size;
        for (int i = 0; i < size; i++) {
            PartData random = partDataTemp.random();
            random.setCurrentX(i/widthSplit);
            random.setCurrentY(i%widthSplit);
            partDataTemp.removeValue(random,false);
            System.out.println(partDataTemp.size);
        }
    }

    public int getHeightSplit() {
        return heightSplit;
    }

    public int getWidthSplit() {
        return widthSplit;
    }
}
