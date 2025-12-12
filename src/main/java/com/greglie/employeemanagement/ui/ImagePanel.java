package com.greglie.employeemanagement.ui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;

public class ImagePanel extends JPanel {

    private BufferedImage image;

    public ImagePanel(String imagePath) {
        try {
            URL imageUrl = getClass().getResource("/" + imagePath);
            if (imageUrl == null) {
                System.err.println("Image not found: " + imagePath); // Debugging
            } else {
                image = ImageIO.read(imageUrl);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            // Handle the exception, e.g., show an error message
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    }
}