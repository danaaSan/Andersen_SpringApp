package coworkingApp.controller;

import coworkingApp.entity.SpaceType;
import coworkingApp.model.SpaceInputModel;
import coworkingApp.service.SpaceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class SpaceController {

    @Autowired
    private SpaceService spaceService;

    @GetMapping("/allSpaces")
    public String showAllSpaces(Model model) {
        model.addAttribute("spaces", spaceService.getAllSpaces());
        return "allSpaces";
    }

    // форма для добавления
    @GetMapping("/addSpace")
    public String showAddForm(Model model) {
        model.addAttribute("spaceInput", new SpaceInputModel());
        model.addAttribute("types", SpaceType.values());
        return "addSpace";
    }

    // обработка формы
    @PostMapping("/addSpace")
    public String addSpace(@ModelAttribute("spaceInput") SpaceInputModel spaceInput,
                           BindingResult result,
                           Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()){
            return "addSpace";
        }
        spaceService.addCoworkingSpace(spaceInput.getType(), spaceInput.getPrice());
        redirectAttributes.addFlashAttribute("success", "Space added successfully");
        return "redirect:/addSpace";
    }

    // Removing a space by ID
    @GetMapping("/removeSpace/{spaceId}")
    public String removeSpace(@PathVariable("spaceId") int spaceId, RedirectAttributes redirectAttributes) {
        try {
            spaceService.removeSpace(spaceId);
            redirectAttributes.addFlashAttribute("successSpace", "Space removed successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorSpace", "Failed to remove space. Please try again.");
        }
        return "redirect:/allSpaces";
    }

    @GetMapping("/availableSpaces")
    public String showAvailableSpaces(Model model) {
        model.addAttribute("spaces", spaceService.getAvailableSpaces());
        return "availableSpaces";
    }



}

