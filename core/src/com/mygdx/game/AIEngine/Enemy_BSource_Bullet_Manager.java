package com.mygdx.game.AIEngine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.Algorithms.BulletMovePattern.CoverAllWithDots;
import com.mygdx.game.entities.bullet.Bullet;
import com.mygdx.game.entities.bullet.BulletSource;
import com.mygdx.game.Algorithms.BulletMovePattern.BulletMovePattern;
import com.mygdx.game.Algorithms.BulletMovePattern.ConstAngel;
import com.mygdx.game.Algorithms.BulletMovePattern.CoverAllWithLines;
import com.mygdx.game.Algorithms.BulletMovePattern.FollowTarget;
import com.mygdx.game.Algorithms.BulletMovePattern.SteadyDrop;
import com.mygdx.game.Algorithms.BulletMovePattern.DenseDrop;
import com.mygdx.game.Algorithms.BulletSoucesLayout.BulletSourcesLayout;
import com.mygdx.game.Algorithms.BulletSoucesLayout.DoubleHelix;
import com.mygdx.game.Algorithms.BulletSoucesLayout.SextupleHelixTogether;
import com.mygdx.game.Algorithms.BulletSoucesLayout.SextupleCenter;
import com.mygdx.game.Algorithms.BulletSoucesLayout.SingleCenter;
import com.mygdx.game.Algorithms.BulletSoucesLayout.Diagonal;
import com.mygdx.game.Algorithms.BulletSoucesLayout.SingleHelix;
import com.mygdx.game.entities.Enemy.Enemy;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

//put an enemy with its bullet sources and bullets
//Note: handle ONE enemy
public class Enemy_BSource_Bullet_Manager {
    private Enemy enemy;
    private List<BulletSource> bulletSources;
    private BulletSourcesLayout BSLayout;
    private BulletMovePattern bulletMovePattern;
    private List<List<Bullet>> bulletsList; //each inner list is a list of bullets for a bulletSource
    private long spawnTime;
    private boolean pause;
    private int nextShootingWaveNum; //start from 0
    private JSONArray wavesJSON;
    private JSONObject nextShootingWaveJson;
    private String bulletType;
    private boolean enemyDie;
    private long lastFireTime;


    public Enemy_BSource_Bullet_Manager(Enemy enemy, JSONArray bulletsJSON)
    {
        this.enemy = enemy;
        bulletSources = new ArrayList<>();
        spawnTime = TimeUtils.millis();
        bulletsList = new ArrayList<>();
        pause = false;
        this.wavesJSON = bulletsJSON;
        nextShootingWaveNum = 0;
        nextShootingWaveJson = (JSONObject) bulletsJSON.get(0);
        enemyDie = false;
        lastFireTime = TimeUtils.millis();
    }

    public Enemy getEnemy()
    {
        return enemy;
    }

    public void render(SpriteBatch batch)
    {
        if (!enemyDie)
            enemy.render(batch); //render enemy
        for (int i = 0; i < bulletSources.size(); i++) //render sources
            bulletSources.get(i).render(batch);

        for (int i = 0; i < bulletsList.size(); i++) //render bullets
            for (int j = 0; j < bulletsList.get(i).size(); j++)
                bulletsList.get(i).get(j).render(batch);
    }

    private void updateEnemy(float delta)
    {
        enemy.update(delta);
        if (enemy.outOfMargin(1000))
            enemy.dispose();
    }

    public void update(float delta)
    {
        if (!enemyDie) {
            updateEnemy(delta);

            //prepare for next shooting wave when it's time
            long timeToNextWave = (long) nextShootingWaveJson.get("time") - (TimeUtils.millis() - spawnTime);
            boolean timeToReleaseAWave = timeToNextWave < 100 && timeToNextWave > -100;
            if (timeToReleaseAWave && nextShootingWaveNum < wavesJSON.size()) //it's time -> release the wave
            {
                if (nextShootingWaveJson.get("pause") != null) //pause shooting
                    pause();
                else
                    releaseTheShooingWave();
                if (nextShootingWaveNum < wavesJSON.size() - 1) //no more new wave after the last wave
                    getNextWave(); //prepare for the next wave
            }

            if (BSLayout != null)
                BSLayout.update(delta);
        }

        if (bulletMovePattern != null)
            bulletMovePattern.update(delta, enemyDie);

    }

    private void releaseTheShooingWave()
    {
        resume();
        BSLayout = getSourceLayout();
        bulletType = (String)nextShootingWaveJson.get("bulletType");
        BSLayout.applyLayout();
        
        bulletMovePattern = getBulletMovePattern((String)nextShootingWaveJson.get("moveStyle"), bulletsList);
        initBulletsList();
    }

    private BulletMovePattern getBulletMovePattern(String moveStyle, List<List<Bullet>> bulletsList)
    {
        BulletMovePattern result;
        switch (moveStyle)
        {
            case "FollowTarget":
                result = new FollowTarget(bulletSources, bulletsList, bulletType, enemy);
                break;
            case "ConstAngel":
                result = new ConstAngel(bulletSources, bulletsList, bulletType, enemy);
                break;
            case "DenseDrop":
                result = new DenseDrop(bulletSources, bulletsList, bulletType, enemy);
                break;
            case "CoverAllWithLines":
                result = new CoverAllWithLines(bulletSources, bulletsList, bulletType, enemy);
                break;
            case "CoverAllWithDots":
                result = new CoverAllWithDots(bulletSources, bulletsList, bulletType, enemy);
                break;
            default:
                result = new SteadyDrop(bulletSources, bulletsList, bulletType, enemy);
                break;
        }
        return result;
    }

    private BulletSourcesLayout getSourceLayout()
    {
        BulletSourcesLayout result;
        switch ((String)nextShootingWaveJson.get("layout"))
        {
            case "SingleCenter":
                result = new SingleCenter(enemy, bulletSources);;
                break;
            case "SingleHelix":
                result = new SingleHelix(enemy, bulletSources);;
                break;
            case "DoubleHelix":
                result = new DoubleHelix(enemy, bulletSources);;
                break;
            case "SextupleCenter":
                result = new SextupleCenter(enemy, bulletSources);;
                break;
            case "SextupleHelixTogether":
                result = new SextupleHelixTogether(enemy, bulletSources);;
                break;
            default:
                result = new Diagonal(enemy, bulletSources);;
                break;
        }
        return result;
    }

    private void getNextWave()
    {
        nextShootingWaveNum += 1;
        nextShootingWaveJson = (JSONObject) wavesJSON.get(nextShootingWaveNum);
    }

    private void initBulletsList()
    {
        bulletsList.removeAll(bulletsList);//  => will erase all "old" bullets, which is not what i want
        for (int i = 0; i < bulletSources.size(); i++)
            bulletsList.add(new ArrayList<Bullet>());
    }

    private void pause() //stop firing
    {
        pause = true;
        bulletSources.removeAll(bulletSources);
    }

    private void resume() //continue firing
    {
        pause = false;
    }

    public List<List<Bullet>> getBulletsList()
    {
        return bulletsList;
    }

    public void removeEnemy() //assign signal to stop render enemy and stop adding bullets to sources
    {
        enemyDie = true;
        enemy.setHitbox(new Rectangle(-1,-1,0,0));
    }

}
