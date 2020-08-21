package com.mygdx.game.AIEngine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entities.Enemy.BossA;
import com.mygdx.game.entities.Enemy.BossB;
import com.mygdx.game.entities.Enemy.Enemy;
import com.mygdx.game.entities.Enemy.EnemyA;
import com.mygdx.game.entities.Enemy.EnemyB;
import com.mygdx.game.Algorithms.EnemyLayerLayout.DownDiagonal;
import com.mygdx.game.Algorithms.EnemyLayerLayout.EnemyLayerLayout;
import com.mygdx.game.Algorithms.EnemyLayerLayout.HorizontalFullWidth;
import com.mygdx.game.Algorithms.EnemyLayerLayout.HorizontalHalfWidth;
import com.mygdx.game.Algorithms.EnemyLayerLayout.RandomLayout;
import com.mygdx.game.Algorithms.EnemyLayerLayout.Single;
import com.mygdx.game.Algorithms.EnemyLayerLayout.UpDiagonal;
import com.mygdx.game.Algorithms.EnemyLayerLayout.V;
import com.mygdx.game.Algorithms.EnemyLayerLayout.Vertical;
import com.mygdx.game.Algorithms.EnemyMovePattern.DropToMiddleThenFollowHorizontally;
import com.mygdx.game.Algorithms.EnemyMovePattern.DropToMiddleThenMoveHorizontally;
import com.mygdx.game.Algorithms.EnemyMovePattern.DropToMiddleThenOscillate;
import com.mygdx.game.Algorithms.EnemyMovePattern.DropToMiddleThenStay;
import com.mygdx.game.Algorithms.EnemyMovePattern.FollowTarget;
import com.mygdx.game.Algorithms.EnemyMovePattern.EnemyMovePattern;
import com.mygdx.game.Algorithms.EnemyMovePattern.SW;
import com.mygdx.game.Algorithms.EnemyMovePattern.SE;
import com.mygdx.game.Algorithms.EnemyMovePattern.SteadyDrop;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

//create a wave of enemy according to json object
//each wave has multiple enemies of same Enemy type
public class EnemyWave {

    private List<Enemy_BSource_Bullet_Manager> managers;
    private String enemyType;

    public EnemyWave(JSONObject enemyJSON)
    {
        //get enemy info
        int numEnemies = ((Long) enemyJSON.get("num")).intValue();
        enemyType = (String) enemyJSON.get("type");
        int width = ((Long) ((JSONArray)enemyJSON.get("size")).get(0)).intValue();
        int height = ((Long) ((JSONArray)enemyJSON.get("size")).get(1)).intValue();
        String layoutStyle = (String) enemyJSON.get("layout");
        String moveStyle = (String) enemyJSON.get("moveStyle");
        JSONArray bulletsJSON = (JSONArray) enemyJSON.get("bullets");
        int attackingDuration = ((Long) enemyJSON.get("attackingDuration")).intValue();

        managers = new ArrayList<>();

        for (int j = 0; j < numEnemies; j++)
        {
            Enemy enemy = createEnemy(enemyType, width, height);
            enemy.setEnemyMovePattern(getMovePattern(moveStyle, attackingDuration));
            Enemy_BSource_Bullet_Manager manager = new Enemy_BSource_Bullet_Manager(enemy, bulletsJSON);
            managers.add(manager);
        }

        applyLayout(layoutStyle);
    }

    private EnemyMovePattern getMovePattern(String moveStyle, int attackingDuration) {
        EnemyMovePattern enemyMovePattern;
        switch (moveStyle)
        {
            case "DropToMiddleThenFollowHorizontally":
                enemyMovePattern = new DropToMiddleThenFollowHorizontally(attackingDuration);
                break;
            case "DropToMiddleThenMoveHorizontally":
                enemyMovePattern = new DropToMiddleThenMoveHorizontally(attackingDuration);
                break;
            case "DropToMiddleThenOscillate":
                enemyMovePattern = new DropToMiddleThenOscillate(attackingDuration);
                break;
            case "DropToMiddleThenStay":
                enemyMovePattern = new DropToMiddleThenStay(attackingDuration);
                break;
            case "FollowTarget":
                enemyMovePattern = new FollowTarget(attackingDuration);
                break;
            case "SW":
                enemyMovePattern = new SW(attackingDuration);
                break;
            case "SE":
                enemyMovePattern = new SE(attackingDuration);
                break;
            default:
                enemyMovePattern = new SteadyDrop(attackingDuration);
        }

        return enemyMovePattern;
    }

    private void applyLayout(String layoutStyle)
    {
        EnemyLayerLayout layout;
        switch (layoutStyle)
        {
            case "DownDiagonal":
                layout = new DownDiagonal();
                break;
            case "HorizontalHalfWidth":
                layout = new HorizontalHalfWidth();
                break;
            case "HorizontalFullWidth":
                layout = new HorizontalFullWidth();
                break;
            case "Single":
                layout = new Single();
                break;
            case "UpDiagonal":
                layout = new UpDiagonal();
                break;
            case "V":
                layout = new V();
                break;
            case "Vertical":
                layout = new Vertical();
                break;
            default:
                layout = new RandomLayout();
        }

        List<Enemy> enemyList = new ArrayList<>();
        for (int i = 0; i < managers.size(); i++)
            enemyList.add(managers.get(i).getEnemy());

        layout.applyLayout(enemyList);
    }

    private Enemy createEnemy(String type, int width, int height)
    {
        Enemy result;
        switch (type)
        {
            case "EnemyA":
                result = new EnemyA(width, height);
                break;
            case "EnemyB":
                result = new EnemyB(width, height);
                break;
            case "BossA":
                result = new BossA(width, height);
                break;
            case "BossB":
                result = new BossB(width, height);
                break;
            default:
                result = new EnemyB(width, height);
        }
        return  result;
    }

    public void render(SpriteBatch batch)
    {
        for (int j = 0; j < managers.size(); j++)
            managers.get(j).render(batch);
    }

    public void update(float delta)
    {
        for (int j = 0; j < managers.size(); j++)
            managers.get(j).update(delta);
    }

    public List<Enemy_BSource_Bullet_Manager> getManagerList()
    {
        return managers;
    }

    public String getEnemyType()
    {
        return enemyType;
    }
}
