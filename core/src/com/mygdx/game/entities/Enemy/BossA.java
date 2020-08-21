package com.mygdx.game.entities.Enemy;

import com.badlogic.gdx.graphics.Texture;

public class BossA extends Enemy {
    public BossA(int width, int height)
    {
        super(width, height);
        texture = new Texture("boss.png");
        speed = 1;
        health = 1000;
    }
}
