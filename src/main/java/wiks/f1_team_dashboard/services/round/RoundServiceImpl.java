package wiks.f1_team_dashboard.services.round;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wiks.f1_team_dashboard.entities.round.Round;
import wiks.f1_team_dashboard.entities.round.RoundStartRequest;
import wiks.f1_team_dashboard.repositories.RoundRepository;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RoundServiceImpl implements RoundService {
    private final RoundRepository roundRepository;

    @Override
    public Round startNewRound(RoundStartRequest request) {
        Round activeRound = roundRepository.findRoundByIsActive(true);
        if (activeRound != null) {
            activeRound.setEndDate(LocalDate.now());
            activeRound.setActive(false);
            roundRepository.save(activeRound);
        }
        Round round = new Round(
                0,
                request.trackName(),
                LocalDate.now(),
                null,
                true
        );
        return roundRepository.save(round);
    }
}
