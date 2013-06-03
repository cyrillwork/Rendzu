/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rendzu;

/**
 *
 * @author Kiselev_kv
 */

import java.awt.Component;
import java.awt.Image;
import java.awt.MediaTracker;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * A collection of all the demo images found in the images directory.
 * Certain classes are preloaded; the rest are loaded lazily.
 */
@SuppressWarnings("serial")
public class GameImages extends Component 
{

    private static final String[] names = 
    {
        "black.png", "white.png", "empty.png"
    };
    
    private static Map<String, Image> cache =
            new ConcurrentHashMap<String, Image>(names.length);

    private GameImages() 
    {
    }

    public static void newDemoImages() 
    {
        GameImages demoImages = new GameImages();
        for (String name : names) {
            cache.put(name, getImage(name, demoImages));
        }
    }


    /*
     * Gets the named image using the toolkit of the specified component.
     * Note that this has to work even before we have had a chance to
     * instantiate DemoImages and preload the cache.
     */
    public static Image getImage(String name, Component cmp) 
    {
        Image img = null;
        if (cache != null) 
        {
            if ((img = cache.get(name)) != null) 
            {
                return img;
            }
        }

        URLClassLoader urlLoader =
                (URLClassLoader) cmp.getClass().getClassLoader();
        URL fileLoc = urlLoader.findResource("images/" + name);
        img = cmp.getToolkit().createImage(fileLoc);

        MediaTracker tracker = new MediaTracker(cmp);
        tracker.addImage(img, 0);
        try 
        {
            tracker.waitForID(0);
            if (tracker.isErrorAny()) 
            {
                System.out.println("Error loading image " + name);
            }
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(GameImages.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //System.out.println("OK load image " + img);
        
        return img;
    }
}
