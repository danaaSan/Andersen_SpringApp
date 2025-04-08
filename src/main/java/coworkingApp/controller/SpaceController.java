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

@Controller
@RequestMapping("/")
public class SpaceController {

    @Autowired
    private SpaceService spaceService;

    @GetMapping
    public String showAllSpaces(Model model) {
        model.addAttribute("spaces", spaceService.getAvailableSpaces());
        return "space-list";
    }

    // форма для добавления
    @GetMapping/*("/add")*/
    public String showAddForm(Model model) {
        model.addAttribute("spaceInput", new SpaceInputModel());
        model.addAttribute("types", SpaceType.values());
        return "addSpace";
    }

    // обработка формы
    @PostMapping/*("/add")*/
    public String addSpace(@ModelAttribute("spaceInput") SpaceInputModel spaceInput,
                           BindingResult result,
                           Model model) {
        if (result.hasErrors()){
            return "addSpace";
        }
        spaceService.addCoworkingSpace(spaceInput.getType(), spaceInput.getPrice());
        model.addAttribute("success", "Space added successfully");
        return "redirect:/";
    }
}

