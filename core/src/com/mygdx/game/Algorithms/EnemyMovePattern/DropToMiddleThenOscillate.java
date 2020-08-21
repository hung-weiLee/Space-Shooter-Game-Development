package com.mygdx.game.Algorithms.EnemyMovePattern;

import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.entities.Enemy.Direction;
import com.mygdx.game.entities.Enemy.Enemy;
import com.mygdx.game.MyGdxGame;

//go steadily straight from top to 3/4 of window height then oscillate left and right
public class DropToMiddleThenOscillate extends EnemyMovePattern {
    private float newX, newY;
    private float curX, curY;
    private Enemy entity;
    private float dropHeight = MyGdxGame.HEIGHT / 4 * 3;
    private float oscillateDistance = 30;

    public DropToMiddleThenOscillate(int attackingDuration)
    {
        super(attackingDuration);
    }

    public void applyMovePattern(Enemy entity) {
        curX = (float) entity.getPos().x;
        curY = (float) entity.getPos().y;
        this.entity = entity;

        if (TimeUtils.millis() - initTime < dTimeToRetrieve)
            move();
        else
            retrieve();
    }

    private void move()
    {
        if (curY > dropHeight)  //dropping until 3/4 of height
        {
            newX = curX;
            newY = (float) (curY - entity.getSpeed());
            entity.setTempPos(newX, newY);
        } else //oscillate
        {
            boolean goWest = entity.getDirection() == Direction.W;
            boolean inOscillateDistance = entity.getTempPos().x - curX < oscillateDistance;
            boolean goEast = entity.getDirection() == Direction.E;
            boolean outOscillateDistance = curX - entity.getTempPos().x > oscillateDistance;

            if (goWest && inOscillateDistance ||
                   goEast && outOscillateDistance) {
                newX = (float) (curX - entity.getSpeed());
            } else {
                newX = (float) (curX + entity.getSpeed());
            }
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
