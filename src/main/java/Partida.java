import java.util.Optional;

public class Partida {
    private final String idPartida;
    private final int ano;
    private final String estado;
    private final String timeMandante;
    private final String timeVisitante;
    private final int golsMandante;
    private final int golsVisitante;

    public Partida(String idPartida,
                   int ano,
                   String estado,
                   String timeMandante,
                   String timeVisitante,
                   int golsMandante,
                   int golsVisitante) {
        this.idPartida = idPartida;
        this.ano = ano;
        this.estado = estado;
        this.timeMandante = timeMandante;
        this.timeVisitante = timeVisitante;
        this.golsMandante = golsMandante;
        this.golsVisitante = golsVisitante;
    }

    public String getIdPartida() {
        return idPartida;
    }

    public int getAno() {
        return ano;
    }

    public String getEstado() {
        return estado;
    }

    public String getTimeMandante() {
        return timeMandante;
    }

    public String getTimeVisitante() {
        return timeVisitante;
    }

    public int getGolsMandante() {
        return golsMandante;
    }

    public int getGolsVisitante() {
        return golsVisitante;
    }

    public int getTotalGols() {
        return golsMandante + golsVisitante;
    }

    public Optional<String> getVencedor() {
        if (golsMandante > golsVisitante) return Optional.of(timeMandante);
        if (golsVisitante > golsMandante) return Optional.of(timeVisitante);
        return Optional.empty(); // empate
    }

    @Override
    public String toString() {
        return "{idPartida:" + idPartida + ", " +
                "ano:" + ano + "," +
                "estado:" + estado + "," +
                "timeMandante:" + timeMandante + "," +
                "timeVisitante:" + timeVisitante + "," +
                "golsMandante:" + golsMandante + "," +
                "golsVisitante:"+ golsVisitante + "}"
                ;
    }
}