package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {

    GamePanel gamePanel;
    Tile[] tiles;
    int mapTileNum[][];

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tiles = new Tile[10];
        mapTileNum = new int[gamePanel.maxScreenColumns][gamePanel.maxScreenRows];
        getTileImage();
        loadMap("/maps/map01.txt");
    }

    public void getTileImage() {
        try {
            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));

            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));

            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));

            tiles[3] = new Tile();
            tiles[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
        } catch (IOException e) {

        }
    }

    public void loadMap(String filePath) {
        try {
            InputStream in = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            int row = 0;
            int col = 0;

            while (row < gamePanel.maxScreenRows && col < gamePanel.maxScreenColumns) {
                String line = br.readLine();

                while (col < gamePanel.maxScreenColumns) {
                    String[] tokens = line.split(" ");
                    int num = Integer.parseInt(tokens[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gamePanel.maxScreenColumns) {
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
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col < gamePanel.maxScreenColumns && row < gamePanel.maxScreenRows) {
            int num = mapTileNum[col][row];

            g.drawImage(tiles[num].image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
            col++;
            x += gamePanel.tileSize;

            if (col == gamePanel.maxScreenColumns) {
                col = 0;
                row++;
                x = 0;
                y += gamePanel.tileSize;
            }
        }
    }
}
