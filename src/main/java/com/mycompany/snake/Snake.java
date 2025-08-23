/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.snake;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;


/**
 *
 *
 */

public class Snake extends JFrame {
    private final GamePanel playArea;
    private final JPanel jpHeader, jpNewGameBtn;
    private final JButton jbtNewGame;
    private final JTextArea jtaInstructions;
    private final String instructions ="Spacebar = Start/Stop\nArrow Keys = move";

    public Snake() {
        playArea = new GamePanel();
        jbtNewGame = new JButton("NEW GAME");
        jpHeader = new JPanel(); 
        jpNewGameBtn = new JPanel();
        jtaInstructions = new JTextArea();
        
        buildHeader();
        
        this.setLayout(new BorderLayout());
        this.add(jpHeader, BorderLayout.NORTH);
        this.setTitle("Snake!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(new Listener());
        this.setFocusable(true);
        this.add(playArea);
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }//end of constructor
    
    private void buildHeader() {
        jbtNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playArea.resetGame();
                requestFocusInWindow();
            }
        });
        jtaInstructions.setBackground(Color.gray);
        jtaInstructions.setText(instructions);
        jpHeader.setBackground(Color.gray);
        jbtNewGame.setBackground(Color.cyan);
        jpNewGameBtn.setBackground(Color.gray);
        playArea.jpScore.setPreferredSize(new Dimension(200, 30));
        jpNewGameBtn.add(jbtNewGame);
        jpNewGameBtn.setPreferredSize(new Dimension(150, 30));
        jtaInstructions.setPreferredSize(new Dimension(200, 30));
        
        jpHeader.add(jtaInstructions);
        jpHeader.add(jpNewGameBtn);
        jpHeader.add(playArea.jpScore);
    }//end of buildHeader
    
    class Listener implements KeyListener {
        @Override
        public void keyReleased(KeyEvent e) {
            //not used
        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getExtendedKeyCode()) {
                case 37 -> {
                    if (playArea.direction != Direction.EAST && playArea.getHeadXCoord() - 25 != playArea.getNeckXCoord()) {
                        playArea.direction = Direction.WEST;
                    }
                }
                case 38 -> { 
                    if (playArea.direction != Direction.SOUTH && playArea.getHeadYCoord() - 25 != playArea.getNeckYCoord()) {
                        playArea.direction = Direction.NORTH;
                    }
                }
                case 39 -> {
                    if (playArea.direction != Direction.WEST && playArea.getHeadXCoord() + 25 != playArea.getNeckXCoord()) {
                        playArea.direction = Direction.EAST;
                    }
                }
                case 40 -> { 
                    if (playArea.direction != Direction.NORTH && playArea.getHeadYCoord() + 25 != playArea.getNeckYCoord()) {
                        playArea.direction = Direction.SOUTH;
                    }
                }
                case 32 -> {
                    if (playArea.timer.isRunning()) {
                        playArea.timer.stop();
                    } else {
                        playArea.timer.start();
                    }
                }
                default -> {
                }
            }
        }//end of keyPressed

        @Override
        public void keyTyped(KeyEvent e) {
            //not used
        }
    }//end of Listener class
    
    public static void main(String[] args) {
        Snake window = new Snake();
    }
}//end of Snake class
