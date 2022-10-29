package nl.ricoapon.terminal.backend.events;

public final class FullScreenTextEvent implements TerminalEvent {
    /** The full text to display. */
    private final String text;

    public FullScreenTextEvent(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
