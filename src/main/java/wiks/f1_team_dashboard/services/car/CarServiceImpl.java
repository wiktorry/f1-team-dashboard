package wiks.f1_team_dashboard.services.car;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wiks.f1_team_dashboard.entities.car.Car;
import wiks.f1_team_dashboard.exceptions.BadRequestException;
import wiks.f1_team_dashboard.repositories.CarRepository;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    @Override
    public Car createCar() {
        long cars = carRepository.count();
        if (cars == 2) {
            throw new BadRequestException("Team already has 2 cars");
        }
        Car car = new Car(
                0,
                null,
                new ArrayList<>()
        );
        return carRepository.save(car);
    }

}
