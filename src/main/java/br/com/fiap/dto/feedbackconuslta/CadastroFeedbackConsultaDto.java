package br.com.fiap.dto.feedbackconuslta;

import br.com.fiap.model.Consulta;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CadastroFeedbackConsultaDto {

    @NotNull(message = "A consulta é obrigatória.")
    private Consulta consulta;

    @Size(max = 500, message = "O comentário deve ter no máximo 500 caracteres.")
    private String comentario;

    @Min(value = 0, message = "A nota mínima é 0.")
    @Max(value = 5, message = "A nota máxima é 5.")
    private double nota;

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
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
