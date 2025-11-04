package wiks.f1_team_dashboard.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wiks.f1_team_dashboard.entities.round.RoundResponse;
import wiks.f1_team_dashboard.entities.round.RoundStartRequest;
import wiks.f1_team_dashboard.services.round.RoundService;

@RestController
@RequestMapping("/f1/rounds")
@RequiredArgsConstructor
public class RoundController {
    private final RoundService roundService;

    @PostMapping("/start-new-round")
    public RoundResponse startNewRound(@RequestBody RoundStartRequest roundRequest) {
        return new RoundResponse(roundService.startNewRound(roundRequest));
    }
}
