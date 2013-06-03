/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rendzu;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import java.io.*;

/**
 *
 * @author Kiselev_KV
 */
public class GameJPanel extends javax.swing.JPanel implements Runnable
{

    public enum PlayerType
    {

        Man,
        Computer
    }

    public enum Player
    {

        Player1,
        Player2
    }
    private BoardModel board;
    private BoardGridView view;
    public PlayerType player1;
    public PlayerType player2;
    private JLabel info;
    private JButton newGame;
    private JCheckBox typeGame;
    private boolean isRun = false;
    public Player turn = Player.Player1;
    public boolean isStopGame = false;
    private BoardModel.Point end1;
    private BoardModel.Point end2;

    /**
     * Creates new form GameJFrame
     */
    public GameJPanel()
    {
        //initComponents();

        board = new BoardModel();
        view = new BoardGridView(board, this);
        info = new JLabel("           ");
        newGame = new JButton("Новая игра");
        newGame.setEnabled(false);
        end1 = new BoardModel.Point(0,0);
        end2 = new BoardModel.Point(0,0);

        typeGame = new javax.swing.JCheckBox("vs Компьютер", true);
        typeGame.setEnabled(false);

        player1 = PlayerType.Man;
        player2 = PlayerType.Computer;

        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setBackground(Color.gray);

        newGame.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                startGame();
            }
        });

        panel.add(typeGame);
        panel.add(newGame);
        panel.add(info);

        add(BorderLayout.CENTER, view);
        add(BorderLayout.SOUTH, panel);
        //this.setResizable(false);

        stopGame(BoardModel.Cell.Empty);

    }

    public synchronized void boardClicked(BoardModel.Point point)
    {
        if ((((turn == Player.Player1) && (player1 == PlayerType.Man))
            || ((turn == Player.Player2) && (player2 == PlayerType.Man)))
            && (isStopGame == false))
        {
            
            if(board.getCell(point) != BoardModel.Cell.Empty)
            {
                return;
            }
            
            
            if (turn == Player.Player1)
            {
                board.changeCell(point, BoardModel.Cell.White);
            } else
            {
                board.changeCell(point, BoardModel.Cell.Black);
                //changeCell(i, j, BoardModel.Cell.White);
            }

            BoardModel.Cell res = board.isEnd(end1, end2);

            if (res == BoardModel.Cell.Empty)
            {
                changePlayer();
                showTurn();
            } 
            else
            {
                //showWiner(res);
                stopGame(res);
                view.setDrawLine(true);
                view.setPoints(end1, end2);
                view.repaint();
            }
            //System.out.println("mouseClicked");
            repaint();
        }
    }

    public void stopGame(BoardModel.Cell cell)
    {
        isRun = false;
        isStopGame = true;
        showWiner(cell);

        newGame.setEnabled(true);
        typeGame.setEnabled(true);

    }

    public final void startGame()
    {
        System.out.println("startGame");
        board.setClear();
        view.setDrawLine(false);

        if (!typeGame.isSelected())
        {
            //System.out.println("typeGame=" + typeGame.isSelected());
            player2 = PlayerType.Man;
        } else
        {
            player2 = PlayerType.Computer;
        }


        if ((player1 == PlayerType.Computer) || (player2 == PlayerType.Computer))
        {
            isRun = true;
            new Thread(this).start();
        }

        turn = Player.Player1;
        showTurn();

        isRun = true;
        isStopGame = false;

        newGame.setEnabled(false);
        typeGame.setEnabled(false);

        repaint();
    }

    public void showTurn()
    {
        String title;
        if (turn == Player.Player1)
        {
            title = "Ход белых";
            info.setText(title);
        } 
        else
        {
            title = "Ход черных";
            info.setText(title);
        }

    }

    public void showWiner(BoardModel.Cell winer)
    {
        System.out.println("winer=" + winer);
        if (BoardModel.Cell.Black == winer)
        {
            info.setText("!!!Черные!!!");
            //System.out.println("set winer="+winer);
        } else if (BoardModel.Cell.White == winer)
        {
            info.setText("!!!Белые!!!");
            //System.out.println("set winer="+winer);
        }
        repaint();
    }

    public void  run()
    {

        while (isRun)
        {
            //System.out.println("run");
            try
            {
               
                    
                if ((turn == Player.Player2) && (isStopGame == false))
                {
                    //System.out.println("My turn");
                    long time1 = System.currentTimeMillis();
                    BoardModel.Point p = board.getComputerPoint(BoardModel.Cell.Black);
                    //System.out.println("Computer point i=" + p.getI() + " j=" + p.getJ());
                    System.out.println("time=" + (System.currentTimeMillis() - time1));
                    board.changeCell(p, BoardModel.Cell.Black);
                    BoardModel.Cell res = board.isEnd(end1, end2);

                    if (res == BoardModel.Cell.Empty)
                    {
                        changePlayer();
                        showTurn();
                    } 
                    else
                    {
                        //showWiner(res);
                        stopGame(res);
                        view.setDrawLine(true);
                        view.setPoints(end1, end2);
                        //view.repaint();
                    }
                    
                    repaint();
                } 
                else
                {
                    //System.out.println("Man turn");
                }
                
                
                
                Thread.sleep(300);
            } catch (InterruptedException ex)
            {
                ex.printStackTrace();
            }
        }
    }

    public void stop()
    {
        isRun = false;
    }

    public void changePlayer()
    {
        if (turn == Player.Player1)
        {
            turn = Player.Player2;
        } else
        {
            turn = Player.Player1;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(GameJPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(GameJPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(GameJPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(GameJPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new GameJPanel().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
