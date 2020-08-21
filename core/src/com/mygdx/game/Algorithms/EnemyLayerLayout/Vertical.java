package com.mygdx.game.Algorithms.EnemyLayerLayout;

import com.mygdx.game.entities.Enemy.Enemy;
import com.mygdx.game.MyGdxGame;

import java.util.List;

//a vertical line
//the enemies come from top of the window
public class Vertical extends EnemyLayerLayout {
    public void applyLayout(List<Enemy> layer) {
        //calculation
        int entityWidth = (int)layer.get(0).getSize().x;
        int entityHeight = (int)layer.get(0).getSize().y;
        int numEntities = layer.size();
        int dy = entityWidth;

        for (int i = 0; i < numEntities; i++) {
            float x = MyGdxGame.WIDTH/2-layer.get(0).getSize().x/2;
            float y = MyGdxGame.HEIGHT + dy*i;
            layer.get(i).setPos(x, y);
        }
    }
}

