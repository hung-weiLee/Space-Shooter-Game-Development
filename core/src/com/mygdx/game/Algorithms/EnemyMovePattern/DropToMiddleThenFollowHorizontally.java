package com.mygdx.game.Algorithms.EnemyMovePattern;

import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.entities.Enemy.Enemy;
import com.mygdx.game.MyGdxGame;

//drop steadily to 2/3 of window height then move horizontally following the target
public class DropToMiddleThenFollowHorizontally extends EnemyMovePattern {
    private float newX, newY;
    private float curX, curY;
    private Enemy entity;
    private float dropHeight = MyGdxGame.HEIGHT / 3 * 2;
    public DropToMiddleThenFollowHorizontally(int attackingDuration)
    {
        super(attackingDuration);
    }

    public void applyMovePattern(Enemy entity) {
        curX = (int)entity.getPos().x;
        curY = (int)entity.getPos().y;
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
        } else //follow the target horizontally
        {
            if (entity.getTarget().getPos().x < curX) // target on the left
                newX = (float) (curX - entity.getSpeed());
            else if (entity.getTarget().getPos().x > curX) // target on the right
                newX = (float) (curX + entity.getSpeed());
            else
                newX = curX;
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
