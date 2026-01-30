import java.util.Locale;

public enum TipoGol {
    NORMAL,
    PENALTI,
    CONTRA;

    public static TipoGol fromString(String raw) {
        if (raw == null) return NORMAL;
        String s = raw.trim().toLowerCase(Locale.ROOT);
        if (s.contains("pen")) return PENALTI;
        if (s.contains("contra")) return CONTRA;
        return NORMAL;
    }
}