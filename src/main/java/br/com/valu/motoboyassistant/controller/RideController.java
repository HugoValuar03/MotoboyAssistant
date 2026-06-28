package br.com.valu.motoboyassistant.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.valu.motoboyassistant.dto.RideCreateRequest;
import br.com.valu.motoboyassistant.dto.RideResponse;
import br.com.valu.motoboyassistant.dto.RideSummaryResponse;
import br.com.valu.motoboyassistant.service.RideService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/corridas")
public class RideController {

    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @PostMapping
    public ResponseEntity<RideResponse> create(@Valid @RequestBody RideCreateRequest request) {
        var created = rideService.create(request);
        return ResponseEntity.created(URI.create("/api/corridas/" + created.id())).body(created);
    }

    @GetMapping
    public List<RideResponse> findAll() {
        return rideService.findAll();
    }

    @GetMapping("/{id}")
    public RideResponse findById(@PathVariable UUID id) {
        return rideService.findById(id);
    }

    @GetMapping("/resumo")
    public RideSummaryResponse summary() {
        return rideService.summary();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        rideService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
