package nl.ricoapon.terminal.backend.events;

/**
 * @param text The full text to display.
 */
public record FullScreenTextEvent(String text) implements TerminalEvent {
}
