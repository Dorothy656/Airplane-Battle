package si.model;

import java.awt.*;

public class Player implements Hittable {
    private int x;
    private int y;
    private Rectangle hitBox;
    private int weaponCountdown;
    private boolean alive = true;
    public static final int SHIP_SCALE = 3;
    private  int facing = 1;
    private DefenderGame game;
    public void setFacing(int facing) {
        this.facing = facing;
    }

    public int getFacing() {
        return facing;
    }

    public Player(DefenderGame game) {
        x = DefenderGame.SCREEN_WIDTH  / 2;
        y = 450;
        this.game = game;
        hitBox = new Rectangle(x, y, 8 * SHIP_SCALE, 5 * SHIP_SCALE);
    }

    public boolean isHit(Bullet b) {
        if (b.getOwner() == this)return false;
        Rectangle s = b.getHitBox();
        if (s.intersects(hitBox.getBounds())) {
            alive = false;
        }
        return s.intersects(hitBox.getBounds());
    }

    @Override
    public Rectangle getHitBox() {
        return hitBox;
    }

    public void tick() {
        if (weaponCountdown > 0) {
            weaponCountdown--;
        } else {
            weaponCountdown = 10;
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public void resetDestroyed() {
        alive = true;
        x = DefenderGame.SCREEN_WIDTH / 2;
        y = 450;
        hitBox = new Rectangle(x, y, 8 * SHIP_SCALE, 5 * SHIP_SCALE);
    }

    public int getPoints() {
        return -100;
    }

    public boolean isPlayer() {
        return true;
    }

    public Bullet fire() {
        Bullet b = null;
        if (weaponCountdown == 0) {
            b = new Bullet(x + 3 * SHIP_SCALE, y - 1 * SHIP_SCALE, 5 * facing ,0, true, "Player");
            b.setOwner(this);
        }
        return b;

    }

    public DefenderGame getGame() {
        return game;
    }

    public void move(int x1, int y1) {
        Rectangle newBox = new Rectangle(x + x1, y + y1, 8 * SHIP_SCALE, 5 * SHIP_SCALE);
        Rectangle checkedBox = checkBounds(newBox);

        // Update the hitBox and coordinate
        hitBox = checkedBox;
        this.x = checkedBox.x;
        this.y = checkedBox.y;
    }

    private Rectangle checkBounds(Rectangle box) {
        int screenHeight = game.getScreenHeight();
        int screenWidth = game.getScreenWidth();
        int maxX = screenWidth + 300 - box.width;// Calculate the maximum allowable X-coordinate (right edge of the screen)
        int maxY = 490- box.height;  // Calculate the maximum allowable Y coordinate (bottom boundary)
        int newX = Math.min(Math.max(box.x, -300), maxX);//Make sure x is inside the left boundary and the right boundary
        int newY = Math.min(Math.max(box.y, 0), maxY);//Make sure y is inside the up boundary and the down boundary

        return new Rectangle(newX, newY, box.width, box.height);
    }




    public int getX() {
        return x ;
    }

    public int getY() {
        return y;
    }

}
