package dao;

import calcularID.CalcularIdECP;
import entidades.Empleado;
import interfaces.OperacionesCRUD;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lmarcoss
 */
public class EmpleadoCRUD extends Conexion implements OperacionesCRUD{

    @Override
    public void registrar(Object objeto) throws Exception {
        Empleado empleado = (Empleado) objeto;
        try{
            this.abrirConexion();
            PreparedStatement st = this.conexion.prepareStatement(
                        "INSERT INTO EMPLEADO (id_empleado,id_persona,id_jefe,roll,estatus) VALUES (?,?,?,?,?)");
            st = cargarObject(st, empleado);
            st.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        } 
    }

    @Override
    public <T> List listar() throws Exception {
        List<Empleado> empleados;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM PERSONAL_EMPLEADO")) {
                empleados = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        Empleado empleado = (Empleado) extraerObject(rs);
                        empleados.add(empleado);
                    }
                }
            }catch(Exception e){
                empleados = null;
                System.out.println(e);
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        } 
        return empleados;
    }
    
    @Override
    public Object modificar(Object objeto) throws Exception {
        Empleado empleadoM = (Empleado) objeto;
        Empleado empleado = null;
        this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM PERSONAL_EMPLEADO WHERE id_empleado = ? AND roll = ?")) {
                st.setString(1, empleadoM.getId_empleado());
                st.setString(2, empleadoM.getRoll());
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        empleado = (Empleado) extraerObject(rs);
                    }
                }
            }
        return empleado;
    }

    @Override
    public void actualizar(Object objeto) throws Exception {
        Empleado empleado = (Empleado) objeto;
        try{
            this.abrirConexion();
            PreparedStatement st= this.conexion.prepareStatement("UPDATE EMPLEADO SET estatus = ? WHERE id_empleado = ? AND roll = ?");
            st.setString(1,empleado.getEstatus());
            st.setString(2,empleado.getId_empleado());
            st.setString(3,empleado.getRoll());
            st.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        }
    }

    @Override
    public void eliminar(Object objeto) throws Exception {
        Empleado empleado = (Empleado) objeto;
        try{
            this.abrirConexion();
            PreparedStatement st= this.conexion.prepareStatement("DELETE FROM EMPLEADO WHERE id_empleado = ? AND id_jefe = ? AND roll = ?");
            st.setString(1,empleado.getId_empleado());
            st.setString(2,empleado.getId_jefe());
            st.setString(3,empleado.getRoll());
            st.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        }
    }

    @Override
    public <T> List buscar(String nombre_campo, String dato) throws Exception {
        List<Empleado> empleados;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM PERSONAL_EMPLEADO WHERE "+nombre_campo+" like ?")) {
                st.setString(1, "%"+dato+"%");
                empleados = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        Empleado empleado = (Empleado) extraerObject(rs);
                        empleados.add(empleado);
                    }
                }
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        }
        return empleados;
    }
    
    @Override
    public Object extraerObject(ResultSet rs) throws SQLException {
        // Extraemos todos los atributos del objeto
        Empleado empleado = new Empleado();
        empleado.setId_empleado(rs.getString("id_empleado"));
        empleado.setId_persona(rs.getString("id_persona"));
        empleado.setEmpleado(rs.getString("empleado"));
        empleado.setId_jefe(rs.getString("id_jefe"));
        empleado.setJefe(rs.getString("jefe"));
        empleado.setRoll(rs.getString("roll"));
        empleado.setEstatus(rs.getString("estatus"));
        return empleado;
    }

    @Override
    public PreparedStatement cargarObject(PreparedStatement st, Object objeto) throws SQLException {
        //Sólo cargamos los datos que se insertan en la tabla EMPLEADO
        Empleado empleado = (Empleado) objeto;
        CalcularIdECP calcularId = new CalcularIdECP();
        st.setString(1,calcularId.CalcularId(empleado.getId_persona(),empleado.getId_jefe())); //calculamos el id con la clase CalcularIdECP
        st.setString(2,empleado.getId_persona());
        st.setString(3,empleado.getId_jefe());
        st.setString(4,empleado.getRoll());
        st.setString(5,empleado.getEstatus());
        return st;
    }
    
    public <T> List listarEmpleadoPorRoll(String roll) throws Exception {
        List<Empleado> empleados;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM PERSONAL_EMPLEADO WHERE roll = ?")) {
                st.setString(1, roll);
                empleados = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        Empleado empleado = (Empleado) extraerObject(rs);
                        empleados.add(empleado);
                    }
                }
            }catch(Exception e){
                empleados = null;
                System.out.println(e);
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        } 
        return empleados;
    }
}
