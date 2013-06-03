/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rendzu;

//import javax.swing.*;
//import java.util.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

/**
 *
 * @author Kiselev_KV
 */
public class BoardGridView extends BoardView
{
    final static int BEGIN_X = 1;
    final static int BEGIN_Y = 1;
    final static int SIZE = 20;
    Image iEmpty;
    Image iWhite;
    Image iBlack;
    BoardModel gModel;
    GameJPanel gamePanel;
 
    private boolean drawLine;
    private BoardModel.Point end1;
    private BoardModel.Point end2;

    public BoardGridView(BoardModel model, GameJPanel game)
    {
        super(model);
        gModel = model;
        this.gamePanel = game;

        updateDisplay();

        iEmpty = GameImages.getImage("empty.png", this);
        iWhite = GameImages.getImage("white.png", this);
        iBlack = GameImages.getImage("black.png", this);
        
        drawLine = false;
        end1 = new BoardModel.Point(0,0);
        end2 = new BoardModel.Point(0,0);

        //java.awt.event.MouseListener
        this.addMouseListener(new java.awt.event.MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                int j = e.getPoint().y / SIZE;
                int i = e.getPoint().x / SIZE;

                j = (e.getPoint().y - j) / SIZE;
                i = (e.getPoint().x - i) / SIZE;
             
                gamePanel.boardClicked(new BoardModel.Point(i, j));                
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                //System.out.println("mousePressed");
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                //System.out.println("mouseReleased");
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                //System.out.println("mouseEntered");
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                //System.out.println("mouseExited");
                //throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }
    
    public void setDrawLine(boolean bool1)
    {
        drawLine = bool1;
    }
    
    public void setPoints(BoardModel.Point p1, BoardModel.Point p2)
    {
        end1.setIJ(p1);
        end2.setIJ(p2);
    }

    public Dimension getPreferredSize()
    {
        int N = gModel.getN();
        int M = gModel.getM();
        return new Dimension(N * SIZE + N + 2 * BEGIN_X /*+ 6 + 5*/,
            M * SIZE + M + BEGIN_Y /*+ 12 + 25*/);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        java.awt.Graphics2D graphics2D = (java.awt.Graphics2D) g;

        int N = getModel().getN();
        int M = getModel().getM();


        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < M; j++)
            {
                Image iii = iEmpty;

                if (model.getBoard()[i][j] == BoardModel.Cell.White)
                {
                    iii = iWhite;
                } else if (model.getBoard()[i][j] == BoardModel.Cell.Black)
                {
                    iii = iBlack;
                }


                graphics2D.drawImage(
                    iii,
                    BEGIN_X + i * SIZE + i,
                    BEGIN_Y + j * SIZE + j,
                    SIZE,
                    SIZE,
                    this);
            }
        }
        
        if(drawLine)
        {
            graphics2D.setColor(Color.red);
            graphics2D.setStroke(new java.awt.BasicStroke(3.0f));
            graphics2D.drawLine(
                BEGIN_X + end1.getI()* SIZE + end1.getI() + (SIZE/2), 
                BEGIN_Y + end1.getJ()* SIZE + end1.getJ() + (SIZE/2), 
                BEGIN_X + end2.getI()* SIZE + end2.getI() + (SIZE/2), 
                BEGIN_Y + end2.getJ()* SIZE + end2.getJ() + (SIZE/2));
        }
        
        if(gModel.getLatsPoint().getI() != -1)
        {
            BoardModel.Point p = new BoardModel.Point(gModel.getLatsPoint());
            graphics2D.setColor(Color.blue);
            graphics2D.setStroke(new java.awt.BasicStroke(2.0f));
            graphics2D.drawRect(
                BEGIN_X + p.getI() * SIZE + p.getI() - 1, 
                BEGIN_Y + p.getJ() * SIZE + p.getJ() - 1, 
                SIZE + 2, SIZE + 2);
            
        }

    }

    public void updateDisplay()
    {
        repaint();
    }
}
