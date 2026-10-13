package com.example.housepricing.repository;

import com.example.housepricing.model.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PredictionRepository extends JpaRepository<Prediction, Long> {
    List<Prediction> findAllByOrderByCreatedAtDesc();
}
