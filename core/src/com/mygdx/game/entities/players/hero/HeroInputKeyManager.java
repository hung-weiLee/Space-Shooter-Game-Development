package com.mygdx.game.entities.players.hero;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.TimeUtils;

public class HeroInputKeyManager {
    private static HeroInputKeyManager _instance;
    public static int leftKey = Input.Keys.LEFT;
    public static int rightKey = Input.Keys.RIGHT;
    public static int upKey = Input.Keys.UP;
    public static int downKey = Input.Keys.DOWN;
    public static int shootKey = Input.Keys.SPACE;
	public static int bombKey = Input.Keys.CONTROL_LEFT;
    public static int slowSpeedKey = Input.Keys.SHIFT_LEFT;
	//public static int subSpeedKey = Input.Keys.SHIFT_RIGHT;
    public static int normalModeKey = Input.Keys.RIGHT_BRACKET;
    public static int slowModeKey = Input.Keys.LEFT_BRACKET;

    private int shootingPace;
    private long lastFireTime;
    private long lastBombTime;
    private Hero hero;

    private HeroInputKeyManager(Hero hero){
        this.hero = hero;
        shootingPace = 100;
        lastFireTime = TimeUtils.millis();
    }

    public static HeroInputKeyManager getInstance(Hero hero)
    {
        if (_instance == null)
            _instance = new HeroInputKeyManager(hero);
        return _instance;
    }

    private boolean isCorrectTimeInterval() {
        return TimeUtils.millis() - lastFireTime > shootingPace;
    }


    public void executeInputCommand(float delta) {
        if(Gdx.input.isKeyPressed(leftKey))
            hero.setPos(hero.getPos().x-hero.getCurSpeed()*delta,hero.getPos().y);

        if(Gdx.input.isKeyPressed(rightKey))
            hero.setPos(hero.getPos().x+hero.getCurSpeed()*delta,hero.getPos().y);

        if(Gdx.input.isKeyPressed(upKey))
            hero.setPos(hero.getPos().x,hero.getPos().y+hero.getCurSpeed()*delta);

        if(Gdx.input.isKeyPressed(downKey))
            hero.setPos(hero.getPos().x,hero.getPos().y-hero.getCurSpeed()*delta);

        if(Gdx.input.isKeyPressed(shootKey)){
            if (isCorrectTimeInterval()) {
                hero.addBullet();
                lastFireTime = TimeUtils.millis();
			}
		}
        if(Gdx.input.isKeyPressed(bombKey)){
            if (TimeUtils.millis() - lastBombTime >1500) {
				hero.setPressBomb(true);   
                lastBombTime = TimeUtils.millis();
			}
        }

        if(Gdx.input.isKeyPressed(slowSpeedKey))
            hero.setCurSpeed((int)(150));
		else
			hero.setCurSpeed((int)(250));

        //if(Gdx.input.isKeyPressed(subSpeedKey))
          //  hero.setCurSpeed((int)(hero.getCurSpeed()-hero.getAcceleration()*delta));

        if(Gdx.input.isKeyPressed(normalModeKey))
            hero.setCurSpeed(hero.getNormalSpeed());

        if(Gdx.input.isKeyPressed(slowModeKey))
            hero.setCurSpeed(hero.getSlowSpeed());
    }

}
