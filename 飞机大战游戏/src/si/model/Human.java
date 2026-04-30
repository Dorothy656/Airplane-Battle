package si.model;

import java.awt.*;

public class Human implements Hittable {
    private int x, y;
    private boolean alive;
    private boolean captured; // Indicates whether a human is being captured
    private int Width = 15; // The width of humans
    private int Height = 50; //The height of humans

    private  DefenderGame game;

    public Human(int startX, int startY , DefenderGame game) {
        this.x = startX;
        this.y = startY;
        this.alive = true;
        this.captured = false;
        this.game = game;
    }

    @Override
    public DefenderGame getGame() {
        return game;
    }

    public boolean isAlive() {
        return alive && !captured;
    }

    // Sets whether humans are captured
    public void setCaptured(boolean captured) {
        this.captured = captured;
        if (captured) {
            this.alive = false;
        }
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
        if (!alive) {
            this.alive = true;
        }
    }


    public Rectangle getHitBox() {
        return new Rectangle((int) x, (int) y, Width, Height);
    }

    // Determine if humans have been hit by enemy ships
    public boolean isHit(Bullet b) {
        return getHitBox().intersects(b.getHitBox());
    }

    public int getPoints() {
        return 0; // 人类没有得分
    }

    public boolean isPlayer() {
        return true;
    }

    // Gets the x of the human when th screen is moving
    public int getNewX() {
        final int n = game.getScreenWidth()/2 - game.getShip().getX();
        return x + n;
    }

    public int getX() {
        return x ;
    }

    public int getY() {
        return y;
    }


    // Detect whether the enemy ship has collided with humans
    public boolean checkCollisionWithEnemy(EnemyShip enemy) {
        return getHitBox().intersects(enemy.getHitBox());
    }
}
