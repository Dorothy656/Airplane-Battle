package si.display;

import si.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameScreen extends JPanel {
    private static final long serialVersionUID = -8282302849760730222L;
    private DefenderGame game;

    public GameScreen(DefenderGame game){
        this.game = game;
    }

    private void drawPlayerShip(Graphics2D gc, Player p) {
        int x = p.getGame().getScreenWidth()/2;
        int y = p.getY();
        int[] x_coords = new int[]{0, 2, 2, 3, 3, 4, 4, 5, 5, 7, 7, 0, 0};
        int[] y_coords = new int[]{2, 2, 1, 1, 0, 0, 1, 1, 2, 2, 4, 4, 2};
        int[] x_adjusted = new int[x_coords.length];
        int[] y_adjusted = new int[y_coords.length];
        for (int i = 0; i < x_coords.length; i++) {
            x_adjusted[i] = x + x_coords[i] * Player.SHIP_SCALE;
            y_adjusted[i] = y + y_coords[i] * Player.SHIP_SCALE;
        }
        Polygon pg = new Polygon(x_adjusted, y_adjusted, x_adjusted.length);
        gc.setColor(Color.GREEN);
        gc.fillPolygon(pg);
    }

    private void drawBullet(Graphics2D gc, Bullet b) {
        gc.setColor(Color.GREEN);
        gc.fillRect(b.getX(), b.getY(), b.BULLET_WIDTH, b.BULLET_HEIGHT);
    }

    private void drawHumans(Graphics2D gc) {
        int humanWidth = 15;
        int humanHeight = 50;
        gc.setColor(Color.YELLOW);
        int currentLevel = game.getCurrentLevel();
        int numHumans = Math.max(1, 6 - currentLevel);
        List<Human> humans = game.getHumans();  // Get a living Human object
        for (int i = 0; i < numHumans; i++) {
            Human human = humans.get(i);
            if (human.isAlive()) {
                // Draw a man (simple rectangular shape)
                gc.fillRect(human.getNewX(), human.getY(), humanWidth, humanHeight);
            }
        }
    }



    private void drawEnemeyShip(Graphics2D gc, EnemyShip es) {
        if (es.getType() == AlienType.A) {
            drawEnemyA(gc, es);
        } else if (es.getType() == AlienType.B) {
            drawEnemyB(gc, es);
        } else {
            drawEnemyC(gc, es);
        }
    }
    //A类飞机是绿色的, 打死得到20分, 不会发出子弹
    private void drawEnemyA(Graphics2D gc, EnemyShip es) {
        int x = es.getX();
        int y = es.getY();
        int[] x_coords = new int[]{0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 8, 8, 7, 7, 6, 6, 5, 5, 4, 4, 3, 3, 2, 2, 1, 1, 0};
        int[] y_coords = new int[]{7, 4, 4, 3, 3, 2, 2, 1, 1, 2, 2, 1, 1, 2, 2, 3, 3, 4, 4, 7, 7, 5, 5, 7, 7, 6, 6, 7, 7, 6, 6, 7, 7, 5, 5, 7, 7};
        int[] x_adjusted = new int[x_coords.length];
        int[] y_adjusted = new int[y_coords.length];
        for (int i = 0; i < x_coords.length; i++) {
            x_adjusted[i] = x + x_coords[i] * EnemyShip.SHIP_SCALE;
            y_adjusted[i] = y + y_coords[i] * EnemyShip.SHIP_SCALE;
        }
        gc.setColor(Color.pink);
        gc.fillPolygon(x_adjusted, y_adjusted, x_adjusted.length);
        gc.fillRect(x + 2 * EnemyShip.SHIP_SCALE, y + EnemyShip.SHIP_SCALE * 0, EnemyShip.SHIP_SCALE, EnemyShip.SHIP_SCALE);
        gc.fillRect(x + 6 * EnemyShip.SHIP_SCALE, y + EnemyShip.SHIP_SCALE * 0, EnemyShip.SHIP_SCALE, EnemyShip.SHIP_SCALE);

        // creating eye holes
        gc.setColor(Color.BLACK);
        gc.fillRect(x + 3 * EnemyShip.SHIP_SCALE, y + EnemyShip.SHIP_SCALE * 3, EnemyShip.SHIP_SCALE, EnemyShip.SHIP_SCALE);
        gc.fillRect(x + 5 * EnemyShip.SHIP_SCALE, y + EnemyShip.SHIP_SCALE * 3, EnemyShip.SHIP_SCALE, EnemyShip.SHIP_SCALE);
    }

    private void drawEnemyB(Graphics2D gc, EnemyShip es) {
        int x = es.getX();
        int y = es.getY();

        // 画粉色的 Enemy A 飞船形状
        int[] x_coords = new int[]{0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 8, 8, 7, 7, 6, 6, 5, 5, 4, 4, 3, 3, 2, 2, 1, 1, 0};
        int[] y_coords = new int[]{7, 4, 4, 3, 3, 2, 2, 1, 1, 2, 2, 1, 1, 2, 2, 3, 3, 4, 4, 7, 7, 5, 5, 7, 7, 6, 6, 7, 7, 6, 6, 7, 7, 5, 5, 7, 7};
        int[] x_adjusted = new int[x_coords.length];
        int[] y_adjusted = new int[y_coords.length];
        for (int i = 0; i < x_coords.length; i++) {
            x_adjusted[i] = x + x_coords[i] * EnemyShip.SHIP_SCALE;
            y_adjusted[i] = y + y_coords[i] * EnemyShip.SHIP_SCALE;
        }
        gc.setColor(Color.PINK); // 设置颜色为粉色
        gc.fillPolygon(x_adjusted, y_adjusted, x_adjusted.length);

        // 画敌舰下方的黄色长方形
        gc.setColor(Color.YELLOW); // 设置颜色为黄色
        int rectangleWidth = 15 ; // 设置黄色长方形的宽度
        int rectangleHeight = 50; // 设置黄色长方形的高度
        gc.fillRect(x + 1 * EnemyShip.SHIP_SCALE, y + 7 * EnemyShip.SHIP_SCALE, rectangleWidth, rectangleHeight); // 在敌舰下方绘制黄色长方形

        gc.setColor(Color.BLACK); // 设置颜色为黑色
        gc.fillRect(x + 3 * EnemyShip.SHIP_SCALE, y + 3 * EnemyShip.SHIP_SCALE, EnemyShip.SHIP_SCALE, EnemyShip.SHIP_SCALE);
        gc.fillRect(x + 5 * EnemyShip.SHIP_SCALE, y + 3 * EnemyShip.SHIP_SCALE, EnemyShip.SHIP_SCALE, EnemyShip.SHIP_SCALE);
    }


    private void drawEnemyC(Graphics2D gc, EnemyShip es) {
        int x = es.getX();
        int y = es.getY();
        int[] x_coords = new int[]{3, 7, 7, 9, 9, 10, 10, 8, 8, 9, 9, 10, 10, 8, 8, 7, 7, 6, 6, 4, 4, 3, 3, 2, 2, 0, 0, 1, 1, 2, 2, 0, 0, 1, 1, 3, 3};
        int[] y_coords = new int[]{0, 0, 1, 1, 2, 2, 5, 5, 6, 6, 7, 7, 8, 8, 7, 7, 6, 6, 7, 7, 6, 6, 7, 7, 8, 8, 7, 7, 6, 6, 5, 5, 2, 2, 1, 1, 0};
        int[] x_adjusted = new int[x_coords.length];
        int[] y_adjusted = new int[y_coords.length];
        for (int i = 0; i < x_coords.length; i++) {
            x_adjusted[i] = x + x_coords[i] * EnemyShip.SHIP_SCALE;
            y_adjusted[i] = y + y_coords[i] * EnemyShip.SHIP_SCALE;
        }
        gc.setColor(Color.ORANGE);
        gc.fillPolygon(x_adjusted, y_adjusted, x_adjusted.length);

        // creating holes
        gc.setColor(Color.BLACK);
        gc.fillRect(x + 2 * EnemyShip.SHIP_SCALE, y + EnemyShip.SHIP_SCALE * 2, EnemyShip.SHIP_SCALE * 2, EnemyShip.SHIP_SCALE * 1);
        gc.fillRect(x + 6 * EnemyShip.SHIP_SCALE, y + EnemyShip.SHIP_SCALE * 2, EnemyShip.SHIP_SCALE * 2, EnemyShip.SHIP_SCALE * 1);
        gc.fillRect(x + 4 * EnemyShip.SHIP_SCALE, y + EnemyShip.SHIP_SCALE * 5, EnemyShip.SHIP_SCALE * 2, EnemyShip.SHIP_SCALE * 1);
    }

    protected void paintComponent(Graphics g) {
        if (game != null) {
            Graphics2D g2 = (Graphics2D) g;
            g.setColor(Color.black);
            g.fillRect(0, 0, DefenderGame.SCREEN_WIDTH, DefenderGame.SCREEN_HEIGHT);
            g.setColor(Color.white);
            g.drawString("Lives: " + game.getLives(), 0, 20);
            g.drawString("Score: " + game.getPlayerScore(), DefenderGame.SCREEN_WIDTH / 2, 20);

            drawPlayerShip(g2,game.getShip() );

            for (Bullet bullet : game.getBullets()) {
                drawBullet(g2, bullet);
            }
            for (EnemyShip e: game.getEnemyShips()){
                drawEnemeyShip(g2,e);
            }
            drawHumans(g2);
            if (game.isPaused() && !game.isGameOver()) {
                g.setColor(Color.white);
                g.drawString("Press 'p' to continue ", 256, 256);
            } else if (game.isGameOver()) {
                g.setColor(Color.white);
                g.drawString("Game over ", 480, 256);
            }
        }
    }
}
