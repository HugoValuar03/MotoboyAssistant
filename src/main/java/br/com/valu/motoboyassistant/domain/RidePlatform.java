package br.com.valu.motoboyassistant.domain;

public enum RidePlatform {
    UBER("Uber"),
    IFOOD("iFood");

    private final String label;

    RidePlatform(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
