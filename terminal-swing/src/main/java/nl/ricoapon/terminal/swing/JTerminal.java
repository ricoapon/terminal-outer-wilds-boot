package nl.ricoapon.terminal.swing;

import nl.ricoapon.terminal.backend.TerminalFacade;
import nl.ricoapon.terminal.backend.events.CommandResponseEvent;
import nl.ricoapon.terminal.backend.events.TerminalEvent;

import javax.swing.*;
import java.awt.*;

public class JTerminal extends JFrame {

    public static void main(String[] args) {
        new JTerminal();
    }

    private final JOutput jOutput = new JOutput();

    public JTerminal() {
        super("JTerminal");

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JTextField input = new JInput(this::processCommand);

        add(jOutput);
        add(input);


        Dimension DIM = new Dimension(640, 480);
        setPreferredSize(DIM);
        setSize(DIM);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        pack();
        setVisible(true);

        // Only works when we put this all the way to the end.
        input.requestFocus();
    }

    private void processCommand(String command) {
        print(command, TextStyle.COMMAND);

        TerminalEvent terminalEvent = new TerminalFacade().processCommand(command);
        if (terminalEvent instanceof CommandResponseEvent event) {
            print(event.getResponse(), TextStyle.RESPONSE);
        }
        // TODO: implement other events.
    }

    private void print(String text, TextStyle textStyle) {
        jOutput.print(text, textStyle);
    }
}
