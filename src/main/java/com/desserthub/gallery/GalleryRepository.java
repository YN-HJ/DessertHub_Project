package com.desserthub.gallery;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {
    List<Gallery> findByUserId(Long userId);

    void deleteAllByUserId(Long userId);
}