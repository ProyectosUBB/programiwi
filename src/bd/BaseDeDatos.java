package bd;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import static ayudas.Tais.print;

/**
 * Clase que se encarga de abstraer la base de datos, según lo requerido por la
 * aplicación. No abstrae la base de datos en la memoria, sólo facalita la comunicación
 * a través de SQL.
 * PUEDE SER IMPLEMENTADA CON PATRON BUILDER!!!
 *
 * @version     2.1 (12/05/2018)
 * @author      Anibal Llanos Prado
 */
public class BaseDeDatos {

    /* VARIABLES DE INSTANCIA */
    private Connection conexion;


    /**
     * Constructor
     *
     * @throws SQLException Si se generan errores de SQL
     */
    public BaseDeDatos() throws SQLException {
        String[][] opciones_arreglo = {     /* Opciones para entregar a la URL del driver */
                {"useUnicode", "true"},
                {"useJDBCCompliantTimezoneShift", "true"},
                {"useLegacyDatetimeCode", "false"},
                {"serverTimezone", "UTC"}
        };
        StringBuilder opciones_builder = new StringBuilder();
        for (String[] opcion : opciones_arreglo) {      /* Empaquetar las opciones en un string */
            opciones_builder.append("&").append(opcion[0]).append("=").append(opcion[1]);
        }
        String opciones_string = opciones_builder.toString();

        try {   /* Intenta cargar el Driver de la base de datos */
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
        conexion = DriverManager.getConnection (        /* Conectarse a la base de datos */
                "jdbc:mysql://localhost/programiwi?" + opciones_string.substring(1),
                "root",
                ""
        );
    }


    /**
     * Ejecuta una consulta cualquiera en la base de datos.
     *
     * @param   consulta El string de consulta SQL.
     * @return  Una lista con HashMaps con las tuplas resultantes.
     * @throws SQLException Si se generan errores de SQL
     */
    ArrayList<HashMap<String, String>> consultar(String consulta) throws SQLException {
        Statement sentencia = conexion.createStatement();   /* Buscar resultados */
        ResultSet resultados = sentencia.executeQuery (consulta);
        
        ArrayList<HashMap<String, String>> resultadosLista = new ArrayList<>();
        HashMap<String, String> filaResultados;
        ResultSetMetaData metaData;
        while (resultados.next()) {     /* Procesar resultados */
            metaData = resultados.getMetaData();
            filaResultados = new HashMap<>();
            for (int i = 1; i <= metaData.getColumnCount(); ++i) {
                filaResultados.put(metaData.getColumnName(i), resultados.getString(i));
            }
            resultadosLista.add(filaResultados);
        }
        return resultadosLista;
    }


    /**
     * Inserta nuevos valores en la base de datos.
     *
     * @param consulta Consulta SQL a procesar.
     */
    public void actualizar(String consulta) {
        try {
            Statement s = conexion.createStatement();
            s.executeUpdate(consulta);
        } catch (Exception e) {
            print("Error al insertar entrada en la base de datos!");
            e.printStackTrace();
        }
    }

}
