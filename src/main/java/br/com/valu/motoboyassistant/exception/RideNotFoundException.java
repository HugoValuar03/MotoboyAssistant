package br.com.valu.motoboyassistant.exception;

import java.util.UUID;

public class RideNotFoundException extends RuntimeException {
    public RideNotFoundException(UUID id) {
        super("Corrida não encontrada: " + id);
    }
}
