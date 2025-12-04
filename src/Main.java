import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== SISTEMA DE AUTENTICACIÓN ===\n");
        System.out.println("--- Autenticación Básica ---");
        AutenticacionBasica basica = new AutenticacionBasica("admin", "admin123");
        //-----------------------------------------------------------------
        System.out.println("Usuario: admin / Password: admin123");
        if (basica.autenticar("admin", "admin123")){
            System.out.println("✓ Autenticación exitosa");
            basica.registrarIntento("admin", true);
        }else{
            System.out.println("✗ Autenticación fallida");
            basica.registrarIntento("admin", false);
        }
        System.out.println();

        //----------------------------------------------------------------
        System.out.println("Usuario: admin / Password: wrongpass");
        if (basica.autenticar("admin", "wrongpass")){
            System.out.println("✓ Autenticación exitosa");
            basica.registrarIntento("admin", true);
        }else{
            System.out.println("✗ Autenticación fallida");
            basica.registrarIntento("admin", false);
        }
        System.out.println();


        //=====================================================================================
        System.out.println("--- Autenticación OTP ---");
        AutenticacionOTP OTP = new AutenticacionOTP("juan.gonzalez", "secure456");
        System.out.println("Usuario: juan.gonzalez / Password: secure456");
        if (OTP.autenticar("juan.gonzalez", "secure456")){
            String codigoGenerado = OTP.generarCodigoVerificacion();
            System.out.println("Ingresando código: " + codigoGenerado);
            OTP.verificarCodigo(codigoGenerado);
            System.out.println("✓ Autenticación OTP exitosa");
            OTP.registrarIntento("juan.gonzalez", true);
        } else {
            OTP.registrarIntento("juan.gonzalez", false);
        }
        System.out.println();


        System.out.println("--- Autenticación Biométrica ---");
        AutenticacionBiometrica biometrica = new AutenticacionBiometrica("maria.lopez", "huella123", "cara456");

        System.out.println("Usuario: maria.lopez");
        System.out.println("Escaneando huella dactilar...");
        biometrica.autenticar("maria.lopez", "huella123");
        System.out.println();


        System.out.println("--- Historial de Usuario: admin ---");
        for (String admin : basica.obtenerHistorial("admin")){
            System.out.println(admin);
        }
        System.out.println();


        System.out.println("--- Prueba de Bloqueo ---");
        AutenticacionBasica bloqueo = new AutenticacionBasica("test", "test123");

        int intentos = 1;
        while (intentos <= 3){
            bloqueo.autenticar("test", "wrongpass");
            if (intentos <= 3) {
                System.out.println("Intento " + intentos + ": ✗ Fallido - Intentos restantes " + (3 - intentos));
            }
            intentos++;
        }
        System.out.println("CUENTA BLOQUEADA - Contacte al administrador");
    }
}