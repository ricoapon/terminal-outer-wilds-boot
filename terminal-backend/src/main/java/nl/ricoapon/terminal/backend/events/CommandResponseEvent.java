package nl.ricoapon.terminal.backend.events;

/**
 * @param response    The output of the command.
 * @param newLocation The new directory the user is in.
 */
public record CommandResponseEvent(String response, String newLocation) implements TerminalEvent {
    public CommandResponseEvent(String response) {
        this(response, null);
    }
}
