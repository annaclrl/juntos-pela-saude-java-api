package br.com.fiap.dto.feedbackconuslta;

import br.com.fiap.model.Consulta;

public class ListarFeedbackConsultaDto {

    private int codigo;
    private Consulta consulta;
    private String comentario;
    private double nota;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

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
