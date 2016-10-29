package controlador;

import dao.MaderaClasificacionCRUD;
import dao.VentaCRUD;
import dao.VentaPaqueteCRUD;
import entidades.Venta;
import entidades.VentaPaquete;
import entidadesVirtuales.CostoMaderaClasificacion;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author lmarcoss
 */
public class VentaPaqueteController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");// Forzar a usar codificación UTF-8 iso-8859-1
        //Llegan url
        String action = request.getParameter("action");
        VentaPaquete ventaPaqueteEC; //Enviar al CRUD
        VentaPaquete ventaPaquete; //Respuesta del CRUD
        VentaPaqueteCRUD ventaPaqueteCRUD;
        switch(action){
            case "nuevo":
                try {
                    //enviamos lista de ventas al jsp
                    VentaCRUD ventaCRUD= new VentaCRUD();
                    List<Venta> ventas;
                    ventas = (List<Venta>)ventaCRUD.listarVentasPaquete();
                    request.setAttribute("ventas",ventas);
                    
                    //enviamos lista de maderaClasificacion al jsp
                    MaderaClasificacionCRUD maderaClasificacionCRUD= new MaderaClasificacionCRUD();
                    List<CostoMaderaClasificacion> costoMaderaClasificaciones;
                    costoMaderaClasificaciones = (List<CostoMaderaClasificacion>)maderaClasificacionCRUD.listarCostoMaderaClasif();
                    request.setAttribute("costoMaderaClasificaciones",costoMaderaClasificaciones);
                    
                    RequestDispatcher view = request.getRequestDispatcher("ventaPaquete/nuevoVentaPaquete.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarVentaPaquetes(request, response,"error_nuevo");
                    Logger.getLogger(VentaPaqueteController.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;
            case "listar":
                listarVentaPaquetes(request, response,"Lista de ventas por paquete");
                break;
            case "modificar":
                ventaPaqueteEC = extraerClavesVentaPaquete(request);
                ventaPaqueteCRUD = new VentaPaqueteCRUD();
                try {
                    //Enviamos clasificacion de madera con su costo
                    MaderaClasificacionCRUD maderaClasificacionCRUD= new MaderaClasificacionCRUD();
                    List<CostoMaderaClasificacion> costoMaderaClasificaciones;
                    costoMaderaClasificaciones = (List<CostoMaderaClasificacion>)maderaClasificacionCRUD.listarCostoMaderaClasif();
                    request.setAttribute("costoMaderaClasificaciones",costoMaderaClasificaciones);
                    //enviamos el paquete a modificar
                    ventaPaquete = (VentaPaquete) ventaPaqueteCRUD.modificar(ventaPaqueteEC);
                    request.setAttribute("ventaPaquete",ventaPaquete);
                    
                    RequestDispatcher view = request.getRequestDispatcher("ventaPaquete/actualizarVentaPaquete.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarVentaPaquetes(request, response, "error_modificar");
                    System.out.println(ex);
                    Logger.getLogger(VentaPaqueteController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "eliminar":
                ventaPaqueteEC = extraerClavesVentaPaquete(request);
                ventaPaqueteCRUD = new VentaPaqueteCRUD();
                try {
                    ventaPaqueteCRUD.eliminar(ventaPaqueteEC);
                    listarVentaPaquetes(request, response,"eliminado");
                } catch (Exception ex) {
                    listarVentaPaquetes(request, response, "error_eliminar");
                    System.out.println(ex);
                    Logger.getLogger(VentaPaqueteController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;                        
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Llegan formularios de tipo post
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");// Forzar a usar codificación UTF-8 iso-8859-1
        String action = request.getParameter("action");
        VentaPaquete ventaPaquete;
        VentaPaqueteCRUD ventaPaqueteCRUD;
        switch(action){
            case "nuevo":
                ventaPaquete = extraerVentaPaqueteForm(request);
                ventaPaqueteCRUD = new VentaPaqueteCRUD();
                try {
                    ventaPaqueteCRUD.registrar(ventaPaquete);
                    listarVentaPaquetes(request, response,"registrado");
                } catch (Exception ex) {
                    listarVentaPaquetes(request, response, "error_registrar");
                    System.out.println(ex);
                    Logger.getLogger(VentaPaqueteController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "actualizar":
                ventaPaquete = extraerVentaPaqueteForm(request);
                ventaPaqueteCRUD = new VentaPaqueteCRUD();
                try {
                    ventaPaqueteCRUD.actualizar(ventaPaquete);
                    listarVentaPaquetes(request, response,"actualizado");
                } catch (Exception ex) {
                    listarVentaPaquetes(request, response, "error_actualizar");
                    System.out.println(ex);
                    Logger.getLogger(VentaPaqueteController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "buscar":
                List <VentaPaquete> ventaPaquetes;
                String nombre_campo = request.getParameter("nombre_campo");
                String dato = request.getParameter("dato");
                ventaPaqueteCRUD = new VentaPaqueteCRUD();
                try {
                    ventaPaquetes = (List<VentaPaquete>)ventaPaqueteCRUD.buscar(nombre_campo, dato);
                    request.setAttribute("ventaPaquetes",ventaPaquetes);
                    RequestDispatcher view = request.getRequestDispatcher("ventaPaquete/ventaPaquetes.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarVentaPaquetes(request, response, "error_buscar_campo");
                    System.out.println(ex);
                    Logger.getLogger(VentaPaqueteController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    private void listarVentaPaquetes(HttpServletRequest request, HttpServletResponse response,String mensaje) {
        List<VentaPaquete> ventaPaquetes;
        VentaPaqueteCRUD ventaPaqueteCrud = new VentaPaqueteCRUD();
        try {
            ventaPaquetes = (List<VentaPaquete>)ventaPaqueteCrud.listar();
            //Enviamos las listas al jsp
            request.setAttribute("ventaPaquetes",ventaPaquetes);
            //Enviamos mensaje al jsp
            request.setAttribute("mensaje",mensaje);
            RequestDispatcher view = request.getRequestDispatcher("ventaPaquete/ventaPaquetes.jsp");
            view.forward(request,response);
        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(VentaPaqueteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Extraer datos del formulario
    private VentaPaquete extraerVentaPaqueteForm(HttpServletRequest request) {
        VentaPaquete ventaPaquete = new VentaPaquete();
        ventaPaquete.setId_venta(Integer.valueOf(request.getParameter("id_venta")));
        ventaPaquete.setNumero_paquete(Integer.valueOf(request.getParameter("numero_paquete")));
        ventaPaquete.setId_madera(request.getParameter("id_madera"));
        ventaPaquete.setNum_piezas(Integer.valueOf(request.getParameter("num_piezas")));
        ventaPaquete.setVolumen(Float.valueOf(request.getParameter("volumen")));
        ventaPaquete.setMonto(Float.valueOf(request.getParameter("monto")));
        return ventaPaquete;
    }

    private VentaPaquete extraerClavesVentaPaquete(HttpServletRequest request) {
        VentaPaquete ventaPaqueteEC = new VentaPaquete();
        ventaPaqueteEC.setId_venta(Integer.valueOf(request.getParameter("id_venta")));
        ventaPaqueteEC.setNumero_paquete(Integer.valueOf(request.getParameter("numero_paquete")));
        ventaPaqueteEC.setId_madera(request.getParameter("id_madera"));
        return ventaPaqueteEC;
    }

}