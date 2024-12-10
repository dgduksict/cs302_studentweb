package cs302.project.student_web.controller;

import cs302.project.student_web.model.JournalModel;
import cs302.project.student_web.repository.JournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    private JournalRepository journalRepository;

    // Add a redirect from base /journal to /journal/list
    @GetMapping("")
    public String redirectToList() {
        return "redirect:/journal/list";
    }

    // Change this to explicit /list mapping
    @GetMapping("/list")
    public String getJournal(Model model) {
        model.addAttribute("journals", journalRepository.findAll());
        return "journal/list";
    }

    @GetMapping("/{id}")
    public String viewJournal(@PathVariable Long id, Model model) {
        return journalRepository.findById(id)
                .map(journal -> {
                    model.addAttribute("journal", journal);
                    return "journal/view";
                })
                .orElse("redirect:/journal/list");  // Updated redirect path
    }

    @DeleteMapping("/{id}")
    public String deleteJournal(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        journalRepository.findById(id).ifPresent(journal -> {
            journalRepository.delete(journal);
            redirectAttributes.addFlashAttribute("message",
                    "Journal entry deleted successfully");
        });
        return "redirect:/journal/list";  // Updated redirect path
    }

    // Add create journal methods
    @PostMapping("/create")
    public String createJournal(@ModelAttribute JournalModel journal, RedirectAttributes redirectAttributes) {
        try {
            journal.setCreatedAt(LocalDateTime.now());
            journalRepository.save(journal);
            redirectAttributes.addFlashAttribute("success", "Journal created successfully!");
            return "redirect:/journal/list";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error creating journal: " + e.getMessage());
            return "redirect:/journal/list";
        }
    }
}