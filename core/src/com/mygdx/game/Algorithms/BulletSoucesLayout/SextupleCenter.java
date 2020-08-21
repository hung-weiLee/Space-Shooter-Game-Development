package com.mygdx.game.Algorithms.BulletSoucesLayout;

import com.mygdx.game.entities.bullet.BulletSource;
import com.mygdx.game.entities.Enemy.Enemy;

import java.util.List;

//6 source in the middle of the enemy
public class SextupleCenter extends BulletSourcesLayout{
    public SextupleCenter(Enemy enemy, List<BulletSource> sources)
    {
        super(enemy, sources);
    }

    private int numSources = 6;
    public void applyLayout()
    {
        sources.removeAll(sources);
        float x= enemy.getPos().x+enemy.getSize().x/2;
        float y = enemy.getPos().y + enemy.getSize().y/2;
        for (int i = 0; i < numSources; i++)
            sources.add(new BulletSource(x, y));
    }

    public void update(float delta)
    {
        float x = enemy.getPos().x + enemy.getSize().x / 2;
        float y = enemy.getPos().y + enemy.getSize().y / 2;
        for (int i = 0; i < sources.size(); i++)
        {
            sources.get(i).setPos(x, y);
        }
    }
}
