
package calcularID;

/**
 *
 * @author lmarcoss
 */
    
// Clase para calcular Id empleado, Id cliente, e Id proveedor a través del Id persona e id jefe
public class CalcularIdECP {
    // Se concatena el id_persona + los 8 primeros caracteres del id_administrador
    public String CalcularId(String id_persona, String id_jefe) {
        String id;
        id = id_persona+ id_jefe.substring(0,8);
        return id;
    }
    
    
}
