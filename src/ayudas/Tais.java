package ayudas;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Clase auxiliar TAIS
 * Dota de constantes y métodos estáticos, a lo largo de la aplicación. Su propósito es
 * simplificar la notación de algunas funciones que puedan resultar en pérdida de legibilidad
 * o simplemente porque creo que se ve más bonito.
 *
 * @version     2.1 (12/05/2018)
 * @author      Anibal Llanos Prado
 *
 */
public class Tais {

    /** Semestre actual. Debe ser proveído desde algún servicio. */
    public static final String SEMESTRE_ACTUAL = "1";

    /** Año actual. Debe ser proveído desde algún servicio. */
    public static final String ANO_ACTUAL = "2018";


    /**
     * Imprime en consola. Simplifica la sintaxis. Corresponde a un print CON salto de línea.
     *
     * @param   string El String que se desea escribir.
     */
    public static void print(String string) {
        System.out.println(string);
    }


    /**
     * Captura una entrada desde el teclado y devuelve su valor.
     *
     * @param   mensaje El mensaje que se desea imprimir.
     * @param   lector  El lector en buffer para capturar el teclado.
     * @return El String capturado desde el teclado.
     * @throws IOException Errores con la entrada de teclado.
     */
    public static String input(String mensaje, BufferedReader lector) throws IOException {
        System.out.print(mensaje);
        return lector.readLine();
    }


    /**
     * Vuelve en mayúsculas la primera letra de cada palabra en una frase.
     *
     * @param   palabras    Frase a capitalizar.
     * @return La frase ya capitalizada (palabra a palabra).
     */
    public static String capitalizarTodo(String palabras) {
        String[] palabraArreglo = palabras.split(" ");
        for (int i = 0; i < palabraArreglo.length; ++i) {
            palabraArreglo[i] = capitalizar(palabraArreglo[i]);
        }
        return String.join(" ", palabraArreglo);
    }


    /**
     * Capitaliza un texto. Sólo capitaliza la primera letra.
     * DEBE DESAPARECER!
     *
     * @param palabra El tecto a capitalizar.
     * @return La frase capitalizada.
     */
    public static String capitalizar(String palabra) {
        String palabraCapitalizada = "";
        if (palabra.length() > 0) {
            palabraCapitalizada = palabra.substring(0, 1).toUpperCase();
            if (palabra.length() > 1) {
                palabraCapitalizada += palabra.substring(1);
            }
        }
        return palabraCapitalizada;
    }

}
