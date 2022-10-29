package nl.ricoapon.terminal.backend.events;

public sealed interface TerminalEvent permits CommandResponseEvent, FullScreenTextEvent, FullScreenVideoEvent {

}
