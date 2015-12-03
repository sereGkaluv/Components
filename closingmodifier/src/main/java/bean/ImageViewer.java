package bean;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.StreamCorruptedException;

/**
 * Created by f00 on 30.11.15.
 */
public class ImageViewer {
    private BufferedImage image;  /* TODO: FastBitMap is Catalano */
    String imageInfo;

    public ImageViewer(BufferedImage image, String savename) {
        //    saveImage(image,savename);
        JOptionPane.showMessageDialog(null, image.toIcon(), "Original image", JOptionPane.PLAIN_MESSAGE);
    }


    private void saveImage(BufferedImage image, String savename) {
        try {
            File outputfile = new File("Documentation/Images/" + savename + ".png"); /* TODO */
            ImageIO.write(image, "png", outputfile);

        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }
}
