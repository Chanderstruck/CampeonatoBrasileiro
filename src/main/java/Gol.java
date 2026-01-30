public class Gol {
    private final String idPartida;
    private final String jogador;
    private final TipoGol tipoGol;

    public Gol(String idPartida, String jogador, TipoGol tipoGol) {
        this.idPartida = idPartida;
        this.jogador = jogador;
        this.tipoGol = tipoGol;
    }

    public String getIdPartida() {
        return idPartida;
    }

    public String getJogador() {
        return jogador;
    }

    public TipoGol getTipoGol() {
        return tipoGol;
    }
}