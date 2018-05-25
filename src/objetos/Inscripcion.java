package objetos;

import ayudas.Tais;
import objetos.bd.Tabla;
import objetos.bd.Tupla;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Clase que abstrae una inscripción académica. Para evitar tener que utilizar 2 variables con datos
 * se ha creado una vista en la base de datos que entrega información sobre la tabla de ramos, así como
 * de la tabla de inscripciones.
 * NOTA: Una inscripción se puede crear desde la base de datos (poblada) o en blanco (nueva inscripción).
 *
 * @version     2.2.3 (25/05/2018)
 * @author      Anibal Llanos Prado
 */
public class Inscripcion  {


    /* Variables de instancia */
    private Tabla ramosAlumno;
    private String rut;


    /**
     * Constructor.
     *
     * @since   2.1
     */
    public Inscripcion(String rut, boolean crearEnBlanco) throws SQLException {
        this.rut = rut;
        if (crearEnBlanco) {
            ArrayList<Tupla> tuplas = new ArrayList<>();
            ramosAlumno = new Tabla("inscripciones_ramos", tuplas);
        } else {
            ramosAlumno = new Tabla("inscripciones_ramos");
            ramosAlumno = ramosAlumno.filtrarTuplas("usuario_rut", rut);
        }
    }


    /**
     * Inscribe un ramo (no persistente si no se finaliza la sesión), en su versión que recibe el código del ramo.
     * Desde la versión 2.2.1 cambia su implementación para funcionar con la interfaz gráfica.
     *
     * @param   ramo El ramo por inscribir.
     * @since   2.1.0
     */
    public void agregarRamo(Ramo ramo) throws SQLException {
        HashMap<String, String> tuplaNueva = new HashMap<>();
        tuplaNueva.put("usuario_rut", rut);
        tuplaNueva.put("ramos_codigo", ramo.valor("codigo"));
        tuplaNueva.put("semestre", Tais.SEMESTRE_ACTUAL);
        tuplaNueva.put("ano", Tais.ANO_ACTUAL);
        ramosAlumno = ramosAlumno.agregarTupla(Tupla.instanciarDesdeHashMap(tuplaNueva));
    }

    /**
     * Elimina un ramo de la inscripción.
     *
     * @param   posicion La posición que tiene el ramo en la inscripción.
     * @throws  SQLException Error al crear tabla nueva.
     * @since   2.2.2
     */
    public void eliminarRamo(int posicion) throws SQLException {
        ramosAlumno = ramosAlumno.eliminarTupla(posicion);
    }


    /**
     * Getter del ramo.
     *
     * @param   posicion La posición del ramo en la lista.
     * @return  El ramo que se encuentra en la posición indicada.
     * @since   2.2.2
     */
    public Tupla obtenerRamo(int posicion) {
        return ramosAlumno.obtenerTupla(posicion);
    }


    /**
     * Determina si la inscripción tiene un ramo previamente inscrito.
     *
     * @param   codigo El código del ramo que se desea verificar.
     * @return  True si el ramo se encuentra previamente inscrito. False si no.
     * @since   2.2.2
     */
    public boolean tieneRamo(String codigo) {
        return ramosAlumno.tiene("ramos_codigo", codigo);
    }


    /**
     * Llama al método de registrar tabla en la base de datos para insertar la nueva inscripción.
     *
     * @since   2.2.2
     */
    public void inscribir() {
        ramosAlumno.insertar();
    }


    /**
     * Construye una representación visual (texto) de la inscripción, listando los ramos que se han agregado
     * en la sesión actual.
     *
     * @return  Un String con la representación en texto de la inscripción.
     * @since   2.1
     */
    public String toString() {
        if (ramosAlumno.contarTuplas() > 0) {
            return "Lista de ramos inscritos:\n" + ramosAlumno.toString();
        }
        return "Aun no se han inscrito ramos para el alumno en cuestión.";
    }


    /**
     * Entrega la cantidad de ramos actualmente inscritos.
     *
     * @return  La cantidad de ramos inscritos.
     * @since   2.2.2
     */
    public int contarRamosInscripcion() {
        return ramosAlumno.contarTuplas();
    }


    /**
     * Entrega una tabla que contiene la última inscripción del alumno (completa). Importante mencionar
     * que primero se eligen las de el año más alto, y luego las del semestre más alto.
     *
     * @return  La última inscripción académica del alumno.
     */
    public Tabla obtenerMasRecientes() throws SQLException {
        Tabla tablaRecientes = new Tabla("ramos_alumno", ramosAlumno.obtenerTuplas());
        tablaRecientes = tablaRecientes.filtrarMaximo("ano");
        tablaRecientes = tablaRecientes.filtrarMaximo("semestre");
        return tablaRecientes;
    }

}
