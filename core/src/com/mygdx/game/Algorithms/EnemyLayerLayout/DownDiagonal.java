package com.mygdx.game.Algorithms.EnemyLayerLayout;

import com.mygdx.game.entities.Enemy.Enemy;
import com.mygdx.game.MyGdxGame;

import java.util.List;

//a diagonal line \ that covers half of the width of the game window
//the enemies come from top left corner of the window
public class DownDiagonal extends EnemyLayerLayout {
    public void applyLayout(List<Enemy> layer) {
        //calculation
        int entityWidth = (int)layer.get(0).getSize().x;
        int numEntities = layer.size();
        int dx = 0;
        int dy = 30;

        if (numEntities > 1)
            dx = (int)(MyGdxGame.WIDTH - entityWidth)/(numEntities - 1)/2;

        //set positions for each enemy in the layer
        if (numEntities == 1)
            layer.get(0).setPos(-entityWidth, MyGdxGame.HEIGHT);
        else {
            for (int i = 0; i < numEntities; i++) {
                float x =  i * dx - MyGdxGame.WIDTH / 2 - entityWidth/2;
                float y = MyGdxGame.HEIGHT + (numEntities-i-1)*dy;
                layer.get(i).setPos(x, y);
            }
        }
    }
}
