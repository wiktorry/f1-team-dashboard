package wiks.f1_team_dashboard.services.tyre;

import wiks.f1_team_dashboard.entities.tyre.Tyre;
import wiks.f1_team_dashboard.entities.tyre.TyreSets;

import java.util.List;

public interface TyreService {
    List<Tyre> addTyreSets(TyreSets request);
}
