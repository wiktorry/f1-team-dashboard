package wiks.f1_team_dashboard.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wiks.f1_team_dashboard.entities.tyre.Tyre;

@Repository
public interface TyreRepository extends JpaRepository<Tyre, Integer> {
}
