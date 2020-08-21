package com.mygdx.game.entities.bullet;

import com.badlogic.gdx.graphics.Texture;

public class BigBullet extends Bullet {

    public BigBullet() {
        super();
        texture = new Texture("cloudBullet.png");
        size.x = size.y = 30;
        hitbox.setSize(size.x, size.y);
        damage = 10;
    }
}
