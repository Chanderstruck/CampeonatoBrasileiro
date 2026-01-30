public class Placar {
    private final String idPartida;
    private final String timeMandante;
    private final String timeVisitante;
    private final int golsMandante;
    private final int golsVisitante;

    public Placar(String idPartida,
                  String timeMandante,
                  String timeVisitante,
                  int golsMandante,
                  int golsVisitante) {
        this.idPartida = idPartida;
        this.timeMandante = timeMandante;
        this.timeVisitante = timeVisitante;
        this.golsMandante = golsMandante;
        this.golsVisitante = golsVisitante;
    }

    public int getTotalGols() {
        return golsMandante + golsVisitante;
    }

    @Override
    public String toString() {
        return "Partida " + idPartida + ": " +
                timeMandante + " " + golsMandante +
                " x " + golsVisitante + " " + timeVisitante +
                " (total de gols: " + getTotalGols() + ")";
    }
}