package com.example.housepricing.service;

import com.example.housepricing.dto.PredictionRequest;
import com.example.housepricing.model.Prediction;
import com.example.housepricing.repository.PredictionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PredictionService {

    // Base price per sq.ft (INR) by location tier; unknown locations use the default.
    private static final Map<String, Double> RATE_PER_SQFT = Map.of(
            "hyderabad", 6500.0,
            "bangalore", 7500.0,
            "mumbai", 15000.0,
            "delhi", 10000.0,
            "chennai", 7000.0,
            "pune", 6800.0,
            "kolkata", 5500.0
    );
    private static final double DEFAULT_RATE = 5000.0;
    private static final double BEDROOM_VALUE = 200_000.0;
    private static final double BATHROOM_VALUE = 100_000.0;
    private static final double PARKING_VALUE = 300_000.0;

    private final PredictionRepository repository;

    public PredictionService(PredictionRepository repository) {
        this.repository = repository;
    }

    public double estimate(PredictionRequest r) {
        double rate = RATE_PER_SQFT.getOrDefault(r.getLocation().trim().toLowerCase(), DEFAULT_RATE);
        double price = r.getBuiltUpArea() * rate
                + r.getBedrooms() * BEDROOM_VALUE
                + r.getBathrooms() * BATHROOM_VALUE
                + (r.isParking() ? PARKING_VALUE : 0);
        return Math.round(price);
    }

    public Prediction predictAndSave(PredictionRequest r) {
        Prediction p = new Prediction(r.getLocation().trim(), r.getBuiltUpArea(),
                r.getBedrooms(), r.getBathrooms(), r.isParking(), estimate(r));
        return repository.save(p);
    }

    public List<Prediction> history() {
        return repository.findAllByOrderByCreatedAtDesc();
    }

    public void clearHistory() {
        repository.deleteAll();
    }
}
