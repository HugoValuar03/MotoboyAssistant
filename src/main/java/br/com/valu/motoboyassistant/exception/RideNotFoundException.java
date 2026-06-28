package br.com.valu.motoboyassistant.exception;

public class RideNotFoundException extends RuntimeException {
    public RideNotFoundException(Long id) {
        super("Corrida não encontrada: " + id);
    }
}
