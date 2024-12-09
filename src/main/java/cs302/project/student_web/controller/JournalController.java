package cs302.project.student_web.controller;

import cs302.project.student_web.model.JournalModel;
import cs302.project.student_web.repository.JournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    private JournalRepository journalRepository;

    // Your existing list method
    @GetMapping
    public String getJournal(Model model) {
        model.addAttribute("journals", journalRepository.findAll());
        return "journal/list";
    }

    // Add view method for single journal
    @GetMapping("/{id}")
    public String viewJournal(@PathVariable Long id, Model model) {
        return journalRepository.findById(id)
                .map(journal -> {
                    model.addAttribute("journal", journal);
                    return "journal/view";
                })
                .orElse("redirect:/journal");
    }

    // Add delete method
    @DeleteMapping("/{id}")
    public String deleteJournal(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        journalRepository.findById(id).ifPresent(journal -> {
            journalRepository.delete(journal);
            redirectAttributes.addFlashAttribute("message",
                    "Journal entry deleted successfully");
        });
        return "redirect:/journal";
    }
}