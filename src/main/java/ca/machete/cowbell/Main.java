package ca.machete.cowbell;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Root root = new Root();
        Layer layer = new Layer();
        root.connect(layer);

        Node redSquare = new Node() {

            @Override
            public void paint(PaintContext paintContext) {
                Graphics2D graphics = paintContext.getGraphics();
                graphics.setPaint(Color.RED);
                graphics.fillRect(0, 0, 20, 20);
            }
        };

        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                Connection connection = layer.connect(redSquare);
                connection.getTransform().translate(x * 30, y * 30);
            }
        }

        final Canvas canvas = new Canvas(root);
        canvas.setPreferredSize(new Dimension(400, 400));

        frame.setContentPane(canvas);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        Timer timer = new Timer(50, new ActionListener() {
            int t = 0;

            @Override
            public void actionPerformed(ActionEvent arg0) {
                t++;
                
                canvas.getCamera().setViewTransform(AffineTransform.getTranslateInstance(t % 30, t % 30));
                canvas.repaint();
            }

        });
        timer.setRepeats(true);
        timer.start();
    }
}
