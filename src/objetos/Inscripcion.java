package objetos;

import objetos.bd.Tabla;

import java.sql.SQLException;
import java.util.ArrayList;

import static ayudas.Tais.print;

/**
 * Objeto que permite almacenar los ramos que se vayan inscribiendo. En el futuro se deberá implementar
 * una interfaz para rellenar JList en la interfaz.
 *
 * @version     2.2.0 (20/05/2018)
 * @author      Anibal Llanos Prado
 */
public class Inscripcion {

    /* Variables de instancia */
    private ArrayList<Ramo> inscripcion;


    /**
     * Constructor
     *
     * @since   2.1.4
     */
    public Inscripcion() {
        inscripcion = new ArrayList<>();
    }


    /**
     * Inscribe un ramo (no persistente si no se finaliza la sesión), en su versión que recibe el código del ramo.
     * TODO Verificación del ramo en inscripción anterior (en interfaz).
     *
     * @param   codigo El código del ramo por inscribir.
     * @throws  SQLException Errores al buscar un ramo en la base de datos.
     * @since   2.1
     */
    public void inscribir(String codigo) throws SQLException {
        Ramo ramo = Ramo.instanciarConCodigo(codigo);
        if (ramo == null) {
            print("El ramo ingresado no existe en la lista de ramos ofertados.");
        } else if (enListaDeIscripcion(codigo)) {
            print("El ramo ya se encuentra en la lista actual de inscripción.");
        } else {
            inscripcion.add(ramo);
            print("Se ha inscrito con éxito el ramo: " + ramo);
        }
    }


    /**
     * Guarda la inscripción en curso en la base de datos (persistente).
     *
     * @since   2.1
     */
    public void guardarInscripcion() {
        if (inscripcion.size() == 0) {  /* No hacemos nada si no hay ramos */
            print("La inscripción actual no posee ramos.");
        }
        for (Ramo ramo : inscripcion) {
            new Tabla("ramos", ramo).insertar();
        }
        vaciar();   /* Refrescamos la inscripción y al alumno */
    }


    /**
     * Vacía la lista de ramos en proceso de inscripción.
     *
     * @since   2.1
     */
    private void vaciar() {
        inscripcion = new ArrayList<>();
        print("Se ha vaciado la lista de inscripción.");
    }


    /**
     * Determina si alguno de los ramos en la inscripción actual tiene el mismo código que el que se
     * entrega como parámetro.
     *
     * @param   codigo  El código del ramo que se desea verificar.
     * @return  True si existe un ramo en la inscripción con el código indicado. False si no.
     * @since   2.1
     */
    private boolean enListaDeIscripcion(String codigo) {
        for (Ramo ramo : inscripcion) {
            if (ramo.tieneCodigo(codigo)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Construye una representación visual (texto) de la inscripción, listando los ramos que se han agregado
     * en la sesión actual.
     *
     * @return  Un String con la representación en texto de la inscripción.
     * @since   2.1
     */
    public String toString() {
        if (inscripcion.size() > 0) {
            StringBuilder salida = new StringBuilder("Lista de ramos inscritos:\n");
            for (Ramo ramo : inscripcion) {
                salida.append(ramo.toString()).append("\n");
            }
            return salida.toString();
        }
        return "Aun no se han inscrito ramos para el alumno en cuestión.";
    }

}
