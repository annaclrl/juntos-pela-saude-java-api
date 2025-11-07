package br.com.fiap.dto.exception;

import java.util.Map;

public class ValidationErrorResponseDto extends ErrorResponseDto {

    private Map<String, String> campos;

    public ValidationErrorResponseDto(String mensagem, Map<String, String> campos) {
        super(mensagem);
        this.campos = campos;
    }

    public Map<String, String> getCampos() {
        return campos;
    }

    public void setCampos(Map<String, String> campos) {
        this.campos = campos;
    }
}
