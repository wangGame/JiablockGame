package com.tony.dominoes.shader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import java.util.HashMap;

public class ShaderUtils {
    public HashMap<ShaderType, ShaderProgram> shaderProgramHashMap = new HashMap<>();
    private static ShaderUtils shaderUtils;
    private ShaderUtils(){
        if (shaderProgramHashMap!=null) {
            shaderProgramHashMap.clear();
        }
        shaderProgramHashMap = new HashMap<>();
    }

    public static ShaderUtils getShaderUtils() {
        if(shaderUtils == null){
            shaderUtils = new ShaderUtils();
        }
        return shaderUtils;
    }

    public ShaderProgram getShaderProgram(ShaderType shaderType){
        ShaderProgram shaderProgram = shaderProgramHashMap.get(shaderType);
        if (shaderProgram==null){
            if (shaderType == ShaderType.PART_CIR){
                shaderProgram = new ShaderProgram(Gdx.files.internal("shader/part_round_corner.vert"),Gdx.files.internal("shader/part_round_corner.frag"));
                shaderProgramHashMap.put(shaderType,shaderProgram);
            }
        }
        return shaderProgram;
    }
}
