package com.exemple.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exemple.model.Adherent;
import com.exemple.model.Exemplaire;
import com.exemple.model.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    
    boolean existsByExemplaireAndAdherent(Exemplaire exemplaire, Adherent adherent);
    
    boolean existsByExemplaireAndAdherentNot(Exemplaire exemplaire, Adherent adherent);
    
    int countByAdherent(Adherent adherent);
    
    void deleteByExemplaireAndAdherent(Exemplaire exemplaire, Adherent adherent);
    boolean existsByExemplaire(Exemplaire exemplaire);

    boolean existsByAdherentAndExemplaire(int idAdherent, int idExemplaire);

    @Query("SELECT r FROM Reservation r WHERE r.exemplaire.id_exemplaire = :idExemplaire ORDER BY r.date_reservation DESC")
    List<Reservation> findByExemplaireIdOrderByDateReservationDesc(@Param("idExemplaire") int idExemplaire);
}