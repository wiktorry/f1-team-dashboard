package wiks.f1_team_dashboard.services.tyre;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wiks.f1_team_dashboard.entities.car.Car;
import wiks.f1_team_dashboard.entities.tyre.Tyre;
import wiks.f1_team_dashboard.entities.tyre.TyreCompound;
import wiks.f1_team_dashboard.entities.tyre.TyrePosition;
import wiks.f1_team_dashboard.entities.tyre.TyreSets;
import wiks.f1_team_dashboard.exceptions.BadRequestException;
import wiks.f1_team_dashboard.exceptions.NotFoundException;
import wiks.f1_team_dashboard.repositories.CarRepository;
import wiks.f1_team_dashboard.repositories.RoundRepository;
import wiks.f1_team_dashboard.repositories.TyreRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TyreServiceImpl implements TyreService {
    private final TyreRepository tyreRepository;
    private final CarRepository carRepository;
    private final RoundRepository roundRepository;

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

    @Override
    public List<Tyre> changeActiveTyreSet(List<Integer> tyreIds) {
        List<Tyre> tyres = tyreRepository.findAllById(tyreIds);
        List<Tyre> activeTyres = tyreRepository
                .findAllByCarAndIsNowUsed(tyres.getFirst().getCar(), true);
        List<Tyre> nowUsedTyres = new ArrayList<>();
        checkIfTyreSetIsProper(tyres);
        activeTyres.forEach(tyre -> {
            tyre.setNowUsed(false);
            tyreRepository.save(tyre);
        });
        tyres.forEach(tyre -> {
            tyre.setNowUsed(true);
            nowUsedTyres.add(tyreRepository.save(tyre));
        });
        return nowUsedTyres;
    }

    private void checkIfTyreSetIsProper(List<Tyre> tyres) {
        checkIfTyreSetHasAllPositions(tyres);
        checkIfTyresHaveSameCompound(tyres);
        checkIfTyreSetIsFromActiveRound(tyres);
        checkIfTyresHaveSameAssignedCar(tyres);
    }

    private void checkIfTyreSetHasAllPositions(List<Tyre> tyres) {
        List<TyrePosition> givenPositions = tyres.stream()
                .map(Tyre::getPosition)
                .toList();
        List<TyrePosition> expected = List.of(TyrePosition.values());
        if (!givenPositions.containsAll(expected) || tyres.size() != 4) {
            throw new BadRequestException("Tyre positions are not proper");
        }
    }

    private void checkIfTyresHaveSameCompound(List<Tyre> tyres) {
        boolean sameCompound = tyres.stream()
                .map(Tyre::getCompound)
                .distinct()
                .count() == 1;
        if (!sameCompound) {
            throw new BadRequestException("Tyre compound is not the same for all tires");
        }
    }

    private void checkIfTyresHaveSameAssignedCar(List<Tyre> tyres) {
        boolean sameCar = tyres.stream()
                .map(Tyre::getCar)
                .distinct()
                .count() == 1;
        if (!sameCar) {
            throw new BadRequestException("Tyres are not assigned to the same car");
        }
    }

    private void checkIfTyreSetIsFromActiveRound(List<Tyre> tyres) {
        int activeRoundId = roundRepository.findRoundByIsActive(true).getId();
        boolean allTyresFromActiveRound = tyres.stream()
                .allMatch(tyre -> tyre.getRoundId() == activeRoundId);
        if (!allTyresFromActiveRound) {
            throw new BadRequestException("Tyres are not from active round");
        }
    }

}
