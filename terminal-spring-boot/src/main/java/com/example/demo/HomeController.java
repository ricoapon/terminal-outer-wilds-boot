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
        TerminalEvent terminalEvent = new TerminalFacade().processCommand(command);
        return switch (terminalEvent) {
            case CommandResponseEvent event -> {
                Line line = new Line();
                line.command = command;
                line.location = location;
                line.response = event.getResponse();
                model.addAttribute("line", line);

                if (event.getNewLocation() != null) {
                    model.addAttribute("newLocation", event.getNewLocation());
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

                model.addAttribute("lines", Line.from(event.getVideoLines()));
                model.addAttribute("uniqueId", new Date().getTime());

                yield "full-screen-video";
            }
            case FullScreenTextEvent event -> // TODO
                    "";
        };
    }
}
