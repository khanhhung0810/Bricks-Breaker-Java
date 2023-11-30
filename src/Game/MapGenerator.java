package Game;

import java.awt.*;

public class MapGenerator {
    public int map [][];
    public int brickWidth;
    public int brickHeight;

    // this creates the brick of size 3x7
    public MapGenerator(int row, int col) {
        map = new int [row][col];
        for (int i = 0; i < map.length; i++) {
            for (int j=0; j< map[0].length;j++) {
                map[i][j] = 1;
            }
        }

        brickWidth = 540/col;
        brickHeight = 150/row;
    }
    // new method to set the level
    public void setLevel(int level) {
        if (level == 1) {
            // Level 1 configuration
            map = new int[][]{{1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1}};
        } else if (level == 2) {
            // Level 2 configuration
            map = new int[][]{{1, 0, 1, 0, 1, 0, 1},
                    {1, 0, 1, 0, 1, 0, 1},
                    {1, 1, 1, 1, 1, 1, 1}};
        } else if (level == 3) {
            // Level 3 configuration
            map = new int[][]{{1, 1, 0, 1, 1, 0, 1},
                    {1, 1, 0, 1, 1, 0, 1},
                    {1, 1, 1, 1, 1, 1, 1}};
        }

        // You can add more levels as needed
        brickWidth = 540 / map[0].length;
        brickHeight = 150 / map.length;
    }

    // this draws the bricks
    public void draw(Graphics2D g) {
        for (int i = 0; i < map.length; i++) {
            for (int j=0; j< map[0].length;j++) {
                if(map[i][j] > 0) {
                    g.setColor(new Color(0XFF8787)); // brick color
                    g.fillRect(j*brickWidth + 80, i*brickHeight + 50, brickWidth, brickHeight);

                    g.setStroke(new BasicStroke(4));
                    g.setColor(Color.BLACK);
                    g.drawRect(j*brickWidth + 80, i*brickHeight + 50, brickWidth, brickHeight);
                }
            }

        }
    }

    // this sets the value of brick to 0 if it is hit by the ball
    public void setBrickValue(int value, int row, int col) {
        map[row][col] = value;
    }

}