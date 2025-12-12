package com.tony.dominoes.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.kw.gdx.constant.Constant;
import com.tony.dominoes.data.GameData;
import com.tony.dominoes.data.PartData;
import com.tony.dominoes.group.PartPicActor;

public class GameContent extends Group {
    private Array<PartPicActor> partPicActors;
    private PartPicActor tempTouchActor;
    private Vector2 touchTempV2;
    private Vector2 touchV2;
    private GameData gameData;
    public GameContent(){
        setSize(Constant.WIDTH,Constant.HIGHT);
        this.partPicActors = new Array<>();
        this.touchV2 = new Vector2();
        this.touchTempV2 = new Vector2();
        gameData = new GameData();
        gameData.initData();
        gameData.shuffleAll();
        Array<PartData> partData = gameData.getPartData();
        Group picGroup = new Group();
        addActor(picGroup);
        picGroup.setSize(gameData.getPicWidth(),gameData.getPicHight());
        picGroup.setPosition(getWidth()/2f,getHeight()/2f, Align.center);

        for (PartData partDatum : partData) {
            PartPicActor partPicActor = new PartPicActor(partDatum);
            picGroup.addActor(partPicActor);
            partPicActors.add(partPicActor);
        }

        picGroup.setOrigin(Align.center);
        picGroup.setScale(1.3f);
        picGroup.addListener(new ClickListener(){
            private Vector2 temV2 = new Vector2();
            private Vector2 temV3 = new Vector2();
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                for (PartPicActor part : partPicActors) {
                    touchV2.set(x,y);
                    part.parentToLocalCoordinates(touchV2);
                    Actor hit = part.hit(touchV2.x, touchV2.y);
                    if (hit!=null){
                        touchTempV2.set(touchV2.x,touchV2.y);
                        tempTouchActor = (PartPicActor) hit;
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
                    tempTouchActor.toFront();
                    tempTouchActor.setPosition(x - touchTempV2.x, y - touchTempV2.y);


                    for (PartPicActor partPicActor : partPicActors) {
                        PartData partDatum = partPicActor.getPartDatum();
                        temV2.set(partDatum.getCurrentX() * partDatum.getPerW() + partDatum.getPerW()/2f, partDatum.getCurrentY() * partDatum.getPerH()+ partDatum.getPerH()/2f);
                        temV3.set(tempTouchActor.getX(Align.center),tempTouchActor.getY(Align.center));
                        float dst = temV2.dst(temV3);


                    }
                }
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                if (tempTouchActor!=null){
                    for (PartPicActor partPicActor : partPicActors) {
                        PartData partDatum = partPicActor.getPartDatum();
                        temV2.set(partDatum.getCurrentX() * partDatum.getPerW() + partDatum.getPerW()/2f,partDatum.getCurrentY() * partDatum.getPerH() + partDatum.getPerH()/2f);
                        temV3.set(tempTouchActor.getX(Align.center),tempTouchActor.getY(Align.center));
                        float dst = temV2.dst(temV3);
                        if (dst<tempTouchActor.getPartDatum().getPerW()/2f) {
                            changePart(tempTouchActor,partPicActor);
                            tempTouchActor = null;
                            checkAllConnect();
                            return;
                        }
                    }
                    tempTouchActor.setPartPosition();
                }
            }
        });

        checkAllConnect();
    }

    private void checkAllConnect() {
        for (int i = 0; i < partPicActors.size; i++) {
            PartPicActor partPicActor = partPicActors.get(i);
            PartData partDatum = partPicActor.getPartDatum();
            partPicActor.resetDir();
            int currentX = partDatum.getCurrentX();
            int currentY = partDatum.getCurrentY();
            for (int i1 = 0; i1 < partPicActors.size; i1++) {
                PartPicActor other = partPicActors.get(i1);
                PartData od = other.getPartDatum();
                int ox = od.getCurrentX();
                int oy = od.getCurrentY();
                int sx = od.getPosX();
                int sy = od.getPosY();
                if (currentX - 1 <0){
                    partPicActor.setLeft(false);
                }

                if (currentX + 1>= gameData.getWidthSplit()){
                    partPicActor.setRight(false);
                }

                if (currentY - 1 <0){
                    partPicActor.setDown(false);
                }

                if (currentY + 1>= gameData.getHeightSplit()){
                    partPicActor.setUp(false);
                }


                int posX = partPicActor.getPartDatum().getPosX();
                int posY = partPicActor.getPartDatum().getPosY();
                // 上
                if (ox == currentX && oy == currentY + 1){
                    if (posX == sx && sy == posY + 1){
                        partPicActor.setUp(true);
                    }
                }
                    // 下
                else if (ox == currentX && oy == currentY - 1) {
                    if (posX == sx && sy == posY - 1){
                        partPicActor.setDown(true);
                    }
                }

                    // 左
                else if (ox == currentX - 1 && oy == currentY) {
                    if (sx == posX - 1 && posY == sy){
                        partPicActor.setLeft(true);
                    }
                }

                    // 右
                else if (ox == currentX + 1 && oy == currentY) {
                    if (sx == posX + 1 && posY == sy){
                        partPicActor.setRight(true);
                    }
                }

                partPicActor.updateBorder();
            }
        }


        checkSuccess();
    }

    private void checkSuccess() {
        for (PartPicActor partPicActor : partPicActors) {
            if (!partPicActor.checkSuccess()) {
                return;
            }
        }
        System.out.println("success  =-===========================  ");
    }

    private void changePart(PartPicActor tempTouchActor, PartPicActor partPicActor) {
        tempTouchActor.updateData(partPicActor.getPartDatum());
        tempTouchActor.setPartPosition();
        partPicActor.setPartPosition();
    }

}
