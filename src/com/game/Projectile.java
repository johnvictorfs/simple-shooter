package com.game;

import com.exceptions.EntityOutOfBoundsException;

class Projectile extends Entity {
    private int speed;
    private boolean disabled = false;

    Projectile(int x, int y, String name, int speed) {
        this.setX(x);
        this.setY(y);
        this.setSprite(name);
        this.speed = speed;

        if (this.getSprite() == null) {
            this.setWidth(10);
            this.setHeight(10);
        }
    }

    int getSpeed() {
        return this.speed;
    }

    void disable() {
        this.disabled = true;
    }

    boolean isDisabled() {
        return this.disabled;
    }

    void moveY(int y) throws EntityOutOfBoundsException {
        if (this.getY() + y > -20 && this.getY() + y < 650) {
            this.setY(this.getY() + y);
        } else {
            throw new EntityOutOfBoundsException();
        }
    }
}
