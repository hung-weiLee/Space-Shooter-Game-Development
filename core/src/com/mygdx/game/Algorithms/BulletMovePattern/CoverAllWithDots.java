package com.mygdx.game.Algorithms.BulletMovePattern;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Enemy.Enemy;
import com.mygdx.game.entities.bullet.Bullet;
import com.mygdx.game.entities.bullet.BulletSource;
import com.mygdx.game.entities.players.hero.Hero;

import java.util.ArrayList;
import java.util.List;

//360 degree, each branch has dots instead of continuous lines of bullets
public class CoverAllWithDots extends BulletMovePattern {

    private double totalAngel =  Math.PI*2;
    private double dTheta;
    private double radius = 3; //how fast the bullets travel
    private List<Double> angles;
    private Hero hero;

    public CoverAllWithDots(List<BulletSource> bulletSources,
                            List<List<Bullet>> bulletsList,
                            String bulletType, Enemy enemy) {
        super(bulletSources, bulletsList, bulletType, enemy);
        dTheta = totalAngel/bulletSources.size();
        hero = enemy.getTarget();
        angles = new ArrayList<>();
        setAngels();
        countBwBullets = 20;
        maxNumBullets = 2;
    }

    public void update(float delta, boolean enemyDie) {

            super.update(delta, enemyDie);

            float curX, curY, newX, newY;
            //move bullets
            for (int i = 0; i < bulletsList.size(); i++)
                for (int j = 0; j < bulletsList.get(i).size(); j++) {
                    curX = bulletsList.get(i).get(j).getPos().x;
                    curY = bulletsList.get(i).get(j).getPos().y;

                    newX = (float)(curX + 2*radius*Math.cos(angles.get(i)));
                    newY = (float)(curY + 2*radius*Math.sin(angles.get(i)));
                    bulletsList.get(i).get(j).setPos(newX, newY);
                }
    }

    private void setAngels()
    {
        //get target angel
        float dx = hero.getPos().x - enemy.getPos().x;
        float dy = hero.getPos().y - enemy.getPos().y;
        Vector2 targetV = new Vector2(dx, dy);

        Vector2 zeroDegree = new Vector2(1, 0);
        double dot = targetV.x*zeroDegree.x + targetV.y*zeroDegree.y;
        double DTargetV = Math.sqrt(targetV.x*targetV.x + targetV.y*targetV.y);
        double targetAngel = Math.PI*2 - Math.acos(dot/DTargetV);

        angles = new ArrayList<>();
        for (int i = 0; i < bulletSources.size(); i++)
        {
            angles.add(targetAngel + i*dTheta);
        }
    }
}
