package com.tony.dominoes.group;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.asset.Asset;
import com.tony.dominoes.data.PartData;

public class PartPicActor extends ModelGroup{
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

    /**
     *
     * @param partDatum
     */

    private Image upImg;
    private Image downImg;
    private Image leftImg;
    private Image rightImg;


    public PartPicActor(PartData partDatum){
        setStartModelTest(true);
        this.partDatum = partDatum;
        this.touchV2 = new Vector2();
        Image image = new Image(Asset.getAsset().getTexture("filePic.png"));
        addActor(image);
        setSize(partDatum.getPerW(),partDatum.getPerH());
        setPosition(partDatum.getPerW() * partDatum.getCurrentX() + partDatum.getPerW()/2f,partDatum.getPerH() * partDatum.getCurrentY()+ partDatum.getPerH()/2f, Align.center);
        image.setPosition(-partDatum.getPosX()*partDatum.getPerW(),-partDatum.getPosY()*partDatum.getPerH());

        {
            upImg = new Image(Asset.getAsset().getTexture("border.png"));
            downImg = new Image(Asset.getAsset().getTexture("border.png"));
            leftImg = new Image(Asset.getAsset().getTexture("border.png"));
            rightImg = new Image(Asset.getAsset().getTexture("border.png"));

            addActor(upImg);
            addActor(downImg);
            addActor(leftImg);
            addActor(rightImg);

            upImg.setPosition(getWidth()/2f,getHeight(),Align.center);
            downImg.setPosition(getWidth()/2f,0,Align.center);
            leftImg.setPosition(0,getHeight()/2f,Align.center);
            rightImg.setPosition(getWidth(),getHeight()/2f,Align.center);
        }
    }

    protected void drawCir(){

        fillRoundRect(partDatum.getOffset(), partDatum.getOffset(), partDatum.getPerW()-partDatum.getOffset()*2,
                partDatum.getPerH()-partDatum.getOffset()*2, 20, 16);
    }

    public void fillRoundRect(float x, float y, float width, float height, float radius, int segments) {


        float r = radius;
        // 中间矩形
        sr.rect(x + r, y, width - 2*r, height);
        sr.rect(x, y + r, r, height - 2*r);
        sr.rect(x + width - r, y + r, r, height - 2*r);
        // 四个角：4 个圆弧
        if (left) {
//            sr.rect(x + r, y + r, r,r);               // 左下角
            sr.rect(x, y, r, r);
            sr.rect(x, y + height - r, r, r);
        }else {
            sr.arc(x + r, y + r, r, 180, 90, segments);               // 左下角
        }
        if (right){
//            sr.rect(x + r, y + r, r,r);       // 右下角
            sr.rect(x + width - r, y, r, r);
            sr.rect(x + width - r, y + height - r, r, r);
        }else {
            sr.arc(x + width - r, y + r, r, 270, 90, segments);       // 右下角
        }
        if (up){
//            sr.rect(x + r, y + r, r,r);  // 右上角
            sr.rect(x + width - r, y + height - r, r, r);
            sr.rect(x, y + height - r, r, r);
        }else {
            sr.arc(x + width - r, y + height - r, r, 0, 90, segments);// 右上角
        }

        if (down){
//            sr.rect(x + r, y + r, r,r);  // 右上角
            sr.rect(x, y + height - r, r, r);
            sr.rect(x + width - r, y + height - r, r, r);
        }else {
            sr.arc(x + r, y + height - r, r, 90, 90, segments);       // 左上角
        }
    }

    public Actor hit (float x, float y) {
        touchV2.set(x,y);
        return touchV2.x >= 0 && touchV2.x < getWidth() && touchV2.y >= 0 && touchV2.y < getHeight() ? this : null;
    }

    public void setPartPosition(){
        setPosition(partDatum.getPerW() * partDatum.getCurrentX()+ partDatum.getPerW()/2f,partDatum.getPerH() * partDatum.getCurrentY()+ partDatum.getPerH()/2f, Align.center);
//        setPosition(partDatum.getPerW() * partDatum.getPosX(),partDatum.getPerH() * partDatum.getPosY(), Align.center);
    }

    public PartData getPartDatum() {
        return partDatum;
    }

    public void updateData(PartData partDatumTemp) {
        int tempX = this.partDatum.getCurrentX();
        int tempY = this.partDatum.getCurrentY();
        int targetX = partDatumTemp.getCurrentX();
        int targetY = partDatumTemp.getCurrentY();
        this.partDatum.setCurrentX(targetX);
        this.partDatum.setCurrentY(targetY);
        partDatumTemp.setCurrentX(tempX);
        partDatumTemp.setCurrentY(tempY);

    }


    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void resetDir() {
        this.up = false;
        this.down = false;
        this.left = false;
        this.right = false;
    }

    public void updateBorder(){
        leftImg.setVisible(!left);
        rightImg.setVisible(!right);
        upImg.setVisible(!up);
        downImg.setVisible(!down);
    }

    public boolean checkSuccess() {
        if (!partDatum.equealsPos()) {
            return false;
        }
        return true;
    }
}
