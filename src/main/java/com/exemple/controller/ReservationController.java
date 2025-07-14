package com.exemple.controller;

import com.exemple.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public String listReservations(@RequestParam(required = false) String search,
                                   @RequestParam(required = false) String filter,
                                   Model model) {
        model.addAttribute("reservations", reservationService.getFilteredReservations(search, filter));
        return "reservations";
    }

    @PostMapping("/valider/{idReservation}")
    public String validerReservation(@PathVariable int idReservation) {
        String message=reservationService.validerReservation(idReservation);
        return "redirect:/reservations?message=" + message;
    }
}
