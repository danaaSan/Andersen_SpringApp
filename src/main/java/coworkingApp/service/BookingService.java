package coworkingApp.service;

import coworkingApp.designPattern.PriceStrategy;
import coworkingApp.designPattern.PriceStrategyFactory;
import coworkingApp.entity.Booking;
import coworkingApp.entity.CoworkingSpace;
import coworkingApp.entity.User;
import coworkingApp.repository.BookingRepository;
import coworkingApp.repository.SpaceRepository;
import coworkingApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    private Booking booking;

    @Transactional
    public Booking addBooking(int spaceId, int userId, LocalDate date, LocalTime time) {
        CoworkingSpace space = spaceRepository.findById(spaceId)
                .orElseThrow(() -> new IllegalArgumentException("Space with ID " + spaceId + " not found."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " not found."));

        if (!space.isAvailable()) {
            throw new IllegalStateException("Space with ID " + spaceId + " is not available.");
        }

        PriceStrategy strategy = PriceStrategyFactory.getStrategy(space.getType());
        double price = strategy.calculatePrice(space, date, time);
        Booking booking = new Booking(space, user);
        booking.setDate(date);
        booking.setTime(time);
        booking.setPrice(price);
        bookingRepository.save(booking);

        space.setAvailable(false);
        spaceRepository.save(space);

        return booking;
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

    @Cacheable(value = "booking", key = "#root.args[0]")
    @Transactional(readOnly = true)
    public List<Booking> getBookingsByUser(int userId) {
        return bookingRepository.findByCustomer_Id(userId);
    }

    @Transactional(readOnly = true)
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

}
