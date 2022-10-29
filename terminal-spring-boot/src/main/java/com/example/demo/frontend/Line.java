package com.example.demo.frontend;

import nl.ricoapon.terminal.backend.events.FullScreenVideoEvent;

import java.util.List;
import java.util.stream.Collectors;

public class Line {
    public String location;
    public String command;
    public String response;

    public static Line from(FullScreenVideoEvent.VideoLine videoLine) {
        Line line = new Line();
        line.location = videoLine.location;
        line.command = videoLine.command;
        line.response = videoLine.response;
        return line;
    }

    public static List<Line> from(List<FullScreenVideoEvent.VideoLine> videoLines) {
        return videoLines.stream()
                .map(Line::from)
                .collect(Collectors.toList());
    }
}
