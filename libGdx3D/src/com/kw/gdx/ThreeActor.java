package com.kw.gdx;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.kw.gdx.constant.Constant;
import com.kw.gdx.screen.BaseScreen;

public class ThreeActor extends Actor {
    public Environment environment;//可以包含点光源集合和线光源集合
    public CameraInputController camController;//视角控制器
    public Array<ModelInstance> instances = new Array<ModelInstance>();
    public ModelBatch modelBatch;
    private ModelInstance shipInstance;

    public ThreeActor(){
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.9f, 0.9f, 0.9f, 1f));//环境光
        modelBatch = new ModelBatch();
        shipInstance = ModelUtils.createInstance();
        shipInstance.transform.setToTranslation(120,120,-400);
        shipInstance.transform.scale(700, 700, 700f);
        shipInstance.calculateTransforms();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.flush();
        batch.end();

        Camera cam = getStage().getCamera();
        if(camController == null){
            camController = new CameraInputController(cam);
            BaseScreen currentActiveScreen = Constant.currentActiveScreen;
            InputMultiplexer multiplexer = currentActiveScreen.getMultiplexer();
            multiplexer.addProcessor(camController);
        }

        camController.update();
        modelBatch.begin(cam);
        modelBatch.render(shipInstance,environment);
        modelBatch.end();
        batch.begin();
    }

    public Vector3 location() {
        return transform().getTranslation(new Vector3());
    }

    public Matrix4 transform() {
        return modelInstance().transform;
    }

    private ModelInstance modelInstance() {
        return shipInstance;
    }



}
