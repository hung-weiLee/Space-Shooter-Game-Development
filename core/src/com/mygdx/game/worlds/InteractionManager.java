package com.mygdx.game.worlds;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.entities.bullet.Bullet;
import com.mygdx.game.entities.Enemy.Enemy;
import com.mygdx.game.AIEngine.EnemyWave;
import com.mygdx.game.AIEngine.Enemy_BSource_Bullet_Manager;
import com.mygdx.game.AIEngine.RunAIEngine;
import com.mygdx.game.entities.players.hero.Hero;
import java.util.List;

public class InteractionManager {
    private RunAIEngine AIenemy;
    private long heroHitTime;
    private int heroHitEnemyDamage = 150;

    public InteractionManager(RunAIEngine AIenemy)
    {
        this.AIenemy = AIenemy;
        heroHitTime = -1;
    }

    public boolean EnemyHitHero() //then enemy and hero die
    {
        boolean result = false;
        List<EnemyWave> waves = AIenemy.getEnemyWave();
        for (int i = 0; i < waves.size(); i++)
        {
            List<Enemy_BSource_Bullet_Manager> layer = waves.get(i).getManagerList();

            for (int j = 0; j < layer.size(); j++) {

                Enemy enemy = layer.get(j).getEnemy();
                Hero hero = enemy.getTarget();
                boolean hit = enemy.getHitbox().overlaps(hero.getHitbox()) &&
                        hero.getInvincible() == false;

                if (hit)
                {
                    result = true;
                    heroTakeDamage(hero, heroHitEnemyDamage);
                    enemyTakeDamage(enemy, heroHitEnemyDamage, layer, j);
                    heroHitTime = TimeUtils.millis();
                }
            }
        }
        return result;
    }


    private void heroTakeDamage(Hero hero, int damage){
        hero.takeDamage(damage);
        if (hero.heathRunOut())
            hero.die();
    }

    private void enemyTakeDamage(Enemy enemy, int damage,
                                 List<Enemy_BSource_Bullet_Manager> layer, int index)
    {
        enemy.takeDamage(damage);
        if (enemy.healthRunOut())
            layer.get(index).removeEnemy();
    }

    public boolean BulletHitHero() //hero and bullet die
    {
        boolean result = false;
        List<EnemyWave> waves = AIenemy.getEnemyWave();
        for (int i = 0; i < waves.size(); i++)
        {
            List<Enemy_BSource_Bullet_Manager> layer = waves.get(i).getManagerList();

            for (int j = 0; j < layer.size(); j++) {
                Enemy enemy = layer.get(j).getEnemy();
                Hero hero = enemy.getTarget();
                List<List<Bullet>> bulletList = layer.get(j).getBulletsList();
                int bulletDamage = aBulletHitHero(enemy, bulletList);
                if (bulletDamage > 0) //bullet dies in there
                {
                     heroTakeDamage(hero, bulletDamage);
                     heroHitTime = TimeUtils.millis();
                     result = true;
                }
            }
        }
        return result;
    }

    //return bullet damage or return 0
    private int aBulletHitHero(Enemy enemy, List<List<Bullet>> bulletList)
    {
        int damage = 0;
        for (int i = 0; i < bulletList.size(); i++)
            for (int j = 0; j < bulletList.get(i).size(); j++)
                if (bulletList.get(i).get(j).getHitbox().overlaps(enemy.getTarget().getHitbox())&&
                        enemy.getTarget().getInvincible() == false) {
                    damage = bulletList.get(i).get(j).getDamage();
                    bulletList.get(i).remove(j); //bullet dies
                }
        return damage;
    }

    //return enemy type if hit, return "" otherwise
    public String BulletHitEnemy() //enemy and bullet die
    {
        String returnEnemyType = "";
        List<EnemyWave> waves = AIenemy.getEnemyWave();
        for (int i = 0; i < waves.size(); i++)
        {
            List<Enemy_BSource_Bullet_Manager> layer = waves.get(i).getManagerList();
            String enemyType = waves.get(i).getEnemyType();

            for (int j = 0; j < layer.size(); j++) {
                Enemy enemy = layer.get(j).getEnemy();
                int bulletDamage = bulletHitAnEnemy(enemy);

                if (bulletDamage > 0) //bullet dies in there
                {
                    enemyTakeDamage(enemy, bulletDamage, layer, j);
                    returnEnemyType = enemyType;
                }
            }

        }
        return returnEnemyType;
    }
    public String BombHitEnemy() //enemy and bullet die
    {
        String returnEnemyType = "";
        List<EnemyWave> waves = AIenemy.getEnemyWave();
        for (int i = 0; i < waves.size(); i++)
        {
            List<Enemy_BSource_Bullet_Manager> layer = waves.get(i).getManagerList();
            String enemyType = waves.get(i).getEnemyType();

            for (int j = 0; j < layer.size(); j++) {
                Enemy enemy = layer.get(j).getEnemy();
                enemyTakeDamage(enemy, 150, layer, j);
                returnEnemyType = enemyType;
            }

        }
        return returnEnemyType;
    }



    //return bullet damage or 0
    private int bulletHitAnEnemy(Enemy enemy)
    {
        int damage = 0;
        for (int i = 0; i < enemy.getTarget().getBullets().size(); i++)
            if (enemy.getTarget().getBullets().get(i).getHitbox().overlaps(enemy.getHitbox())) {
                damage =  enemy.getTarget().getBullets().get(i).getDamage();
                enemy.getTarget().getBullets().remove(i);
            }
        return damage;
    }

    public long getHeroHitTime()
    {
        return  heroHitTime;
    }
}
