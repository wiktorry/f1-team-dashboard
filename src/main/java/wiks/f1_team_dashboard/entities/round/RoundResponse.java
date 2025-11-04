package wiks.f1_team_dashboard.entities.round;

import java.time.LocalDate;

public record RoundResponse(int id, String trackName, LocalDate startDate,
                            LocalDate endDate, boolean isActive) {

    public RoundResponse(Round round) {
        this(round.getId(), round.getTrackName(), round.getStartDate(),
                round.getEndDate(), round.isActive());
    }
}
