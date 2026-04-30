package si.model;

import si.display.PlayerListener;
import ucd.comp2011j.engine.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DefenderGame implements Game {
    public static final int SCREEN_WIDTH = 768;
    public static final int SCREEN_HEIGHT = 512;
    private static final int NO_LEVELS = 5;
    private static final Rectangle SCREEN_BOUNDS = new Rectangle(0, 0, SCREEN_WIDTH+-300, SCREEN_HEIGHT);

    private int playerLives;
    private int playerScore;
    private boolean pause = true;
    private List<Bullet> playerBullets;
    private List<Bullet> enemyBullets;
    private ArrayList<Hittable> targets;
    private PlayerListener listener;
    private Player player;
    private Level[] level;
    private int currentLevel = 0;
    private List<Human> humans;


    public DefenderGame(PlayerListener listener) {
        this.listener = listener;
        startNewGame();
    }

    @Override
    public int getPlayerScore() {
        return playerScore;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public int getLives() {
        return playerLives;
    }

    public void checkForPause() {
        if (listener.hasPressedPause()) {
            pause = !pause;
            listener.resetPause();
        }
    }

    @Override
    public void updateGame() {
        if (!isPaused()) {
            player.tick();
            targets.clear();
            targets.addAll(level[currentLevel].getHittable());
            targets.add(player);
            playerBullets();
            enemyBullets();
            enemyBullets.addAll(level[currentLevel].move());
            movePlayer();
            checkHumanCollisions();
        }
    }

    private void movePlayer() {
        if (listener.hasPressedFire()) {
            Bullet b = player.fire();
            if (b != null) {
                playerBullets.add(b);
                listener.resetFire();
            }
        }
        if (listener.isPressingLeft()) {
            player.move(-6, 0);
            player.setFacing(-1);
        }
        if (listener.isPressingRight()) {
            player.move(+6, 0);
            player.setFacing(1);
        }
        if (listener.isPressingUp()) {
            player.move(0, -6); // 向上移动
        }
        if (listener.isPressingDown()) {
            player.move(0, 6); // 向下移动
        }

    }

    private void playerBullets() {
        List<Bullet> remove = new ArrayList<Bullet>();
        for (int i = 0; i < playerBullets.size(); i++) {
            if (playerBullets.get(i).isAlive() && playerBullets.get(i).getHitBox().intersects(SCREEN_BOUNDS)
                    || playerBullets.get(i).getHitBox().getX() > SCREEN_WIDTH -300 || playerBullets.get(i).getHitBox().getX() < SCREEN_WIDTH + 300 ) {
                playerBullets.get(i).move();
                for (Hittable t : targets) {
                    if (t != playerBullets.get(i)) {
                        if (t.isHit(playerBullets.get(i))) {
                            playerScore += t.getPoints();
                            playerBullets.get(i).destroy();
                        }
                    }
                }
            } else {
                remove.add(playerBullets.get(i));
            }
        }
        playerBullets.removeAll(remove);
    }

    private void enemyBullets() {
        List<Bullet> remove = new ArrayList<Bullet>();
        for (int i = 0; i < enemyBullets.size(); i++) {
            Bullet b = enemyBullets.get(i);
            if (b.isAlive() && b.getHitBox().intersects(SCREEN_BOUNDS)||
                    b.getHitBox().getX() >SCREEN_WIDTH -300 || b.getHitBox().getX() < SCREEN_WIDTH + 300) {
                b.move();
                for (Hittable t : targets) {
                    if (t != b) {
                        if (t.isHit(b)) {
                            if (t.isPlayer()) {
                                playerLives--;
                                pause = true;
                            }
                            b.destroy();
                        }
                    }
                }
            } else {
                remove.add(b);
            }
        }
        enemyBullets.removeAll(remove);
    }


    @Override
    public boolean isPaused() {
        return pause;
    }

    @Override
    public void startNewGame() {
        targets = new ArrayList<Hittable>();
        playerLives = 3;
        playerScore = 0;
        playerBullets = new ArrayList<Bullet>();
        enemyBullets = new ArrayList<Bullet>();
        player = new Player(this);
        humans = new ArrayList<Human>();
        level = new Level[5];
        level[0] = new Level(0.5, 1, 5, this);
        level[1] = new Level(1, 2, 5, this);
        level[2] = new Level(1.5, 3, 5, this);
        level[3] = new Level(2, 4, 5, this);
        level[4] = new Level(2.5, 5, 5, this);
        for (int i = 0; i < 6; i++) {
            humans.add(new Human(-200 + (i * 200), SCREEN_HEIGHT - 50 - 10 , this)); // 初始化位置
        }
    }

    @Override
    public boolean isLevelFinished() {
        if (currentLevel < NO_LEVELS) {
            int noShips = level[currentLevel].getShipsRemaining();
            return noShips == 0;
        } else {
            return true;
        }
    }
    @Override
    public boolean isPlayerAlive() {
        return player.isAlive();
    }

    @Override
    public void resetDestroyedPlayer() {
        player.resetDestroyed();
        playerBullets = new ArrayList<Bullet>();
        enemyBullets = new ArrayList<Bullet>();
    }

    @Override
    public void moveToNextLevel() {
        pause = true;
        if (currentLevel < NO_LEVELS - 1) {
            currentLevel++;
        }
        player.resetDestroyed();
        playerBullets = new ArrayList<Bullet>();
        enemyBullets = new ArrayList<Bullet>();

        if (humans.size() > 0) {
            humans.remove(humans.size() - 1);
            for (Human human : humans) {
                human.setCaptured(false); // 复活 human
                human.setAlive(true);// 复活 human
            }
        }
    }

    @Override
    public boolean isGameOver() {
        return !(playerLives > 0 && currentLevel < NO_LEVELS);
    }

    @Override
    public int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    @Override
    public int getScreenHeight() {
        return SCREEN_HEIGHT;
    }


    public Player getShip() {
        return player;
    }


    public List<Bullet> getBullets() {
        ArrayList<Bullet> bullets = new ArrayList<Bullet>();
        bullets.addAll(playerBullets);
        bullets.addAll(enemyBullets);
        return bullets;
    }

    public List<EnemyShip> getEnemyShips() {
        return level[currentLevel].getEnemyShips();
    }


    public List<Human> getHumans() {
        return humans;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }


    private void checkHumanCollisions() {

        for (Human human : humans) {
            if (!human.isAlive()) {
                continue;  // If the Human has already been captured, its collision with an enemy ship is no longer detected
            }
            for (EnemyShip enemy : level[currentLevel].getEnemyShips()) {
                if (human.checkCollisionWithEnemy(enemy) && enemy.getType() == AlienType.A) {
                    enemy.setType(AlienType.B);
                    playerScore -= 150;
                    human.setCaptured(true);// Set Human to the captured state


                }
            }
        }
    }
}