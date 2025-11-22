package com.kw.gdx.actor3d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Attributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;

public class Model3DActor extends BaseActorLib3D {
    private Matrix4 resourceMax = new Matrix4();
    protected ModelInstance modelInstance;
    protected Array<Decal> decals;
    public Model3DActor(Model tableModel){
        this(0,0,0,tableModel);
    }


    public Model3DActor(float x, float y, float z, ModelInstance model){
        setPosition(x,y,z);
        if (model!=null){
            setModelInstance(model);
        }
        this.decals = new Array<>();
    }

    public Model3DActor(float x, float y, float z, Model model){
        setPosition(x,y,z);
        if (model!=null){
            ModelInstance instance = new ModelInstance(model,new Vector3(x,y,z));
            setModelInstance(instance);
        }
        this.decals = new Array<>();
    }

    public void setModelInstance(ModelInstance modelInstance) {
        this.modelInstance = modelInstance;
        modelInstance.calculateBoundingBox(bounds);
    }

    public void drawShadow(ModelBatch batch, Environment environment) {
        if (modelInstance != null) {
            Matrix4 matrix4 = calculateTransform();
            if (parent3D != null) {
                Matrix4 pM = parent3D.getActorMatrix();
                resourceMax.idt();
                resourceMax.set(pM);
                resourceMax.mul(matrix4);
                modelInstance.transform.set(resourceMax);
            } else {
                modelInstance.transform.set(matrix4);
            }
            batch.render(modelInstance);
        }
    }

    public void draw(ModelBatch batch, Environment env) {
        if (modelInstance != null) {
//            if (!isCaremaClip()) return;
            Matrix4 matrix4 = calculateTransform();
            if (parent3D != null) {
                Matrix4 pM = parent3D.getActorMatrix();
                resourceMax.idt();
                resourceMax.set(pM);
                resourceMax.mul(matrix4);
                modelInstance.transform.set(resourceMax);
            } else {
                modelInstance.transform.set(matrix4);
            }
            batch.render(modelInstance, env);
        }
    }

    private Color tempC = new Color();

    public void setColor(Color c) {
        setColor(c.r,c.g,c.b,c.a);
    }

    public void setColor(float r,float g,float b,float a){
        if (r == tempC.r &&
                g == tempC.g&&
                b == tempC.b&&
                a == tempC.a){
            return;
        }
        tempC.set(r,g,b,a);
        for (Material m : modelInstance.materials) {
            m.set(ColorAttribute.createDiffuse(tempC));
        }

    }


    public void setParent3D(BaseActor3DGroupLib parent3D) {
        this.parent3D = parent3D;
    }

    public BaseActor3DGroupLib getParent3D() {
        return parent3D;
    }

    Vector3 dimensions = new Vector3();
    public void updateBox() {
        if (modelInstance != null) {
            Matrix4 matrix4 = calculateTransform();
            bounds.mul(matrix4);
            bounds.getDimensions(dimensions);
            radius = dimensions.len() / 2f;
            bounds.getCenter(center);
            bounds.mul(actorMatrix);
        }
    }

    public ModelInstance getModel() {
        return modelInstance;
    }

}
