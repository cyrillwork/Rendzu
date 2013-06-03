/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rendzu;

/**
 *
 * @author Kiselev_KV
 */
public class Rendzu
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {

        rendzu.GameImages.newDemoImages();

        System.out.println("" + System.getProperty("os.name"));
        System.out.println("totalmem=" + Runtime.getRuntime().totalMemory() + "(bytes)");
        System.out.println("freemem=" + Runtime.getRuntime().freeMemory() + "(bytes)");

        //int arr1[][] = new int [10][10];
        //int arr2[][] = new int [10][10];
        //arr1[0][0] = 1;
        //arr1[1][1] = 2;
        //arr1[2][2] = 3;
        //arr1[3][3] = 4;
        //System.arraycopy(arr1, 0, arr2, 0, arr1.length);
        //System.out.println("" + arr2[0][0] + arr2[1][1] + arr2[2][2]+ arr2[3][3]);


        // TODO code application logic here
        MainJFrame frame = new MainJFrame("Рендзю");
        frame.setSize(200, 200);
        frame.setVisible(true);

    }
}
