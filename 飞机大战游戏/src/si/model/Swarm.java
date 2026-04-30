package si.model;


import java.util.ArrayList;
import java.util.List;

public class Swarm implements Movable {
    private List<EnemyShip> ships;
    private boolean direction = true; // true for moving right false for left
    private double x = 50, y = 40;
    private int space = 30;
    private EnemyShip[][] shipGrid;
    private int rows, cols;
    private int count = 0;
    private double moveX;
    private double moveY;
    private DefenderGame game;
    private Human human;

    @Override
    public int getX() {
        return 0;
    }

    public  int getY(){
        return  0;
    }

    public Swarm(int r, int c, double sX, double sY, DefenderGame g) {
        game = g;
        rows = r;
        cols = c;
        moveX = sX;
        moveY = sY;
        shipGrid = new EnemyShip[r][c];
        ships = new ArrayList<EnemyShip>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                EnemyShip a;
                int randomOffsetX = (int) (Math.random() * 100);  // 添加随机的偏移量
                int randomOffsetY = (int) (Math.random() * 100);
                if (i % 5 == 0) {
                    a = new EnemyShip((int) x + (1 + space) * j, (int) y + i * space, AlienType.A ,g);
                } else if (i % 5 == 1 || i % 5 == 2) {
                    a = new EnemyShip((int) x + (1 + space) * j, (int) y + i * space, AlienType.A , g);
                } else {
                    a = new EnemyShip((int) x + (1 + space) * j, (int) y + i * space, AlienType.A , g);
                }
                ships.add(a);
                shipGrid[i][j] = a;
            }
        }
    }

    public void move() {
        List<EnemyShip> remove = new ArrayList<EnemyShip>();
        for (EnemyShip s : ships) {
            if (!s.isAlive()) {
                remove.add(s);
            }else {
                s.move();  // Invoke an independent movement method for each enemy ship
                s.fire();  // Call the fire method to generate bullets
            }
        }
        ships.removeAll(remove);
        for (EnemyShip ship : ships) {
            if (ship.getY() <= 0 && ship.getType() == AlienType.B) {
                ship.setType(AlienType.C);
            }
        }
    }


    public List<Hittable> getHittable() {
        return new ArrayList<Hittable>(ships);
    }

    public List<EnemyShip> getBottom() {
        List<EnemyShip> bottomShips = new ArrayList<EnemyShip>();

        for (int i = 0; i < cols; i++) {
            boolean found = false;
            for (int j = rows - 1; j >= 0 && !found; j--) {
                if (shipGrid[j][i].isAlive()) {
                    found = true;
                    bottomShips.add(shipGrid[j][i]);
                }
            }
        }
        return bottomShips;
    }

    public List<EnemyShip> getEnemyShips() {
        return new ArrayList<EnemyShip>(ships);
    }

    public int getShipsRemaining() {
        return ships.size();
    }

}
