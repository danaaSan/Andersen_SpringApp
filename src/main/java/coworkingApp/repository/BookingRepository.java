package coworkingApp.repository;

import coworkingApp.entity.Booking;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BookingRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(Booking booking) {
        entityManager.persist(booking);
    }

    public Optional<Booking> findById(int id) {
        Booking booking = entityManager.find(Booking.class, id);
        return Optional.ofNullable(booking);
    }


    @Transactional
    public void delete(Booking booking) {
        if (entityManager.contains(booking)) {
            entityManager.remove(booking);
        } else {
            Booking attached = entityManager.merge(booking);
            entityManager.remove(attached);
        }
    }

    public List<Booking> findAll() {
        return entityManager.createQuery("SELECT b FROM Booking b", Booking.class)
                .getResultList();
    }

    public List<Booking> findByCustomerId(int userId) {
        return entityManager.createQuery(
                        "SELECT b FROM Booking b WHERE b.customer.id = :userId", Booking.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}

