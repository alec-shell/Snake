/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.snake;

/**
 *
 * @author ALEC
 */
class SnakeSegment {
    private int coordX;
    private int coordY;
    
    public SnakeSegment(int x, int y) {
        this.coordX = x;
        this.coordY = y;
    }//end of constructor
    
    //setters
    public final void setX(int x) {
        this.coordX = x;
    }//end of setX
    
    public final void setY(int y) {
        this.coordY = y;
    }//end of setY
    
    //getters
    public final int getX() {
        return this.coordX;
    }//end of getX
    
    public final int getY() {
        return this.coordY;
    }//end of getY
}//end of SnakeSegment class
