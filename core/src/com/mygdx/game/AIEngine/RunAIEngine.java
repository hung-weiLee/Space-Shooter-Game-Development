package com.mygdx.game.AIEngine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;


public class RunAIEngine {
    private static RunAIEngine _instance;
    private JSONArray wavesJson;
    private int nextWaveNum; //start from 0
    private JSONObject nextWaveJson;
    private long startTime;
    private List<EnemyWave> waves;
    private String jsonFilePath;

    private RunAIEngine(String jsonFilePath)
    {
        waves = new ArrayList<>();
        setWaves(jsonFilePath);
        nextWaveNum = 0;
        nextWaveJson = (JSONObject) wavesJson.get(0);
        startTime = TimeUtils.millis();
        this.jsonFilePath = jsonFilePath;
    }

    public void reInitialize() //because singleton does not re init this object when replay
    {
        waves = new ArrayList<>();
        setWaves(jsonFilePath);
        nextWaveNum = 0;
        nextWaveJson = (JSONObject) wavesJson.get(0);
        startTime = TimeUtils.millis();
    }

    public static RunAIEngine getInstance(String jsonFilePath)
    {
        if (_instance == null)
            _instance = new RunAIEngine(jsonFilePath);
        return _instance;
    }

    private void setWaves(String jsonFilePath) {
        try (FileReader reader = new FileReader(jsonFilePath))
        {
            //Read JSON file
            JSONObject obj = (JSONObject)JSONValue.parse(reader); //whole file
            this.wavesJson = (JSONArray)obj.get("waves");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getNextWave()
    {
            nextWaveNum += 1;
            nextWaveJson = (JSONObject) wavesJson.get(nextWaveNum);
    }

    public void render(SpriteBatch batch)
    {
        for(int i = 0; i < waves.size(); i++)
        {
            waves.get(i).render(batch);
        }
    }

    public void update(float delta)
    {
        //spawn the next wave of enemy
        long timeToNextWave = (long) nextWaveJson.get("time") - (TimeUtils.millis() - startTime);
        boolean timeToReleaseAWave =  timeToNextWave < 10 && timeToNextWave > -10;
        if (timeToReleaseAWave && nextWaveNum< wavesJson.size()) //get a wave
        {
            EnemyWave newWave =  createWave();
            waves.add(newWave);
            if (nextWaveNum< wavesJson.size()-1) //no more new wave after the last wave
                getNextWave();
        }

        for(int i = 0; i < waves.size(); i++) //update each existing wave
        {
            waves.get(i).update(delta);
        }
    }

    private EnemyWave createWave()
    {
        EnemyWave result = new EnemyWave((JSONObject) nextWaveJson.get("enemy"));
        return result;
    }

    public List<EnemyWave> getEnemyWave()
    {
        return waves;
    }

    public boolean lastEnemyDie()
    {
        if ( waves.size() == wavesJson.size()) //at last wave
        {
            int numEnemiesLastWave = waves.get(wavesJson.size()-1).getManagerList().size();
            if (numEnemiesLastWave == 0)
                return true;
        }
        return false;
    }

    public boolean lastEnemyRetrieve()
    {
        JSONObject lastWave = (JSONObject) wavesJson.get(wavesJson.size()-1);
        JSONObject lastEnemy = (JSONObject)lastWave.get("enemy");
        long timeLastEnemyRetrieve = (long)lastWave.get("time") +
                (long)lastEnemy.get("attackingDuration") + 5000; //5000 is extra time to retrieve totally

         return TimeUtils.millis()-startTime > timeLastEnemyRetrieve;
    }
}
