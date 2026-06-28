package br.com.valu.motoboyassistant.repository;

import br.com.valu.motoboyassistant.domain.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RideRepository extends JpaRepository<Ride, UUID> {
    List<Ride> findAllByOrderByOccurredAtDesc();
}
