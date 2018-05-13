package objetos;

import bd.Tabla;
import bd.Tupla;

import java.sql.SQLException;

import static ayudas.Tais.capitalizar;

/**
 * Representa a un alumno. Los alumnos podrían tener ramos inscritos en el pasado. Su información
 * se encuentra almacenada en una Tupla, que perfectamente podría venir desde la base de datos.
 *
 * @version     2.1.1 (12/05/2018)
 * @author      Anibal Llanos Prado
 */
public class Alumno {

    /* Variables de instancia */
    private Tupla alumno;
    private Tabla inscripcionesPrevias;


    /**
     * Constructor.
     *
     * @param   rut El RUT de la persona que se quiera construir.
     * @throws SQLException Si es que se generan errores al recuperar el alumno desde la base de datos.
     */
    public Alumno(String rut) throws SQLException {
        Tabla alumnos = new Tabla("usuario");
        alumno = alumnos.buscarPrimero("rut", rut);
        actualizar();
    }


    /**
     * Actualiza las inscripciones anteriores del alumno. Esto sirve para visualizar su información
     * luego de haber modificado la base de datos.
     *
     * @throws SQLException Si se encuentran problemas al intentar escribir en la base de datos.
     */
    void actualizar() throws SQLException {
        inscripcionesPrevias = new Tabla("inscripciones_ramos");
        inscripcionesPrevias.filtrarTuplas("usuario_rut", alumno.columna("rut"));
    }


    /**
     * Entrega el nombre completo de un alumno, asumiendo que siempre se desea usar un nombre
     * y dos apellidos.
     *
     * @return El nombre completo del alumno.
     */
    public String nombreCompleto() {
        String nombreCompleto = capitalizar(alumno.columna("primer_nombre")) + " ";
        if (tiene("primer_apellido")) {
            nombreCompleto += capitalizar(columna("primer_apellido")) + " ";
        }
        if (tiene("segundo_apellido")) {
            nombreCompleto += capitalizar(columna("segundo_apellido"));
        }
        return nombreCompleto.trim();
    }


    /**
     * Determina si es que el alumno tiene inscrito el ramo (cuyo código es indicado) en algún semestre
     * anterior al actual.
     *
     * @param   codigo  El código del ramo que se deberá verificar.
     * @return True si se ha encontrado el ramo en el historial del alumno. False si no.
     */
    boolean tieneRamoInscrito(String codigo) {
        return inscripcionesPrevias.tiene("ramos_codigo", codigo);
    }


    /**
     * Determina si el alumno tiene definida alguna columna, pero que no sea un elemento nulo
     * (notar la diferencia entre tener un nulo y no tener nada).
     *
     * @param   columna La columna del alumno que se desea verificar.
     * @return True si la columna tiene un valor y no es julo. False si no.
     */
    private boolean tiene(String columna) {
        return alumno.tieneNoNulo(columna);
    }


    /**
     * Entrega el valor almacenado en alguna columna de la tupla.
     *
     * @param   columna El nombre de la columna que almacena el valor a consultar.
     * @return Un String cuyo valor es el que se almacena en la columna de la tupla.
     */
    String columna(String columna) {
        return alumno.columna(columna);
    }

}
