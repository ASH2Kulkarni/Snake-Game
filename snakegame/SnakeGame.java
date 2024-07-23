package snakegame;

import javax.swing.*;

public class SnakeGame extends JFrame {

    SnakeGame() {
        super("SNAKE GAME");
        add(new Board());
        setResizable(false);
        pack(); // Ensure the frame sizes to fit the board
        setSize(400, 400); // Adjust to match the board size
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SnakeGame();
        });
    }
}
