package com.classwork;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
public class GameWindow extends JFrame {
    private static long last_frame_time;
    private static  GameWindow game_window;
    private static Image background;
    private static Image end;
    private static Image drop;
    public static float drop_left = 200;
    public static float drop_top = -100;
    public static float drop_v = 200;
    public static int score;




    public static void main(String[] args)  throws IOException {
        background = ImageIO.read(GameWindow.class.getResourceAsStream("background.png"));
      drop = ImageIO.read(GameWindow.class.getResourceAsStream("drop.png"));
       end = ImageIO.read(GameWindow.class.getResourceAsStream("end.png"));
        game_window = new GameWindow();
        game_window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game_window.setLocation(200,100);
        game_window.setSize(906,478);
        game_window.setResizable(false);
        GameField gameField = new GameField();
        gameField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                float drop_Right = drop_left + drop.getWidth(null);
                float drop_bottom = drop_top + drop.getHeight(null);
                boolean isDrop = x >=drop_left && x<= drop_Right && y >= drop_top && y<= drop_bottom;
               drop_top = -100;
               drop_left = (int) Math.random() * (gameField.getWidth() - drop.getWidth(null));
               drop_v = drop_v + 20;
               score ++;
               game_window.setTitle("Score: " +score);
            }
        });
        game_window.add(gameField);
        game_window.setVisible(true);
        last_frame_time = System.nanoTime();
    }
    private static void onRepaint (Graphics g) {
        long current_time = System.nanoTime();
        float delta_time = (current_time - last_frame_time) * 0.000000001f;
        last_frame_time = current_time;

        drop_top = drop_top + drop_v * delta_time;
        drop_left = drop_left + drop_v * delta_time;
        last_frame_time = current_time;
        g.drawImage(background, 0, 0, null);
        g.drawImage(drop, (int) drop_left, (int) drop_top, null);
        if (drop_top > game_window.getHeight()) g.drawImage(end,280,120, null);

    }
    private static class GameField extends JPanel {

        @Override
        protected void paintComponent (Graphics g) {
            super.paintComponent(g);
            onRepaint(g);
            repaint();



        }

    }
}
