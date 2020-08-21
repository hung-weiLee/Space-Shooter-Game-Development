package com.mygdx.game.Algorithms.EnemyMovePattern;

import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.entities.Enemy.Direction;
import com.mygdx.game.entities.Enemy.Enemy;
import com.mygdx.game.MyGdxGame;

//drop from top to 2/3 window height then move horizontally back and forth
public class DropToMiddleThenMoveHorizontally extends EnemyMovePattern {

    private float newX, newY;
    private float curX, curY;
    private Enemy entity;
    private float dropHeight = MyGdxGame.HEIGHT / 3 * 2;

    public DropToMiddleThenMoveHorizontally(int attackingDuration)
    {
        super(attackingDuration);
    }

    public void applyMovePattern(Enemy entity) {
        curX = (float)entity.getPos().x;
        curY = (float)entity.getPos().y;
        this.entity = entity;

        if (TimeUtils.millis() - initTime < dTimeToRetrieve)
            move();
        else
            retrieve();
    }

    private void move()
    {
        if (curY > dropHeight) {
            newX = curX;
            newY = (float) (curY - entity.getSpeed());
        } else //move horizontally
        {
            boolean goWest = entity.getDirection() == Direction.W;
            boolean inRangeW = curX > 0;
            boolean inRangeE = curX >= MyGdxGame.WIDTH - entity.getSize().x;
            boolean goEast = entity.getDirection() == Direction.E;

            if (goWest && inRangeW || goEast && inRangeE)
                newX = (float) (curX - entity.getSpeed());
            else
                newX = (float) (curX + entity.getSpeed());
            newY = curY;
        }
        entity.setPos(newX, newY);
    }

    private void retrieve()
    {
        newX = curX;
        newY = curY+1;
        entity.setPos(newX, newY);
    }
}
