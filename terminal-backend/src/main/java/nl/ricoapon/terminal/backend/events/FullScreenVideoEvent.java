package nl.ricoapon.terminal.backend.events;

import java.util.List;

public final class FullScreenVideoEvent implements TerminalEvent {
    public static class VideoLine {
        public String location;
        public String command;
        public String response;
    }

    /** The video lines to display on screen one by one. */
    private final List<VideoLine> videoLines;

    public FullScreenVideoEvent(List<VideoLine> videoLines) {
        this.videoLines = videoLines;
    }

    public List<VideoLine> getVideoLines() {
        return videoLines;
    }
}
