import java.io.IOException;
import java.util.*;

public class CampeonatoBrasileiroApp {

    public static void main(String[] args) {

        String fullCsv = "campeonato-brasileiro-full.csv";
        String golsCsv = "campeonato-brasileiro-gols.csv";
        String cartoesCsv = "campeonato-brasileiro-cartoes.csv";
        String estatisticasCsv = "campeonato-brasileiro-estatisticas.csv";

        Repositorio repositorio = new Repositorio();
        EstatisticasService service = new EstatisticasService();

        try {
            // Carregamento dos dados
            long inicio = System.nanoTime();
            List<Partida> partidas = repositorio.carregarPartidas(fullCsv);
            List<Gol> gols = repositorio.carregarGols(golsCsv);
            List<Cartao> cartoes = repositorio.carregarCartoes(cartoesCsv);
            long fim = System.nanoTime();
            System.out.printf("Carga concluída em %.3f ms%n", (fim - inicio) / 1000000.0);

            System.out.println("\n###############################################################" +
                    "\nResultados do Campeonato Brasileiro (2003 - 2022)" +
                    "\n###############################################################"
            );

            // 1) Time que mais venceu em 2008
            var anoTeste = 2008;
            var vencedoresAnoTeste = service.timeComMaisVitoriasNoAno(partidas, anoTeste);
            service.exibirRelatorio(vencedoresAnoTeste
                    ,"Times com mais vitórias em " + anoTeste + ":", "vitórias" );

            // 2) Estado com menos jogos entre 2003 e 2022
            var anoPeriodoInicio = 2003;
            var anoPeriodoFim = 2022;
            var estadoMenosJogos = service.estadoComMenosJogosNoPeriodo(partidas,anoPeriodoInicio,anoPeriodoFim);

            service.exibirRelatorio(estadoMenosJogos
                    ,"Estado(s) com menos jogos (" + anoPeriodoInicio + " - " + anoPeriodoFim + "):", "jogos" );


            // 3) Jogador que mais fez gols
            var artilheiro = service.jogadorComMaisGols(gols);
            service.exibirRelatorio(artilheiro,"Jogador(es) com mais gols:", "gols");

            // 4) Jogador que mais fez gols de pênalti
            var artilheiroPenalti = service.jogadorComMaisGolsPorTipo(gols, TipoGol.PENALTI);
            service.exibirRelatorio(artilheiroPenalti,"Jogador(es) com mais gols de pênalti:", "gols");


            // 5) Jogador que mais fez gols contra
            var artilheiroContra = service.jogadorComMaisGolsPorTipo(gols, TipoGol.CONTRA);
            service.exibirRelatorio(artilheiroContra,"Jogador(es) com mais gols contra:", "gols");

            // 6) Jogador com mais cartões amarelos
            var jogadorCartaoAmarelo = service.jogadorComMaisCartoesPorTipo(cartoes, TipoCartao.AMARELO);
            service.exibirRelatorio(jogadorCartaoAmarelo,"Jogador(es) com mais cartões amarelos:", "cartões");


            // 7) Jogador com mais cartões vermelhos
            var jogadorCartaoVermelho = service.jogadorComMaisCartoesPorTipo(cartoes, TipoCartao.VERMELHO);
            service.exibirRelatorio(jogadorCartaoVermelho,"Jogador(es) com mais cartões vermelhos:", "cartões");

            // 8) Placar da partida com mais gols
            service.placarComMaisGols(partidas)
                    .ifPresent(p ->
                    System.out.printf("\n\nPlacar da partida com mais gols: \n%s",p));

        } catch (IOException e) {
            System.err.println("Erro ao ler arquivos: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
