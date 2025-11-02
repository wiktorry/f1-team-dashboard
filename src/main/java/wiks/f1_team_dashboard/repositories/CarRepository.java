package wiks.f1_team_dashboard.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wiks.f1_team_dashboard.entities.car.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
}
