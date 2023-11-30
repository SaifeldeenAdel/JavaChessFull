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
    private JPanel prevSquare = null;

    public MainBoard() {
        game = new ChessGame();

        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(8, 8));

        initialiseSquares();
        add(boardPanel, BorderLayout.CENTER);

        this.setTitle("8139 & 8277's Chess");
        this.setSize(1000,1000);
        this.setLocation(450,20);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void initialiseSquares(){
        int count = 0;
        for (int rank = 0; rank < Constants.BOARD_HEIGHT; rank++) {
            for (int file = 0; file < Constants.BOARD_WIDTH; file++) {
                final int rankFinal = rank;
                final int fileFinal = file;
                JPanel square = new JPanel();

                square.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        if (prevSquare != null) {
                            // Reset color of the previously clicked square
                            Color prevColor = prevSquare.getBackground();
                            prevSquare.setBackground((prevColor == TileColors.LIGHT_ACCENT) ? TileColors.LIGHT : TileColors.DARK);
                        }
                        prevSquare = square;

                        Color currentColor = square.getBackground();
                        square.setBackground((currentColor == TileColors.LIGHT) ? TileColors.LIGHT_ACCENT: TileColors.DARK_ACCENT);
                        System.out.println("Clicked on File: " + fileFinal + ", Rank: " + rankFinal);

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
