package com.tony.dominoes.view;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
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

import java.util.HashMap;
import java.util.HashSet;

public class GameContent extends Group {
    private Array<PartPicActor> partPicActors;
    private PartPicActor tempTouchActor;
    private Vector2 touchV2;
    private GameData gameData;
    public GameContent(){
        setSize(Constant.WIDTH,Constant.HIGHT);
        this.partPicActors = new Array<>();
        this.touchV2 = new Vector2();
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
            private HashSet<PartPicActor> collectAll = new HashSet<>();
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                collectAll.clear();
                for (PartPicActor part : partPicActors) {
                    touchV2.set(x,y);
                    part.parentToLocalCoordinates(touchV2);
                    Actor hit = part.hit(touchV2.x, touchV2.y);
                    if (hit!=null){
                        collectAll.add(part);
                        tempTouchActor = (PartPicActor) hit;
                        tempTouchActor.setColor(Color.BLACK);
                        collectAll(tempTouchActor);
                        for (PartPicActor partPicActor : collectAll) {
                            partPicActor.setTouchTempV2(x,y);
                        }
                        break;
                    }
                }
                return super.touchDown(event, x, y, pointer, button);
            }

            private void collectAll(PartPicActor partPicActor) {
                Array<PartPicActor> adjacents = partPicActor.getAdjacents();
                for (PartPicActor adjacent : adjacents) {
                    if (!collectAll.contains(adjacent)) {
                        collectAll.add(adjacent);
                        collectAll(adjacent);
                    }
                }
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                super.touchDragged(event, x, y, pointer);
                if (tempTouchActor!=null) {
                    for (PartPicActor partPicActor : collectAll) {
                        partPicActor.toFront();
                        partPicActor.move(x,y);
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
                            changePart(collectAll,partPicActors,partPicActor);
                            tempTouchActor = null;
                            checkAllConnect();
                            return;
                        }
                    }
                    for (PartPicActor partPicActor : collectAll) {
                        partPicActor.setPartPosition();
                    }
                }
            }
        });

        checkAllConnect();
    }

    private void checkAllConnect() {
        for (int i = 0; i < partPicActors.size; i++) {
            PartPicActor partPicActor = partPicActors.get(i);
            partPicActor.clearAllAdjacent();
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
                        partPicActor.addAdjacent(other);
                    }
                }
                    // 下
                else if (ox == currentX && oy == currentY - 1) {
                    if (posX == sx && sy == posY - 1){
                        partPicActor.setDown(true);
                        partPicActor.addAdjacent(other);
                    }
                }

                    // 左
                else if (ox == currentX - 1 && oy == currentY) {
                    if (sx == posX - 1 && posY == sy){
                        partPicActor.setLeft(true);
                        partPicActor.addAdjacent(other);
                    }
                }

                    // 右
                else if (ox == currentX + 1 && oy == currentY) {
                    if (sx == posX + 1 && posY == sy){
                        partPicActor.setRight(true);
                        partPicActor.addAdjacent(other);
                    }
                }
                partPicActor.updateBorder();
            }
        }


        checkSuccess();
    }

    public void moveAll(){

    }

    private void checkSuccess() {
        for (PartPicActor partPicActor : partPicActors) {
            if (!partPicActor.checkSuccess()) {
                return;
            }
        }
        System.out.println("success  =-===========================  ");
    }

    private void changePart(HashSet<PartPicActor> hashSet, Array<PartPicActor> partPicActors,PartPicActor partPicActorTemp) {
        HashMap<Integer,PartPicActor> actorHashSet = new HashMap<>();
        HashMap<Integer,PartPicActor> actorHashSetAll = new HashMap<>();
        //修要移动的
        HashSet<Integer> allEmpty = new HashSet<>();
        Array<PartPicActor> actorArray = new Array<>();

        int sourXY = tempTouchActor.getCurrenXY();
        int currenXY = partPicActorTemp.getCurrenXY();
        int minus = currenXY - sourXY;

        for (PartPicActor partPicActor : hashSet) {
            if (partPicActor.getCurrenXY()+minus>=16||partPicActor.getCurrenXY()<0) {
                //失败
                for (PartPicActor temp : hashSet) {
                    temp.setPartPosition();
                    System.out.println("失败了 ");
                    return;
                }
                return;
            }
            actorHashSet.put(partPicActor.getCurrenXY(),partPicActor);
        }
        //当前所有的
        for (PartPicActor partPicActor : partPicActors) {
            actorHashSetAll.put(partPicActor.getCurrenXY(),partPicActor);
        }

        HashSet<Integer> hashSet1 = new HashSet<>();
        for (PartPicActor partPicActor : hashSet) {
            hashSet1.add(partPicActor.getCurrenXY());
            hashSet1.add(actorHashSetAll.get(partPicActor.getCurrenXY()+minus).getCurrenXY());
        }

        Array<PartPicActor> allPart = new Array<>();
        for (Integer i : hashSet1) {
            allPart.add(actorHashSetAll.get(i));
        }
        //设置位置
        for (PartPicActor partPicActor : hashSet) {
            int currenXY1 = partPicActor.getCurrenXY() + minus;
            partPicActor.splitValue(currenXY1);
            partPicActor.setPartPosition();
            hashSet1.remove(currenXY1);
            allPart.removeValue(partPicActor,true);
        }

        int index = 0;
        for (Integer i : hashSet1) {
            PartPicActor partPicActor = allPart.get(index);
            partPicActor.splitValue(i);
            index ++;
            partPicActor.setPartPosition();
        }


    }

    private void changePartItem(PartPicActor tempTouchActor, PartPicActor partPicActor) {
        tempTouchActor.updateData(partPicActor.getPartDatum());
        tempTouchActor.setPartPosition();
//        partPicActor.setPartPosition();
    }

}
