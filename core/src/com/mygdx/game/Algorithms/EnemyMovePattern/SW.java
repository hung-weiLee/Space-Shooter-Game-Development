package com.mygdx.game.Algorithms.EnemyMovePattern;

import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.entities.Enemy.Enemy;

//move in Northeast direction
public class SW extends EnemyMovePattern {
    private float newX, newY;
    private float curX, curY;
    private Enemy entity;

    public SW(int attackingDuration)
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
        newX = (int) (curX - entity.getSpeed());
        newY = (int) (curY - entity.getSpeed());
        entity.setPos(newX, newY);
    }

    private void retrieve()
    {
        newX = curX;
        newY = curY+1;
        entity.setPos(newX, newY);
    }
}
