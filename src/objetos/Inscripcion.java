package objetos;

import ayudas.Tais;
import bd.BaseDeDatos;

import java.sql.SQLException;
import java.util.ArrayList;

import static ayudas.Tais.print;

/**
 * Objeto que permite almacenar tanto a un alumno como los ramos que se vayan inscribiendo, haciendo
 * de nexo entre el alumno (y su historial) y la base de datos, tanto para leer como para escribir.
 *
 * @version     2.1.1 (12/05/2018)
 * @author      Anibal Llanos Prado
 */
public class Inscripcion {

    /* Variables de instancia */
    private Alumno alumno;
    private BaseDeDatos bd;
    private ArrayList<Ramo> inscripcion;


    /**
     * Constructor
     *
     * @param rut El rut del usuario al cual se desea inscribir ramos.
     * @throws SQLException Errores al consultar información en la base de datos.
     */
    public Inscripcion(String rut) throws SQLException {
        alumno = new Alumno(rut);
        bd = new BaseDeDatos();
        inscripcion = new ArrayList<>();
    }


    /**
     * Inscribe un ramo (no persistente si no se finaliza la sesión), en su versión que recibe el código del ramo.
     *
     * @param codigo El código del ramo por inscribir.
     * @throws SQLException Errores al buscar un ramo en la base de datos.
     */
    public void inscribir(String codigo) throws SQLException {
        Ramo ramo = new Ramo(codigo);
        if (!ramo.existe()) {
            print("El ramo ingresado no existe en la lista de ramos ofertados.");
        } else if (enListaDeIscripcion(codigo)) {
            print("El ramo ya se encuentra en la lista actual de inscripción.");
        } else if (alumno.tieneRamoInscrito(codigo)) {
            print("El alumno ya ha cursado previamente el ramo.");
        } else {
            inscripcion.add(ramo);
            print("Se ha inscrito con éxito el ramo: " + ramo);
        }
    }


    /**
     * Guarda la inscripción en curso en la base de datos (persistente).
     *
     * @throws SQLException Errores al guardar datos en la base de datos.
     */
    public void guardarInscripcion() throws SQLException {
        if (inscripcion.size() == 0) {  /* No hacemos nada si no hay ramos */
            print("La inscripción actual no posee ramos.");
        }
        for (Ramo ramo : inscripcion) {
            try {   /* Intentamos guardar la inscripción */
                bd.actualizar(
                        "INSERT INTO inscripciones_ramos " +
                                "(usuario_rut, ramos_codigo, semestre, ano) " +
                                "VALUES (" + alumno.columna("rut") + "," + ramo.obtenerCodigo() + "," + Tais.SEMESTRE_ACTUAL + "," + Tais.ANO_ACTUAL + ")"
                );
            } catch (Exception e) {
                print("Error al inscribir ramo: " + ramo.toString());
                e.printStackTrace();
            }
        }
        vaciar();   /* Refrescamos la inscripción y al alumno */
        alumno.actualizar();
    }


    /**
     * Vacía la lista de ramos en proceso de inscripción.
     */
    public void vaciar() {
        inscripcion = new ArrayList<>();
        print("Se ha vaciado la lista de inscripción.");
    }


    /**
     * Determina si alguno de los ramos en la inscripción actual tiene el mismo código que el que se
     * entrega como parámetro.
     *
     * @param   codigo  El código del ramo que se desea verificar.
     * @return True si existe un ramo en la inscripción con el código indicado. False si no.
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
     * @return Un String con la representación en texto de la inscripción.
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
