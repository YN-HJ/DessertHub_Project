package com.desserthub.dessert;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class DessertService {

    private final DessertRepository dessertRepository;

    public DessertService(DessertRepository dessertRepository) {
        this.dessertRepository = dessertRepository;
    }

    public List<Dessert> getAllDesserts() {
        return dessertRepository.findAll();
    }

    public Optional<Dessert> getDessert(Long id) {
        return dessertRepository.findById(id);
    }

    public Dessert findid(String dessertChara, String dessertName){
        return dessertRepository.findByDessertCharaOrDessertName(dessertChara, dessertName);
    }

}