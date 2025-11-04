package wiks.f1_team_dashboard.services.round;

import wiks.f1_team_dashboard.entities.round.Round;
import wiks.f1_team_dashboard.entities.round.RoundStartRequest;

public interface RoundService {
    Round startNewRound(RoundStartRequest request);
}
