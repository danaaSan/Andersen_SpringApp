package coworkingApp.entity;

import coworkingApp.entity.user.User;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private int bookingId;

    @ManyToOne
    @JoinColumn(name = "space_id", nullable = false)
    private CoworkingSpace coworkingSpace;  // Ссылка на пространство

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User customer;  // Ссылка на пользователя

    @Column(name = "booking_date", nullable = false)
    private LocalDate date;

    @Column(name = "booking_time", nullable = false)
    private LocalTime time;

    public Booking() {}

    @Autowired
    public Booking(CoworkingSpace coworkingSpace, @Qualifier("customer") User customer) {
        this.coworkingSpace = coworkingSpace;
        this.customer = customer;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", coworkingSpace=" + (coworkingSpace != null ? coworkingSpace.getSpaceId() : "null") +
                ", customer=" + (customer != null ? customer.getId() : "null") +
                ", date=" + date +
                ", time=" + time +
                '}';
    }


    // Геттеры и сеттеры
    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public CoworkingSpace getCoworkingSpace() { return coworkingSpace; }
    public void setCoworkingSpace(CoworkingSpace coworkingSpace) { this.coworkingSpace = coworkingSpace; }

    public User getCustomer() { return customer; }
    public void setCustomer(User customer) { this.customer = customer; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getTime() { return time; }
    public void setTime(LocalTime time) { this.time = time; }
}
