package com.tony.dominoes.screen;

import com.kw.gdx.BaseGame;
import com.kw.gdx.screen.BaseScreen;
import com.tony.dominoes.view.PicCutGroup;

public class LoadingScreen extends BaseScreen {
    public LoadingScreen(BaseGame game) {
        super(game);
    }

    @Override
    public void initView() {
        super.initView();
        PicCutGroup picCutGroup = new PicCutGroup();
        addActor(picCutGroup);
        picCutGroup.setPosition(200,200);
    }
}
