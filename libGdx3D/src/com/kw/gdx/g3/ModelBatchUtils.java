package com.kw.gdx.g3;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;
import com.badlogic.gdx.math.Vector3;
import com.kw.gdx.constant.Constant;

public class ModelBatchUtils extends ModelBatch {
    private static ModelBatchUtils modelBatchUtils;
    private static Camera useCamera;
    private Environment environment;//可以包含点光源集合和线光源集合
    private DirectionalShadowLight shadowLight;
    float am = 0.437f;
    float dirm = 0.6f;
    float dirf = 0.15f;
    private  ModelBatch shadowBatch;
    public ModelBatchUtils(){
        initLight();
        useCamera = Constant.camera;
        shadowBatch = new ModelBatch(new DepthShaderProvider());
    }

    public static ModelBatchUtils getInstance(){
        if (modelBatchUtils == null){
            modelBatchUtils = new ModelBatchUtils();
        }
        return modelBatchUtils;
    }

    public static void disposeAll(){
        if (modelBatchUtils != null){
            modelBatchUtils.dispose();
            modelBatchUtils = null;
        }
    }

    public void setUseCamera(Camera useCamera) {
        this.useCamera = useCamera;
    }

    public static void disposeBatch() {
        modelBatchUtils = null;
    }

    public void begin(){
        begin(useCamera);
    }

    public void draw(ModelInstance instances){

//        shadowLight.begin(Vector3.Zero, useCamera.direction);
//        shadowBatch.begin(shadowLight.getCamera());
//        shadowBatch.render(instances);
//        shadowBatch.end();
//        shadowLight.end();

        render(instances,environment);

    }

    public Environment getEnvironment() {
        return environment;
    }

    public void initLight(){
        environment = new Environment();
//        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1.f, 1.f, 1.0f, 1f));//环境光
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, am, am, am, 1f));//环境光
        DirectionalLight directionalLightMain = new DirectionalLight().set(dirm, dirm, dirm, -0, -0.2f, -0.5f);
//        DirectionalLight directionalLightLeft = new DirectionalLight().set(dirf, dirf, dirf, -0.1f, 0, 0);
//        DirectionalLight directionalLightRight = new DirectionalLight().set(dirf, dirf, dirf, 0.1f, 0, 0);
        DirectionalLight directionalLightUp = new DirectionalLight().set(dirf, dirf, dirf, 0, 0.2f, -0f);
//        DirectionalLight directionalLightDown = new DirectionalLight().set(dirf, dirf, dirf, 0, -0.2f, -0f);

//        environment.add((shadowLight = new DirectionalShadowLight(1024, 1024, 30f, 30f, 1f, 100f)).
//                set(0.8f, 0.8f, 0.8f, 0, 0, -1000));
//        environment.shadowMap = shadowLight;

//        DirectionalLight directionalLightLeft = new DirectionalLight().set(dirl, dirl,dirl, -0, 0.2f, -0.5f);
//        DirectionalLight directionalLight = new DirectionalLight().set(dirl, dirl,dirl, -0, -0.2f, -0.5f);
//        PointLight pointLight = new PointLight().set(Color.WHITE,-0, -0, -1,100);
        environment.add(directionalLightMain);
//        environment.add(directionalLightLeft);
//        environment.add(directionalLightRight);
        environment.add(directionalLightUp);
//        environment.add(directionalLightDown);

//        environment.add(pointLight);
    }
}
