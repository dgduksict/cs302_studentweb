package cs302.project.student_web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class HomeController {
    public String index() {
        return "index";
    }

    @GetMapping("/about")
    public String about() { return "about"; }
}
