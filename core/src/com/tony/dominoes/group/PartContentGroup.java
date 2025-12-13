package com.tony.dominoes.group;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.asset.Asset;
import com.tony.dominoes.data.PartData;

public class PartContentGroup extends ModelGroup{
    private float offsetX;
    private float offsetY;
    private float offsetWidth;
    private float offsetHight;
    private PartData partDatum;

    private boolean up = false;
    private boolean down = false;
    private boolean left = false;
    private boolean right = false;
    public PartContentGroup(PartData partDatum){
        this.partDatum = partDatum;
        Image image = new Image(Asset.getAsset().getTexture("filePic.png"));
        addActor(image);
        image.setPosition(-partDatum.getPosX()*partDatum.getPerW(),-partDatum.getPosY()*partDatum.getPerH());
        setSize(partDatum.getPerW(),partDatum.getPerH());
    }

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
                44, 16);
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

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }


    public void resetDir() {
        this.up = false;
        this.down = false;
        this.left = false;
        this.right = false;

    }
}
