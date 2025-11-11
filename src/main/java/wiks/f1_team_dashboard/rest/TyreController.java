package wiks.f1_team_dashboard.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wiks.f1_team_dashboard.entities.tyre.TyreResponse;
import wiks.f1_team_dashboard.services.tyre.TyreService;

import java.util.List;

@RestController
@RequestMapping("/f1/tyres")
@RequiredArgsConstructor
public class TyreController {
    private final TyreService tyreService;

    @PostMapping("/change-tyre-set")
    public List<TyreResponse> changeActiveTyreSet(@RequestBody List<Integer> tyreIds) {
        return tyreService.changeActiveTyreSet(tyreIds).stream()
                .map(TyreResponse::new).toList();
    }
}
