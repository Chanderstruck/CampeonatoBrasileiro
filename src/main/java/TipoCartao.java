import java.util.Locale;

public enum TipoCartao {
    AMARELO,
    VERMELHO;

    public static TipoCartao fromString(String raw) {
        if (raw == null) return AMARELO;
        String s = raw.trim().toLowerCase(Locale.ROOT);
        if (s.startsWith("v")) return VERMELHO;
        return AMARELO;
    }
}