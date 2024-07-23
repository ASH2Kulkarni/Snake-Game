package snakegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JPanel implements ActionListener {
    private int dots;
    private Image apple;
    private Image dot;
    private Image head;
    private int AppleX;
    private int AppleY;
    
    private Timer t;
    
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;
    
    private final int ALL_DOTS = 1600;
    private final int DOT_SIZE = 10;
    private final int RANDOM = 39;
     
    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];
    
    Board() {
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);
        loadImages();
        InitGame();
    }
    
    public void loadImages() {
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/apple.png"));
        apple = i1.getImage();
        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/dot.png"));
        dot = i2.getImage();
        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/head.png"));
        head = i3.getImage();
    }

    public void InitGame() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            y[i] = 120; // from top
            x[i] = 120 - i * DOT_SIZE;
        }
        
        // place an apple
        locateApple();
        t = new Timer(140, this); // adjusted speed
        t.start();
    }
     
    private void locateApple() {
        int r = (int)(Math.random() * RANDOM);
        AppleX = r * DOT_SIZE; // for x axis
        r = (int)(Math.random() * RANDOM);
        AppleY = r * DOT_SIZE;    
        
           for (int i = 0; i < dots; i++) {
            if (x[i] == AppleX && y[i] == AppleY) {
                locateApple();
            }
        }
    }

    public void paintComponent(Graphics g) { // paint image on frame
        super.paintComponent(g);
        draw(g);
    }
    
    public void draw(Graphics g) {
        if (inGame) {
            g.drawImage(apple, AppleX, AppleY, this);
            for (int i = 0; i < dots; i++) {
                if (i == 0) {
                    g.drawImage(head, x[i], y[i], this);
                } else {
                    g.drawImage(dot, x[i], y[i], this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        } else {
            gameOver(g);
        }
    }
  
    public void checkApple() {
        if ((x[0] == AppleX) && (y[0] == AppleY)) {
            dots++; // increase size
            locateApple();
        }
    }
    
    public void checkCollision() {
        for (int i = dots; i > 0; i--) {
            if ((i > 3) && (x[0] == x[i]) && (y[0] == y[i])) {
                inGame = false;
            }
        }
        
        if (y[0] >= 400) {
            inGame = false;
        }
        
        if (y[0] < 0) {
            inGame = false;
        }
        
        if (x[0] >= 400) {
            inGame = false;
        }
        
        if (x[0] < 0) {
            inGame = false;
        }
        
        if (!inGame) {
            t.stop();
        }
    }

    private void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        
        if (left) {
            x[0] = x[0] - DOT_SIZE;
        }
        if (right) {
            x[0] = x[0] + DOT_SIZE;
        }
        if (up) {
            y[0] = y[0] - DOT_SIZE;
        }
        if (down) {
            y[0] = y[0] + DOT_SIZE;
        }
    }
    
    public class TAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent ae) {
            int key = ae.getKeyCode();
             
            if (key == KeyEvent.VK_LEFT && (!right)) {
                left = true;
                up = false;
                down = false;
            }
             
            if (key == KeyEvent.VK_RIGHT && (!left)) {
                right = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_UP && (!down)) {
                left = false;
                up = true;
                right = false;
            }
            if (key == KeyEvent.VK_DOWN && (!up)) {
                left = false;
                down = true;
                right = false;
            }
            
            // Restart the game when it is over and any key is pressed
            if (!inGame) {
                inGame = true;
                InitGame();
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }
    
    private void gameOver(Graphics g) {
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 24);
        FontMetrics metr = getFontMetrics(small);
        
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (300 - metr.stringWidth(msg)) / 2, 300 / 2);
    }
}
