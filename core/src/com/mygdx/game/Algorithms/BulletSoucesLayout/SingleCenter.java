package com.mygdx.game.Algorithms.BulletSoucesLayout;

import com.mygdx.game.entities.bullet.BulletSource;
import com.mygdx.game.entities.Enemy.Enemy;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.List;

//a single source in the middle of the enemy
public class SingleCenter extends BulletSourcesLayout{
    private static long lastFireTime;
    public SingleCenter(Enemy enemy, List<BulletSource> sources)
    {
        super(enemy, sources);
        lastFireTime = TimeUtils.millis();
    }

    public void applyLayout()
    {
        sources.removeAll(sources);
        float x= enemy.getPos().x+enemy.getSize().x/2;
        float y = enemy.getPos().y + enemy.getSize().y/2;
        if(TimeUtils.millis() - lastFireTime > -1){
            sources.add(new BulletSource(x, y));
            lastFireTime=TimeUtils.millis();
        }
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
