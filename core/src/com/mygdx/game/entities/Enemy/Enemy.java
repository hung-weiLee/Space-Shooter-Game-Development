package com.mygdx.game.entities.Enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Algorithms.EnemyMovePattern.EnemyMovePattern;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.entities.players.hero.Hero;



public abstract class Enemy {
    protected Texture texture;
    protected double speed;
    protected int health;

    private Rectangle hitbox;
    private Vector2 size;
    private EnemyMovePattern enemyMovePattern;
    private Vector2 tempPos; //position at some point depending on the application/client
    private Direction direction; //moving direction
    private Hero target; //the entity that this enemy may follow/attack
    private Vector2 pos;

    public Enemy(int width, int height) {
        pos = new Vector2(0, 0);
        size = new Vector2(width, height);
        target = Hero.getInstance();
        tempPos = new Vector2(0, 0);
        hitbox = new Rectangle();
        health = 10;
        hitbox.setSize(size.x / 3*2, size.y / 3*2);
    }

    public void setEnemyMovePattern(EnemyMovePattern enemyMovePattern) {
        this.enemyMovePattern = enemyMovePattern;
    }

    public Direction getDirection() {
        return direction;
    }

    public Vector2 getPos() {
        return pos;
    }

    public Vector2 getTempPos() {
        return tempPos;
    }


    public void setPos(float x, float y)
    {
        pos.x = x;
        pos.y = y;
        hitbox.setPosition(x+size.x/6,y+size.y/6);
    }

    public void setTempPos(float x, float y)
    {
        tempPos.x = x;
        tempPos.y = y;
    }

    public double getSpeed()
    {
        return speed;
    }

    public Vector2 getSize()
    {
        return size;
    }

    public void render(SpriteBatch batch)
    {
        batch.draw(texture, pos.x, pos.y, size.x, size.y);
    }

    public void update(float delta)
    {
        Vector2 prevPos = new Vector2(pos.x, pos.y);
        move(delta);
        Vector2 curPos = pos;

        updateDirection(prevPos, curPos);
    }

    private void updateDirection(Vector2 prevPos, Vector2 curPos)
    {
        if (curPos.x > prevPos.x)
        {
            if (curPos.y > prevPos.y)
                direction = Direction.NE;
            else if (curPos.y < prevPos.y)
                direction = Direction.SE;
            else
                direction = Direction.E;
        }
        else if (curPos.x < prevPos.x)
        {
            if (curPos.y > prevPos.y)
                direction = Direction.NW;
            else if (curPos.y < prevPos.y)
                direction = Direction.SW;
            else
                direction = Direction.W;
        }
        else
        {
            if (curPos.y > prevPos.y)
                direction = Direction.N;
            else if (curPos.y < prevPos.y)
                direction = Direction.S;
            else
                direction = Direction.NONE;
        }
    }

    private void move(float delta)
    {
        enemyMovePattern.applyMovePattern(this);
    }

    public Hero getTarget()
    {
        return target;
    }

    public void dispose()
    {
        texture.dispose();
    }

    //return true if enemy position is out of the game window margin
    //the margin is the amount of pixels more from 4 sides of the window
    public boolean outOfMargin(int pixels)
    {
        return (pos.x+size.x < -pixels || pos.y+size.y < -pixels ||
                pos.x > MyGdxGame.WIDTH + pixels ||
                pos.y > MyGdxGame.HEIGHT + pixels);
    }

    public Rectangle getHitbox()
    {
        return hitbox;
    }

    public boolean healthRunOut()
    {
        return health <= 0;
    }

    public void takeDamage(int damage)
    {
        health -= damage;
    }

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }
}
