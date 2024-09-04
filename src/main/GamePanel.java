package main;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable {

    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48 pixels
    public final int maxScreenColumns = 20;
    public final int maxScreenRows = 13;

    final int screenWidth = tileSize * maxScreenColumns; // 960 pixels
    final int screenHeight = tileSize * maxScreenRows; // 720 pixels

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this, keyHandler);
    TileManager tileManager = new TileManager(this);

    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    int FPS = 60;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double nsPerFrame = 1000000000.0 / FPS;

        while (gameThread != null) {
            long now = System.nanoTime();

            try {
                Thread.sleep((long) (nsPerFrame / 1000000.0));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Game loop
            update(); // Update game state
            repaint(); // Repaint game state

            // Calculate actual FPS
            long frameTime = System.nanoTime() - now;
            int actualFPS = 1000000000 / (int) frameTime;
            // System.out.println("Actual FPS: " + actualFPS);
        }
    }

    public void update() {
        player.update();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tileManager.draw(g2);
        player.draw(g2);

        g2.dispose();
    }
}