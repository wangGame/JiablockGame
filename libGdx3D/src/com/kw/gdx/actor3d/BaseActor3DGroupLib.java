package com.kw.gdx.actor3d;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;

public class BaseActor3DGroupLib extends BaseActorLib3D {
    private Array<BaseActorLib3D> actor3DS;
    public boolean transform = true;
    private Actor actor;

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public BaseActor3DGroupLib(){
        this.actor3DS = new Array<>();
    }

    public BaseActor3DGroupLib(float x, float y, float z) {
        super(x, y, z);
        this.actor3DS = new Array<>();
    }

    public void drawShadow(ModelBatch batch, Environment environment){
        super.drawShadow(batch,environment);
        for (BaseActorLib3D actor3D : actor3DS) {
            actor3D.drawShadow(batch,environment);
        }
    }


//    @Override
//    public void draw(Batch batch, float parentAlpha) {
//        super.draw(batch, parentAlpha);
//
//    }

    @Override
    public void draw(ModelBatch batch, Environment env) {
        super.draw(batch,env);

        for (BaseActorLib3D actor3D : actor3DS) {
            actor3D.draw(batch,env);
        }
    }


    public Matrix4 getActorMatrix() {
        actorMatrix.idt();
        Matrix4 rotate = actorMatrix.rotate(rotation);
        Matrix4 matrix4 = rotate
                .scale(
                        scale.x,
                        scale.y,
                        scale.z)
                .trn(
                        position.x,
                        position.y,
                        position.z)
                ;
        actorMatrix=  matrix4;
        if (parent3D!=null){
            Matrix4 cpy = parent3D.getActorMatrix().cpy();
            cpy.mul(actorMatrix);
            actorMatrix.set(cpy);
        }else if (actor!=null){
            if (actorMatrix4 != null) {
                Matrix4 cpy = actorMatrix4.cpy();
                cpy.mul(actorMatrix);
                actorMatrix.set(cpy);
            }
        }
        return actorMatrix;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        for (BaseActorLib3D actor3D : actor3DS) {
            actor3D.act(delta);
        }
    }

    public void addActor3D(BaseActorLib3D actorLib3D){
        actor3DS.add(actorLib3D);
        actorLib3D.setParent3D(this);
    }

    @Override
    public void setPosition(float x, float y, int alignment) {
        super.setPosition(x, y, alignment);
        setPosition(x,y,-5600f);
    }
}
