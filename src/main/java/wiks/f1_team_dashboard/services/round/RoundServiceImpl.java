package wiks.f1_team_dashboard.services.round;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wiks.f1_team_dashboard.entities.car.Car;
import wiks.f1_team_dashboard.entities.round.Round;
import wiks.f1_team_dashboard.entities.round.RoundStartRequest;
import wiks.f1_team_dashboard.entities.tyre.TyreCompoundSets;
import wiks.f1_team_dashboard.entities.tyre.TyreSets;
import wiks.f1_team_dashboard.repositories.CarRepository;
import wiks.f1_team_dashboard.repositories.RoundRepository;
import wiks.f1_team_dashboard.services.tyre.TyreService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoundServiceImpl implements RoundService {
    private final RoundRepository roundRepository;
    private final CarRepository carRepository;
    private final TyreService tyreService;

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
        round = roundRepository.save(round);
        addTyresForRound(request.tyreSets(), round.getId());
        return round;
    }

    private void addTyresForRound(List<TyreCompoundSets> tyreCompoundSets, int roundId) {
        List<Car> cars = carRepository.findAll();
        cars.forEach(car -> {
            TyreSets tyreSets = new TyreSets(tyreCompoundSets, car.getId(), roundId);
            tyreService.addTyreSets(tyreSets);
        });
    }
}
