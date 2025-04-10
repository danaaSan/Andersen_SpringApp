package coworkingApp.controller;

import coworkingApp.service.BookingService;
import coworkingApp.entity.Booking;
import coworkingApp.model.BookingInputModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // Страница со всеми бронированиями
    @GetMapping("/allBookings")
    public String listBookings(Model model) {
        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "allBookings";
    }

    // Страница для добавления бронирования
    @GetMapping("/addBooking")
    public String showAddBookingForm(Model model) {
        model.addAttribute("bookingInput", new BookingInputModel());
        return "addBooking";
    }

    // Обработка формы для добавления бронирования
    @PostMapping("/addBooking")
    public String addBooking(@ModelAttribute("bookingInput") @Valid BookingInputModel bookingInput,
                             BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorBooking", "Booking failed. Please check your input.");
            return "redirect:/addBooking"; // Return the form page if validation fails
        }

        try {
            bookingService.addBooking(
                    bookingInput.getSpaceId(),
                    bookingInput.getUserId(),
                    bookingInput.getDate(),
                    bookingInput.getTime()
            );
            redirectAttributes.addFlashAttribute("successBooking", "Booking successfully added!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorBooking", "Failed to add booking. Please try again.");
        }

        return "redirect:/addBooking";
    }

    @GetMapping("/selectUser")
    public String showUserSelectionForm(Model model) {
        model.addAttribute("userId", 0); // Placeholder for user input
        return "selectUser";
    }

    @PostMapping("/selectUser")
    public String handleUserSelection(@RequestParam("userId") int userId, Model model) {
        if (userId <= 0) {
            model.addAttribute("errorUser", "Invalid User ID. Please try again.");
            return "selectUser";
        }

        List<Booking> bookings = bookingService.getBookingsByUser(userId);
        if (bookings.isEmpty()) {
            model.addAttribute("infoMessage", "No bookings found for this user.");
        }
        model.addAttribute("bookings", bookings);
        model.addAttribute("userId", userId);
        return "selectUser";
    }

    // Отмена бронирования
    @GetMapping("/cancelBooking/{bookingId}")
    public String cancelBooking(@PathVariable("bookingId") int bookingId, RedirectAttributes redirectAttributes) {
        try {
            bookingService.cancelBooking(bookingId);
            redirectAttributes.addFlashAttribute("successBooking", "cancelled successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorBooking", "Failed to cancel. Please try again.");
        }
        return "redirect:/selectUser";
    }

    // Filter bookings by user ID
    @GetMapping("/userBooking/{bookingId}")
    public String getBookingsByUser(@RequestParam("userId") int userId, Model model, RedirectAttributes redirectAttributes) {
        List<Booking> bookings = bookingService.getBookingsByUser(userId);
        if (bookings.isEmpty()) {
            redirectAttributes.addFlashAttribute("infoMessage", "No bookings found for this user.");
        }
        redirectAttributes.addFlashAttribute("bookings", bookings);
        return "redirect:/selectUser";
    }

}
