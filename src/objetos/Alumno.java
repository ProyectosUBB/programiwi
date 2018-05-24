package objetos;

import objetos.bd.Tupla;

import java.sql.SQLException;

import static ayudas.Tais.capitalizar;

/**
 * Representa a un alumno. Los alumnos podrían tener ramos inscritos en el pasado. Su información
 * se encuentra almacenada en una Tupla, que perfectamente podría venir desde la base de datos.
 *
 * @version     2.2.1 (20/05/2018)
 * @author      Anibal Llanos Prado
 */
public class Alumno extends Tupla {

    /* Variables de instancia. */
    private Tupla carrera;


    /**
     * Constructor.
     *
     * @param   rut El RUT de la persona que se quiera construir.
     * @throws  SQLException Si es que se generan errores al recuperar el alumno desde la base de datos.
     * @since   2.1
     */
    private Alumno(String rut) throws SQLException {
        super("usuario", "rut", rut);
        Tupla alumnoTieneCarrera =
                Tupla.instanciarDesdeBaseDeDatos("usuario_tiene_carrera", "usuario_rut", rut);
        if (alumnoTieneCarrera != null) {
            carrera = Tupla.instanciarDesdeBaseDeDatos(
                    "carrera",
                    "codigo",
                    alumnoTieneCarrera.valor("carrera_codigo")
            );
        }
    }


    /**
     * Entrega el nombre completo de un alumno, asumiendo que siempre se desea usar un nombre
     * y dos apellidos.
     *
     * @return  El nombre completo del alumno.
     * @since   2.1
     */
    public String nombreCompleto() {
        String nombreCompleto = capitalizar(valor("primer_nombre")) + " ";
        if (tieneNoNulo("primer_apellido")) {
            nombreCompleto += capitalizar(valor("primer_apellido")) + " ";
        }
        if (tieneNoNulo("segundo_apellido")) {
            nombreCompleto += capitalizar(valor("segundo_apellido"));
        }
        return nombreCompleto.trim();
    }


    /**
     * Entrega una representación visual del alumno, en forma de texto.
     *
     * @return  Un String que representa al alumno.
     * @since   2.1.4
     */
    public String toString() {
        return valor("rut") + "  " + nombreCompleto() + "  (" + valor("email") + ")";
    }


    /**
     * Getter para los elementos de la carrera.
     *
     * @param   columna La columna (de la tabla) que se desea consultar.
     * @return  El calor almacenado en la columna consultada.
     * @since   2.2.1
     */
    public String carrera(String columna) {
        return carrera.valor(columna);
    }



    /*
     *  CONSTRUCTORES ESTÁTICOS
     *  Protegen a las clases que utilizan los objetos de obtener instancias inválidas
     *  por errores en las consultas a la base de datos. Permite respetar el contrato
     *  primordial de esta abstracción de la base de datos: Si no existe, es null.
     */


    /**
     * Entrega una instancia de Alumno utilizando un RUT para buscar en la base de datos.
     *
     * @param   rut El RUT del alumno que se desea buscar.
     * @return  Una instancia de Alumno co la información de el mismo. Si no lo encuentra, Null.
     * @throws  SQLException Error al consultar la tabla de alumnos en la base de datos.
     * @since   2.2.0
     */
    public static Alumno instanciarConRut(String rut) throws SQLException {
        Alumno nuevoAlumno = new Alumno(rut);
        if (nuevoAlumno.existe()) {
            return nuevoAlumno;
        }
        return null;
    }

}
