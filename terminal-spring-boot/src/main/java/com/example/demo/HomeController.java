package com.example.demo;

import com.example.demo.frontend.Line;
import nl.ricoapon.terminal.backend.TerminalFacade;
import nl.ricoapon.terminal.backend.TerminalState;
import nl.ricoapon.terminal.backend.events.CommandResponseEvent;
import nl.ricoapon.terminal.backend.events.FullScreenTextEvent;
import nl.ricoapon.terminal.backend.events.FullScreenVideoEvent;
import nl.ricoapon.terminal.backend.events.TerminalEvent;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    private final TerminalFacade terminalFacade;

    public HomeController(TerminalFacade terminalFacade) {
        this.terminalFacade = terminalFacade;
    }

    @RequestMapping("/")
    public String home(HttpServletResponse response) {
        // Every time we refresh the page, we want to refresh the entire state.
        // Do we really?
        response.addCookie(new Cookie("Terminal-State", new TerminalState().serializeToString()));

        return "index";
    }

    @RequestMapping("/post-command")
    public String postCommand(HttpServletResponse response, Model model,
                              @RequestParam("command") String command,
                              @RequestParam("location") String location,
                              @CookieValue("Terminal-State") String terminalStateAsString) {
        TerminalState terminalState = TerminalState.ofString(terminalStateAsString);

        // Process command
        TerminalEvent terminalEvent = terminalFacade.processCommand(command, terminalState);

        // If the state changed, then we store it again.
        // A quite rudimentary approach to equals on strings, but it works.
        String newTerminalStateAsString = terminalState.serializeToString();
        if (!newTerminalStateAsString.equals(terminalStateAsString)) {
            response.addCookie(new Cookie("Terminal-State", newTerminalStateAsString));
        }

        List<String> hxTriggerNames = new ArrayList<>();

        return switch (terminalEvent) {
            case CommandResponseEvent event -> {
                Line line = new Line();
                line.command = command;
                line.location = location;
                line.response = event.response();
                model.addAttribute("line", line);

                if (event.newLocation() != null) {
                    model.addAttribute("newLocation", event.newLocation());
                }

                yield "executed-command";
            }
            case FullScreenVideoEvent event -> {
                hxTriggerNames.add("startFullScreen");

                // Create a line without a response to indicate the command was run.
                Line line = new Line();
                line.command = command;
                line.location = location;
                model.addAttribute("line", line);

                model.addAttribute("lines", Line.from(event.videoLines()));
                model.addAttribute("uniqueId", new Date().getTime());
                addHxTriggerHeaders(response, hxTriggerNames);

                yield "full-screen-video";
            }
            case FullScreenTextEvent event -> {
                hxTriggerNames.add("startFullScreen");

                // Create a line without a response to indicate the command was run.
                Line line = new Line();
                line.command = command;
                line.location = location;
                model.addAttribute("line", line);

                model.addAttribute("fullText", event.text());
                addHxTriggerHeaders(response, hxTriggerNames);

                yield "full-screen-text";
            }
        };
    }

    private void addHxTriggerHeaders(HttpServletResponse response, List<String> headers) {
        if (headers.size() == 0) {
            return;
        }

        if (headers.size() == 1) {
            response.addHeader("HX-Trigger-After-Swap", headers.get(0));
            return;
        }

        String fullHeader = "{" +
                headers.stream().map(header -> "\"" + header + "\": \"\"").collect(Collectors.joining(",")) +
                "}";
        response.addHeader("HX-Trigger-After-Swap", fullHeader);
    }
}
