package coworkingApp.model;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public class BookingInputModel {

    @Min(value = 1, message = "Space ID must be greater than 0")
    private int spaceId;

    @Min(value = 1, message = "User ID must be greater than 0")
    private int userId;

    @NotNull(message = "Booking date cannot be null")
    @Future(message = "Booking date must be in the future")
    private LocalDate date;

    @NotNull(message = "Booking time cannot be null")
    private LocalTime time;

    // Getters and Setters
    public int getSpaceId() { return spaceId; }
    public void setSpaceId(int spaceId) { this.spaceId = spaceId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getTime() { return time; }
    public void setTime(LocalTime time) { this.time = time; }
}