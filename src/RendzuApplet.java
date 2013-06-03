/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rendzu;

import java.applet.*;
//import java.awt.*;
//import java.awt.event.*;
//import javax.swing.*;

/**
 *
 * @author Admin
 */
public class RendzuApplet extends Applet implements Runnable
{
    //private boolean isRun = false;
    //JButton buttonRun;
    GameJPanel game;

    
    public void init()
    {
        game = new GameJPanel();
        add(game);
	/*
	JPanel panel = new JPanel();
	panel.setBackground(Color.gray);


	buttonRun = new JButton("Run");

	buttonRun.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent e)
	    {

		if (isRun)
		{
		    stop();
		} else
		{
		    //go();
		}
	    }
	});

	panel.add(buttonRun);
	*/

    }

    public void run()
    {
        /*
        while (isRun)
        {
            System.out.println("Run loop");
            try
            {
                //model.stepLife();
                //lifePanel.repaint();

                Thread.sleep(500);

            } catch (InterruptedException ex)
            {
                ex.printStackTrace();
            }
        }
        */ 
    }

    
}
