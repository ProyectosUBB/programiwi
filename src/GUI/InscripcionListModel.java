package GUI;

import objetos.Inscripcion;
import objetos.Ramo;
import objetos.bd.Tabla;
import objetos.bd.Tupla;

import javax.swing.*;
import java.sql.SQLException;

/**
 * Objeto que permite almacenar los ramos que se vayan inscribiendo. La clase extiende a AbstractListModel,
 * pudiendo así la inscripción puede hacerse cargo de definirse y comunicarse con la interfaz gráfica.
 *
 * @version     1.0.1 (24/05/2018)
 * @author      Anibal Llanos Prado
 */
public class InscripcionListModel extends AbstractListModel {

    /* Variables de instancia */
    private Inscripcion inscripcion;
    private Tabla todosLosRamos;


    /**
     * Constructor.
     *
     * @param   rut El RUT del alumno al que se le desea inscribir ramos.
     * @param   crearEnBlanco Si se debe crear en blanco (nueva inscripción) o no (inscripción anterior).
     * @throws  SQLException Error al buscar los ramos disponibles.
     * @since   1.0.0
     */
    InscripcionListModel(String rut, boolean crearEnBlanco) throws SQLException {
        inscripcion = new Inscripcion(rut, crearEnBlanco);
        todosLosRamos = new Tabla("ramos");
    }


    /**
     * Agrega un elemento a la inscripción.
     * NOTA: Está en inglés porque, según leí, es una práctica común y merece mantener su nombre original.
     *
     * @param   ramo El ramo que se desea inscribir.
     * @since   1.0.0
     */
    void addElement(Ramo ramo) {
        inscripcion.agregarRamo(ramo);
        fireIntervalAdded(
                this,
                inscripcion.contarRamosInscripcion() - 1,
                inscripcion.contarRamosInscripcion() - 1
        );
    }


    /**
     * Elimina un ramo de la inscripción.
     * NOTA: Está en inglés porque, según leí, es una práctica común y merece mantener su nombre original.
     *
     * @param   posicion La posición del elemento en la lista de ramos a inscribir.
     * @since   1.0.1
     */
    void removeElement(int posicion) {
        inscripcion.eliminarRamo(posicion);
        fireIntervalRemoved(this, posicion, posicion);
    }


    /**
     * Retorna el largo de la lista.
     *
     * @return  El largo de la lista.
     * @since   1.0.0
     */
    @Override
    public int getSize() {
        return inscripcion.contarRamosInscripcion();
    }


    /**
     * Ejecuta la inscripción de ramos, llamando al "inscriptor de ramos" de la Inscripción.
     *
     * @since   1.0.1
     */
    void inscribir() {
        inscripcion.inscribir();
    }


    /**
     * Retorna el valor guardado en cierto índice.
     *
     * @param   posicion El índice solicitado
     * @return  El valor almacenado en el índice.
     * @since   1.0.0
     *
     */
    @Override
    public String getElementAt(int posicion) {
        Tupla ramoTupla = inscripcion.obtenerRamo(posicion);
        Tupla ramoDesatlle = todosLosRamos.buscarPrimero("codigo", ramoTupla.valor("ramos_codigo"));
        return "[" + ramoTupla.valor("ano") + "-" + ramoTupla.valor("semestre") + "] " +
                "(" + ramoDesatlle.valor("codigo") + ") " + ramoDesatlle.valor("nombre");
    }


    /**
     * Determina si la inscripción tiene o no un ramo inscrito
     *
     * @param   codigo El código del ramo que se desea verificar.
     * @return  True si el ramo NO se encuentra inscrito. False si si.
     * @since   1.0.0
     */
    boolean noTieneRamos(String codigo) {
        return !inscripcion.tieneRamo(codigo);
    }


    /**
     * Getter de la inscripción.
     *
     * @return  La inscripción académica que corresponda.
     * @since   1.0.0
     */
    Inscripcion obtenerInscripcion() {
        return inscripcion;
    }


    /**
     * Entrega la cantidad de ramos inscritos.
     *
     * @return  La cantidad de ramos inscritos.
     * @since   1.0.1
     */
    int contarRamos() {
        return inscripcion.contarRamosInscripcion();
    }

}
