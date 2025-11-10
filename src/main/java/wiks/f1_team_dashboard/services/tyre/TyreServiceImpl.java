package wiks.f1_team_dashboard.services.tyre;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wiks.f1_team_dashboard.entities.car.Car;
import wiks.f1_team_dashboard.entities.tyre.Tyre;
import wiks.f1_team_dashboard.entities.tyre.TyreCompound;
import wiks.f1_team_dashboard.entities.tyre.TyrePosition;
import wiks.f1_team_dashboard.entities.tyre.TyreSets;
import wiks.f1_team_dashboard.exceptions.NotFoundException;
import wiks.f1_team_dashboard.repositories.CarRepository;
import wiks.f1_team_dashboard.repositories.TyreRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TyreServiceImpl implements TyreService {
    private final TyreRepository tyreRepository;
    private final CarRepository carRepository;

    @Override
    public List<Tyre> addTyreSets(TyreSets tyreSets) {
        List<Tyre> tyres = new ArrayList<>();
        tyreSets.tyreSets().forEach(
                tyreSet ->
                        tyres.addAll(addTyreSetsByCompound(tyreSet.compound(), tyreSets.carId(),
                                tyreSets.roundId(), tyreSet.numberOfSets()))
        );
        return tyres;
    }

    private List<Tyre> addTyreSetsByCompound(TyreCompound compound, int carId, int roundId, int numberOfSets) {
        List<Tyre> tyres = new ArrayList<>();
        for (int i = 0; i < numberOfSets; i++) {
            tyres.add(addTyre(compound, carId, roundId, TyrePosition.FRONT_LEFT));
            tyres.add(addTyre(compound, carId, roundId, TyrePosition.FRONT_RIGHT));
            tyres.add(addTyre(compound, carId, roundId, TyrePosition.REAR_LEFT));
            tyres.add(addTyre(compound, carId, roundId, TyrePosition.REAR_RIGHT));
        }
        return tyres;
    }

    private Tyre addTyre(TyreCompound compound, int carId, int roundId, TyrePosition position) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new NotFoundException("Car not found"));
        Tyre tyre = new Tyre(
                0,
                position,
                compound,
                0,
                0,
                0,
                false,
                false,
                roundId,
                LocalDateTime.now(),
                car
        );
        return tyreRepository.save(tyre);
    }
}
