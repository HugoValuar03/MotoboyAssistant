package br.com.valu.motoboyassistant.repository;

import br.com.valu.motoboyassistant.domain.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RideRepository extends JpaRepository<Ride, Long> {
    List<Ride> findAllByOrderByOccurredAtDesc();
}
