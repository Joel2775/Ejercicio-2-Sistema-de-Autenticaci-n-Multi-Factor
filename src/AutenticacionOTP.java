import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AutenticacionOTP implements Autenticable, MultiFactor, Auditable {
    private String usuario;
    private String password;
    private String codigoOTP;
    private int intentos;
    private boolean sesionActiva;
    private List<String> historial;

    public AutenticacionOTP(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
        this.historial = new ArrayList<>();
    }

    public String generarCodigoVerificacion() {
        // Genera número aleatorio de 6 dígitos
        codigoOTP = String.format("%06d", (int)(Math.random() * 1000000));
        intentos = 3;
        System.out.println("Código OTP generado: " + codigoOTP);
        System.out.println("Código enviado por SMS");
        return codigoOTP;
    }

    @Override
    public boolean verificarCodigo(String codigo) {
        if (intentos <= 0) {
            System.out.println("✗ Sin intentos restantes");
            return false;
        }

        intentos--;
        if (codigoOTP.equals(codigo)) {
            System.out.println("✓ Código verificado correctamente");
            return true;
        } else {
            System.out.println("✗ Código incorrecto - Intentos restantes: " + intentos);
            return false;
        }
    }

    @Override
    public int intentosRestantes() {
        return intentos;
    }

    @Override
    public boolean autenticar(String usuario, String credencial) {
        if (this.usuario.equals(usuario) && this.password.equals(credencial)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void cerrarSesion() {
        sesionActiva = false;
    }

    @Override
    public boolean sesionActiva() {
        return sesionActiva;
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
