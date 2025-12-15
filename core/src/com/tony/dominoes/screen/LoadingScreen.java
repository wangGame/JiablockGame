package com.tony.dominoes.screen;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.BaseGame;
import com.kw.gdx.asset.Asset;
import com.kw.gdx.constant.Constant;
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

        Image image = new Image(Asset.getAsset().getTexture("line/line11.png"));
        stage.addActor(image);
        image.setPosition(Constant.GAMEWIDTH/2f,Constant.GAMEHIGHT/2f, Align.center);
    }
}
