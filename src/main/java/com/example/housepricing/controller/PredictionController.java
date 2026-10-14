package com.example.housepricing.controller;

import com.example.housepricing.dto.PredictionRequest;
import com.example.housepricing.model.Prediction;
import com.example.housepricing.service.PredictionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PredictionController {

    private final PredictionService service;

    public PredictionController(PredictionService service) {
        this.service = service;
    }

    @PostMapping("/predict")
    public Prediction predict(@Valid @RequestBody PredictionRequest request) {
        return service.predictAndSave(request);
    }

    @GetMapping("/predictions")
    public List<Prediction> history() {
        return service.history();
    }

    @DeleteMapping("/predictions")
    public ResponseEntity<Void> clear() {
        service.clearHistory();
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(e -> errors.put(e.getField(), e.getDefaultMessage()));
        return errors;
    }
}
