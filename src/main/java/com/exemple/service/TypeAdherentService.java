package com.exemple.service;

import com.exemple.model.TypeAdherent;
import com.exemple.repository.TypeAdherentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeAdherentService {
    @Autowired
    private TypeAdherentRepository typeAdherentRepository;

    public List<TypeAdherent> getAllTypeAdherents() {
        return typeAdherentRepository.findAll();
    }

    public TypeAdherent getTypeAdherentById(Integer id) {
        return typeAdherentRepository.findById(id).orElse(null);
    }

    public TypeAdherent saveTypeAdherent(TypeAdherent typeAdherent) {
        return typeAdherentRepository.save(typeAdherent);
    }

    public void deleteTypeAdherent(Integer id) {
        typeAdherentRepository.deleteById(id);
    }
}