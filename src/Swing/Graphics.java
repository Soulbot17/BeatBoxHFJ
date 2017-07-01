package Swing;

import javax.swing.*;
import java.awt.*;

/**
 * Created by SuperDenissio on 30.06.2017.
 */
public class Graphics extends JFrame{
    Graphics graphics;
    MyCircle circle;
    int x = 20;
    int y = 20;
    public static void main(String[] args) throws InterruptedException {
        Graphics g = new Graphics();
        g.createFrame();
        g.move();
    }

    Graphics(){
        System.out.println("Swing.Graphics created");
    }
    public void move() throws InterruptedException {
        for (int i = 0; i<100; i++) {
            x+=5;
            y+=5;
            circle.repaint();
            Thread.sleep(50);
        }
    }

    public void createFrame() {
        graphics = new Graphics();
        graphics.setTitle("Head first java");
        graphics.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        graphics.setLocationRelativeTo(null);
        graphics.setSize(600,400);
        graphics.setBackground(Color.WHITE);
        graphics.setLayout(new BorderLayout());
        circle = new MyCircle();
        graphics.add(circle);

        graphics.setVisible(true);
    }

    class MyCircle extends JPanel{
        @Override
        protected void paintComponent(java.awt.Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.WHITE);
            g.fillRect(0,0,this.getWidth(),this.getHeight());

            g.setColor(Color.ORANGE);
            g.fillOval(x,y,40,40);
            System.out.println("Circle printed");
        }
    }
}
