package nl.ricoapon.terminal.backend.events;

public final class CommandResponseEvent implements TerminalEvent {
    /** The output of the command. */
    private final String response;
    /** The new directory the user is in. */
    private final String newLocation;

    public CommandResponseEvent(String response, String newLocation) {
        this.response = response;
        this.newLocation = newLocation;
    }

    public String getResponse() {
        return response;
    }

    public String getNewLocation() {
        return newLocation;
    }
}
