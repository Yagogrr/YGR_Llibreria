package com.iticbcn.mywebapp.llibresApp.Repositoris;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.iticbcn.mywebapp.llibresApp.Model.Llibre;

import io.micrometer.common.lang.NonNull;

public interface Repositori extends CrudRepository<Llibre,Long> {
    @Override
    @NonNull
    Set<Llibre>findAll();
    Optional<Llibre> findByTitol(String nom);
    Set<Llibre>findByTitolAndEditorial (String titol, String editorial);
    Optional<Llibre> findById(Long id); 

}