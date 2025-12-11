package com.tony.dominoes.data;

public class PartData {
    private float perW;
    private float perH;
    private int posX;
    private int posY;
    private float offset;
    private int currentX;
    private int currentY;

    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    public float getPerW() {
        return perW;
    }

    public void setPerW(float perW) {
        this.perW = perW;
    }

    public float getPerH() {
        return perH;
    }

    public void setPerH(float perH) {
        this.perH = perH;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public float getOffset() {
        return offset;
    }

    public void setOffset(float offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        return "PartData{" +
                "perW=" + perW +
                ", perH=" + perH +
                ", posX=" + posX +
                ", posY=" + posY +
                ", offset=" + offset +
                '}';
    }

    public boolean equealsPos() {
        return this.posX == this.currentX && this.posY == this.currentY;
    }
}
