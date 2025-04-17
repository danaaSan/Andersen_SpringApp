package coworkingApp.designPattern;

import coworkingApp.entity.SpaceType;

public class PriceStrategyFactory {
    public static PriceStrategy getStrategy(SpaceType type) {
        return switch (type) {
            case OPEN -> new OpenSpaceStrategy();
            case MEETING_ROOM -> new MeetingRoomStrategy();
            default -> new OpenSpaceStrategy(); // по умолчанию
        };
    }
}
