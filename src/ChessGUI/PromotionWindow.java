package ChessGUI;

import ChessCore.PieceType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PromotionWindow extends JFrame implements Node{
    private JButton queenButton;
    private JButton bishopButton;
    private JButton rookButton;
    private JButton knightButton;
    private PieceType promotionPiece;

    public void setPromotionPiece(PieceType promotionPiece) {
        this.promotionPiece = promotionPiece;
    }

    public PieceType getPromotionPiece() {
        return promotionPiece;
    }

    public PromotionWindow() {
        queenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPromotionPiece(PieceType.QUEEN);
                ((MainBoard)getParentNode()).setPromotionPiece(getPromotionPiece());
            }
        });

        knightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPromotionPiece(PieceType.KNIGHT);
            }
        });

        rookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPromotionPiece(PieceType.ROOK);
            }
        });

        bishopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPromotionPiece(PieceType.BISHOP);
            }
        });
    }

    @Override
    public void setParentNode(Node n) {

    }

    @Override
    public Node getParentNode() {
        return null;
    }
}
