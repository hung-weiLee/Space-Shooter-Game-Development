package com.mygdx.game.Algorithms.EnemyMovePattern;

import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.entities.Enemy.Enemy;
import com.mygdx.game.MyGdxGame;

//go steadily straight from top to 2/3 of window height then stay still
public class DropToMiddleThenStay extends EnemyMovePattern {
    private float newX, newY;
    private float curX, curY;
    private Enemy entity;

    public DropToMiddleThenStay(int attackingDuration)
    {
        super(attackingDuration);
    }

    public void applyMovePattern(Enemy entity) {
        this.entity = entity;
        curX = (int) entity.getPos().x;
        curY = (int) entity.getPos().y;

        if (TimeUtils.millis() - initTime < dTimeToRetrieve)
            move();
        else
            retrieve();
    }

    private void move()
    {
        newX = curX;

        if (curY > MyGdxGame.HEIGHT / 5*4) {
            newY = (float) (curY - entity.getSpeed());
        } else
            newY = curY;
        entity.setPos(newX, newY);
    }

    private void retrieve()
    {
        newX = curX;
        newY = curY-1;
        entity.setPos(newX, newY);
    }
}
