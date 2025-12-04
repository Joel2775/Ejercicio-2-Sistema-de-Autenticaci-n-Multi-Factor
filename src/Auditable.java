import java.util.List;

public interface Auditable {

    void registrarIntento(String usuario, boolean exitoso);

    List<String> obtenerHistorial(String usuario);

}
