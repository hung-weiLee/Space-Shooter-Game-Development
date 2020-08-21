package com.mygdx.game.entities.Enemy;

import com.badlogic.gdx.graphics.Texture;

public class EnemyB extends Enemy {
    public EnemyB(int width, int height)
    {
        super(width, height);
        texture = new Texture("EnemyB.png");
        speed = 2;
        health = 100;
    }
}

