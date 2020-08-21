package com.mygdx.game.Algorithms.BulletSoucesLayout;

import com.mygdx.game.entities.bullet.BulletSource;
import com.mygdx.game.entities.Enemy.Enemy;

import java.util.List;

public abstract class BulletSourcesLayout {
    protected Enemy enemy;
    protected List<BulletSource> sources;

    public BulletSourcesLayout(Enemy enemy, List<BulletSource> sources)
    {
        this.enemy = enemy;
        this.sources = sources;
    }
    public abstract void applyLayout();

    public abstract void update(float delta);
}
