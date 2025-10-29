package br.com.fiap.dto.feedbackconuslta;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CadastroFeedbackConsultaDto {

    @NotNull(message = "A consulta é obrigatória.")
    @JsonProperty("consulta_id")
    private Integer consultaId;

    @Size(max = 500, message = "O comentário deve ter no máximo 500 caracteres.")
    private String comentario;

    @Min(value = 0, message = "A nota mínima é 0.")
    @Max(value = 5, message = "A nota máxima é 5.")
    private double nota;

    public Integer getConsultaId() {
        return consultaId;
    }

    public void setConsultaId(Integer consultaId) {
        this.consultaId = consultaId;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }
}
