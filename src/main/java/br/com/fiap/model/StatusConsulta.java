package br.com.fiap.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum StatusConsulta {
    CONFIRMADA("Confirmada"),
    EM_ANDAMENTO("Em Andamento"),
    CONCLUIDA("Realizada");

    private final String valorBanco;

    StatusConsulta(String valorBanco) {
        this.valorBanco = valorBanco;
    }

    public String getValorBanco() {
        return valorBanco;
    }

    public static StatusConsulta fromDbValue(String valorBanco) {
        if (valorBanco == null) return null;

        String valorNormalizado = valorBanco.trim().toUpperCase().replace(" ", "_");

        for (StatusConsulta st : values()) {
            if (st.valorBanco.equalsIgnoreCase(valorBanco) ||
                    st.name().equalsIgnoreCase(valorNormalizado)) {
                return st;
            }
        }
        throw new IllegalArgumentException("Status inválido no banco: " + valorBanco);
    }

    @JsonCreator
    public static StatusConsulta fromJson(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        String normalizado = value.trim().toUpperCase().replace(" ", "_");

        for (StatusConsulta status : values()) {
            if (status.name().equalsIgnoreCase(normalizado)
                    || status.getValorBanco().equalsIgnoreCase(value)) {
                return status;
            }
        }

        throw new IllegalArgumentException("Status inválido: " + value);
    }
}



