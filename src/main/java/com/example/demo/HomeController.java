package com.example.demo;

import com.example.demo.frontend.Line;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

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

        String newLocation = location;
        if (command.startsWith("cd ")) {
            newLocation = command.substring(3);
        }

        Line line = new Line();
        line.command = command;
        line.location = location;
        line.response = "I have not implemented this command yet!";
        model.addAttribute("line", line);
        model.addAttribute("newLocation", newLocation);

        if (command.startsWith("view")) {
            response.addHeader("Terminal-Full-Screen", "true");
            return "full-screen-video";
        }
        return "executed-command";
    }
}
