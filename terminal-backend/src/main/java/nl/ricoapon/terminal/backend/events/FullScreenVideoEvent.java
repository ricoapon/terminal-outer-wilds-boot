package nl.ricoapon.terminal.backend.events;

import java.util.List;

/**
 * @param videoLines The video lines to display on screen one by one.
 */
public record FullScreenVideoEvent(List<VideoLine> videoLines) implements TerminalEvent {
    public static class VideoLine {
        public String location;
        public String command;
        public String response;
    }
}
