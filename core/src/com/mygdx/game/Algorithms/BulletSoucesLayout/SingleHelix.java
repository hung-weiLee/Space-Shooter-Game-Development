package com.mygdx.game.Algorithms.BulletSoucesLayout;

import com.mygdx.game.entities.bullet.BulletSource;
import com.mygdx.game.entities.Enemy.Enemy;

import java.util.List;

//a single source in the middle of the enemy that moves spirally around the enemy
public class SingleHelix extends BulletSourcesLayout{

    private float radius;
    private float theta;

    public SingleHelix(Enemy enemy, List<BulletSource> sources)
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
        sources.add(new BulletSource(x, y));
    }

    public void update(float delta)
    {
        radius += 0.3;
        theta += 0.1;
        float x = (float) (enemy.getPos().x + radius * Math.cos(theta));
        float y = (float) (enemy.getPos().y + radius * Math.sin(theta));
        for (int i = 0; i < sources.size(); i++)
        {
            sources.get(0).setPos(x, y);
        }
    }
}

