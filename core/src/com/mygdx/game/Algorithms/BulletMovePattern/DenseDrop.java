package com.mygdx.game.Algorithms.BulletMovePattern;

import com.mygdx.game.entities.bullet.Bullet;
import com.mygdx.game.entities.bullet.BulletSource;
import com.mygdx.game.entities.Enemy.Enemy;

import java.util.List;

public class DenseDrop extends BulletMovePattern {

    public DenseDrop(List<BulletSource> bulletSources,
                     List<List<Bullet>> bulletsList,
                     String bulletType, Enemy enemy) {
        super(bulletSources, bulletsList, bulletType, enemy);
        countBwBullets = 1;
        speed = 10;
    }

    public void update(float delta, boolean enemyDie) {
        super.update(delta, enemyDie);

        float curX, curY, newX, newY;
        //move bullets
        for (int i = 0; i < bulletsList.size(); i++)
            for (int j = 0; j < bulletsList.get(i).size(); j++) {
                curX = bulletsList.get(i).get(j).getPos().x;
                curY = bulletsList.get(i).get(j).getPos().y;
                newX = curX;
                newY = curY-speed;
                bulletsList.get(i).get(j).setPos(newX, newY);
            }


    }
}