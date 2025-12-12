package com.tony.dominoes.group;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
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

    private Vector2 touchTempV2;
    /**
     *
     * @param partDatum
     */

    private Image upImg;
    private Image downImg;
    private Image leftImg;
    private Image rightImg;
    private Array<PartPicActor> adjacents;

    public PartPicActor(PartData partDatum){
        setStartModelTest(true);
        this.touchTempV2 = new Vector2();
        this.adjacents = new Array<>();
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

        Actor a = new Actor();
        a.setSize(100,100);
        addActor(a);
        a.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println(left +"   "+right+"    "+up+"   "+down);
            }
        });
    }

    public void setTouchTempV2(float x,float y) {
        this.touchTempV2.x = x;
        this.touchTempV2.y = y;
        parentToLocalCoordinates(touchTempV2);
    }

    public void move(float x,float y){
        setPosition(x-touchTempV2.x,y-touchTempV2.y);
    }

    private float offsetX;
    private float offsetY;
    private float offsetWidth;
    private float offsetHight;
    protected void drawCir(){
        offsetX = partDatum.getOffset();
        offsetY = partDatum.getOffset();
        offsetWidth = partDatum.getOffset();
        offsetHight = partDatum.getOffset();

        if (left){
            offsetX = 0;
        }
        if (right){
            offsetWidth = 0;
        }
        if (up){
            offsetHight = 0;
        }

        if (down){
            offsetY = 0;
        }

        fillRoundRect(offsetX,
                offsetY,
                partDatum.getPerW()-offsetWidth*2,
                partDatum.getPerH()-offsetHight*2,
                20, 16);
    }
    public void fillRoundRect(float x, float y, float width, float height, float radius, int segments) {
        float r = radius;

        if (up){
            sr.rect(x + r, y, width - 2*r, height);               // 中间横向主体
        }
        // 中间矩形和边条（不包含四角）
        sr.rect(x + r, y, width - 2*r, height);               // 中间横向主体
        sr.rect(x, y + r, r, height - 2*r);                   // 左边竖条（去掉上下 r）
        sr.rect(x + width - r, y + r, r, height - 2*r);       // 右边竖条（去掉上下 r）

        // 每个角由对应的 side flags 决定是否为方角。
        // bottom-left (左下): 如果 left || down 任一为 true 就画方块，否则画弧
        if (left || down) {
            if (left){
                sr.rect(x, y, r, r);
            }else {
                sr.rect(x, y, r, r);
            }
        } else {
            sr.arc(x + r, y + r, r, 180, 90, segments);      // 左下角：中心 (x+r, y+r)
        }

        // bottom-right (右下)
        if (right || down) {
            sr.rect(x + width - r, y, r, r);
        } else {
            sr.arc(x + width - r, y + r, r, 270, 90, segments); // 右下角：中心 (x+width-r, y+r)
        }

        // top-right (右上)
        if (right || up) {
            sr.rect(x + width - r, y + height - r, r, r);
        } else {
            sr.arc(x + width - r, y + height - r, r, 0, 90, segments); // 右上角：中心 (x+width-r, y+height-r)
        }

        // top-left (左上)
        if (left || up) {
            sr.rect(x, y + height - r, r, r);
        } else {
            sr.arc(x + r, y + height - r, r, 90, 90, segments); // 左上角：中心 (x+r, y+height-r)
        }
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
}
