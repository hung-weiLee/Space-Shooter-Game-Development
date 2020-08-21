package com.mygdx.game.Algorithms.EnemyLayerLayout;

import com.mygdx.game.entities.Enemy.Enemy;
import com.mygdx.game.MyGdxGame;

import java.util.List;

//a single enemy only shows from center top of the game window
public class Single extends EnemyLayerLayout {

    public void applyLayout(List<Enemy> layer) {
        int x = (int)(MyGdxGame.WIDTH/2-layer.get(0).getSize().x/2);
        layer.get(0).setPos(x, MyGdxGame.HEIGHT);
    }
}
