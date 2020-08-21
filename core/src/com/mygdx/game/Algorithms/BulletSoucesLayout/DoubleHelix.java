package com.mygdx.game.Algorithms.BulletSoucesLayout;

import com.mygdx.game.entities.bullet.BulletSource;
import com.mygdx.game.entities.Enemy.Enemy;

import java.util.List;

//2 sources in the middle of the enemy that moves spirally around the enemy
//in the same direction but 180 degree apart
public class DoubleHelix extends BulletSourcesLayout{

    private float radius;
    private float theta;
    private double dRadius = 1;
    private double dTheta = 0.15;

    public DoubleHelix(Enemy enemy, List<BulletSource> sources)
    {
        super(enemy, sources);
        radius = 0;
        theta = 0;
    }

    public void applyLayout()
    {
        sources.removeAll(sources);
        float x = enemy.getPos().x+enemy.getSize().x/2;
        float y = enemy.getPos().y + enemy.getSize().y/2;
        sources.add(new BulletSource(x, y)); //source 1
        sources.add(new BulletSource(x, y)); //source 2
    }

    public void update(float delta)
    {
        radius += dRadius;
        theta += dTheta;
        float x, y;
        for (int i = 0; i < sources.size(); i++)
        {
            x = (float) (enemy.getPos().x + radius * Math.cos(theta+i*Math.PI));
            y = (float) (enemy.getPos().y + radius * Math.sin(theta+i*Math.PI));
            sources.get(i).setPos(x, y);
        }
    }
}

