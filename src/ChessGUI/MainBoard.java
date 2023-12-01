package ChessGUI;

import ChessCore.*;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

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
        boardPanel.setLayout(new GridLayout(Constants.BOARD_HEIGHT, Constants.BOARD_WIDTH));

        // Filling in the board panel then adding it to our frame
        initialiseSquares();

        add(boardPanel, BorderLayout.CENTER);

        // Setting the dimensions of our main window.
        this.setTitle("8139 & 8277's Chess");
        this.setSize(600,600);
        this.setLocation(450,20);
        this.setVisible(true);
        setPieces();
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

    public void setPieces(){
        Component[] squares = boardPanel.getComponents();
        // goes through all components (squares) to add image according to piece
        for(int element=0; element< boardPanel.getComponentCount(); element++){

            //finding the address of each square using the address of the element in the squares array
            int rank = element/Constants.BOARD_HEIGHT;
            int file = element%Constants.BOARD_WIDTH;
            System.out.println("Rank : "+ rank + "file: "+ file);
            //typecasting each squares array element as Jpanel
            JPanel square = (JPanel) squares[element];
            int width = boardPanel.getWidth()/Constants.BOARD_WIDTH;
            int height = boardPanel.getHeight()/Constants.BOARD_HEIGHT;
            //getting piece on board game
            Piece crPiece = game.getBoard().getSquare(rank, file).getPiece();

            //convert image to type Image to resize image according to square size in grid and set image to square
            Image resizedImage = pieceImage(crPiece).getImage().getScaledInstance(width,height,Image.SCALE_SMOOTH);
            ImageIcon cImage = new ImageIcon(resizedImage);
            JLabel finalImage = new JLabel(cImage);
            finalImage.setHorizontalAlignment(JLabel.CENTER);
            finalImage.setVerticalAlignment(JLabel.CENTER);
            square.add(finalImage);
        }
    }

    private ImageIcon pieceImage(Piece crPiece) {
        if(crPiece!=null){
            if (!crPiece.isWhite()) {
                if (crPiece.getType().equals(PieceType.PAWN)) {
                    return new ImageIcon("ChessImages/WhitePawn.png");
                }
                if(crPiece.getType().equals(PieceType.BISHOP)){
                    return new ImageIcon("ChessImages/WhiteBishop.png");
                }
                if(crPiece.getType().equals(PieceType.ROOK)){
                    return new ImageIcon("ChessImages/WhiteRook.png");
                }
                if(crPiece.getType().equals(PieceType.KNIGHT)){
                    return new ImageIcon("ChessImages/WhiteKnight.png");
                }
                if(crPiece.getType().equals(PieceType.QUEEN)){
                    return new ImageIcon("ChessImages/WhiteQueen.png");
                }
                if(crPiece.getType().equals(PieceType.KING)){
                    return new ImageIcon("ChessImages/WhiteKing.png");
                }

            } else {
                if (crPiece.getType().equals(PieceType.PAWN)) {
                    return new ImageIcon("ChessImages/BlackPawn.png");
                }
                if(crPiece.getType().equals(PieceType.ROOK)){
                    return new ImageIcon("ChessImages/BlackRook.png");
                }
                if(crPiece.getType().equals(PieceType.KNIGHT)){
                    return new ImageIcon("ChessImages/BlackKnight.png");
                }
                if(crPiece.getType().equals(PieceType.BISHOP)){
                        return new ImageIcon("ChessImages/BlackBishop.png");
                }
                if(crPiece.getType().equals(PieceType.QUEEN)){
                return new ImageIcon("ChessImages/BlackQueen.png");
                }
                if(crPiece.getType().equals(PieceType.KING)){
                return new ImageIcon("ChessImages/BlackKing.png");
                }
            }
        }
        return new ImageIcon("ChessImages/default.png");
    }


    public static void main(String[] args) {
        MainBoard chessGUI= new MainBoard();
    }

}
