package wiks.f1_team_dashboard.entities.tyre;

import java.util.List;

public record TyreSets(List<TyreCompoundSets> tyreSets, int carId, int roundId) {
}
