package com.mygdx.game.entities.bullet;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

//the clouds surrounding enemies that emit bullets
public class BulletSource {
    private Texture texture;
    private Vector2 pos;

    public BulletSource(float x, float y)
    {
        pos = new Vector2(x, y);
        texture = new Texture("bulletSource.png");
    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(float x, float y)
    {
        pos.x = x;
        pos.y = y;
    }

    public void render(SpriteBatch batch)
    {
        //batch.draw(texture, pos.x, pos.y, 30, 30);
    }
}
