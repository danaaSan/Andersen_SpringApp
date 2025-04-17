package coworkingApp.designPattern;

import coworkingApp.entity.CoworkingSpace;
import java.time.LocalDate;
import java.time.LocalTime;

public interface PriceStrategy {
    double calculatePrice(CoworkingSpace space, LocalDate date, LocalTime time);
}

