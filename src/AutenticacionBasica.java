
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AutenticacionBasica implements Autenticable, Auditable{
    private String usuario;
    private String password;
    private boolean sesionActiva;
    private List<String> historial;

    public AutenticacionBasica(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
        this.historial = new ArrayList<>();
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

    @Override
    public boolean autenticar(String usuario, String credencial) {
        if (this.usuario.equals(usuario) && this.password.equals(credencial)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void cerrarSesion() {
        sesionActiva = false;
    }

    @Override
    public boolean sesionActiva() {
        return sesionActiva = true;
    }

}
