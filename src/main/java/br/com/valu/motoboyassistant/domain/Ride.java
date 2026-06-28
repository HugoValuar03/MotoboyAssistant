package br.com.valu.motoboyassistant.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "rides")
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RidePlatform platform;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal distanceKm;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalValue;

    @Column(nullable = false)
    private LocalDateTime occurredAt;

    @Column(length = 255)
    private String notes;

    protected Ride() {
    }

    public Ride(RidePlatform platform, BigDecimal distanceKm, BigDecimal totalValue, LocalDateTime occurredAt,
            String notes) {
        this.platform = platform;
        this.distanceKm = distanceKm;
        this.totalValue = totalValue;
        this.occurredAt = occurredAt;
        this.notes = notes;
    }

    public UUID getId() {
        return id;
    }

    public RidePlatform getPlatform() {
        return platform;
    }

    public BigDecimal getDistanceKm() {
        return distanceKm;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }

    public String getNotes() {
        return notes;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setPlatform(RidePlatform platform) {
        this.platform = platform;
    }

    public void setDistanceKm(BigDecimal distanceKm) {
        this.distanceKm = distanceKm;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public void setOccurredAt(LocalDateTime occurredAt) {
        this.occurredAt = occurredAt;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
