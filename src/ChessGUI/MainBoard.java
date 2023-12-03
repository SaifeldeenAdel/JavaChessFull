package ChessGUI;

import ChessCore.*;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MainBoard extends JFrame {
    private ChessGame game;
    private JPanel boardPanel;
    private JPanel prevClickedSquare = null;
    private Color[][] tileColors = new Color[8][8];
    private int[] squareFrom = new int[2];
    private int[] squareTo = new int[2];
    private boolean setFrom = false;
    private JPanel highlightedKing = null;

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
        this.setSize(800,800);
        this.setLocation(450,20);
        this.setVisible(true);
        setPieces();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    // public void setPieces


    public JPanel getHighlightedKing() {
        return highlightedKing;
    }

    public void setHighlightedKing(JPanel highlightedKing) {
        this.highlightedKing = highlightedKing;
    }

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
                prevClickedSquare.setBackground((prevColor == TileColors.LIGHT_ACCENT || prevColor == TileColors.LIGHT_RED || prevColor == TileColors.LIGHT) ? TileColors.LIGHT : TileColors.DARK);
                prevClickedSquare = null;
            }
            Piece piece = game.getBoard().getSquare(rank, file).getPiece();

            // Setting the colors of the squares only if theres a piece there
            if (piece != null){
                prevClickedSquare = square;
                square.setBackground((square.getBackground() == TileColors.LIGHT || square.getBackground() == TileColors.LIGHT_RED || square.getBackground() == TileColors.LIGHT_ACCENT) ? TileColors.LIGHT_ACCENT: TileColors.DARK_ACCENT);
            }

            // Setting the squareFrom
            if(piece != null && !setFrom){
                squareFrom[0] = file;
                squareFrom[1] = rank;
                setFrom = true;
            } else if (setFrom) {
                // If a square was selected before this one, check if this will be a valid move, if not, we will set the squareFrom again.
                squareTo[0] = file;
                squareTo[1] = rank;
                if (!game.move(squareFrom[0], squareFrom[1], squareTo[0],squareTo[1], null)){
                    squareFrom[0] = file;
                    squareFrom[1] = rank;
                    setFrom = true;
                    //showValidMoves(squareFrom);
                } else {
                    // If it's a valid move, flip the board
                    showGameStatus();
                    flipBoard();
                    highlightKingInCheck();
                    setPieces();
                    setFrom = false;
                }
            }

        }
    }

    private void resetColors(){
        Component[] components = boardPanel.getComponents();
        for (int i = 0; i < components.length; i++) {
            int rank = i / Constants.BOARD_HEIGHT;
            int file = i % Constants.BOARD_WIDTH;
            components[i].setBackground(tileColors[rank][file]);
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

    public void setPieces(){
        Component[] squares = boardPanel.getComponents();
        // Goes through all components (squares) to add image according to piece
        for(int element=0; element< boardPanel.getComponentCount(); element++){
            // Finding the address of each square using the address of the element in the squares array
            int rank = element/Constants.BOARD_HEIGHT;
            int file = element%Constants.BOARD_WIDTH;
            rank = game.getPlayerTurn() == ChessCore.Color.WHITE ? 7 - rank : rank;
            file = game.getPlayerTurn() == ChessCore.Color.WHITE ? file : 7 - file;

            JPanel square = (JPanel) squares[element];
            square.removeAll(); // Remove any components (labels) from the square

            int width = boardPanel.getWidth()/Constants.BOARD_WIDTH;
            int height = boardPanel.getHeight()/Constants.BOARD_HEIGHT;

            // Getting piece on board game
            Piece crPiece = game.getBoard().getSquare(rank, file).getPiece();
            // Convert image to type Image to resize image according to square size in grid and set image to square
            Image resizedImage = pieceImage(crPiece).getImage().getScaledInstance(width,height,Image.SCALE_SMOOTH);
            ImageIcon cImage = new ImageIcon(resizedImage);
            JLabel finalImage = new JLabel(cImage);
            finalImage.setHorizontalAlignment(JLabel.CENTER);
            finalImage.setVerticalAlignment(JLabel.CENTER);
            square.add(finalImage);

            square.revalidate();
            square.repaint();
        }
    }

    //TODO
    // Both methods below will be called after a move is successful (around line 96)
    // void showGameStatus() - just checks current game status and gives out a message dialog if White Won, Black Won, Stalemate, or Insufficient mats
    // void showValidMoves(Square square) - pass the square from the board, run the getValidMovesFromSquare then highlights the squares returned.
    // void highlightKingInCheck()

    private void showGameStatus(){
        if(game.getGameStatus() == GameStatus.STALEMATE || game.getGameStatus() == GameStatus.INSUFFICIENT_MATERIAL||game.getGameStatus() == GameStatus.BLACK_WON || game.getGameStatus() == GameStatus.WHITE_WON){
            JDialog dialog = new JDialog();
            dialog.setTitle("Game status");
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

            JLabel label = new JLabel();
            label.setFont(new Font("Times New Roman", Font.BOLD, 20));
            label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            if(game.getGameStatus() == GameStatus.STALEMATE){
                label.setText("STALEMATE");
            }
            else if(game.getGameStatus()== GameStatus.INSUFFICIENT_MATERIAL){
                label.setText("INSUFFICIENT MATERIAL");
            }
            else if(game.getGameStatus()==GameStatus.BLACK_WON){
                label.setText("BLACK WON");
            }
            else if(game.getGameStatus()== GameStatus.WHITE_WON){
                label.setText("WHITE WON");
            }
            // add game ended dialogue?
            dialog.getContentPane().add(label);
            dialog.setSize(400,200);
            dialog.setVisible(true);

        }

    }

    public void showValidMoves(int [] squareFrom){
        //game.getAllValidMovesFromSquare(game.getBoard().getSquare(squareFrom[0], squareFrom[1]));
    }

    // Highlighting the king if there is a king in check
    public void highlightKingInCheck(){
        if(game.getGameStatus()== GameStatus.WHITE_IN_CHECK
                || game.getGameStatus()== GameStatus.BLACK_IN_CHECK
                || game.getGameStatus()== GameStatus.WHITE_WON
                || game.getGameStatus()== GameStatus.BLACK_WON){
            // Getting the king's position relative to the board's orientation
            Component[] squares = boardPanel.getComponents();
            Square king = game.getKingInCheckSquare();
            int rank = game.getPlayerTurn() == ChessCore.Color.WHITE ? 7 - king.rank : king.rank;
            int file = game.getPlayerTurn() == ChessCore.Color.WHITE ? king.file : 7 - king.file;
            JPanel square = (JPanel) squares[rank * 8 + file];

            // Highlighting the square based on the color square its on
            square.setBackground(square.getBackground() == TileColors.DARK ? TileColors.DARK_RED : TileColors.LIGHT_RED);
            setHighlightedKing(square);
        } else if (highlightedKing != null){
            // Reverting back the highlighted king colors after the player evades the check
            resetColors();
        }
    }

    // Getting the image of the given piece based on its type
    private ImageIcon pieceImage(Piece crPiece) {
        if(crPiece!=null){
            if (crPiece.isWhite()) {
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
