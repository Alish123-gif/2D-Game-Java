package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
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

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        screenX = gamePanel.screenWidth / 2 - gamePanel.tileSize / 2;
        screenY = gamePanel.screenHeight / 2 - gamePanel.tileSize / 2;
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
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));

            // Load idle images
            idleDown = new BufferedImage[6];
            idleRight = new BufferedImage[6];
            idleLeft = new BufferedImage[6];
            idleUp = new BufferedImage[6];

            for (int i = 0; i < 6; i++) {
                idleDown[i] = ImageIO
                        .read(getClass().getResourceAsStream("/player/idle/boy_idle_down_" + (i) + ".png"));
                idleRight[i] = ImageIO
                        .read(getClass().getResourceAsStream("/player/idle_right/boy_idle_right_" + (i) + ".png"));
                idleLeft[i] = ImageIO
                        .read(getClass().getResourceAsStream("/player/idle_left/boy_idle_left_" + (i) + ".png"));
                idleUp[i] = ImageIO
                        .read(getClass().getResourceAsStream("/player/idle_up/boy_idle_up_" + (i) + ".png"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        boolean moving = false;

        if (keyHandler.up) {
            direction = "up";
            worldY -= speed;
            moving = true;
        }
        if (keyHandler.down) {
            direction = "down";
            worldY += speed;
            moving = true;
        }
        if (keyHandler.left) {
            direction = "left";
            worldX -= speed;
            moving = true;
        }
        if (keyHandler.right) {
            direction = "right";
            worldX += speed;
            moving = true;
        }

        if (moving) {
            spriteCounter++;
            if (spriteCounter > 10) {
                spriteCounter = 0;
                spriteNum = spriteNum == 1 ? 2 : 1;
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
                    img = spriteNum == 1 ? up1 : up2;
                    break;
                case "down":
                    img = spriteNum == 1 ? down1 : down2;
                    break;
                case "left":
                    img = spriteNum == 1 ? left1 : left2;
                    break;
                case "right":
                    img = spriteNum == 1 ? right1 : right2;
                    break;
                default:
                    img = down1;
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