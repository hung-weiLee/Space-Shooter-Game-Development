package com.mygdx.game.Algorithms.EnemyLayerLayout;

import com.mygdx.game.entities.Enemy.Enemy;
import com.mygdx.game.MyGdxGame;

import java.util.List;

//a horizontal line that covers the full width of the game window
//the enemies come from top of the window
public class HorizontalFullWidth extends EnemyLayerLayout {
    public void applyLayout(List<Enemy> layer) {
        //calculation
        int entityWidth = (int)layer.get(0).getSize().x;
        int numEntities = layer.size();
        int dx = 0;

        if (numEntities > 1)
            dx = (int)(MyGdxGame.WIDTH - entityWidth)/(numEntities - 1);

        //set positions for each enemy in the layer
        if (numEntities == 1)
            layer.get(0).setPos(MyGdxGame.WIDTH/2-entityWidth/2, MyGdxGame.HEIGHT);
        else {
            for (int i = 0; i < numEntities; i++) {
                layer.get(i).setPos(i * dx, MyGdxGame.HEIGHT);
            }
        }
    }
}
