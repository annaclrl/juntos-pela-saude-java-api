package br.com.fiap.model;

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
}



