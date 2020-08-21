package com.mygdx.game.entities.players.hero;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.entities.bullet.Bullet;
import com.mygdx.game.MyGdxGame;

import java.util.List;

public class Hero {
    private static Hero _instance;
    private Vector2 pos;
    private Vector2 size;
    private Texture texture;
    private Texture bomb;
	private boolean pressBomb;
    private int curSpeed;
    private Rectangle hitbox;
    private HeroInputKeyManager inputKeyManager;
    private int acceleration;
    private boolean invincible;
    private int lives;
    private int curHealth;
    private int totalHeath;
    private long invincibleDuration;
    private long bornTime;
    private long count;
    private Texture hitboxT; //del
    private HeroBulletsManager bulletsManager;
    private static int normalSpeed = 300;
    private static int slowSpeed = 200;
	public static boolean cheat = false;


    private Hero() {
        pos = new Vector2(MyGdxGame.WIDTH / 2, 100);
        texture = new Texture("Hero.png");
        bomb = new Texture("bomb.png");
		pressBomb=false;
        size = new Vector2(60, 60);
        curSpeed = 250;
        hitbox = new Rectangle(pos.x+size.x/2, pos.y+size.y/2, size.x/60, size.y/60);
        inputKeyManager = HeroInputKeyManager.getInstance(this);
        acceleration = 100;
        invincible = false;
        lives = 3;
        invincibleDuration = 5000;
        totalHeath = 25;
        curHealth = totalHeath;
        bornTime = 0;
        count = 0;
        hitboxT = new Texture("bulletSource.png");
        bulletsManager = new HeroBulletsManager();
    }

    public static Hero getInstance() {
        if (_instance == null) {
            _instance = new Hero();
        }
        return _instance;
    }

    public void reInitialize()
    {
        pos = new Vector2(MyGdxGame.WIDTH / 2, 100);
        curSpeed = 300;
        hitbox = new Rectangle(pos.x+size.x/2, pos.y+size.y/2, size.x/60, size.y/60);
        invincible = false;
        lives = 3;
        bornTime = 0;
        curHealth = totalHeath;
    }

	public void setPressBomb(boolean pressBomb)
	{
		this.pressBomb=pressBomb;
	}

	public boolean getPressBomb()
	{
		return this.pressBomb;
	}
    public Vector2 getPos() {
        return pos;
    }

    public Vector2 getSize() {
        return size;
    }

    public void render(SpriteBatch batch) {
        if (invincible)
            renderHeroBlinking(batch);
        else
            batch.draw(texture, pos.x, pos.y, size.x, size.y);

        bulletsManager.render(batch);

        if (curSpeed <= slowSpeed)
            batch.draw(hitboxT,hitbox.x, hitbox.y, hitbox.width, hitbox.height);

        count++;
    }

    private void renderHeroBlinking(SpriteBatch batch)
    {
        if (count % 2 == 0) //give blinking effect
            batch.draw(texture, pos.x, pos.y, size.x, size.y);
    }

	public void renderBomb(SpriteBatch batch)
	{
		batch.draw(bomb,0,200,500,600);
	}

    public void update(float delta) {
        inputKeyManager.executeInputCommand(delta);

        updateInvincible();

        borderControl();

        bulletsManager.updateBullets(delta);
    }

    private void updateInvincible()
    {
        if (TimeUtils.millis() - bornTime > invincibleDuration)
            invincible = false;
        else
            invincible = true;
    }

    public void addBullet() {
        Vector2 posx = new Vector2();
        posx.y=pos.y;
        posx.x=pos.x-10;
        bulletsManager.addBullet(posx, size);
    }

    public int getCurSpeed() {
        return curSpeed;
    }

    public void setCurSpeed(int curSpeed) {
        this.curSpeed = curSpeed;
    }

	

    public void setPos(float x, float y) {
        pos.x = x;
        pos.y = y;
        hitbox.setPosition(pos.x+size.x/2, pos.y+size.y/2);
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public List<Bullet> getBullets() {
        return bulletsManager.getBullets();
    }

    public int getAcceleration() {
        return acceleration;
    }

    private void borderControl() {
        if (pos.x < 0)
            setPos(0, pos.y);
        if (pos.x > MyGdxGame.WIDTH - size.x)
            setPos(MyGdxGame.WIDTH - size.x, pos.y);
        if (pos.y < 0)
            setPos(pos.x, 0);
        if (pos.y > (MyGdxGame.HEIGHT - size.y))
            setPos(pos.x, MyGdxGame.HEIGHT - size.y);
    }

    public void die() {
        this.lives--;
        this.pos.x = MyGdxGame.WIDTH / 2;
        this.pos.y = 100;
        curHealth = totalHeath;
        invincible = true;
        bornTime = TimeUtils.millis();
    }

    public int getLives()
    {
        return lives;
    }

    public void takeDamage(int damage)
    {
        if (!invincible&&!cheat)
            curHealth -= damage;
    }

    public boolean heathRunOut()
    {
        if (curHealth <= 0)
            return true;
        return false;
    }

    public int getCurHealth()
    {
        return curHealth;
    }

    public void setHitbox(Rectangle rec)
    {
        this.hitbox = rec;
    }

    public boolean getInvincible()
    {
        return invincible;
    }

    public void dispose()
    {
        texture.dispose();
        hitboxT.dispose();
        bulletsManager.dispose();
    }

    public int getTotalHeath()
    {
        return totalHeath;
    }

    public int getNormalSpeed()
    {
        return normalSpeed;
    }

    public int getSlowSpeed()
    {
        return slowSpeed;
    }
}
