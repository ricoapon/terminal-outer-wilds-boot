package nl.ricoapon.terminal.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.function.Consumer;

public class JInput extends JTextField {

    public JInput(Consumer<String> commandConsumer) {
        this.addKeyListener(createKeyListenerForCommand(commandConsumer));
        this.setBackground(Color.black);
        this.setForeground(Color.green);
        this.setCaretColor(Color.green);
        this.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        this.setBorder(BorderFactory.createLineBorder(Color.GREEN));
    }

    private KeyListener createKeyListenerForCommand(Consumer<String> commandConsumer) {
        JInput jTextField = this;
        return new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        String command = jTextField.getText();
                        if (command.equals("")) return;

                        jTextField.setText("");
                        jTextField.setEditable(false);

                        // Process command
                        commandConsumer.accept(command);

                        jTextField.setEditable(true);

                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }

            public void keyTyped(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
            }
        };
    }
}
