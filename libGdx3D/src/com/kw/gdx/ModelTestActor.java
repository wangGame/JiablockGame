package com.kw.gdx;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.g3.ModelBatchUtils;


/**
 * 继承Actor
 */
public class ModelTestActor extends Actor {
    private float baseRotationX;
    private float baseRotationY;
    private boolean isDrity;
    protected ModelInstance modelInstance;
    /**
     * @engineInternal NOTE: this is engineInternal interface that doesn't have a side effect of updating the transforms
     */
    public Matrix4 _mat = new Matrix4();
    public Matrix4 useMat = new Matrix4();
    // local transform
//    @serializable
    protected Vector3 _lpos = new Vector3();

//    @serializable
    protected Vector3 _lrot = new Vector3();

//    @serializable
    protected Vector3 _lscale = new Vector3(1, 1, 1);


    public ModelTestActor(){

    }

    public void setBaseRotationX(float baseRotationX) {
        this.baseRotationX = baseRotationX;
    }

    public float getBaseRotationX() {
        return baseRotationX;
    }

    public ModelTestActor(ModelInstance instance){
        this.modelInstance = instance;
        BoundingBox boundingBox = boundingBox();
        setSize(boundingBox.getWidth(),boundingBox.getHeight());
    }

    public Vector3 get_lrot() {
        return _lrot;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        ModelBatchUtils modelBatchUtils = ModelBatchUtils.getInstance();
        modelBatchUtils.begin();
        calMatr(batch);
        modelBatchUtils.draw(modelInstance);
        modelBatchUtils.end();
        batch.begin();
    }

    public void setPosition(Vector3 vector3){
        setPosition(vector3.x,vector3.y, Align.center);
        //设置本地坐标
        _lpos.set(vector3);
        isDrity = true;
    }

    @Override
    public void setScale(float scaleX, float scaleY) {
        super.setScale(scaleX, scaleY);
        _lscale.set(scaleX,scaleY,scaleX);
        isDrity = true;
    }

    public void setScaleZ(float scaleZ) {
        _lscale.z = scaleZ;
        isDrity = true;
    }

    @Override
    public void setPosition(float x, float y, int alignment){
        super.setPosition(x,y,alignment);
        _lpos.set(x,y,-400);
        isDrity = true;
    }

    public void setScale(Vector3 scale){
        _lscale.set(scale);
        isDrity = true;
    }

    public void rotation(float rox,float roy,float roz){
        _lrot.set(rox,roy,roz);
        setRotation(_lrot.z);
        isDrity = true;
    }

    public void rotation(Vector3 rotation){
        _lrot.set(rotation);
        setRotation(rotation.z);
        isDrity = true;
    }



    public void rotation(Vector3 rotation,NodeSpace nodeSpace){
        if(nodeSpace == NodeSpace.LOCAL){

        }else {
            _lrot.set(rotation);
            setRotation(rotation.z);
        }
    }

    public BoundingBox boundingBox() {
        return modelInstance.calculateBoundingBox(new BoundingBox());
    }


    public void calMatr(Batch batch){
        //必须先平移在旋转   不知道这是什么丑毛病
        if (  true ) {
            isDrity = false;
            _mat.idt();

            _mat.mul(batch.getTransformMatrix());
            //获取父类位置加过来
            //本地位置
            _mat.translate(_lpos.x, _lpos.y+speacalOffsetY, _lpos.z);
            //旋转
            //获取父类的乘过来
            _mat.rotate(Vector3.X, _lrot.x + baseRotationX);
            _mat.rotate(Vector3.Y, _lrot.y + baseRotationY);
            _mat.rotate(Vector3.Z, _lrot.z);
            //缩放
            //获取父类的乘过来
            _mat.scale(_lscale.x, _lscale.y, _lscale.z);

            useMat.set(_mat);
        }

        modelInstance.transform.set(useMat);
    }


    public void setBaseRotationY(int baseRotationY) {
            this.baseRotationY = baseRotationY;
    }

    public void forcusX() {
        _lrot.x = baseRotationX;
        baseRotationX = 0;

    }

    public void set_lrot(Vector3 vector3) {
            _lrot.set(vector3);
    }

    public void setDepth(float packDepth) {
            _lpos.z = packDepth;
    }

    public float getDepth() {
        return _lpos.z;
    }

    private float speacalOffsetY ;
    public void speacalOffsetY(int i) {
        this.speacalOffsetY = i;
    }


    public enum NodeSpace {
        LOCAL,
        WORLD,
        NONE
    }

}
