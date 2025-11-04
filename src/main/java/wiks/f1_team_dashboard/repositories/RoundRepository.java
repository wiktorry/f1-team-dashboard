package wiks.f1_team_dashboard.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import wiks.f1_team_dashboard.entities.round.Round;

public interface RoundRepository extends JpaRepository<Round, Integer> {
    Round findRoundByIsActive(boolean isActive);
}
