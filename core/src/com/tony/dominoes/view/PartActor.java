package com.tony.dominoes.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Null;
import com.tony.dominoes.shader.ShaderType;
import com.tony.dominoes.shader.ShaderUtils;

public class PartActor extends Group {
    private int stdX;
    private int stdY;
    private int currentX;
    private int currentY;
    private float distance;
    private TextureRegion region;
    private ShaderProgram program;
    public PartActor(TextureRegion region){
        this.region = region;
        Image image = new Image(region);
        addActor(image);
        setTouchable(Touchable.disabled);
        program = ShaderUtils.getShaderUtils().getShaderProgram(ShaderType.PART_CIR);
    }

    private Vector2 touchV2 = new Vector2();
    public Actor hit (float x, float y) {
        touchV2.set(x,y);
        return touchV2.x >= 0 && touchV2.x < getWidth() && touchV2.y >= 0 && touchV2.y < getHeight() ? this : null;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    public void setStdX(int stdX) {
        this.stdX = stdX;
    }

    public void setStdY(int stdY) {
        this.stdY = stdY;
    }

    public void setPartPosition(){
        setPosition(currentX*getWidth()+distance + getWidth() / 2,currentY*getHeight()+distance + getHeight() / 2, Align.center);
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setShader(program);
        // region UV: 注意 LibGDX 的 getU(), getV(), getU2(), getV2()
        program.setUniformf("u_region_min", region.getU(), region.getV());
        program.setUniformf("u_region_max", region.getU2(), region.getV2());

// 将像素半径转换为 normalized（相对于最短边）
        float minSide = Math.min(getWidth(), getHeight()); // partWidth/partHeight 或 actor 的实际宽高
        float radiusNorm = 0.02f / minSide; // 例如 12px / 100px = 0.12
        program.setUniformf("u_radius_norm", radiusNorm);

// smooth 值决定抗锯齿厚度（以 normalized 单位）。可以用 1.0 / minSide 作为默认（约 1 像素）
        program.setUniformf("u_smooth", 0.01f / minSide);
        super.draw(batch, parentAlpha);
        batch.setShader(null);
    }
}
