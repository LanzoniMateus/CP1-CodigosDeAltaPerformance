package entidades;

public class Mensagem {

    private String nome;
    private String contato;
    private int motivo; // 1 = reclamação, 2 = sugestão
    private String texto;
    private String canal;

    public Mensagem(String nome, String contato, int motivo, String texto, String canal) {
        this.nome = nome;
        this.contato = contato;
        this.motivo = motivo;
        this.texto = texto;
        this.canal = canal;
    }

    // Getters e Setters (compactos)
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getContato() { return contato; }
    public void setContato(String contato) { this.contato = contato; }

    public int getMotivo() { return motivo; }
    public void setMotivo(int motivo) { this.motivo = motivo; }

    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }

    public String getCanal() { return canal; }
    public void setCanal(String canal) { this.canal = canal; }

    // Método auxiliar (deixa o toString mais limpo)
    public String getMotivoStr() {
        return (motivo == 1) ? "Reclamação" : "Sugestão";
    }

    @Override
    public String toString() {
        return "------------------------------\n" +
                "Canal   : " + canal + "\n" +
                "Nome    : " + (nome == null || nome.isBlank() ? "(não informado)" : nome) + "\n" +
                "Contato : " + contato + "\n" +
                "Motivo  : " + getMotivoStr() + "\n" +
                "Mensagem: " + texto + "\n" +
                "------------------------------";
    }
}