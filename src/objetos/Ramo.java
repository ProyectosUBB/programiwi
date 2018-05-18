package objetos;

import bd.Tabla;
import bd.Tupla;

import java.sql.SQLException;
import java.util.ArrayList;

import static ayudas.Tais.capitalizarTodo;

/**
 * Representación de un ramo, el cual es en sí una tupla, pero con un par de cosas adicionales. En
 * algún momento probablemente desaparezca esta clase para usar meramente una Tupla.
 *
 * @version     2.1.2 (18/05/2018)
 * @author      Anibal Llanos Prado
 */
public class Ramo {

    /* Variables de instancia */
    private Tupla ramo;


    /**
     * Constructor
     *
     * @param   codigo  El código del ramo que se desea crear.
     * @throws SQLException En casos de generarse errores al buscar el ramo en la base de datos.
     */
    Ramo(String codigo) throws SQLException {
        Tabla ramos = new Tabla("ramos");
        ramo = ramos.buscarPrimero("codigo", codigo);

    }


    /**
     * Construye una representación visual (texto) del ramo.
     *
     * @return Un String con la representación en texto del ramo.
     */
    public String toString() {
        return "(" + ramo.valor("codigo") + ") " + capitalizarTodo(ramo.valor("nombre"));
    }


    /**
     * Determina si el ramo existe. Definimos existencia como que su tupla sea nula.
     *
     * @return True si la tupla del ramo es nula. False si no.
     */
    boolean existe() {
        return ramo != null;
    }


    /**
     * Determina si el código del ramo es igual al que se indica a través de los parámetros.
     *
     * @param   codigo  El código conhtra el cual se comparará.
     * @return True si el código entregado y el almacenado son iguales. False si no.
     */
    boolean tieneCodigo(String codigo) {
        return ramo.tiene("codigo", codigo);
    }


    /**
     * Entrega códgo del ramo.
     *
     * @return Un String cuyo valor es el código del ramo.
     */
    String obtenerCodigo() {
        return ramo.valor("codigo");
    }


    /**
     * Convierte el ramo en una tabla. Esto permite interactuar con la base de datos utilizando los
     * objetos del programa (abstracción).
     *
     * @return  Una tabla con 1 tupla, el ramo.
     */
    Tabla convertirEnTabla() {
        ArrayList<Tupla> tuplas = new ArrayList<>();
        tuplas.add(ramo);
        return new Tabla("ramos", tuplas);
    }

}
