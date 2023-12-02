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
    private Color[][] tileColors = new Color[8][8];
    private int[] squareFrom = new int[2];
    private int[] squareTo = new int[2];
    private boolean setFrom = false;

    public MainBoard() {
        game = new ChessGame();

        // Creating the main panel of the whole board and setting an 8x8 grid inside it
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(8, 8));

        // Filling in the board panel then adding it to our frame
        initialiseSquares();
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
                tileColors[rank][file] = square.getBackground();
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
            rank = game.getPlayerTurn() == ChessCore.Color.WHITE ? 7 - rank : rank;
            file = game.getPlayerTurn() == ChessCore.Color.WHITE ? file : 7 - file;

            // Reset color of the previously clicked square
            if (prevClickedSquare != null) {
                Color prevColor = prevClickedSquare.getBackground();
                prevClickedSquare.setBackground((prevColor == TileColors.LIGHT_ACCENT) ? TileColors.LIGHT : TileColors.DARK);
                prevClickedSquare = null;
            }
            Piece piece = game.getBoard().getSquare(rank, file).getPiece();

            // Setting the colors of the squares only if theres a piece there
            if (piece != null){
                prevClickedSquare = square;
                square.setBackground((square.getBackground() == TileColors.LIGHT) ? TileColors.LIGHT_ACCENT: TileColors.DARK_ACCENT);
            }

            // Setting the squareFrom
            if(piece != null && !setFrom){
                squareFrom[0] = file;
                squareFrom[1] = rank;
                System.out.println("Setting squareFrom");
                setFrom = true;
            } else if (setFrom) {
                // If a square was selected before this one, check if this will be a valid move, if not, we will set the squareFrom again.
                squareTo[0] = file;
                squareTo[1] = rank;
                if (!game.move(squareFrom[0], squareFrom[1], squareTo[0],squareTo[1], null)){
                    System.out.println("Setting squareFrom again");
                    squareFrom[0] = file;
                    squareFrom[1] = rank;
                    setFrom = true;
                } else {
                    // If it's a valid move, flip the board
                    flipBoard();
                    game.display();
                    setFrom = false;
                }
            }

        }
    }

    // Flipping board
    private void flipBoard() {
        Component[] components = boardPanel.getComponents();
        boardPanel.removeAll();

        for (int i = components.length - 1; i >= 0; i--) {
            boardPanel.add(components[i]); // Add components in reverse order
            
//            For resetting the color of the squares, still undecided if I will do it
//            int rank = i / Constants.BOARD_HEIGHT;
//            int file = i % Constants.BOARD_WIDTH;
//            components[i].setBackground(tileColors[rank][file]);
        }

        boardPanel.revalidate();
        boardPanel.repaint();
    }

    public static void main(String[] args) {
        MainBoard chessGUI= new MainBoard();
    }

}
