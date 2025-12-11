package com.tony.dominoes.screen;

import com.kw.gdx.BaseGame;
import com.kw.gdx.screen.BaseScreen;
import com.tony.dominoes.view.GameContent;
import com.tony.dominoes.view.GameView;

public class LoadingScreen extends BaseScreen {
    public LoadingScreen(BaseGame game) {
        super(game);
    }

    @Override
    public void initView() {
        super.initView();
//        GameView gameView = new GameView();
//        addActor(gameView);
//        gameView.setPosition(200,200);
        GameContent gameContent = new GameContent();
        addActor(gameContent);
    }
}
