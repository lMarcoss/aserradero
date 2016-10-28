package entidades;

/**
 *
 * @author Ricardo Cortés Cruz ->> ricardo.crts.crz@gmail.com
 */

 public class CostoMaderaEntrada{
   public String clasificacion;
   public Float costo;

   public CostoMaderaEntrada(){
       
   }
   
    public CostoMaderaEntrada(String clasificacion, Float costo) {
        this.clasificacion = clasificacion;
        this.costo = costo;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public Float getCosto() {
        return costo;
    }

    public void setCosto(Float costo) {
        this.costo = costo;
    }

    
   
 }
