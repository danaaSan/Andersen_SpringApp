package coworkingApp.controller;

import coworkingApp.entity.Booking;
import coworkingApp.entity.CoworkingSpace;
import coworkingApp.entity.SpaceType;
import coworkingApp.model.BookingInputModel;
import coworkingApp.model.SpaceInputModel;
import coworkingApp.service.SpaceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RestController
@RequestMapping("/")
public class SpaceController {

    @Autowired
    private SpaceService spaceService;

    @GetMapping("/allSpaces")
    public List<CoworkingSpace> listSpaces() {
        return spaceService.getAllSpaces();
    }

    @PostMapping("/addSpace")
    public ResponseEntity<CoworkingSpace> addSpace(
            @Valid @RequestBody SpaceInputModel spaceInput
    ) {
        try {
            CoworkingSpace space = spaceService.addCoworkingSpace(
                    spaceInput.getType(),
                    spaceInput.getPrice());
            return ResponseEntity.status(HttpStatus.CREATED).body(space);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/removeSpace/{spaceId}")
    public ResponseEntity<Void> deleteSpace(@PathVariable int spaceId) {
        try {
            spaceService.removeSpace(spaceId);
            System.out.println("removed");
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/availableSpaces")
    public List<CoworkingSpace> showAvailableSpaces() {
        return spaceService.getAvailableSpaces();
    }



}

