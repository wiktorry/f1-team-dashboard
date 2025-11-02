package wiks.f1_team_dashboard.entities.tyre;

import java.util.List;

public record TyreSetsRequest(List<TyreSetRequest> tyreSets, int carId) {
}
