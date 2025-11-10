package wiks.f1_team_dashboard.entities.round;

import wiks.f1_team_dashboard.entities.tyre.TyreCompoundSets;

import java.util.List;

public record RoundStartRequest(String trackName, List<TyreCompoundSets> tyreSets) {
}
