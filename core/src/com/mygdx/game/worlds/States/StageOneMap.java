package com.mygdx.game.worlds.States;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.AIEngine.RunAIEngine;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.entities.players.hero.Hero;
import com.mygdx.game.AIEngine.EnemyWave;
import com.mygdx.game.worlds.InteractionManager;

public class StageOneMap {

    private RunAIEngine AIEnemy;
    private Texture background1, background2;
    private int bgYOffset1, bgYOffset2;
    private BitmapFont font;
    private InteractionManager iManager;
    private boolean isWin, isLose;
    private int score;
	private int bombNum;
    private String tempEnemyType;
    private Hero hero;
    private long startTime;
    private String curHealthBar;
    private String totalHealthBar;
    private Sound collisionSound;
    private Sound bombSound;
	private Sound awardSound;


    public StageOneMap(boolean hard) {
		hero = Hero.getInstance();
		hero.reInitialize();
		if(hard==false)
			AIEnemy = RunAIEngine.getInstance("./core/assets/easy.json");
		else
			AIEnemy = RunAIEngine.getInstance("./core/assets/hard.json");
        AIEnemy.reInitialize();
        background1 = new Texture("galaxy.jpg");
        background2 = new Texture("galaxy.jpg");
        bgYOffset1 = 0;
        bgYOffset2 = MyGdxGame.HEIGHT;
        font = new BitmapFont();
        iManager = new InteractionManager(AIEnemy);
        isWin = isLose = false;
        score = 0;
		bombNum=3;
        startTime = TimeUtils.millis();
        totalHealthBar = "|||||||||||||||";
        curHealthBar = totalHealthBar;
        collisionSound = Gdx.audio.newSound(Gdx.files.internal("explode.mp3"));
		bombSound = Gdx.audio.newSound(Gdx.files.internal("explode2.mp3"));
		awardSound = Gdx.audio.newSound(Gdx.files.internal("award.mp3"));

    }

    public void render(OrthographicCamera camera, SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        drawBackGround(batch);

        if (!isLose) {
            hero.render(batch);
        }

        AIEnemy.render(batch);

        if(hero.getPressBomb() && bombNum>0){
            hero.renderBomb(batch);
            hero.setPressBomb(false);
			bombSound.play();
			bombNum-=1;
			iManager.BombHitEnemy();
			/*
			for(int i = 0;i<AIEnemy.getEnemyWave().size();i++)
			{
				EnemyWave wave = AIEnemy.getEnemyWave().get(i);
				for(int j=0;j<wave.getManagerList().size();j++)
				{
					wave.getManagerList().get(j).getEnemy().takeDamage(150);
				}
			}*/
        }
		else
		{
			hero.setPressBomb(false);
		}


        drawStats(batch);

        drawEndGame(batch);

        batch.end();
    }

    private void drawBackGround(SpriteBatch batch)
    {
        batch.draw(background1, 0, bgYOffset1, (int) (MyGdxGame.WIDTH), (int) (MyGdxGame.HEIGHT));
        batch.draw(background2, 0, bgYOffset2, (int) (MyGdxGame.WIDTH), (int) (MyGdxGame.HEIGHT));
    }

    private void drawStats(SpriteBatch batch)
    {
        font.getData().setScale(1.1f);
        font.draw(batch, "Health: " + curHealthBar, MyGdxGame.WIDTH-150, MyGdxGame.HEIGHT-20);
        font.draw(batch, "Lives: " + hero.getLives(), MyGdxGame.WIDTH-100, MyGdxGame.HEIGHT-50);
        font.draw(batch, "Score: " + score, MyGdxGame.WIDTH-100, MyGdxGame.HEIGHT-80);
        font.draw(batch, "Bomb: " + bombNum, MyGdxGame.WIDTH-100, MyGdxGame.HEIGHT-110);
		font.getData().setScale(1.5f);
    }

    private void drawEndGame(SpriteBatch batch)
    {
        if (isLose)
        {
            hero.setHitbox(new Rectangle(-1000,-1000,0,0));
            font.draw(batch, "Game Over!", MyGdxGame.WIDTH/2-40, MyGdxGame.HEIGHT/2);
        }
        else if (isWin)
        {
            font.draw(batch, "WIN!", MyGdxGame.WIDTH/2-30, MyGdxGame.HEIGHT/2);
        }
    }

    public void update(float delta) {
        AIEnemy.update(delta);

        hero.update(delta);

        runBackground();

        handleInteractions();

        updateScore(tempEnemyType);

        updateHealthBar();

        updateWinningLosing();
    }

    private void updateHealthBar()
    {
        if (hero.getTotalHeath() > 0) {
            double ratio = totalHealthBar.length()/(double)hero.getTotalHeath();
            curHealthBar = "";
            for (int i = 0; i < (int)(ratio*hero.getCurHealth()); i++)
                curHealthBar = curHealthBar + "|";
        }
    }

    private void runBackground()
    {
        bgYOffset1 -=1;
        if ( bgYOffset1*(-1) >= MyGdxGame.HEIGHT) {
            bgYOffset1 = MyGdxGame.HEIGHT;
        }
        bgYOffset2 -=1;
        if ( bgYOffset2*(-1) >= MyGdxGame.HEIGHT) {
            bgYOffset2 = MyGdxGame.HEIGHT;
        }
    }

    private void handleInteractions()
    {
        boolean EHHit = iManager.EnemyHitHero();
        boolean BHHit =  iManager.BulletHitHero();
        tempEnemyType = iManager.BulletHitEnemy();

        if ( EHHit || BHHit || tempEnemyType != "")
            collisionSound.play();
    }

    private void updateScore(String enemyType)
    {
        switch (enemyType)
        {
            case "BossA":
                score += 5;
                break;
            case "EnemyA":
                score += 5;
                break;
            case "BossB":
                score += 5;
                break;
            case "EnemyB":
                score += 5;
                break;
            default:
                score += 0;
        }
		if(score>1000 && score%1000<50)
		{
			bombNum +=1;
			score+=50;
			awardSound.play();

		}
    }
	public int getBombNum()
	{
		return this.bombNum;
	}
	public void setBombNum(int bombNum)
	{
		this.bombNum=bombNum;
	}
    private void updateWinningLosing()
    {
        isWin =  AIEnemy.lastEnemyRetrieve() || AIEnemy.lastEnemyDie();
        isLose = hero.getLives() <= 0;
    }

    public void dispose() {
        background1.dispose();
        background2.dispose();
    }

    public boolean isWin()
    {
        return isWin;
    }

    public boolean isLose()
    {
        return isLose;
    }

    public int getScore()
    {
        return score;
    }

}
