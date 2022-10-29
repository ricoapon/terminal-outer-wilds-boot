package nl.ricoapon.terminal.swing;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;

public class JOutput extends JScrollPane {
    private final JTextPane area;

    public JOutput() {
        this(new JTextPane());
    }

    private JOutput(JTextPane jTextPane) {
        super(jTextPane);
        this.area = jTextPane;

        area.setBackground(Color.black);
        area.setCaretColor(Color.green);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        area.setEditable(false);

        this.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.setPreferredSize(new Dimension(640, 460));

    }

    public void print(String text, TextStyle textStyle) {
        try {
            area.getStyledDocument().insertString(area.getStyledDocument().getLength(), text + "\n", textStyle.getSimpleAttributeSet());
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }
}
