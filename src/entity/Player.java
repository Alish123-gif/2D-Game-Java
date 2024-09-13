package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {

    GamePanel gamePanel;
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;

    BufferedImage[] idleDown;
    BufferedImage[] idleRight;
    BufferedImage[] idleLeft;
    BufferedImage[] idleUp;

    BufferedImage[] movingDown;
    BufferedImage[] movingRight;
    BufferedImage[] movingLeft;
    BufferedImage[] movingUp;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {

        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
        screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);

        solidArea = new Rectangle(8, 16, 28, 28);

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gamePanel.tileSize * 17;
        worldY = gamePanel.tileSize * 23;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            // Load idle images
            idleDown = new BufferedImage[6];
            idleRight = new BufferedImage[6];
            idleLeft = new BufferedImage[6];
            idleUp = new BufferedImage[6];

            for (int i = 0; i < 6; i++) {
                idleDown[i] = ImageIO
                        .read(getClass().getResourceAsStream("/player/idle/boy_idle_down_" + i + ".png"));
                idleRight[i] = ImageIO
                        .read(getClass().getResourceAsStream("/player/idle_right/boy_idle_right_" + i + ".png"));
                idleLeft[i] = ImageIO
                        .read(getClass().getResourceAsStream("/player/idle_left/boy_idle_left_" + i + ".png"));
                idleUp[i] = ImageIO
                        .read(getClass().getResourceAsStream("/player/idle_up/boy_idle_up_" + i + ".png"));
            }

            // Load moving images
            movingDown = new BufferedImage[6];
            movingRight = new BufferedImage[6];
            movingLeft = new BufferedImage[6];
            movingUp = new BufferedImage[6];

            for (int i = 0; i < 6; i++) {
                movingDown[i] = ImageIO
                        .read(getClass().getResourceAsStream("/player/moving_down/boy_moving_down_" + i + ".png"));
                movingRight[i] = ImageIO
                        .read(getClass().getResourceAsStream("/player/moving_right/boy_moving_right_" + i + ".png"));
                movingLeft[i] = ImageIO
                        .read(getClass().getResourceAsStream("/player/moving_left/boy_moving_left_" + i + ".png"));
                movingUp[i] = ImageIO
                        .read(getClass().getResourceAsStream("/player/moving_up/boy_moving_up_" + i + ".png"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        boolean moving = false;

        if (keyHandler.up) {
            direction = "up";
            moving = true;
        }
        if (keyHandler.down) {
            direction = "down";
            moving = true;
        }
        if (keyHandler.left) {
            direction = "left";
            moving = true;
        }
        if (keyHandler.right) {
            direction = "right";
            moving = true;
        }

        // Check collision
        collisionOn = false;
        gamePanel.cDetection.checkTile(this);

        if (!collisionOn && moving) {
            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;

                    break;
                case "left":
                    worldX -= speed;

                    break;
                case "right":
                    worldX += speed;

                    break;
                default:
                    break;
            }
        }

        if (moving) {
            spriteCounter++;
            if (spriteCounter > 10) {
                spriteCounter = 0;
                spriteNum++;
                if (spriteNum >= movingDown.length) {
                    spriteNum = 0;
                }
            }
        } else {
            // Handle idle animation
            spriteCounter++;
            if (spriteCounter > 10) {
                spriteCounter = 0;
                spriteNum++;
                if (spriteNum >= idleDown.length) {
                    spriteNum = 0;
                }
            }
        }
    }

    public void draw(Graphics2D g) {
        BufferedImage img = null;

        if (keyHandler.up || keyHandler.down || keyHandler.left || keyHandler.right) {
            switch (direction) {
                case "up":
                    img = movingUp[spriteNum];
                    break;
                case "down":
                    img = movingDown[spriteNum];
                    break;
                case "left":
                    img = movingLeft[spriteNum];
                    break;
                case "right":
                    img = movingRight[spriteNum];
                    break;
                default:
                    img = movingDown[spriteNum];
                    break;
            }
        } else {
            // Draw idle animation based on the last direction
            switch (direction) {
                case "down":
                    img = idleDown[spriteNum];
                    break;
                case "right":
                    img = idleRight[spriteNum];
                    break;
                case "left":
                    img = idleLeft[spriteNum];
                    break;
                case "up":
                    img = idleUp[spriteNum];
                    break;
                default:
                    img = idleDown[spriteNum];
                    break;
            }
        }

        g.drawImage(img, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}