package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePlay extends JPanel implements KeyListener, ActionListener {
    private boolean play = true;
    private int score = 0;
    private int currentLevel = 1;
    private int totalBricks = 21;

    private Timer timer;
    private int delay = 5;

    private int playerX = 310;

    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -2;
    private int ballYdir = -4;
    private MapGenerator map;


    public GamePlay() {
        map = new MapGenerator(3, 7);
        map.setLevel(currentLevel);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {

        //background color
        g.setColor(new Color(0xA2FF64));
        g.fillRect(1, 1, 692, 592);

        map.draw((Graphics2D)g);

        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        g.setColor(Color.blue);
        g.fillRect(playerX, 550, 200, 12);

        g.setColor(Color.RED);  // ball color
        g.fillOval(ballposX, ballposY, 20, 20);

        g.setColor(Color.black);
        g.setFont(new Font("MV Arial", Font.BOLD, 25));
        g.drawString("Điểm: " + score, 520, 30);


        if (totalBricks <= 0) {
            // Level completed, move to the next level
            currentLevel++;
            System.out.println("tăng level");
            if (currentLevel < 4) {  // You can add more levels if needed
                map.setLevel(currentLevel);
                totalBricks = map.getTotalBrick();
                System.out.println(totalBricks);
                ballposX = 120;
                ballposY = 350;
                ballXdir = -2;
                ballYdir = -4;
            } else {
                // All levels completed, you win
                play = false;
                ballXdir = 0;
                ballYdir = 0;
                g.setColor(Color.BLACK);
                g.setFont(new Font("MV Arial", Font.BOLD, 30));
                g.drawString("Chiến Thắng, Số điểm: " + score, 190, 300);
                g.setFont(new Font("MV Arial", Font.BOLD, 20));
                g.drawString("Nhấn Enter để chơi lại", 230, 350);
            }
        }
        if(ballposY > 570) { // if ball goes below the paddle then you lose
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.BLACK);
            g.setFont(new Font("MV Arial", Font.BOLD, 30));
            g.drawString("Bạn đã thua, Số điểm: " + score, 190, 300);

            g.setFont(new Font("MV Arial", Font.BOLD, 20));
            g.drawString("Nhấn Enter để chơi lại", 230, 350);

        }
        g.dispose();

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        timer.start();
        if(play) {
            // Ball - Pedal  interaction
            if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 200, 8))) {
                ballYdir = - ballYdir;

            }

            for( int i = 0; i<map.map.length; i++) { // Ball - Brick interaction
                for(int j = 0; j<map.map[0].length; j++) {  // map.map[0].length is the number of columns
                    if(map.map[i][j] > 0) {
                        int brickX = j*map.brickWidth + 80;
                        int brickY = i*map.brickHeight + 50;
                        int brickWidth= map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20,20);
                        Rectangle brickRect = rect;

                        if(ballRect.intersects(brickRect) ) {
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            System.out.println(totalBricks);
                            score+=5;

                            if((ballXdir > 0 && ballposX + 20 - ballXdir <= brickRect.x) ||
                                    (ballXdir < 0 && ballposX + ballXdir >= brickRect.x + brickRect.width)) {
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }
                        }

                    }

                }
            }


            ballposX += ballXdir;
            ballposY += ballYdir;
            if(ballposX < 0) { // if ball hits the left wall then it bounces back
                ballXdir = -ballXdir;
            }
            if(ballposY < 0) {  // if ball hits the top wall then it bounces back
                ballYdir = -ballYdir;
            }
            if(ballposX > 670) { // if ball hits the right wall then it bounces back
                ballXdir = -ballXdir;

            }

        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent arg0) {

    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        if(arg0.getKeyCode() == KeyEvent.VK_RIGHT) { // if right arrow key is pressed then paddle moves right
            if(playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();

            }
        }
        if(arg0.getKeyCode() == KeyEvent.VK_LEFT) { // if left arrow key is pressed then paddle moves left
            if(playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();

            }
        }

        if(arg0.getKeyCode() == KeyEvent.VK_ENTER) { // if enter key is pressed then game restarts
            if(!play) {
                play = true;
                ballposX = 120;
                ballposY = 350;
                ballXdir = -2;
                ballYdir = -4;
                score = 0;
                totalBricks = 21;
                map = new MapGenerator(3,7);

                repaint();
            }
        }

    }
    public void moveRight() { // paddle moves right by 50 pixels
        play = true;
        playerX += 65;
    }
    public void moveLeft() { // paddle moves left by 50 pixels
        play = true;
        playerX -= 65;
    }



    @Override
    public void keyReleased(KeyEvent arg0) {

    }


}