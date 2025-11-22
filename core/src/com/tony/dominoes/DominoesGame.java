package com.tony.dominoes;

import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.kw.gdx.BaseGame;
import com.kw.gdx.constant.Constant;
import com.kw.gdx.resource.annotation.GameInfo;
import com.kw.gdx.utils.log.NLog;
import com.tony.dominoes.screen.LoadingScreen;

@GameInfo(width = 1080, height = 1920, batch = Constant.COUPOLYGONBATCH)
public class DominoesGame extends BaseGame {

    @Override
    public void create() {
        super.create();
        Constant.TOUEABLETYPE = 1;
    }

    @Override
    protected void loadingView() {
        super.loadingView();
        setScreen(LoadingScreen.class);
    }

    @Override
    protected void initViewport() {
        stageViewport = new ExtendViewport(Constant.WIDTH, Constant.HIGHT);
        Constant.camera = stageViewport.getCamera();
        Constant.camera.far = 7000;
        NLog.i("stageViewport :" + Constant.WIDTH + "," + Constant.HIGHT);
        NLog.i("camera far :" + 5000);
    }

    @Override
    public void resume() {
        super.resume();
    }
}
