package com.exemple.controller;

import com.exemple.model.Adherent;
import com.exemple.model.TypeAdherent;
import com.exemple.service.AdherentService;
import com.exemple.service.TypeAdherentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/inscription")
public class InscriptionController {
    @Autowired
    private AdherentService adherentService;
    
    @Autowired
    private TypeAdherentService typeAdherentService;

    @GetMapping
    public String showInscriptionForm(Model model) {
        model.addAttribute("adherent", new Adherent());
        model.addAttribute("typesAdherent", typeAdherentService.getAllTypeAdherents());
        return "inscription/form";
    }

    @PostMapping
    public String processInscription(Adherent adherent, 
                                   @RequestParam("dateNaissance") String dateNaissanceStr,
                                   @RequestParam("id_type_adherent") Integer idTypeAdherent) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateNaissance = sdf.parse(dateNaissanceStr);
            adherent.setDateNaissance(dateNaissance);
            
            TypeAdherent typeAdherent = typeAdherentService.getTypeAdherentById(idTypeAdherent);
            adherent.setTypeAdherent(typeAdherent);
            
            adherentService.saveAdherent(adherent);
            return "redirect:/paiement?adherentId=" + adherent.getId_adherent();
        } catch (Exception e) {
            return "redirect:/inscription?error";
        }
    }
}