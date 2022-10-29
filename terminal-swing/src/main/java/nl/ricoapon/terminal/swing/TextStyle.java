package nl.ricoapon.terminal.swing;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;

enum TextStyle {
    COMMAND(Color.GREEN, Color.BLACK),
    RESPONSE(Color.WHITE, Color.BLACK);

    private final SimpleAttributeSet simpleAttributeSet;

    TextStyle(Color foregroundColor, Color backgroundColor) {
        simpleAttributeSet = new SimpleAttributeSet();
        StyleConstants.setForeground(simpleAttributeSet, foregroundColor);
        StyleConstants.setBackground(simpleAttributeSet, backgroundColor);
    }

    public SimpleAttributeSet getSimpleAttributeSet() {
        return simpleAttributeSet;
    }
}
