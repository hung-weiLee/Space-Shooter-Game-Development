package com.mygdx.game.Algorithms.BulletSoucesLayout;

import com.mygdx.game.entities.bullet.BulletSource;
import com.mygdx.game.entities.Enemy.Enemy;

import java.util.List;

//16 source in the middle of the enemy
public class SextupleHelixTogether extends BulletSourcesLayout{

    private float radius;
    private float theta;
    private int numSource = 3;
    private double dRadius = 0.8;
    private double dTheta = 0.2;

    public SextupleHelixTogether(Enemy enemy, List<BulletSource> sources)
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
        for (int i = 0; i < numSource; i++)
            sources.add(new BulletSource(x, y));
    }

    public void update(float delta)
    {
        radius += dRadius;
        theta += dTheta;
        float x = (float) (enemy.getPos().x + radius * Math.cos(theta));
        float y = (float) (enemy.getPos().y + radius * Math.sin(theta));
        for (int i = 0; i < sources.size(); i++)
        {
            sources.get(i).setPos(x, y);
        }
    }
}
