package com.mygdx.game.entities.bullet;

import com.badlogic.gdx.graphics.Texture;

public class MediumBullet extends Bullet {

    public MediumBullet() {
        super();
        texture = new Texture("enemyBBullet.png");
        size.x = size.y = 20;
        hitbox.setSize(size.x, size.y);
        damage = 5;
    }
}
