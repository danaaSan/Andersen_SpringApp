package coworkingApp;

import coworkingApp.entity.*;
import coworkingApp.entity.user.Admin;
import coworkingApp.entity.user.Customer;
import coworkingApp.entity.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Repository
public class WorkspaceManagement {

    @PersistenceContext
    private EntityManager entityManager;


    @Transactional
    public void addCoworkingSpace( SpaceType type, double price){
        try {
            entityManager.persist(new CoworkingSpace(price, type));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Transactional
    public void removeSpace(int id) {
        CoworkingSpace space = entityManager.find(CoworkingSpace.class, id);
        if (space == null) {
            System.out.println("Coworking space with ID " + id + " not found.");
            return;
        }

        try {
            entityManager.remove(space);
            System.out.println("Coworking space with ID " + id + " removed successfully.");
        } catch (Exception e) {
            e.printStackTrace(); // Логирование ошибки
        }
    }


    @Transactional
    public void addUser(String name, String surname, String email, String userType) {
        User user;
        if ("Admin".equalsIgnoreCase(userType)) {
            user = new Admin(name, surname, email);
        } else if ("Customer".equalsIgnoreCase(userType)) {
            user = new Customer(name, surname, email);
        } else {
            System.out.println("Invalid user type: " + userType);
            return;
        }

        try {
            entityManager.persist(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void addBooking(int spaceId, int userId, LocalDate date, LocalTime time) {
        CoworkingSpace coworkingSpace = entityManager.find(CoworkingSpace.class, spaceId);
        if (coworkingSpace == null) {
            System.out.println("Space ID " + spaceId + " not found");
            return;
        }
        if (!coworkingSpace.isAvailable()) {
            System.out.println("Space ID " + spaceId + " is not available.");
            return;
        }
        User user = entityManager.find(User.class, userId);
        if (user == null) {
            System.out.println("User ID " + userId + " not found");
            return;
        }

        try {
            Booking booking = new Booking(coworkingSpace, user);
            booking.setDate(date);
            booking.setTime(time);
            entityManager.persist(booking);
            coworkingSpace.setAvailable(false);
            entityManager.merge(coworkingSpace);
            System.out.println("Success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void customersBooking(int userId) {
        try {

            TypedQuery<Booking> query = entityManager.createQuery(
                    "SELECT b FROM Booking b WHERE b.customer.id = :userId", Booking.class
            );
            query.setParameter("userId", userId);

            List<Booking> bookings = query.getResultList();

            if (bookings.isEmpty()) {
                System.out.println("User with ID " + userId + "dont have booking");
            } else {
                System.out.println("Bookings: " + userId + ":");
                for (Booking booking : bookings) {
                    System.out.println(booking);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void spacesInfo() {
        entityManager.createQuery("SELECT c FROM CoworkingSpace c", CoworkingSpace.class)
                .getResultList()
                .forEach(System.out::println);
    }


    public void availableSpacesInfo() {
        entityManager.createQuery("SELECT c FROM CoworkingSpace c WHERE c.isAvailable IS TRUE", CoworkingSpace.class)
                .getResultList()
                .forEach(System.out::println);
    }


    @Transactional
    public void cancelBooking(int bookingId) {
        Booking booking = entityManager.find(Booking.class, bookingId);
        if (booking == null) {
            System.out.println("Booking ID " + bookingId + " not found");
            return;
        }

        try {
            CoworkingSpace coworkingSpace = booking.getCoworkingSpace();
            coworkingSpace.setAvailable(true);
            entityManager.merge(coworkingSpace);
            entityManager.remove(booking);
            System.out.println("Success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void bookingInfo() {
        entityManager.createQuery("SELECT b FROM Booking b", Booking.class)
                .getResultList()
                .forEach(System.out::println);
    }

}
