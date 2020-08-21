package com.mygdx.game.Algorithms.BulletMovePattern;

import com.mygdx.game.entities.bullet.Bullet;
import com.mygdx.game.entities.bullet.BulletSource;
import com.mygdx.game.entities.Enemy.Enemy;

import java.util.ArrayList;
import java.util.List;

public class ConstAngel extends BulletMovePattern {

    private double totalAngel =  Math.PI/5;
    private double dTheta;
    private double radius;
    private List<Double> angles;

    public ConstAngel(List<BulletSource> bulletSources,
                        List<List<Bullet>> bulletsList,
                        String bulletType, Enemy enemy) {
        super(bulletSources, bulletsList, bulletType, enemy);
        dTheta = totalAngel/(bulletSources.size()-1);

        setAngels();
        radius = 3;
        countBwBullets = 30;
    }

    public void update(float delta, boolean enemyDie) {
        super.update(delta, enemyDie);

        float curX, curY, newX, newY;
        //move bullets
        for (int i = 0; i < bulletsList.size(); i++)
            for (int j = 0; j < bulletsList.get(i).size(); j++) {
                curX = bulletsList.get(i).get(j).getPos().x;
                curY = bulletsList.get(i).get(j).getPos().y;

                newX = (float)(curX + radius*Math.cos(angles.get(i)));
                newY = (float)(curY + radius*Math.sin(angles.get(i)));
                bulletsList.get(i).get(j).setPos(newX, newY);
            }
    }

    private void setAngels()
    {
        angles = new ArrayList<>();
        for (int i = 0; i < bulletSources.size(); i++)
        {
            angles.add((Math.PI*3/2-totalAngel/2)+i*dTheta);
        }
    }
}