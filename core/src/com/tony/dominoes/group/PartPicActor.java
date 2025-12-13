package com.tony.dominoes.group;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.kw.gdx.asset.Asset;
import com.tony.dominoes.data.PartData;

public class PartPicActor extends Group {
    private PartData partDatum;
    private Vector2 touchV2;
    /**
     * 我的上下左右
     * @param partDatum
     */
    private boolean up = false;
    private boolean down = false;
    private boolean left = false;
    private boolean right = false;

    private Vector2 touchTempV2;
    /**
     *
     * @param partDatum
     */
    private Array<PartPicActor> adjacents;
    //边缘
    //左上
    private Image leftUpBorderImg;
    //右上
    private Image rightUpBorderImg;
    //左下
    private Image leftDownBorderImg;
    //右下
    private Image rightDownBorderImg;
    //left
    private Image leftBorderImg;
    //right
    private Image rightBorderImg;
    //up
    private Image upBorderImg;
    //down
    private Image downBorderImg;
    private PartContentGroup partContentGroup;
    public PartPicActor(PartData partDatum){
        partContentGroup = new PartContentGroup(partDatum);
        partContentGroup.setStartModelTest(true);
        addActor(partContentGroup);
        this.touchTempV2 = new Vector2();
        this.adjacents = new Array<>();
        this.partDatum = partDatum;
        this.touchV2 = new Vector2();
        setSize(partDatum.getPerW(),partDatum.getPerH());
        setPosition(partDatum.getPerW() * partDatum.getCurrentX() + partDatum.getPerW()/2f,partDatum.getPerH() * partDatum.getCurrentY()+ partDatum.getPerH()/2f, Align.center);
        {
            leftUpBorderImg     = new Image(Asset.getAsset().getTexture("line/line1.png"));
            rightUpBorderImg    = new Image(Asset.getAsset().getTexture("line/line3.png"));
            leftDownBorderImg   = new Image(Asset.getAsset().getTexture("line/line7.png"));
            rightDownBorderImg  = new Image(Asset.getAsset().getTexture("line/line5.png"));
            leftBorderImg       = new Image(Asset.getAsset().getTexture("line/line8.png"));
            rightBorderImg      = new Image(Asset.getAsset().getTexture("line/line4.png"));
            upBorderImg         = new Image(Asset.getAsset().getTexture("line/line2.png"));
            downBorderImg       = new Image(Asset.getAsset().getTexture("line/line6.png"));

            addActor(leftUpBorderImg);
            addActor(rightUpBorderImg);
            addActor(leftDownBorderImg);
            addActor(rightDownBorderImg);
            addActor(leftBorderImg);
            addActor(rightBorderImg);
            addActor(upBorderImg);
            addActor(downBorderImg);

            leftUpBorderImg.setPosition(0,getHeight(),Align.topLeft);
            rightUpBorderImg.setPosition(getWidth(),getHeight(),Align.topRight);
            leftDownBorderImg.setPosition(0,0,Align.bottomLeft);
            rightDownBorderImg.setPosition(getWidth(),0,Align.bottomRight);
            leftBorderImg.setPosition(0,getHeight()/2f,Align.left);
            rightBorderImg.setPosition(getWidth(),getHeight()/2f,Align.right);
            upBorderImg.setPosition(getWidth()/2f,getHeight(),Align.top);
            downBorderImg.setPosition(getWidth()/2f,0,Align.bottom);

        }
    }

    public void setTouchTempV2(float x,float y) {
        this.touchTempV2.x = x;
        this.touchTempV2.y = y;
        parentToLocalCoordinates(touchTempV2);
    }

    public void move(float x,float y){
        setPosition(x-touchTempV2.x,y-touchTempV2.y);
    }



    public Actor hit (float x, float y) {
        touchV2.set(x,y);
        return touchV2.x >= 0 && touchV2.x < getWidth() && touchV2.y >= 0 && touchV2.y < getHeight() ? this : null;
    }

    public void setPartPosition(){
        setPosition(partDatum.getPerW() * partDatum.getCurrentX()+ partDatum.getPerW()/2f,partDatum.getPerH() * partDatum.getCurrentY()+ partDatum.getPerH()/2f, Align.center);
    }

    public PartData getPartDatum() {
        return partDatum;
    }

    public void updateData(PartData partDatumTemp) {
        int targetX = partDatumTemp.getCurrentX();
        int targetY = partDatumTemp.getCurrentY();
        this.partDatum.setCurrentX(targetX);
        this.partDatum.setCurrentY(targetY);
    }


    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
        partContentGroup.setUp(up);
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
        partContentGroup.setDown(down);
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
        partContentGroup.setLeft(left);
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
        partContentGroup.setRight(right);
    }

    public void resetDir() {
        this.up = false;
        this.down = false;
        this.left = false;
        this.right = false;
        partContentGroup.resetDir();
    }

    public void updateBorder(){

        // 边
        leftBorderImg.setVisible(!left);
        rightBorderImg.setVisible(!right);
        upBorderImg.setVisible(!up);
        downBorderImg.setVisible(!down);

        // 四个角（必须同时满足两个方向都“没连接”）
        leftUpBorderImg.setVisible(!left && !up);
        rightUpBorderImg.setVisible(!right && !up);
        leftDownBorderImg.setVisible(!left && !down);
        rightDownBorderImg.setVisible(!right && !down);

        if (!up){
            upBorderImg.setWidth(getWidth());
            upBorderImg.setX(getWidth()/2f,Align.center);
        }
        if (!down){
            downBorderImg.setWidth(getWidth());
            downBorderImg.setX(getWidth()/2f,Align.center);
        }
        if (!left){
            leftBorderImg.setHeight(getHeight());
            leftBorderImg.setY(getHeight()/2f,Align.center);
        }
        if (!right){
            rightBorderImg.setHeight(getHeight());
            rightBorderImg.setY(getHeight()/2f,Align.center);
        }


//        leftBorderImg.setVisible(!left);
//        leftUpBorderImg.setVisible(!left);
//        leftDownBorderImg.setVisible(!left);
//
//        rightBorderImg.setVisible(!right);
//        rightUpBorderImg.setVisible(!right);
//        rightDownBorderImg.setVisible(!right);
//
//        upBorderImg.setVisible(!up);
//        leftUpBorderImg.setVisible(!up);
//        rightUpBorderImg.setVisible(!up);
//
//        downBorderImg.setVisible(!down);
//        leftDownBorderImg.setVisible(!down);
//        rightDownBorderImg.setVisible(!down);
    }

    public boolean checkSuccess() {
        if (!partDatum.equealsPos()) {
            return false;
        }
        return true;
    }


    public void addAdjacent(PartPicActor other) {
        adjacents.add(other);
    }

    public Array<PartPicActor> getAdjacents() {
        return adjacents;
    }

    public int getCurrenXY(){
        return partDatum.getCurrentY()*partDatum.getSplitX() + partDatum.getCurrentX();
    }

    public void splitValue(int index){
        int xx = index % partDatum.getSplitX();
        int yy = index / partDatum.getSplitY();
        partDatum.setCurrentX(xx);
        partDatum.setCurrentY(yy);
    }

    public void clearAllAdjacent() {
        adjacents.clear();
    }

    @Override
    public String toString() {
        return "PartPicActor{" +
                "partDatum=" + partDatum +
                '}';
    }
}
