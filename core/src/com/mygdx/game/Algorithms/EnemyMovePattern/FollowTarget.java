package com.mygdx.game.Algorithms.EnemyMovePattern;

import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.entities.Enemy.Enemy;

//steadily move toward the target
public class FollowTarget extends EnemyMovePattern {
    private float newX, newY;
    private float curX, curY;
    private Enemy entity;

    public FollowTarget(int attackingDuration)
    {
        super(attackingDuration);
    }

    public void applyMovePattern(Enemy entity) {
        curX = entity.getPos().x;
        curY = entity.getPos().y;
        this.entity = entity;

        if (TimeUtils.millis() - initTime < dTimeToRetrieve)
            move();
        else
            retrieve();
    }

    private void move()
    {
        //get newX
        if (entity.getTarget().getPos().x < curX) // target on the left
            newX = (float) (curX - 0.5 * entity.getSpeed());
        else if (entity.getTarget().getPos().x > curX) // target on the right
            newX = (float) (curX + 0.5 * entity.getSpeed());
        else
            newX = curX;

        //get newY
        if (entity.getTarget().getPos().y < curY) // target is below
            newY = (int) (curY - entity.getSpeed());
        else if (entity.getTarget().getPos().y > curY) // target is above
            newY = (int) (curY + entity.getSpeed());
        else
            newY = curY;
        entity.setPos(newX, newY);
    }

    private void retrieve()
    {
        newX = curX;
        newY = curY+1;
        entity.setPos(newX, newY);
    }
}
