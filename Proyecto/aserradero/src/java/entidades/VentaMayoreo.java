package entidades;

/**
 *
 * @author lmarcoss
 */
public class VentaMayoreo {

    private String id_venta;
    private String id_madera;
    private int num_piezas;
    private float volumen;
    private float monto;
    private String tipo_madera;

    public VentaMayoreo() {
    }

    public VentaMayoreo(String id_venta, String id_madera, int num_piezas, float volumen, float monto, String tipo_madera) {
        this.id_venta = id_venta;
        this.id_madera = id_madera;
        this.num_piezas = num_piezas;
        this.volumen = volumen;
        this.monto = monto;
        this.tipo_madera = tipo_madera;
    }

    public void setId_venta(String id_venta) {
        this.id_venta = id_venta;
    }

    public void setId_madera(String id_madera) {
        this.id_madera = id_madera;
    }

    public void setNum_piezas(int num_piezas) {
        this.num_piezas = num_piezas;
    }

    public void setVolumen(float volumen) {
        this.volumen = volumen;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public void setTipo_madera(String tipo_madera) {
        this.tipo_madera = tipo_madera;
    }

    public String getId_venta() {
        return id_venta;
    }

    public String getId_madera() {
        return id_madera;
    }

    public int getNum_piezas() {
        return num_piezas;
    }

    public float getVolumen() {
        return volumen;
    }

    public float getMonto() {
        return monto;
    }

    public String getTipo_madera() {
        return tipo_madera;
    }

}
