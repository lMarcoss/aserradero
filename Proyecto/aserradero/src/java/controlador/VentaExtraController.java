package controlador;

import dao.VentaCRUD;
import dao.VentaExtraCRUD;
import entidades.Venta;
import entidades.VentaExtra;
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
public class VentaExtraController extends HttpServlet {

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
        VentaExtra ventaExtraEC; //Enviar al CRUD
        VentaExtra ventaExtra; //Respuesta del CRUD
        VentaExtraCRUD ventaExtraCRUD;
        switch(action){
            case "nuevo":
                try {
                    VentaCRUD ventaCRUD= new VentaCRUD();
                    List<Venta> ventas;
                    ventas = (List<Venta>)ventaCRUD.listarVentasExtras();
                    request.setAttribute("ventas",ventas);
                    RequestDispatcher view = request.getRequestDispatcher("ventaExtra/nuevoVentaExtra.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarVentaExtras(request, response,"error_nuevo");
                    Logger.getLogger(VentaController.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;
            case "listar":
                listarVentaExtras(request, response,"Lista de ventas extras");
                break;
            case "modificar":
                ventaExtraEC = new VentaExtra();
                ventaExtraEC.setId_venta(Integer.valueOf(request.getParameter("id_venta")));
                ventaExtraEC.setTipo(request.getParameter("tipo"));
                ventaExtraCRUD = new VentaExtraCRUD();
                try {
                    ventaExtra = (VentaExtra) ventaExtraCRUD.modificar(ventaExtraEC);
                    request.setAttribute("ventaExtra",ventaExtra);
                    RequestDispatcher view = request.getRequestDispatcher("ventaExtra/actualizarVentaExtra.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarVentaExtras(request, response, "error_modificar");
                    System.out.println(ex);
                    Logger.getLogger(VentaExtraController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "eliminar":
                ventaExtraEC = new VentaExtra();
                ventaExtraEC.setId_venta(Integer.valueOf(request.getParameter("id_venta")));
                ventaExtraEC.setTipo(request.getParameter("tipo"));
                ventaExtraCRUD = new VentaExtraCRUD();
                try {
                    ventaExtraCRUD.eliminar(ventaExtraEC);
                    listarVentaExtras(request, response,"eliminado");
                } catch (Exception ex) {
                    listarVentaExtras(request, response, "error_eliminar");
                    System.out.println(ex);
                    Logger.getLogger(VentaExtraController.class.getName()).log(Level.SEVERE, null, ex);
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
        VentaExtra ventaExtra;
        VentaExtraCRUD ventaExtraCRUD;
        switch(action){
            case "nuevo":
                ventaExtra = extraerVentaExtraForm(request);
                ventaExtraCRUD = new VentaExtraCRUD();
                try {
                    ventaExtraCRUD.registrar(ventaExtra);
                    listarVentaExtras(request, response,"registrado");
                } catch (Exception ex) {
                    listarVentaExtras(request, response, "error_registrar");
                    System.out.println(ex);
                    Logger.getLogger(VentaExtraController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "actualizar":
                ventaExtra = extraerVentaExtraForm(request);
                ventaExtraCRUD = new VentaExtraCRUD();
                try {
                    ventaExtraCRUD.actualizar(ventaExtra);
                    listarVentaExtras(request, response,"actualizado");
                } catch (Exception ex) {
                    listarVentaExtras(request, response, "error_actualizar");
                    System.out.println(ex);
                    Logger.getLogger(VentaExtraController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "buscar":
                List <VentaExtra> ventaExtras;
                String nombre_campo = request.getParameter("nombre_campo");
                String dato = request.getParameter("dato");
                ventaExtraCRUD = new VentaExtraCRUD();
                try {
                    ventaExtras = (List<VentaExtra>)ventaExtraCRUD.buscar(nombre_campo, dato);
                    request.setAttribute("ventaExtras",ventaExtras);
                    RequestDispatcher view = request.getRequestDispatcher("ventaExtra/ventaExtras.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarVentaExtras(request, response, "error_buscar_campo");
                    System.out.println(ex);
                    Logger.getLogger(VentaExtraController.class.getName()).log(Level.SEVERE, null, ex);
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
    
    private void listarVentaExtras(HttpServletRequest request, HttpServletResponse response, String mensaje) {
        List<VentaExtra> ventaExtras;
        VentaExtraCRUD ventaExtraCrud = new VentaExtraCRUD();
        try {
            ventaExtras = (List<VentaExtra>)ventaExtraCrud.listar();
            //Enviamos las listas al jsp
            request.setAttribute("ventaExtras",ventaExtras);
            //enviamos mensaje al jsp
            request.setAttribute("mensaje",mensaje);
            RequestDispatcher view = request.getRequestDispatcher("ventaExtra/ventaExtras.jsp");
            view.forward(request,response);
        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(VentaExtraController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Extraer datos del formulario
    private VentaExtra extraerVentaExtraForm(HttpServletRequest request) {
        VentaExtra ventaExtra = new VentaExtra();
        ventaExtra.setId_venta(Integer.valueOf(request.getParameter("id_venta")));
        ventaExtra.setTipo(request.getParameter("tipo"));
        ventaExtra.setMonto(Float.valueOf(request.getParameter("monto")));
        ventaExtra.setObservacion(request.getParameter("observacion"));
        return ventaExtra;
    }

}