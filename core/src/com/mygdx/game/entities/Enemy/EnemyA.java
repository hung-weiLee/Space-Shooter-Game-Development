package com.mygdx.game.entities.Enemy;

import com.badlogic.gdx.graphics.Texture;

public class EnemyA extends Enemy {
    public EnemyA(int width, int height)
    {
        super(width, height);
        texture = new Texture("EnemyA.png");
        speed = 1;
        health = 50;
    }
}
