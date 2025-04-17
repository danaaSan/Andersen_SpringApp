package coworkingApp.designPattern;

import coworkingApp.entity.CoworkingSpace;
import java.time.LocalDate;
import java.time.LocalTime;

public class OpenSpaceStrategy implements PriceStrategy {
    @Override
    public double calculatePrice(CoworkingSpace space, LocalDate date, LocalTime time) {
        return space.getPrice(); // базовая цена
    }
}
