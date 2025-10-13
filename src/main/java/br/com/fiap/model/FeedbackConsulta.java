package br.com.fiap.model;

public class FeedbackConsulta {

    private int codigo;
    private Consulta consulta;
    private String comentario;
    private double nota;


    public FeedbackConsulta() {
    }

    public FeedbackConsulta(int codigo, Consulta consulta, String comentario, double nota) {
        this.codigo = codigo;
        this.consulta = consulta;
        this.comentario = comentario;
        this.nota = nota;
    }

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

    public boolean notaValida() {
        return nota >= 0 && nota <= 5;
    }

    public boolean comentarioValido() {
        return comentario != null && comentario.length() <= 500;
    }

    @Override
    public String toString() {
        return "\nCódigo: " + codigo +
                "\nConsulta: " + (consulta != null ? consulta.getCodigo() : "N/A") +
                "\nNota: " + nota +
                "\nComentário: " + comentario;
    }


}
