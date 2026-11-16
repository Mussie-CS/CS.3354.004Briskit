
package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Displays the chess game move history,
 * captured pieces, and undo controls.
 */
public class GameHistory extends JPanel {

    private DefaultListModel<String> moveModel;
    private DefaultListModel<String> captureModel;

    private JList<String> moveList;
    private JList<String> captureList;

    private JButton undoButton;

    // Records whether each move captured a piece
    private List<Boolean> captureHistory = new ArrayList<>();

    /**
     * Creates the game history panel.
     */
    public GameHistory() {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(250, 500));

        moveModel = new DefaultListModel<>();
        captureModel = new DefaultListModel<>();

        moveList = new JList<>(moveModel);
        captureList = new JList<>(captureModel);

        JLabel title = new JLabel(
            "Game History",
            SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 18));

        add(title, BorderLayout.NORTH);

        // Move and capture panels
        JPanel listsPanel = new JPanel(new GridLayout(2, 1));

        JPanel movesPanel = new JPanel(new BorderLayout());
        movesPanel.add(new JLabel("Moves:"), BorderLayout.NORTH);
        movesPanel.add(new JScrollPane(moveList), BorderLayout.CENTER);

        JPanel capturesPanel = new JPanel(new BorderLayout());
        capturesPanel.add(new JLabel("Captured Pieces:"), BorderLayout.NORTH);
        capturesPanel.add(new JScrollPane(captureList), BorderLayout.CENTER);

        listsPanel.add(movesPanel);
        listsPanel.add(capturesPanel);

        add(listsPanel, BorderLayout.CENTER);

        // Undo button
        undoButton = new JButton("Undo Move");

        add(undoButton, BorderLayout.SOUTH);
    }

    /**
     * Connects the Undo button to the chessboard.
     */
    public void setUndoAction(Runnable action) {

        undoButton.addActionListener(e -> action.run());
    }

    /**
     * Records a move.
     */
    public void addMove(String move) {

        moveModel.addElement(move);
        captureHistory.add(false);

        moveList.ensureIndexIsVisible(moveModel.size() - 1);
    }

    /**
     * Records a captured piece.
     */
    public void addCapture(String capture) {

        captureModel.addElement(capture);

        if (!captureHistory.isEmpty()) {
            captureHistory.set(
                captureHistory.size() - 1,
                true
            );
        }
    }

    /**
     * Removes the most recent move and its capture.
     */
    public void undoLastMove() {

        if (moveModel.isEmpty()) {
            return;
        }

        int last = moveModel.size() - 1;

        moveModel.remove(last);

        boolean hadCapture =
            captureHistory.remove(captureHistory.size() - 1);

        if (hadCapture && !captureModel.isEmpty()) {
            captureModel.remove(captureModel.size() - 1);
        }
    }

    /**
     * Clears the move and capture history.
     */
    public void clearHistory() {

        moveModel.clear();
        captureModel.clear();
        captureHistory.clear();
    }
}
