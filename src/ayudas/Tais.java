package ayudas;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Clase auxiliar TAIS
 * Dota de constantes y métodos estáticos, a lo largo de la aplicación. Su propósito es
 * simplificar la notación de algunas funciones que puedan resultar en pérdida de legibilidad
 * o simplemente porque creo que se ve más bonito.
 *
 * @version     2.2.1 (18/05/2018)
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
     * @since   2.1
     */
    public static void print(String string) {
        System.out.println(string);
    }


    /**
     * Vuelve en mayúsculas la primera letra de cada palabra en una frase.
     *
     * @param   palabras    Frase a capitalizar.
     * @return  La frase ya capitalizada (palabra a palabra).
     * @since   2.1
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
     * @param   palabra El tecto a capitalizar.
     * @return  La frase capitalizada.
     * @since   2.1
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


    /**
     * Entrega el elemento mayor entre 2 Strings, evaluados como enteros.
     *
     * @param   valor1  El primer valor a comparar.
     * @param   valor2  El segundo valor a comparar.
     * @return  El mayor de los dos valores.
     * @since   2.2
     */
    public static String mayor(String valor1, String valor2) {
        int entero1 = Integer.parseInt(valor1);
        int entero2 = Integer.parseInt(valor2);
        if (entero1 > entero2) {
            return valor1;
        }
        return valor2;
    }

}
