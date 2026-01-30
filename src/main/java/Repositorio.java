import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Repositorio {

    private static final String SEPARADOR = ",";

    // Exemplo de mapeamento de colunas para o campeonato-brasileiro-full.csv
    //"ID" 0
    // ,"rodata" 1
    // ,"data" 2
    // ,"hora" 3
    // ,"mandante" 4
    // ,"visitante" 5
    // ,"formacao_mandante" 6
    // ,"formacao_visitante" 7
    // ,"tecnico_mandante" 8
    // ,"tecnico_visitante" 9
    // ,"vencedor" 10
    // ,"arena" 11
    // ,"mandante_Placar" 12
    // ,"visitante_Placar" 13
    // ,"mandante_Estado" 14
    // ,"visitante_Estado" 15

    private static final int IDX_ID_PARTIDA = 0;
    private static final int IDX_ANO = 2;
    private static final int IDX_ESTADO = 14;
    private static final int IDX_TIME_MANDANTE = 4;
    private static final int IDX_TIME_VISITANTE = 5;
    private static final int IDX_GOLS_MANDANTE = 12;
    private static final int IDX_GOLS_VISITANTE = 13;

    public List<Partida> carregarPartidas(String arquivoCSV) throws IOException {
        List<Partida> partidas = new ArrayList<>();

        DateTimeFormatter BR_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy", new Locale("pt", "BR"));

        try (BufferedReader reader = abrirRecursoComoReader(arquivoCSV)) {
            String header = reader.readLine(); // descarta cabeçalho
            if (header == null) {
                return partidas;
            }

            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] colunas = linha.split(SEPARADOR, -1);

                if (colunas.length <= IDX_ESTADO) continue;

                String idPartida = sanitizar(colunas[IDX_ID_PARTIDA]);
                LocalDate data = LocalDate.parse(sanitizar(colunas[IDX_ANO]), BR_FORMATTER);
                int ano = data.getYear();
                String estado = sanitizar(colunas[IDX_ESTADO]);
                String mandante = sanitizar(colunas[IDX_TIME_MANDANTE]);
                String visitante = sanitizar(colunas[IDX_TIME_VISITANTE]);
                int golsMandante = parseIntSafe(sanitizar(colunas[IDX_GOLS_MANDANTE]));
                int golsVisitante = parseIntSafe(sanitizar(colunas[IDX_GOLS_VISITANTE]));

                partidas.add(new Partida(
                        idPartida, ano, estado, mandante, visitante,
                        golsMandante, golsVisitante
                ));
            }
        }
        return partidas;
    }

    // Exemplo de mapeamento básico para gols
    // Ajuste índices conforme o CSV real campeonato-brasileiro-gols.csv
    //"partida_id" 0
    // ,"rodata" 1
    // ,"clube" 2
    // ,"atleta" 3
    // ,"minuto" 4
    // ,"tipo_de_gol" 5
    private static final int IDX_GOL_ID_PARTIDA = 0;
    private static final int IDX_GOL_JOGADOR = 3;
    private static final int IDX_GOL_TIPO = 5;

    public List<Gol> carregarGols(String arquivoCSV) throws IOException {
        List<Gol> gols = new ArrayList<>();

        try (BufferedReader reader = abrirRecursoComoReader(arquivoCSV)) {
            String header = reader.readLine(); // cabeçalho
            if (header == null) return gols;

            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] colunas = linha.split(SEPARADOR, -1);
                if (colunas.length <= IDX_GOL_TIPO) continue;

                String idPartida = sanitizar(colunas[IDX_GOL_ID_PARTIDA]);
                String jogador = sanitizar(colunas[IDX_GOL_JOGADOR]);
                String tipoRaw = sanitizar(colunas[IDX_GOL_TIPO]);
                TipoGol tipo = TipoGol.fromString(tipoRaw);

                gols.add(new Gol(idPartida, jogador, tipo));
            }
        }
        return gols;
    }

    // Exemplo de mapeamento básico para cartões
    // Ajuste índices conforme o CSV real campeonato-brasileiro-cartoes.csv
    //"partida_id" 0
    // ,"rodata" 1
    // ,"clube" 2
    // ,"cartao" 3
    // ,"atleta" 4
    // ,"num_camisa" 5
    // ,"posicao" 6
    // ,"minuto" 7
    private static final int IDX_CARTAO_ID_PARTIDA = 0;
    private static final int IDX_CARTAO_JOGADOR = 4;
    private static final int IDX_CARTAO_TIPO = 3;

    public List<Cartao> carregarCartoes(String arquivoCSV) throws IOException {
        List<Cartao> cartoes = new ArrayList<>();

        try (BufferedReader reader = abrirRecursoComoReader(arquivoCSV)) {
           String header = reader.readLine();
            if (header == null) return cartoes;

            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] colunas = linha.split(SEPARADOR, -1);
                if (colunas.length <= IDX_CARTAO_JOGADOR) continue;

                String idPartida = sanitizar(colunas[IDX_CARTAO_ID_PARTIDA]);
                String jogador = sanitizar(colunas[IDX_CARTAO_JOGADOR]);
                String tipoRaw = sanitizar(colunas[IDX_CARTAO_TIPO]);
                TipoCartao tipoCartao = TipoCartao.fromString(tipoRaw);

                cartoes.add(new Cartao(idPartida, jogador, tipoCartao));
            }
        }
        return cartoes;
    }


    private BufferedReader abrirRecursoComoReader(String nomeArquivo) {
        InputStream in = Repositorio.class.getResourceAsStream("/" + nomeArquivo);
        if (in == null) {
            throw new IllegalArgumentException(
                    "Recurso não encontrado no classpath: " + nomeArquivo +
                            ". Verifique se o arquivo está em src/main/resources."
            );
        }
        return new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
    }

    private static String sanitizar(String s){
        return s.replaceAll("^\"|\"$", "").trim();
    }

    private static int parseIntSafe(String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch (Exception e) {
            return 0;
        }
    }
}