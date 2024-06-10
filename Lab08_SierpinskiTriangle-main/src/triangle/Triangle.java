package triangle;

import resizable.ResizableImage;

import java.awt.*;
import java.awt.image.BufferedImage;

import static resizable.Debug.print;

/**
 * Implement your Sierpinski Triangle here.
 *
 *
 * You only need to change the drawTriangle
 * method!
 *
 *
 * If you want to, you can also adapt the
 * getResizeImage() method to draw a fast
 * preview.
 *
 */
public class Triangle implements ResizableImage {
    int drawTriangle = 0;

    private static final Color[] COLORS = {
            new Color(255, 179, 186), // Pastel Red
            new Color(255, 223, 186), // Pastel Orange
            new Color(255, 255, 186), // Pastel Yellow
            new Color(186, 255, 201), // Pastel Green
            new Color(186, 255, 255), // Pastel Cyan
            new Color(186, 218, 255), // Pastel Blue
            new Color(218, 186, 255), // Pastel Purple
            new Color(255, 186, 255)  // Pastel Pink
    };

    /**
     * change this method to implement the triangle!
     * @param size the outer bounds of the triangle
     * @return an Image containing the Triangle
     */
    private BufferedImage drawEquilateralTriangle(Dimension size) {
        print("drawTriangle: " + ++drawTriangle + " size: " + size);
        BufferedImage bufferedImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gBuffer = (Graphics2D) bufferedImage.getGraphics();
        gBuffer.setColor(Color.black);

        // Define triangle vertices
        int x1 = 0;
        int y1 = size.height - 1;
        int x2 = size.width - 1;
        int y2 = size.height - 1;
        int x3 = size.width / 2;
        int y3 = 0;

        // Draw the outer triangle
        int[] xPoints = {x1, x2, x3};
        int[] yPoints = {y1, y2, y3};
        gBuffer.drawPolygon(xPoints, yPoints, 3);

        // Recursively draw inner triangles
        drawInnerTriangles(gBuffer, x1, y1, x2, y2, x3, y3, 0);

        return bufferedImage;
    }

    private void drawInnerTriangles(Graphics2D g, int x1, int y1, int x2, int y2, int x3, int y3, int depth) {
        if (Math.abs(x1 - x2) < 5 && Math.abs(y1 - y2) < 5)
            return;

        // Calculate midpoints
        int mx1 = (x1 + x2) / 2;
        int my1 = (y1 + y2) / 2;
        int mx2 = (x2 + x3) / 2;
        int my2 = (y2 + y3) / 2;
        int mx3 = (x1 + x3) / 2;
        int my3 = (y1 + y3) / 2;

        // Set color for the current depth
        g.setColor(COLORS[depth % COLORS.length]);

        // Draw and fill the inner triangle connecting midpoints
        int[] xPoints = {mx1, mx2, mx3};
        int[] yPoints = {my1, my2, my3};
        g.fillPolygon(xPoints, yPoints, 3);
        g.setColor(Color.black);
        g.drawPolygon(xPoints, yPoints, 3);

        // Draw smaller triangles recursively
        drawInnerTriangles(g, x1, y1, mx1, my1, mx3, my3, depth + 1);
        drawInnerTriangles(g, mx1, my1, x2, y2, mx2, my2, depth + 1);
        drawInnerTriangles(g, mx3, my3, mx2, my2, x3, y3, depth + 1);
    }

    BufferedImage bufferedImage;
    Dimension bufferedImageSize;

    @Override
    public Image getImage(Dimension triangleSize) {
        if (triangleSize.equals(bufferedImageSize))
            return bufferedImage;
        bufferedImage = drawEquilateralTriangle(triangleSize);
        bufferedImageSize = triangleSize;
        return bufferedImage;
    }

    @Override
    public Image getResizeImage(Dimension size) {
        BufferedImage bufferedImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gBuffer = (Graphics2D) bufferedImage.getGraphics();
        gBuffer.setColor(Color.pink);
        int border = 2;
        gBuffer.drawRect(border, border, size.width - 2 * border, size.height - 2 * border);
        return bufferedImage;
    }
}
