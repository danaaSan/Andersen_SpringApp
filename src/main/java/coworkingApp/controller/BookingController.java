package coworkingApp.controller;

import coworkingApp.service.BookingService;
import coworkingApp.entity.Booking;
import coworkingApp.model.BookingInputModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // Страница со всеми бронированиями
    @GetMapping
    public List<Booking> listBookings() {
        return bookingService.getAllBookings();
    }

    // Обработка формы для добавления бронирования
    @PostMapping
    public ResponseEntity<Booking> addBooking(
            @Valid @RequestBody BookingInputModel bookingInput
    ) {
        try {
            Booking booking = bookingService.addBooking(
                    bookingInput.getSpaceId(),
                    bookingInput.getUserId(),
                    bookingInput.getDate(),
                    bookingInput.getTime()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(booking);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> cancelBooking(@PathVariable int bookingId) {
        try {
            bookingService.cancelBooking(bookingId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Booking>> getBookingsByUser(@PathVariable int userId) {
        List<Booking> bookings = bookingService.getBookingsByUser(userId);
        if (bookings.isEmpty()) {
            return ResponseEntity.noContent().build(); // Возвращаем 204 No Content, если нет данных
        }
        return ResponseEntity.ok(bookings);
    }

}
