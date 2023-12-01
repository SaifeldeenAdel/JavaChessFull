package ChessGUI;

import ChessCore.*;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainBoard extends JFrame {
    private ChessGame game;
    private JPanel boardPanel;
    private JPanel prevClickedSquare = null;
    // squareFrom
    // squareTo

    public MainBoard() {
        game = new ChessGame();

        // Creating the main panel of the whole board and setting an 8x8 grid inside it
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(8, 8));

        // Filling in the board panel then adding it to our frame
        initialiseSquares();
        //setPieces();
        add(boardPanel, BorderLayout.CENTER);

        // Setting the dimensions of our main window.
        this.setTitle("8139 & 8277's Chess");
        this.setSize(1000,1000);
        this.setLocation(450,20);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    // public void setPieces

    public void initialiseSquares(){
        int count = 0;
        // Goes through the 8x8 grid and add JPanels which are our "squares" with alternating colors
        for (int rank = 0; rank < Constants.BOARD_HEIGHT; rank++) {
            for (int file = 0; file < Constants.BOARD_WIDTH; file++) {
                JPanel square = new JPanel();
                square.addMouseListener(new SquareMouseListener());
                square.setBackground((file + rank) % 2 == 0 ? TileColors.LIGHT : TileColors.DARK);
                boardPanel.add(square);
            }
        }
    }

    private class SquareMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            JPanel square = (JPanel) e.getComponent();
            int index = boardPanel.getComponentZOrder(square);
            int rank = index / Constants.BOARD_HEIGHT;
            int file = index % Constants.BOARD_WIDTH;
            Piece piece = game.getBoard().getSquare(rank, file).getPiece();

            // Reset color of the previously clicked square
            if (prevClickedSquare != null) {
                Color prevColor = prevClickedSquare.getBackground();
                prevClickedSquare.setBackground((prevColor == TileColors.LIGHT_ACCENT) ? TileColors.LIGHT : TileColors.DARK);
                prevClickedSquare = null;
            }
            if (piece != null){
                prevClickedSquare = square;
                Color currentColor = square.getBackground();
                square.setBackground((currentColor == TileColors.LIGHT) ? TileColors.LIGHT_ACCENT: TileColors.DARK_ACCENT);
                int rankReal = game.getPlayerTurn() == ChessCore.Color.WHITE ? 8 - rank : rank + 1;
                int fileReal = game.getPlayerTurn() == ChessCore.Color.WHITE ? file + 1 + 96 : 8 - file + 96;
                System.out.println("Clicked on File: " + (char)fileReal + ", Rank: " + rankReal);
            }
            System.out.println(game.getPlayerTurn());
        }
    }

    // Flipping board
    private void flipBoard() {
        Component[] components = boardPanel.getComponents();
        boardPanel.removeAll();

        for (int i = components.length - 1; i >= 0; i--) {
            boardPanel.add(components[i]); // Add components in reverse order
        }

        boardPanel.revalidate();
        boardPanel.repaint();
    }

    public static void main(String[] args) {
        MainBoard chessGUI= new MainBoard();
    }

}
