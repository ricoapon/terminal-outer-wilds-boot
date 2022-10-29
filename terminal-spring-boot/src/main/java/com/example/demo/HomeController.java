package com.example.demo;

import com.example.demo.frontend.Line;
import nl.ricoapon.terminal.backend.TerminalFacade;
import nl.ricoapon.terminal.backend.events.CommandResponseEvent;
import nl.ricoapon.terminal.backend.events.FullScreenTextEvent;
import nl.ricoapon.terminal.backend.events.FullScreenVideoEvent;
import nl.ricoapon.terminal.backend.events.TerminalEvent;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
public class HomeController {
    private final TerminalFacade terminalFacade;

    public HomeController(TerminalFacade terminalFacade) {
        this.terminalFacade = terminalFacade;
    }

    @RequestMapping("/")
    public String home() {
        return "index";
    }

    @RequestMapping("/post-command")
    public String postCommand(HttpServletResponse response, Model model,
                              @RequestParam("command") String command,
                              @RequestParam("location") String location) {
        // Set header for fun.
        if (command.equals("donotclear")) {
            response.addHeader("Terminal-Clear-Input", "false");
        }

        // Process command
        TerminalEvent terminalEvent = terminalFacade.processCommand(command, location);
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
                response.addHeader("Terminal-Full-Screen", "true");

                // Create a line without a response to indicate the command was run.
                Line line = new Line();
                line.command = command;
                line.location = location;
                model.addAttribute("line", line);

                model.addAttribute("lines", Line.from(event.videoLines()));
                model.addAttribute("uniqueId", new Date().getTime());

                yield "full-screen-video";
            }
            case FullScreenTextEvent event -> {
                response.addHeader("Terminal-Full-Screen", "true");

                // Create a line without a response to indicate the command was run.
                Line line = new Line();
                line.command = command;
                line.location = location;
                model.addAttribute("line", line);

                model.addAttribute("fullText", event.text());

                yield "full-screen-text";
            }
        };
    }
}
