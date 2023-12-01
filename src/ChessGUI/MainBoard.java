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
                final int rankFinal = rank;
                final int fileFinal = file;
                JPanel square = new JPanel();
                //TODO
                // Probably fill the squares with the appropriate image by checking the PieceType in game.getBoard().getSquare(rank,file).getPiece().getType()
                // And choosing the image based on the PieceType and color?

                // Piece piece = game.getBoard().getSquare(rank,file).getPiece()
                // getImageFromPiece(Piece piece) piece.isWhite() piece.getType()

                // Adding the clicking event mouse listener
                square.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        // if no piece
                        // if piece
                        if (prevClickedSquare != null) {
                            // Reset color of the previously clicked square
                            Color prevColor = prevClickedSquare.getBackground();
                            prevClickedSquare.setBackground((prevColor == TileColors.LIGHT_ACCENT) ? TileColors.LIGHT : TileColors.DARK);
                        }
                        prevClickedSquare = square;

                        Color currentColor = square.getBackground();
                        square.setBackground((currentColor == TileColors.LIGHT) ? TileColors.LIGHT_ACCENT: TileColors.DARK_ACCENT);
                        System.out.println("Clicked on File: " + fileFinal + ", Rank: " + rankFinal);
//                        System.out.println(game.getBoard().getSquare(rankFinal, fileFinal).getPiece().getType());
                    }
                });
                square.setBackground((file + rank) % 2 == 0 ? TileColors.LIGHT : TileColors.DARK);
                boardPanel.add(square);
            }
        }
    }

    public static void main(String[] args) {
        MainBoard chessGUI= new MainBoard();
    }

}
