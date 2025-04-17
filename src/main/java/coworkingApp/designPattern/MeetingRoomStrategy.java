package coworkingApp.designPattern;

import coworkingApp.entity.CoworkingSpace;
import java.time.LocalDate;
import java.time.LocalTime;

public class MeetingRoomStrategy implements PriceStrategy {
    @Override
    public double calculatePrice(CoworkingSpace space, LocalDate date, LocalTime time) {
        if (time.isAfter(LocalTime.of(18, 0))) {
            return space.getPrice() * 1.5; // после 18:00 — дороже
        }
            return space.getPrice();
    }
}
