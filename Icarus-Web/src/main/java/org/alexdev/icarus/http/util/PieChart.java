package org.alexdev.icarus.http.util;

import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PieChart {
    Slice[] slices = {
            new Slice(5, Color.blue),
            new Slice(33, Color.orange),
            new Slice(20, Color.yellow),
            new Slice(15, Color.red),
            new Slice(27, Color.cyan)
    };


    public PieChart(BufferedImage image) {
        drawPie((Graphics2D) image.getGraphics(), new Rectangle(image.getWidth(), image.getHeight()), slices);
    }

    void drawPie(Graphics2D g, Rectangle area, Slice[] slices) {

        double total = 0.0D;

        for (int i = 0; i < slices.length; i++) {
            total += slices[i].value;
        }

        double curValue = 0;
        int startAngle;

        for (int i = 0; i < slices.length; i++) {

            startAngle = (int) (curValue * 361 / total);
            int arcAngle = (int) (slices[i].value * 361 / total);

            g.setColor(slices[i].color);
            g.fillArc((int)area.getX(), (int)area.getY(), (int)area.getWidth(), (int)area.getHeight(), startAngle, arcAngle);

            curValue += slices[i].value;
        }
    }

    private class Slice {
        double value;
        Color color;

        public Slice(double value, Color color) {
            this.value = value;
            this.color = color;
        }
    }
}