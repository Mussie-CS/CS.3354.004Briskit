
package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Main window for the Phase 2 Chess Game GUI.
 */
public class ChessGUI extends JFrame {

    private ChessPanel chessPanel;
    private GameHistory gameHistory;

    public ChessGUI() {

        setTitle("Chess Game - Phase 2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create game history
        gameHistory = new GameHistory();

        // Create chessboard
        chessPanel = new ChessPanel(gameHistory);

        // Connect history undo button to chessboard
        gameHistory.setUndoAction(() -> chessPanel.undoMove());

        // Add components
        add(chessPanel, BorderLayout.CENTER);
        add(gameHistory, BorderLayout.EAST);

        // Create menu bar
        createMenuBar();

        pack();

// Make the window slightly bigger when it opens
setSize(1100, 750);

// Center the window on the screen
setLocationRelativeTo(null);

setVisible(true);
    }

    /**
     * Creates menu controls for the chess game.
     */
    private void createMenuBar() {

        JMenuBar menuBar = new JMenuBar();

        JMenu gameMenu = new JMenu("Game");

        JMenuItem newGame = new JMenuItem("New Game");

        newGame.addActionListener(e -> {
            chessPanel.newGame();
            gameHistory.clearHistory();
        });

        gameMenu.add(newGame);

        JMenu settingsMenu = new JMenu("Settings");

        JMenuItem classic = new JMenuItem("Classic Board");
        classic.addActionListener(e ->
            chessPanel.setBoardColors(
                new Color(238, 238, 210),
                new Color(118, 150, 86)
            )
        );

        JMenuItem gray = new JMenuItem("Gray Board");
        gray.addActionListener(e ->
            chessPanel.setBoardColors(
                new Color(220, 220, 220),
                new Color(100, 100, 100)
            )
        );

        JMenuItem blue = new JMenuItem("Blue Board");
        blue.addActionListener(e ->
            chessPanel.setBoardColors(
                new Color(235, 240, 245),
                new Color(90, 140, 190)
            )
        );

        JMenuItem small = new JMenuItem("Small Board");
        small.addActionListener(e ->
            chessPanel.setSquareSize(50)
        );

        JMenuItem medium = new JMenuItem("Medium Board");
        medium.addActionListener(e ->
            chessPanel.setSquareSize(65)
        );

        JMenuItem large = new JMenuItem("Large Board");
        large.addActionListener(e ->
            chessPanel.setSquareSize(80)
        );

        settingsMenu.add(classic);
        settingsMenu.add(gray);
        settingsMenu.add(blue);
        settingsMenu.addSeparator();
        settingsMenu.add(small);
        settingsMenu.add(medium);
        settingsMenu.add(large);

        menuBar.add(gameMenu);
        menuBar.add(settingsMenu);

        setJMenuBar(menuBar);
    }

    /**
     * Starts the GUI.
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new ChessGUI());
    }
}
