package cs302.project.student_web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/journal")
public class JournalController {
    @GetMapping
    public String getJournal(

    ) {
        return "journal/list";
    }
}