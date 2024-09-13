package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import main.GamePanel;

public class TileManager {

    GamePanel gamePanel;
    public Tile[] tiles;
    public int mapTileNum[][];

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tiles = new Tile[10];

        mapTileNum = new int[gamePanel.maxWorldColumns][gamePanel.maxWorldRows];

        getTileImage();
        loadMap("/maps/mapEx1.txt");
    }

    public void getTileImage() {
        try {
            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));

            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
            tiles[1].collidable = true;

            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));

            tiles[3] = new Tile();
            tiles[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
            tiles[3].collidable = true;

            tiles[4] = new Tile();
            tiles[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));

            tiles[5] = new Tile();
            tiles[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
            tiles[5].collidable = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {
        try {
            InputStream in = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            int row = 0;
            int col = 0;

            while (row < gamePanel.maxWorldRows) {
                String line = br.readLine();
                String[] tokens = line.split(" ");

                while (col < gamePanel.maxWorldColumns) {
                    int num = Integer.parseInt(tokens[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gamePanel.maxWorldColumns) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void draw(Graphics2D g) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gamePanel.maxWorldColumns && worldRow < gamePanel.maxWorldRows) {
            int num = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gamePanel.tileSize;
            int worldY = worldRow * gamePanel.tileSize;

            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

            int extraTiles = 1 * gamePanel.tileSize;

            if (worldX + extraTiles >= gamePanel.player.worldX - gamePanel.player.screenX
                    && worldX - extraTiles <= gamePanel.player.worldX + gamePanel.player.screenX
                    && worldY + extraTiles >= gamePanel.player.worldY - gamePanel.player.screenY
                    && worldY - extraTiles <= gamePanel.player.worldY + gamePanel.player.screenY) {

                g.drawImage(tiles[num].image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
            }
            worldCol++;

            if (worldCol == gamePanel.maxWorldColumns) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}