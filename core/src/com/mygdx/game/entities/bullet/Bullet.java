package com.mygdx.game.entities.bullet;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.MyGdxGame;

public abstract class Bullet {
    protected Texture texture;
    protected Vector2 size;
    protected Vector2 pos;
    protected long initTime;
    protected Rectangle hitbox;
    protected int damage;

    public Bullet()
    {
        pos = new Vector2();
        size = new Vector2();
        initTime = TimeUtils.millis();
        hitbox = new Rectangle();
        damage = 5;
    }

    public void render(SpriteBatch batch)
    {
        batch.draw(texture, pos.x, pos.y, size.x, size.y);
    }

    public void setPos(float x, float y)
    {
        this.pos.x = x;
        this.pos.y = y;
        hitbox.setPosition(x,y);
    }

    public Vector2 getPos()
    {
        return pos;
    }

    public void dispose()
    {
        texture.dispose();
    }

    public boolean outOfBorder(int pixels)
    {
        return (pos.x+size.x < -pixels || pos.y+size.y < -pixels ||
                pos.x > MyGdxGame.WIDTH + pixels ||
                pos.y > MyGdxGame.HEIGHT + pixels);
    }

    public long getInitTime()
    {
        return initTime;
    }

    public Rectangle getHitbox()
    {
        return hitbox;
    }

    public int getDamage()
    {
        return damage;
    }
}
