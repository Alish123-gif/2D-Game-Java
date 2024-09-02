package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {

    GamePanel gamePanel;
    KeyHandler keyHandler;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyHandler.up) {
            direction = "up";
            y -= speed;
        }
        if (keyHandler.down) {
            y += speed;
            direction = "down";
        }
        if (keyHandler.left) {
            x -= speed;
            direction = "left";
        }
        if (keyHandler.right) {
            x += speed;
            direction = "right";
        }
        if (keyHandler.moving) {
            spriteCounter++;
            if (spriteCounter > 12) {
                spriteCounter = 0;
                spriteNum = spriteNum == 1 ? 2 : 1;
            }
        }

    }

    public void draw(Graphics2D g) {

        BufferedImage img = null;

        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    img = up1;

                } else if (spriteNum == 2) {
                    img = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    img = down1;
                } else if (spriteNum == 2) {
                    img = down2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    img = left1;
                } else if (spriteNum == 2) {
                    img = left2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    img = right1;
                } else if (spriteNum == 2) {
                    img = right2;
                }
                break;
            default:
                img = down1;
                break;
        }
        g.drawImage(img, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}
