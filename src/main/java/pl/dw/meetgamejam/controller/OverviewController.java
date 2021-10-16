package pl.dw.meetgamejam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OverviewController {
    @GetMapping("/overview")
    public String overview() {
        return "overview";
    }
}
