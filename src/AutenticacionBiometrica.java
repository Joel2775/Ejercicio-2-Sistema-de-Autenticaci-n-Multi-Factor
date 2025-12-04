import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AutenticacionBiometrica implements Autenticable, Auditable {

    private String usuario;
    private String huellaDactilar;
    private String reconocimientoFacial;
    private List<String> historial = new ArrayList<>();

    public AutenticacionBiometrica(String usuario, String huella, String facial) {
        this.usuario = usuario;
        this.huellaDactilar = huella;
        this.reconocimientoFacial = facial;
    }

    @Override
    public boolean autenticar(String usuario, String credencial) {
        String nivelConfianza = "BAJO";

        if (this.huellaDactilar.equals(credencial)) {
            nivelConfianza = "ALTA";
        } else if (this.reconocimientoFacial.equals(credencial)) {
            nivelConfianza = "MEDIA";
        } else {
            registrarIntento(usuario + " (Biométrica/Baja)", false);
            return false;
        }

        System.out.println("✓ Huella verificada - Confianza: " + nivelConfianza);
        System.out.println("✓ Autenticación biométrica exitosa");
        registrarIntento(usuario + " (Biométrica/" + nivelConfianza + ")", true);
        return true;
    }

    @Override
    public void cerrarSesion() {
        System.out.println("cerrando sesion...");
    }

    @Override
    public boolean sesionActiva() {
        return true;
    }

    @Override
    public void registrarIntento(String usuario, boolean exitoso) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String msg = "[AUDIT] " + (exitoso ? "Intento exitoso: " : "Intento fallido: ") + usuario + " - " + LocalDateTime.now().format(formato);
        historial.add(msg);
        System.out.println(msg);
    }

    @Override
    public List<String> obtenerHistorial(String usuario) {
        return historial;
    }
}