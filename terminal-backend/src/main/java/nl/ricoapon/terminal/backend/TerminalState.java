package nl.ricoapon.terminal.backend;

import nl.ricoapon.terminal.backend.filesystem.FileSystemPath;

public class TerminalState {
    public FileSystemPath.Absolute currentDirectory;

    public TerminalState() {
        currentDirectory = FileSystemPath.ofAbsolute("/");
    }

    public String serializeToString() {
        // TODO: make something generic. Jackson?
        return currentDirectory.getPathAsString();
    }

    public static TerminalState ofString(String terminalStateAsString) {
        TerminalState terminalState = new TerminalState();
        terminalState.currentDirectory = FileSystemPath.ofAbsolute(terminalStateAsString);
        return terminalState;
    }
}
