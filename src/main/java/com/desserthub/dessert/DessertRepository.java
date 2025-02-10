package com.desserthub.dessert;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DessertRepository extends JpaRepository<Dessert, Long> {
    
    Dessert findByDessertCharaOrDessertName(String dessertChara, String dessertName);
}