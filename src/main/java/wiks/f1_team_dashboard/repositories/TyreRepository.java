package wiks.f1_team_dashboard.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wiks.f1_team_dashboard.entities.car.Car;
import wiks.f1_team_dashboard.entities.tyre.Tyre;

import java.util.List;

@Repository
public interface TyreRepository extends JpaRepository<Tyre, Integer> {
    List<Tyre> findAllByCarAndIsNowUsed(Car car, boolean isNowUsed);
}
