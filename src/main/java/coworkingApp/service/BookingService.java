package coworkingApp.service;

import coworkingApp.entity.Booking;
import coworkingApp.entity.CoworkingSpace;
import coworkingApp.entity.user.User;
import coworkingApp.repository.BookingRepository;
import coworkingApp.repository.SpaceRepository;
import coworkingApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void addBooking(int spaceId, int userId, LocalDate date, LocalTime time) {
        CoworkingSpace space = spaceRepository.findById(spaceId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if (space != null && user != null && space.isAvailable()) {
            Booking booking = new Booking(space, user);
            booking.setDate(date);
            booking.setTime(time);
            bookingRepository.save(booking);

            space.setAvailable(false);
            spaceRepository.save(space);
        }
    }

    @Transactional
    public void cancelBooking(int bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking != null) {
            CoworkingSpace space = booking.getCoworkingSpace();
            space.setAvailable(true);
            spaceRepository.save(space);

            bookingRepository.delete(booking);
        }
    }

    public List<Booking> getBookingsByUser(int userId) {
        return bookingRepository.findByCustomerId(userId);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}
