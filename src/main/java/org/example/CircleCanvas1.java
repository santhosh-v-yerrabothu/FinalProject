package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class CircleCanvas1 extends JPanel {
    private int x, y, radius, neighborhoodSize;


    public CircleCanvas1(int x, int y, int radius, int neighborhoodSize) {
        this.x = x;
        this.y = y;
        this.radius =radius;
        this.neighborhoodSize = neighborhoodSize;
    }
    // Method to move the circle
    public void moveCircle(int newX, int newY) {
        x = newX;
        y = newY;
        repaint(); // Repaint the panel to reflect the new position
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        g.drawOval(x - (radius/2), y - (radius/2), radius, radius);
    }

    public void startRandomHop() {
        int x_min =  x-neighborhoodSize+radius;
        int x_max =  x+neighborhoodSize-radius;
        int y_min =  y-neighborhoodSize+radius;
        int y_max =  y+neighborhoodSize-radius;
        Random random = new Random();
        new Timer(2000, e -> {
            int x_random = random.nextInt((x_max - x_min) + 1) + x_min;
            int y_random = random.nextInt((y_max - y_min) + 1) + y_min;
            moveCircle(x_random, y_random);
        }).start();
    }

//    // Main method to show the panel
//    public static void main(String[] args) {
//        JFrame frame = new JFrame("Circle Mover");
//        CircleCanvas canvas = new CircleCanvas();
//        frame.add(canvas);
//        frame.setSize(400, 400);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setVisible(true);
//
//        // Example: move the circle after 2 seconds
//        new Timer(2000, e -> canvas.moveCircle(200, 150)).start();
//    }
}