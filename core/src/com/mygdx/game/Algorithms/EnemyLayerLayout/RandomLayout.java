package com.mygdx.game.Algorithms.EnemyLayerLayout;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Enemy.Enemy;
import com.mygdx.game.MyGdxGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//enemies comes from random positions on border of the game window
public class RandomLayout extends EnemyLayerLayout {

    private static List<Vector2> takenPositions = new ArrayList<>();
    private static int entityWidth;
    private static int entityHeight;
    private static int  curEnemyIndex;
    private static Random rand;
    private static int numEntities;
    private static int numLeft;
    private static int numTop;
    private static int numRight;
    private static int numBottom;
    private static List<Enemy> layer;

    public void applyLayout(List<Enemy> layer) {
        this.layer = layer;
        entityWidth = (int)layer.get(0).getSize().x;
        entityHeight = (int)layer.get(0).getSize().y;
        rand = new Random();
        numEntities = layer.size();
        numLeft = rand.nextInt(numEntities);
        numTop = rand.nextInt(numEntities-numLeft);
        numRight = rand.nextInt(numEntities-numLeft-numTop);
        numBottom = numEntities - numLeft - numRight - numTop;
        curEnemyIndex = 0;

        positionLeftEntities();
        positionTopEntities();
        positionRightEntities();
        positionBottomEntities();
    }

    private boolean checkOverlap(int x, int y)
    {
        for (int i = 0; i < takenPositions.size(); i++)
        {
            if (Math.abs(x-takenPositions.get(i).x) <= entityWidth+10 ||
            Math.abs(y-takenPositions.get(i).y) <= entityHeight+10)
                return true;
        }
        return false;
    }

    private void positionLeftEntities()
    {
        int x,y;
        while (curEnemyIndex < numLeft)
        {
            x = -entityWidth;
            y = rand.nextInt(MyGdxGame.HEIGHT-entityHeight);
            if (checkOverlap(x,y))
            {
                continue;
            }
            layer.get(curEnemyIndex).setPos(x, y);
            curEnemyIndex++;
        }
    }
    private void positionTopEntities()
    {
        int x,y;
        while(curEnemyIndex < numLeft + numTop)
        {
            x = rand.nextInt(MyGdxGame.WIDTH-entityWidth);
            y = MyGdxGame.HEIGHT;
            if (checkOverlap(x,y))
            {
                continue;
            }
            layer.get(curEnemyIndex).setPos(x,y);
            curEnemyIndex++;
        }
    }
    private void positionRightEntities()
    {
        int x, y;
        while(curEnemyIndex < numLeft+numTop+numRight)
        {
            x = MyGdxGame.WIDTH;
            y = rand.nextInt(MyGdxGame.HEIGHT-entityHeight);
            if (checkOverlap(x,y))
            {
                continue;
            }
            layer.get(curEnemyIndex).setPos(x,y);
            curEnemyIndex++;
        }
    }
    private void positionBottomEntities()
    {
        int x,y;
        while(curEnemyIndex < numEntities)
        {
            x = rand.nextInt(MyGdxGame.WIDTH-entityWidth);
            y = -entityHeight;
            if (checkOverlap(x,y))
            {
                continue;
            }
            layer.get(curEnemyIndex).setPos(x,y);
            curEnemyIndex++;
        }
    }
}
