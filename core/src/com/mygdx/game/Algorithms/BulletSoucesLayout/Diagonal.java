package com.mygdx.game.Algorithms.BulletSoucesLayout;

import com.mygdx.game.entities.bullet.BulletSource;
import com.mygdx.game.entities.Enemy.Enemy;

import java.util.List;

/*
        *
       *
      *
     *
 */
public class Diagonal extends BulletSourcesLayout{
    private float dx = 5;
    private float dy = 20;
    private int numSources = 1;

    //choose bullet
    public Diagonal(Enemy enemy, List<BulletSource> sources)
    {
        super(enemy, sources);
    }

    public void applyLayout()
    {
        sources.removeAll(sources);
        for (int i = 0; i < numSources; i++) {
            float x = enemy.getPos().x + i * dx+enemy.getSize().x/2;
            float y = enemy.getPos().y + i * dy+enemy.getSize().y/2;
            BulletSource newSource = new BulletSource(x, y);
            sources.add(newSource);
        }
    }

    public void update(float delta)
    {
        for (int i = 0; i < sources.size(); i++) {
            float x = enemy.getPos().x + i * dx+enemy.getSize().x/2;
            float y = enemy.getPos().y + i * dy+enemy.getSize().x/2;
            sources.get(i).setPos(x, y);
        }
    }
}
