package com.mygdx.game.entities.bullet;

import com.badlogic.gdx.graphics.Texture;

public class SmallBullet extends Bullet {

    public SmallBullet() {
        super();
        texture = new Texture("enemyABullet.png");
        size.x = size.y = 10;
        hitbox.setSize(size.x, size.y);
        damage = 2;
    }
}
