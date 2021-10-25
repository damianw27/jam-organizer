package pl.dw.meetgamejam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.dw.meetgamejam.entities.UserEntity;
import pl.dw.meetgamejam.repositories.UserRepository;

import javax.inject.Inject;

@Controller
public class OverviewController {

    @GetMapping("/overview")
    public String overview() {
        return "overview";
    }

}
