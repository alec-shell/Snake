/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;

/**
 *
 * @author ALEC
 */
class GamePanel extends JPanel implements ActionListener {
    private final int SCREEN_HEIGHT = 525;
    private final int SCREEN_WIDTH = 600;
    private final int OBJECT_SCALE = 25;
    private int refreshRate = 130;
    protected Direction direction = Direction.SOUTH;
    private int snakeX = 275;
    private int snakeY = 250;
    private int appleY;
    private int appleX;
    private int appleClr = 0;
    private int score = 0;
    private final Random rand = new Random();
    protected final Timer timer;
    private final ArrayList<SnakeSegment> snake = new ArrayList<>();
   
    protected final JTextArea jpScore;
    
    public GamePanel() {
        timer = new Timer(refreshRate, this);
        jpScore = new JTextArea();
        jpScore.setText("SCORE: " + score);
        jpScore.setBackground(Color.gray);
        jpScore.setEditable(false);
        
        snake.add(new SnakeSegment(snakeX, snakeY));
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        locateApple();
        while (isOverlapping()) {
            locateApple();
        }
    }//end of constructor
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;    
        g2d.setColor(Color.white);
        g2d.fillRect(snake.get(0).getX(), snake.get(0).getY(), 
                OBJECT_SCALE, OBJECT_SCALE);
        int snakeLength = snake.size();
        for (int i = 1; i < snakeLength; i++) {
            if (i % 2 == 0) {
                g2d.setColor(Color.magenta);
            } else {
                g2d.setColor(Color.cyan);
            }
            g2d.fillRect(snake.get(i).getX(), snake.get(i).getY(), OBJECT_SCALE, OBJECT_SCALE);
        }
        g2d.setColor(setAppleClr());
        g2d.fillOval(appleX, appleY, OBJECT_SCALE, OBJECT_SCALE);
    }//end of paintComponent

    private Color setAppleClr() {
        if (appleClr == 0) return Color.GREEN;
        else if (appleClr == 1) return Color.RED;
        else return Color.YELLOW;
    } // end of setAppleClr

    private boolean isOverlapping() {
        for (SnakeSegment seg : snake) {
            if (seg.getX() == appleX && seg.getY() == appleY) {
                return true;
            }
        }
        return false;
    } // end of isOverlapping
    
    private void locateApple() {
        appleX = (int) rand.nextInt(0, SCREEN_WIDTH / OBJECT_SCALE) * OBJECT_SCALE;
        appleY = (int) rand.nextInt(0, SCREEN_HEIGHT / OBJECT_SCALE) * OBJECT_SCALE;
    }//end of locateApple
    
    protected final void detectCollision() {
        if (snakeX == appleX && snakeY == appleY) {
            addPoints();
            generateAppleClr();
            snake.add(new SnakeSegment(-25, -25));
            locateApple();
            while (isOverlapping()) locateApple();
            jpScore.setText("SCORE: " + score);
            if (score % 10 == 0 && refreshRate > 50) {
                refreshRate *= .9;
                timer.setDelay(refreshRate);
            }
        } else if (snakeX < 0 || snakeY < 0 || snakeX > SCREEN_WIDTH - OBJECT_SCALE || snakeY > SCREEN_HEIGHT - OBJECT_SCALE) {
            gameOver();
        }
        int snakeLength = snake.size();
        for (int i = 1; i < snakeLength; i++) {
            if (snakeX == snake.get(i).getX() && snakeY == snake.get(i).getY()) {
                gameOver();
            }
        }
    }//end of detectCollision

    private void generateAppleClr() {
        int color = rand.nextInt(100);
        if (color < 70) {
            appleClr = 0;
        }
        else if (color < 95) {
            appleClr = 1;
        }
        else {
            appleClr = 2;
        }
    } // end of generateAppleClr

    private void addPoints() {
        if (appleClr == 0) score++;
        else if (appleClr == 1) score += 2;
        else score += 10;
    } // end of addPoints

    protected final void move() {
        switch (direction) {
            case Direction.NORTH -> snakeY -= OBJECT_SCALE;
            case Direction.SOUTH -> snakeY += OBJECT_SCALE;
            case Direction.EAST -> snakeX += OBJECT_SCALE;
            default -> snakeX -= OBJECT_SCALE;
        } // end of move
        
        int snakeLength = snake.size();
        if (snakeLength > 1) {
            for (int i = snakeLength - 1; i >= 1; i--) {
                snake.get(i).setX(snake.get(i - 1).getX());
                snake.get(i).setY(snake.get(i - 1).getY());
            }
        }
        snake.get(0).setX(snakeX);
        snake.get(0).setY(snakeY);
    }//end of move
    
    private void gameOver() {
        timer.stop();
        JOptionPane.showMessageDialog(null, "GAME OVER\nFinal Score: " + score);
        resetGame();
    }//end of gameOver
    
    protected void resetGame() {
        refreshRate = 130;
        timer.setDelay(refreshRate);
        score = 0;
        snakeX = 275;
        snakeY = 250;
        direction = Direction.SOUTH;
        locateApple();
        jpScore.setText("SCORE: " + score);
        snake.clear();
        snake.add(new SnakeSegment(snakeX, snakeY));
        appleClr = 0;
        repaint();
        timer.stop();
    }//end of resetGame
    
    protected int getHeadXCoord() {
        return snakeX;
    }//end of getHeadXCoord
    
    protected int getHeadYCoord() {
        return snakeY;
    }//end of getHeadYCoord
    
    protected int getNeckXCoord() {
        if (snake.size() > 1) {
            return snake.get(1).getX();
        }
        return -1;
    }//end of getNeckXCoord
    
    protected int getNeckYCoord() {
        if (snake.size() > 1) {
            return snake.get(1).getY();
        }
        return -1;
    }//end of getNeckYCoord
    
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        detectCollision();
        repaint();
    }//end of actionPerformed
}//end of GamePanel class
