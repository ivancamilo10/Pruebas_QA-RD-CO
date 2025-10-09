import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Swing extends JPanel implements KeyListener {

    int x = 200, y = 200; // posición del bloque
    int size = 50;        // tamaño del bloque

    public Swing() {
        JFrame frame = new JFrame("Mini Minecraft");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.addKeyListener(this);
        frame.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(new Color(139, 69, 19)); // color marrón tipo tierra
        g.fillRect(x, y, size, size);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) x -= 10;
        if (key == KeyEvent.VK_RIGHT) x += 10;
        if (key == KeyEvent.VK_UP) y -= 10;
        if (key == KeyEvent.VK_DOWN) y += 10;
        repaint();
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        new Swing();
    }
}
