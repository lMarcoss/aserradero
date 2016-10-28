package dao;

import entidades.CostoMaderaEntrada;
import interfaces.OperacionesCRUD;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rcortes
 */
public class CostoMaderaEntradaCRUD extends Conexion implements OperacionesCRUD{
    @Override
    public void registrar(Object objeto) throws Exception {
        CostoMaderaEntrada costoMaderaEntrada = (CostoMaderaEntrada) objeto;
        try {
            this.abrirConexion();
            PreparedStatement st = this.conexion.prepareStatement(""
                    + "INSERT INTO COSTO_MADERA_ENTRADA (clasificacion,costo) values(?,?)");
            st = cargarObject(st, costoMaderaEntrada);
            st.executeUpdate();
        }catch(Exception e){
            System.err.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        }
    }

    @Override
    public <T> List listar() throws Exception {
        List<CostoMaderaEntrada> costoMaderaEntradas;
        try {
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM COSTO_MADERA_ENTRADA")){
                costoMaderaEntradas = new ArrayList<>();
                try (ResultSet rs = st.executeQuery()){
                    while (rs.next()) {
                        CostoMaderaEntrada costoMaderaEntrada = (CostoMaderaEntrada) extraerObject(rs);
                        costoMaderaEntradas.add(costoMaderaEntrada);
                    }
                } catch (Exception e) {
                }
            } catch (Exception e) {
                costoMaderaEntradas = null;
                System.out.println(e);
            }
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }finally{
            cerrarConexion();
        }
        return costoMaderaEntradas;
    }

    @Override
    public Object modificar(Object objeto) throws Exception {
        CostoMaderaEntrada costoMaderaEntradaM = (CostoMaderaEntrada) objeto;
        CostoMaderaEntrada costoMaderaEntrada = null;
        this.abrirConexion();
        try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM COSTO_MADERA_ENTRADA WHERE clasificacion = ?")) {
            st.setString(1, costoMaderaEntradaM.getClasificacion());
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    costoMaderaEntrada = (CostoMaderaEntrada) extraerObject(rs);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return costoMaderaEntrada;
    }

    @Override
    public void actualizar(Object objeto) throws Exception {
        CostoMaderaEntrada costoMaderaEntrada = (CostoMaderaEntrada) objeto;
        try {
            this.abrirConexion();
            PreparedStatement st = this.conexion.prepareStatement("UPDATE COSTO_MADERA_ENTRADA SET costo = ? where clasificacion=?");            
            st.setFloat(1, costoMaderaEntrada.getCosto());
            st.setString(2, costoMaderaEntrada.getClasificacion());
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
        CostoMaderaEntrada costoMaderaEntrada = (CostoMaderaEntrada) objeto;
        try {
            this.abrirConexion();
            PreparedStatement st = this.conexion.prepareStatement("DELETE FROM COSTO_MADERA_ENTRADA WHERE clasificacion = ?");
            st.setString(1, costoMaderaEntrada.getClasificacion());
            st.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        }
    }

    @Override
    public <T> List buscar(String nombre_campo, String dato) throws Exception {
        List<CostoMaderaEntrada> costoMaderaEntradas;
        try {
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM COSTO_MADERA_ENTRADA WHERE "+nombre_campo+" like ?")){
                st.setString(1, "%"+dato+"%");
                costoMaderaEntradas = new ArrayList<>();
                try (ResultSet rs = st.executeQuery()){
                    while (rs.next()) {
                        CostoMaderaEntrada costoMaderaEntrada = (CostoMaderaEntrada) extraerObject(rs);
                        costoMaderaEntradas.add(costoMaderaEntrada);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        }
        return costoMaderaEntradas;
    }

    @Override
    public Object extraerObject(ResultSet rs) throws SQLException {
        CostoMaderaEntrada costoMaderaEntrada = new CostoMaderaEntrada();
        costoMaderaEntrada.setClasificacion(rs.getString("clasificacion"));
        costoMaderaEntrada.setCosto(Float.valueOf(rs.getString("costo")));
        return costoMaderaEntrada;
    }

    @Override
    public PreparedStatement cargarObject(PreparedStatement st, Object objeto) throws SQLException {
        CostoMaderaEntrada costoMaderaEntrada = (CostoMaderaEntrada) objeto;
        st.setString(1, costoMaderaEntrada.getClasificacion());
        st.setString(2,String.valueOf(costoMaderaEntrada.getCosto()));
        return st;
    }
}
