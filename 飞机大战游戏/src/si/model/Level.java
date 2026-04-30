package si.model;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private Swarm swarm;
    private double startingSpeed;
    private int rows;
    private int cols;
    private DefenderGame game;
    private Human human;

    public Level(double ss, int row, int col, DefenderGame g) {
        game = g;
        startingSpeed = ss;
        rows = row;
        cols = col;
        reset();
    }

    public int getShipsRemaining() {
        return swarm.getShipsRemaining();
    }



    public List<Hittable> getHittable() {
        List<Hittable> targets = new ArrayList<Hittable>();
        targets.addAll(swarm.getHittable());
        return targets;
    }

    public List<Bullet> move() {
        swarm.move();
        List<EnemyShip> ships = swarm.getBottom();
        List<Bullet> eBullets = new ArrayList<Bullet>();
        for (EnemyShip s : ships) {
            Bullet b = s.fire();
            if (b != null) {
                eBullets.add(b);
            }
        }
        return eBullets;
    }
    public List<EnemyShip> getEnemyShips() {
        return swarm.getEnemyShips();
    }


    public void reset() {
        swarm = new Swarm(rows, cols, startingSpeed, 1, game);
    }
}

