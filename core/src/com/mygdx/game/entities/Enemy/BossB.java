package com.mygdx.game.entities.Enemy;

import com.badlogic.gdx.graphics.Texture;

public class BossB extends Enemy {
    public BossB(int width, int height)
    {
        super(width, height);
        texture = new Texture("bossb.png");
        speed = 1;
        health = 2000;
    }
}
