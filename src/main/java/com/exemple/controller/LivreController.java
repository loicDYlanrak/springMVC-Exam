package com.exemple.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
   
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.exemple.model.Exemplaire;
import com.exemple.model.Livre;
import com.exemple.model.Pret;
import com.exemple.model.Reservation;
import com.exemple.service.AdherentService;
import com.exemple.service.ExemplaireService;
import com.exemple.service.LivreService;
import com.exemple.service.PretService;
import com.exemple.service.ReservationService;

@Controller
@RequestMapping("/livre")
public class LivreController {
    
    @Autowired
    private LivreService livreService;
    
    @Autowired 
    private PretService pretService;
        
    @Autowired
    private AdherentService adherentService;

    @Autowired
    private ExemplaireService exemplaireService;


    @Autowired
    private ReservationService reservationService;
    
    @GetMapping
    public String listLivres(Model model) {
        model.addAttribute("livres", livreService.getAllLivres());
        return "livre/list";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("livre", new Livre());
        return "livre/add";
    }

    @PostMapping("/save")
    public String saveLivre(@ModelAttribute Livre livre) {
        livreService.saveLivre(livre);
        return "redirect:/livres";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        model.addAttribute("livre", livreService.getLivreById(id));
        return "livre/edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteLivre(@PathVariable Integer id) {
        livreService.deleteLivre(id);
        return "redirect:/livres";
    }

    @GetMapping("/pret/{idLivre}")
    public String showPretForm(@PathVariable int idLivre, Model model) {
        model.addAttribute("idLivre", idLivre);
        return "livre/pret";
    }

    @PostMapping("/pret")
    public String processPret(
            @RequestParam("id_exemplaire") int idExemplaire,
            @RequestParam("id_adherent") int idAdherent,
            @RequestParam("date_pret") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate datePret,
            Model model) {
        
        String result = pretService.emprunterExemplaire(idExemplaire, idAdherent, datePret);
        model.addAttribute("message", result);
        
        return "redirect:/";
    }

    @PostMapping("/retour")
    public String retournerExemplaire(
            @RequestParam("id_pret") int idPret,
            @RequestParam("date_retour") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateRetour,
            Model model) {
        
        String result = pretService.retournerExemplaireParPret(idPret, dateRetour);
        model.addAttribute("message", result);
        
        return "redirect:/";
    }

    @PostMapping("/prolonger")
    public String prolongerPret(
            @RequestParam("id_pret") int idPret,
            @RequestParam("date_retour") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateRetour,
            Model model) {
        String result = pretService.prolongerPret(idPret, dateRetour);
        model.addAttribute("message", result);
        Pret pret = pretService.getPretById(idPret);
        return "redirect:/livre/exemplaire/loans/" + pret.getExemplaire().getId_exemplaire();
    }

    @PostMapping("/reserver")
    public String reserverExemplaire(
            @RequestParam("id_exemplaire") int idExemplaire,
            @RequestParam("id_adherent") int idAdherent,
            @RequestParam("date_reservation") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime dateReservation,
            Model model) {
        
        String result = reservationService.reserverExemplaire(idExemplaire, idAdherent, dateReservation);
        model.addAttribute("message", result);
        return "redirect:/";
    }

    @GetMapping("/exemplaires/{idLivre}")
    public String listExemplaires(@PathVariable int idLivre, Model model) {
        Livre livre = livreService.getLivreById(idLivre);
        model.addAttribute("livre", livre);
        model.addAttribute("exemplaires", exemplaireService.getExemplairesByLivreId(idLivre));
        model.addAttribute("adherents", adherentService.getAllAdherents());
        return "exemplaires";
    }

    @GetMapping("/exemplaire/details/{idExemplaire}")
    public String showExemplaireDetails(@PathVariable int idExemplaire, Model model) {
        Exemplaire exemplaire = exemplaireService.getExemplaireWithInitializedStatus(idExemplaire);
        List<Pret> historiquePrets = pretService.getHistoriquePretsByExemplaire(idExemplaire);
        List<Reservation> reservations = reservationService.getReservationsByExemplaire(idExemplaire);
        
        model.addAttribute("exemplaire", exemplaire);
        model.addAttribute("historiquePrets", historiquePrets);
        model.addAttribute("reservations", reservations);
        model.addAttribute("livre", exemplaire.getLivre());
        model.addAttribute("adherents", adherentService.getAllAdherents());
        
        if (exemplaire.getCurrentStatus() != null 
            && exemplaire.getCurrentStatus().getEtat() != null
            && "reserve".equals(exemplaire.getCurrentStatus().getEtat().getLibelle())) {
            Optional<Reservation> activeReservation = reservationService.getActiveReservationForExemplaire(idExemplaire);
            activeReservation.ifPresent(res -> model.addAttribute("activeReservation", res));
        }
        
        return "livre/detailsExemplaire";
    }

    // Dans LivreController.java
    @PostMapping("/validerReservation/{idReservation}")
    public String validerReservation(
            @PathVariable int idReservation,
            HttpSession session,
            Model model) {
        
        // Vérifier que l'utilisateur est un bibliothécaire
        if (session.getAttribute("bibliothecaire") == null) {
            return "redirect:/login";
        }
        
        String result = reservationService.validerReservation(idReservation);
        model.addAttribute("message", result);
        
        // Rediriger vers la page de détails de l'exemplaire
        Reservation reservation = reservationService.getReservationById(idReservation);
        return "redirect:/livre/exemplaire/details/" + reservation.getExemplaire().getId_exemplaire();
    }

    @PostMapping("/annulerReservation/{idReservation}")
    public String annulerReservation(
            @PathVariable int idReservation,
            HttpSession session,
            Model model) {
        
        if (session.getAttribute("bibliothecaire") == null) {
            return "redirect:/login";
        }
        
        String result = reservationService.annulerReservation(idReservation);
        model.addAttribute("message", result);
        
        Reservation reservation = reservationService.getReservationById(idReservation);
        return "redirect:/livre/exemplaire/details/" + reservation.getExemplaire().getId_exemplaire();
    }

    @GetMapping("/exemplaire/loans/{idExemplaire}")
    public String manageLoans(@PathVariable int idExemplaire, Model model) {
        Exemplaire exemplaire = exemplaireService.getExemplaireWithInitializedStatus(idExemplaire);
        List<Pret> pretsEnCours = pretService.getPretsEnCoursByExemplaire(idExemplaire);
        model.addAttribute("exemplaire", exemplaire);
        model.addAttribute("pretsEnCours", pretsEnCours);
        return "livre/manageLoans";
    }

    @GetMapping("/")
    public String home(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "age_minimum", required = false) Integer ageMinimum,
            @RequestParam(value = "annee_publication", required = false) Integer anneePublication,
            Model model) {
        List<Livre> livres = livreService.filterLivres(search, ageMinimum, anneePublication);
        model.addAttribute("livres", livres);
        return "home";
    }
}