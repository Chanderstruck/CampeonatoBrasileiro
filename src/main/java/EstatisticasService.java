
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class EstatisticasService {

    public record Relatorio(String entidade, long contagem) {}

    public void exibirRelatorio (List<Relatorio> lista, String titulo, String unidade ){

        System.out.print("\n\n" + titulo);

        if (lista.isEmpty()) {
            System.out.print("\nDados inexistentes");
        } else {
            lista.forEach(v ->
                    System.out.printf("\n%s (%d %s)",v.entidade(), v.contagem(), unidade)
            );
        }


    }
    // Time com mais vitórias em um dado ano
    public List<Relatorio> timeComMaisVitoriasNoAno(List<Partida> partidas, int ano) {

        Map<String,Long> mapa = partidas.stream()
                .filter(p -> p.getAno() == ano)
                .flatMap(p -> p.getVencedor().stream())
                .collect(Collectors.groupingBy(v -> v, Collectors.counting()));

        if (mapa.isEmpty()){
            return List.of(); //Nenhum vencedor encontrado
        }

        long maior = mapa.values().stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0);


        return mapa.entrySet().stream()
                .filter(e -> e.getValue() == maior)
                .map(e -> new Relatorio(e.getKey(), e.getValue()))
                .toList();

    }

    // Estado com menos jogos em um período [anoInicio, anoFim]
    public List<Relatorio> estadoComMenosJogosNoPeriodo(List<Partida> partidas, int anoInicio, int anoFim) {

        int minimo = Math.min(anoInicio, anoFim);
        int maximo = Math.max(anoInicio, anoFim);

        Map<String,Long> mapa = partidas.stream()
                .filter(p -> p.getAno() >= minimo && p.getAno() <= maximo)
                .map(p -> p.getEstado())
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        if (mapa.isEmpty()){
            return List.of(); //Nenhum vencedor encontrado
        }

        long menor = mapa.values().stream()
                .mapToLong(Long::longValue)
                .min()
                .orElse(0);

        return mapa.entrySet().stream()
                .filter(e -> e.getValue() == menor)
                .map(e -> new Relatorio(e.getKey(), e.getValue()))
                .toList();
    }

    // Jogador com mais gols (qualquer tipo)
    public List<Relatorio> jogadorComMaisGols(List<Gol> gols) {
        Map<String,Long> mapa = gols.stream()
                .map(Gol::getJogador)
                .collect(Collectors.groupingBy(j -> j, Collectors.counting()));

        if (mapa.isEmpty()){
            return List.of();
        }

        var maximo = mapa.values().stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0);

        return mapa.entrySet().stream()
                .filter(e -> e.getValue() == maximo)
                .map(e -> new Relatorio(e.getKey(), e.getValue()))
                .toList();

    }

    // Jogador com mais gols de um tipo específico (pênalti, contra, etc.)
    public List<Relatorio> jogadorComMaisGolsPorTipo(List<Gol> gols, TipoGol tipoGol) {
        Map<String,Long> mapa = gols.stream()
                .filter(g -> g.getTipoGol() == tipoGol)
                .map(Gol::getJogador)
                .collect(Collectors.groupingBy(j -> j, Collectors.counting()));

        if (mapa.isEmpty()){
            return List.of();
        }

        var maximo = mapa.values().stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0);

        return mapa.entrySet().stream()
                .filter(e -> e.getValue() == maximo)
                .map(e -> new Relatorio(e.getKey(), e.getValue()))
                .toList();
    }

    // Jogador com mais cartões de determinado tipo
    public List<Relatorio> jogadorComMaisCartoesPorTipo(List<Cartao> cartoes, TipoCartao tipoCartao) {
        Map<String,Long> mapa = cartoes.stream()
                .filter(c -> c.getTipoCartao() == tipoCartao)
                .map(Cartao::getJogador)
                .collect(Collectors.groupingBy(j -> j, Collectors.counting()));

        if (mapa.isEmpty()){
            return List.of();
        }

        var maximo = mapa.values().stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0);

        return mapa.entrySet().stream()
                .filter(e -> e.getValue() == maximo)
                .map(e -> new Relatorio(e.getKey(), e.getValue()))
                .toList();
    }

    // Placar da partida com mais gols
    public Optional<Placar> placarComMaisGols(List<Partida> partidas) {
        return partidas.stream()
                .max(Comparator.comparingInt(Partida::getTotalGols))
                .map(p -> new Placar(
                        p.getIdPartida(),
                        p.getTimeMandante(),
                        p.getTimeVisitante(),
                        p.getGolsMandante(),
                        p.getGolsVisitante()
                ));
    }
}