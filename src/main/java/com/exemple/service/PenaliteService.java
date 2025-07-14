package com.exemple.service;

import com.exemple.repository.PenaliteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PenaliteService {

    @Autowired
    private PenaliteRepository penaliteRepository;

    public List<Object[]> getPenalitesStatistiques() {
        return penaliteRepository.findPenalitesStatistiques();
    }
}
