// EnemyshipA is lander.
// EnemyshipB is the state that human is captured by landers.
// EnemyshipC are mutants.


package si.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;


public class EnemyShip implements Hittable {
    private String name;
    private boolean alive;
    private double x, y;
    private AlienType type;
    private Random rand;
    public static final int SHIP_SCALE = 2;
    private double moveX, moveY;
    private final DefenderGame game;


    public EnemyShip(int x, int y, AlienType type, DefenderGame game) {
        this.x = (int) (Math.random() * 800);
        this.y = 0;
        this.type = type;
        this.rand = new Random();
        this.alive = true;
        this.game = game;
        this.moveX = rand.nextDouble() * 2 - 1;
        this.moveY = rand.nextDouble() * 2 - 1;
    }

    // 随机移动方法
    public void move() {
        switch (type) {
            case A:
                moveTowardsHuman(); // Landers' method of moving
                break;
            case B:
                moveTowardsTop();   //The way that Lander who catch the human moves
                break;
            case C:
                moveTowardsPlayer(); // Mutants'method of moving
                break;
            default:
                break;
        }

        checkBounds();
        x += moveX;
        y += moveY;

    }

    private void moveTowardsHuman() {
        List<Human> availableHumans = new ArrayList<>();
        for (Human human : game.getHumans()) {
            if (human.isAlive()) {
                availableHumans.add(human);
            }
        }

        if (!availableHumans.isEmpty()) {
            Human target = availableHumans.get(0);  // Simply get the first available Human as the target
            int targetX = target.getX();
            int targetY = target.getY();

            // Calculate the direction vector towards the target
            double dx = targetX - x;
            double dy = targetY - y;
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance > 0) {
                dx /= distance;
                dy /= distance;
            }

            // Set the moving speed according to the direction vector
            moveX = dx * 1.5;
            moveY = dy * 1.5;
        }else{
            moveRandom();
        }
    }
    private void moveRandom() {
        if (rand.nextInt(100) < 5) {
            moveX = rand.nextDouble() * 4 - 2;
            moveY = rand.nextDouble() * 4- 2;
        }
    }

    private void moveTowardsTop() {
        // Set the upward movement direction
        moveY = -1;
        moveX = 0;
    }

    private void moveTowardsPlayer() {
        Player player = game.getShip();
        if (player!= null) {
            int targetX = player.getX();
            int targetY = player.getY();

            // Calculate the direction vector towards the player
            double dx = targetX - x;
            double dy = targetY - y;
            double distance = Math.sqrt(dx * dx + dy * dy);

            // Normalized direction vector (if distance is not 0)
            if (distance > 0) {
                dx /= distance;
                dy /= distance;
            }

            //Set the moving speed
            moveX = dx * 2.5;
            moveY = dy * 2.5;
        }
    }



    private void checkBounds() {
        if (x < 0 - 300) {
            x = 0 - 300;
            moveX = Math.abs(moveX);
        }
        if (x > 768 + 300 - SHIP_SCALE * type.getWidth()) {
            x = 768 + 300 - SHIP_SCALE * type.getWidth();
            moveX = -Math.abs(moveX);
        }
        if (y < 0) {
            moveY = Math.abs(moveY);
        }
        if (y > 490 - SHIP_SCALE * type.getHeight()) {
            y = 490 - SHIP_SCALE * type.getHeight();
            moveY = -Math.abs(moveY);
        }
    }


    public boolean isHit(Bullet b) {
        if (b.bulletName() == "Player") {
            boolean hit = getHitBox().intersects(b.getHitBox());
            if (hit) {
                alive = false;
            }
            return hit;
        } else {
            // If the bullet is not from an enemy ship, it is not processed
            return false;
        }
    }

    @Override
    public Rectangle getHitBox() {
        return new Rectangle((int) x, (int) y, SHIP_SCALE * type.getWidth(), SHIP_SCALE * type.getHeight());
    }

    public boolean isAlive() {
        return alive;
    }

    public int getPoints() {
        return type.getScore();
    }

    public boolean isPlayer() {
        return false;
    }


    public Bullet fire() {
        Bullet bul = null;
        if (rand.nextInt() % 150 == 0) {
            int a = ((int) x + (type.getWidth() * SHIP_SCALE) / 2);
            int b = (int) y + (SHIP_SCALE * type.getHeight());

            final Player player = game.getShip();

            final int dx = player.getX() - (int)x;
            final int dy = player.getY() - (int)y;

            final double norm = Math.sqrt((double) (dx*dx + dy*dy));

            bul = new Bullet(a, b, (int)(3*dx/norm), (int)(3*dy/norm),false, name);
            bul.setOwner(this);
        }
        return bul;
    }

    public DefenderGame getGame() {
        return game;
    }

    public int getX() {
        final int n = game.getScreenWidth()/2 - game.getShip().getX();
        return (int) x+n;
    }

    public int getY() {
        return (int) y;
    }

    public AlienType getType() {
        return type;
    }

    public void setType(AlienType type) {
        this.type = type;
    }
}
