public class Cartao {
    private final String idPartida;
    private final String jogador;
    private final TipoCartao tipoCartao;

    public Cartao(String idPartida, String jogador, TipoCartao tipoCartao) {
        this.idPartida = idPartida;
        this.jogador = jogador;
        this.tipoCartao = tipoCartao;
    }

    public String getIdPartida() {
        return idPartida;
    }

    public String getJogador() {
        return jogador;
    }

    public TipoCartao getTipoCartao() {
        return tipoCartao;
    }
}