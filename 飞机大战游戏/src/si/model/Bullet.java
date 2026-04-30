package si.model;

import java.awt.*;

public class Bullet implements Movable, Hittable {
    private int x, y;
    private  int speedx,speedy;
    private boolean direction; // True for up, false for down
    private boolean alive = true;
    private Rectangle hitBox;
    private String name;
    private static int bulletCounter = 0;
    public static final int BULLET_HEIGHT = 6;
    public static final int BULLET_WIDTH = 6;
    private static final int BULLET_SPEED = 1;
    private boolean isFromPlayer;
    private Hittable owner;
    public Bullet(int x, int y, int speedx , int speedy ,boolean direction, String name) {
        this.direction = direction;
        this.x = x;
        this.y = y;
        this.speedx = speedx;
        this.speedy = speedy;
        this.name = name;
        hitBox = new Rectangle(x, y, BULLET_WIDTH, BULLET_HEIGHT);

    }
    public String bulletName(){
        return name;
    }

    public Hittable getOwner() {
        return owner;
    }

    public void setOwner(Hittable owner) {
        this.owner = owner;
    }

    public void move() {
        x += speedx;
        y += speedy;
        hitBox.setLocation(x, y);
    }

    @Override
    public DefenderGame getGame() {
        return owner.getGame();
    }

    public int getX() {
        final int n = getGame().getScreenWidth()/2 - getGame().getShip().getX();
        return x + n;
    }

    public int getY() {
        return y;
    }

    public boolean isHit(Bullet b) {
        if (hitBox.intersects(b.hitBox)) {
            alive = false;
            b.alive = false;
        }
        return hitBox.intersects(b.hitBox);
    }

    public Rectangle getHitBox() {
        return new Rectangle(hitBox);
    }

    public boolean isAlive() {
        return alive;
    }

    public int getPoints() {
        return 0;
    }

    public boolean isPlayer() {
        return isFromPlayer;
    }

    public void destroy() {
        alive = false;
    }

}
