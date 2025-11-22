package com.kw.gdx.actor3d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.kw.gdx.g3.ModelBatchUtils;

public class BaseActorLib3D extends Actor {
    protected BaseActor3DGroupLib parent3D;
    protected Vector3 position;
    protected float radius;
    protected BoundingBox bounds;
    protected Quaternion rotation;
    protected Vector3 scale;
    protected boolean isDity;
    protected Vector3 center;
    protected Matrix4 actorMatrix;
    protected Vector3 clipTempV3;
    protected BoundingBox boundingBoxTemp;
    protected Vector3 checkCollisionV3;
    protected Color color;
    protected boolean debug;
    protected boolean isVisible;

    public BaseActorLib3D(){
        this(0,0,0);
    }

    public BaseActorLib3D(float x, float y, float z) {
        this.position = new Vector3(x, y, z);
        this.rotation = new Quaternion();
        this.scale = new Vector3(1, 1, 1);
        this.actorMatrix = new Matrix4();
        this.bounds = new BoundingBox();
        this.clipTempV3 = new Vector3();
        this.center = new Vector3();
        this.boundingBoxTemp = new BoundingBox();
        this.checkCollisionV3 = new Vector3();
        this.isDity = true;
        this.isVisible = true;
    }

    public Matrix4 getActorMatrix() {
        calculateTransform();
        return actorMatrix;
    }

    /**
     * 计算当前的mat
     * @return
     */
    public Matrix4 calculateTransform() {
//        if (!isDity)return actorMatrix;
        actorMatrix.idt();
        Matrix4 rotate = actorMatrix.rotate(rotation);
        Matrix4 matrix4 = rotate
                .trn(
                        position.x,
                        position.y,
                        position.z)
                .scale(
                        scale.x,
                        scale.y,
                        scale.z);
        actorMatrix=  matrix4;
        return matrix4;
    }

    public void drawShadow(ModelBatch batch, Environment environment){

    }

    public void draw(ModelBatch batch, Environment env) {

    }

    public void setColor(Color c) {
        this.color.set(c.r,c.g,c.b,c.a);
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 v) {
        position.set(v);
        isDity = true;
    }

    public void setPosition(float x, float y, float z) {
        position.set(x, y, z);
        isDity = true;
    }

    public void moveBy(Vector3 v) {
        position.add(v);
        isDity = true;
    }

    public void moveBy(float x, float y, float z) {
        moveBy(new Vector3(x, y, z));
        isDity = true;
    }

    public void moveByForward(float dist) {
        moveBy(rotation.transform(new Vector3(0, 0, 1)).scl(dist));
        isDity = true;
    }

    public void moveByUp(float dist) {
        moveBy(rotation.transform(new Vector3(1, 0, 0)).scl(dist));
        isDity = true;
    }

    public void moveByRight(float dist) {
        moveBy(rotation.transform(new Vector3(0, 1, 0)).scl(dist));
        isDity = true;
    }

    public void moveForward(float dist){
        position.z += dist;
        isDity = true;
    }

    public void moveUp(float dist){
        position.y += dist;
        isDity = true;
    }

    public void moveRight(float dist){
        position.x -= dist;
        isDity = true;
    }

    public float getTurnAngle() {
        return rotation.getAngleAround(1, 0, 0);
    }

    public void setTurnAngle(float degrees) {
        rotation.set(new Quaternion(Vector3.X, degrees));
        isDity = true;
    }

    public void turnBy(float degrees) {
        rotation.mul(new Quaternion(Vector3.X, -degrees));
        isDity = true;
    }

    public void setScale(float x, float y, float z) {
        scale.set(x, y, z);
        isDity = true;
    }

    public Quaternion getQRotation() {
        return rotation;
    }

    public Vector3 getScale() {
        return scale;
    }

    public float getAngle() {
        return rotation.getAngle();
    }

    public void setParent3D(BaseActor3DGroupLib parent3D) {
        this.parent3D = parent3D;
    }

    public BaseActor3DGroupLib getParent3D() {
        return parent3D;
    }

    public void touchUp(Vector3 vector3, int pointer, int button){

    }

    public float getX(){
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getZ() {
        return position.z;
    }

    //设置角度
    public void setFromAxis(int x, int y, int z, float rotationA) {
        rotation.setFromAxis(x,y,z,rotationA);
        isDity = true;
    }

    public void setRotation(Quaternion quaternion){
        this.rotation.set(quaternion);
        isDity = true;
    }

    public void updateBox(){

    }

    public BoundingBox getBounds() {
        if (isDity){
            updateBox();
        }
        return bounds;
    }


    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    protected Matrix4 actorMatrix4;

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        actorMatrix4 = batch.getTransformMatrix();
        batch.flush();
        batch.end();
        ModelBatchUtils modelBatchUtils = ModelBatchUtils.getInstance();
        modelBatchUtils.begin();

        draw(modelBatchUtils,modelBatchUtils.getEnvironment());

        modelBatchUtils.end();
        batch.begin();
        actorMatrix4 = null;
    }
}
