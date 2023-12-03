package ChessGUI;

import ChessCore.PieceType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PromotionWindow extends JDialog implements Node{
    private JButton queenButton;
    private JButton bishopButton;
    private JButton rookButton;
    private JButton knightButton;
    private JPanel promotionWindow;
    private PieceType promotionPiece;
    private Node parent;


    public void setPromotionPiece(PieceType promotionPiece) {
        this.promotionPiece = promotionPiece;
    }

    public PieceType getPromotionPiece() {
        return promotionPiece;
    }

    public PromotionWindow() {
        this.setContentPane(this.promotionWindow);
        this.setSize(200,300);
        this.setLocation(550,100);
        this.setVisible(true);

        queenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPromotionPiece(PieceType.QUEEN);
                System.out.println(((MainBoard)PromotionWindow.this.getParentNode()));
//                ((MainBoard)PromotionWindow.this.getParentNode()).setPromotionPiece(getPromotionPiece());
//                System.out.println(getPromotionPiece());
                setVisible(false);
            }
        });

        knightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPromotionPiece(PieceType.KNIGHT);
                ((MainBoard)getParentNode()).setPromotionPiece(getPromotionPiece());
                setVisible(false);

            }
        });

        rookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPromotionPiece(PieceType.ROOK);
                ((MainBoard)getParentNode()).setPromotionPiece(getPromotionPiece());
                setVisible(false);

            }
        });

        bishopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPromotionPiece(PieceType.BISHOP);
                setVisible(false);
            }
        });
    }

    @Override
    public void setParentNode(Node n) {

    }

    public PieceType showPromotionDialogAndWait(){
        setVisible(true);
        return getPromotionPiece();
    }

    @Override
    public Node getParentNode() {
        System.out.println(parent);
        return parent;
    }

    public static void main(String[] args) {
        PromotionWindow p = new PromotionWindow();
    }
}
// TODO
// could override method to
