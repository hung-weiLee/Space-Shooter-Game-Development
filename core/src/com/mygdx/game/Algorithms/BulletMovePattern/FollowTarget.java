package com.mygdx.game.Algorithms.BulletMovePattern;

import com.mygdx.game.entities.bullet.Bullet;
import com.mygdx.game.entities.bullet.BulletSource;
import com.mygdx.game.entities.Enemy.Enemy;
import com.mygdx.game.entities.players.hero.Hero;

import java.util.List;

public class FollowTarget extends BulletMovePattern {
    float curX, curY, newX, newY;
    Hero target;

    public FollowTarget(List<BulletSource> bulletSources,
                        List<List<Bullet>> bulletsList,
                        String bulletType, Enemy enemy) {
        super(bulletSources, bulletsList, bulletType, enemy);
        speed = 2;
        target = enemy.getTarget();
    }

    public void update(float delta, boolean enemyDie) {
        super.update(delta, enemyDie);

        //move bullets
        for (int i = 0; i < bulletsList.size(); i++)
            for (int j = 0; j < bulletsList.get(i).size(); j++) {
                curX = bulletsList.get(i).get(j).getPos().x;
                curY = bulletsList.get(i).get(j).getPos().y;
                getNewX();
                getNewY();
                bulletsList.get(i).get(j).setPos(newX, newY);
            }
    }

    private void getNewX() {
        float targetMiddle = target.getPos().x + target.getSize().x/2;

        if(targetMiddle <curX) // target on the left
            newX =(float)(curX-speed);
        else if(targetMiddle > curX) // target on the right
            newX =(float)(curX+speed);
        else
            newX =curX;
    }

    private void getNewY() {
        float targetMiddle = target.getPos().y + target.getSize().y/2;

        if (targetMiddle < curY) // target is below
            newY = (float) (curY - speed);
        else if (targetMiddle > curY) // target is above
            newY = (float) (curY + speed);
        else
            newY = curY;
    }
}