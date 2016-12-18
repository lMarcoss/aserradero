package controlador.registros;

import dao.registros.MunicipioCRUD;
import entidades.registros.Municipio;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author lmarcoss
 */
public class MunicipioController extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");// Forzar a usar codificación UTF-8 iso-8859-1

        // Sesiones
        HttpSession sesion = request.getSession(false);
        String nombre_usuario = (String) sesion.getAttribute("nombre_usuario");
        String rol = (String) sesion.getAttribute("rol");
        if (nombre_usuario.equals("")) {
            response.sendRedirect("/aserradero/");
        } else if (rol.equals("Administrador") || rol.equals("Empleado") || rol.equals("Vendedor")) {
            //Acción a realizar
            String action = request.getParameter("action");
            switch (action) {
                /**
                 * *************** Respuestas a métodos POST
                 * *********************
                 */
                case "insertar":
                    registrarMunicipio(request, response, sesion, action);
                    break;
                case "actualizar":
                    actualizarMunicipio(request, response, sesion, action);
                    break;
                case "buscar":
                    buscarMunicipio(request, response, sesion, action);
                    break;
                case "buscar_municipio":
                    buscarMunicipio(request, response);
                    break;
                /**
                 * *************** Respuestas a métodos GET
                 * *********************
                 */
                case "nuevo":
                    prepararNuevoMunicipio(request, response, sesion);
                    break;
                case "listar":
                    listarMunicipios(request, response, sesion, action);
                    break;
                case "modificar":
                    modificarMunicipio(request, response, sesion, action);
                    break;
                case "eliminar":
                    eliminarMunicipio(request, response, sesion, action);
                    break;
            }
        } else {
            try {
                sesion.invalidate();
            } catch (Exception e) {
                System.out.println(e);
                response.sendRedirect("/aserradero/");
            }
            response.sendRedirect("/aserradero/");
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
        Municipio municipioEC; //Enviar al CRUD
        Municipio municipio; //Respuesta del CRUD
        MunicipioCRUD municipioCRUD;
        switch (action) {
            case "listar":
                listarMunicipios(request, response, "");
                break;
            case "modificar":

                break;
            case "eliminar":

                break;
            case "buscar_municipio":
                String nombre_municipio = request.getParameter("nombre_municipio");
                try {
                    buscarMunicipio(request, response, nombre_municipio);
                } catch (Exception ex) {
                    listarMunicipios(request, response, "error_buscar");
                    Logger.getLogger(MunicipioController.class.getName()).log(Level.SEVERE, null, ex);
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
        processRequest(request, response);
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

//    //Mostrar lista de municipios
//    private void listarMunicipios(HttpServletRequest request, HttpServletResponse response, String mensaje) {
//        List<Municipio> municipios;
//        MunicipioCRUD municipiocrud = new MunicipioCRUD();
//        try {
//            municipios = (List<Municipio>)municipiocrud.listar();
//            //Enviamos las listas al jsp
//            request.setAttribute("municipios",municipios);
//            request.setAttribute("mensaje",mensaje);
//            RequestDispatcher view;
//            view = request.getRequestDispatcher("municipio/municipios.jsp");
//            view.forward(request,response);
//        } catch (Exception ex) {
//            System.out.println(ex);
//            Logger.getLogger(MunicipioController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    
//    // Extraer datos del formulario
//    private Municipio extraerMunicipioForm(HttpServletRequest request) {
//    }
//
//    private void buscarMunicipio(HttpServletRequest request, HttpServletResponse response, String nombre_municipio) throws Exception{
//    }
    private void registrarMunicipio(HttpServletRequest request, HttpServletResponse response, HttpSession sesion, String action) {
        Municipio municipio = extraerMunicipioForm(request, sesion, action);
        MunicipioCRUD municipioCRUD = new MunicipioCRUD();
        try {
            municipioCRUD.registrar(municipio);
            listarMunicipios(request, response, sesion, action);
        } catch (Exception ex) {
            listarMunicipios(request, response, sesion, "error_registrar");
            Logger.getLogger(MunicipioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Extraer datos de municipio en el formulario HTML
    private Municipio extraerMunicipioForm(HttpServletRequest request, HttpSession sesion, String action) {
        Municipio municipio = new Municipio();
        municipio.setNombre_municipio(request.getParameter("nombre_municipio"));
        municipio.setEstado(request.getParameter("estado"));
        municipio.setTelefono(request.getParameter("telefono"));
        return municipio;
    }

    private void actualizarMunicipio(HttpServletRequest request, HttpServletResponse response, HttpSession sesion, String action) {
        Municipio municipio = extraerMunicipioForm(request, sesion, action);
        MunicipioCRUD municipioCRUD = new MunicipioCRUD();
        try {
            municipioCRUD.actualizar(municipio);
            listarMunicipios(request, response, sesion, action);
        } catch (Exception ex) {
            listarMunicipios(request, response, sesion, action);
            Logger.getLogger(MunicipioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void buscarMunicipio(HttpServletRequest request, HttpServletResponse response, HttpSession sesion, String action) {
        List<Municipio> municipios;
        String nombre_campo = request.getParameter("nombre_campo");
        String dato = request.getParameter("dato");
        MunicipioCRUD municipioCRUD = new MunicipioCRUD();
        try {
            municipios = (List<Municipio>) municipioCRUD.buscar(nombre_campo, dato);
            mostrarMunicipios(request, response, municipios, action);
        } catch (Exception ex) {
            listarMunicipios(request, response, sesion, "error_buscar");
            Logger.getLogger(MunicipioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void mostrarMunicipios(HttpServletRequest request, HttpServletResponse response, List<Municipio> listaMunicipioes, String action) {
        request.setAttribute("mensaje", action);
        request.setAttribute("listaMunicipioes", listaMunicipioes);
        RequestDispatcher view = request.getRequestDispatcher("moduloRegistros/localidad/listarMunicipios.jsp");
        try {
            view.forward(request, response);
        } catch (ServletException | IOException ex) {
            System.err.println("No se pudo mostrar la lista de municipios");
            Logger.getLogger(MunicipioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void prepararNuevoMunicipio(HttpServletRequest request, HttpServletResponse response, HttpSession sesion) {
        MunicipioCRUD municipioCRUD = new MunicipioCRUD();
        List<Municipio> municipios;
        try {
            municipios = (List<Municipio>) municipioCRUD.listar();
            request.setAttribute("municipios", municipios);
            RequestDispatcher view = request.getRequestDispatcher("moduloRegistros/localidad/nuevoMunicipio.jsp");
            view.forward(request, response);
        } catch (Exception ex) {
            listarMunicipios(request, response, sesion, "error_nuevo");
            Logger.getLogger(MunicipioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listarMunicipios(HttpServletRequest request, HttpServletResponse response, HttpSession sesion, String action) {
        List<Municipio> listaMunicipios;
        MunicipioCRUD municipioCRUD = new MunicipioCRUD();
        try {
            listaMunicipios = (List<Municipio>)municipioCRUD.listar();
            mostrarMunicipios(request, response, listaMunicipios, action);
        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(MunicipioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void modificarMunicipio(HttpServletRequest request, HttpServletResponse response, HttpSession sesion, String action) {
        Municipio municipioEC = new Municipio();
        municipioEC.setNombre_municipio(request.getParameter("nombre_municipio"));
        MunicipioCRUD municipioCRUD = new MunicipioCRUD();
        try {
            Municipio municipio = municipioCRUD.modificar(municipioEC);
            request.setAttribute("municipio", municipio);
            RequestDispatcher view = request.getRequestDispatcher("municipio/actualizarMunicipio.jsp");
            view.forward(request, response);
        } catch (Exception ex) {
            listarMunicipios(request, response, sesion, "error_modificar");
            Logger.getLogger(MunicipioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void eliminarMunicipio(HttpServletRequest request, HttpServletResponse response, HttpSession sesion, String action) {
        Municipio municipioEC = new Municipio();
        municipioEC.setNombre_municipio(request.getParameter("nombre_municipio"));
        MunicipioCRUD municipioCRUD = new MunicipioCRUD();
        try {
            municipioCRUD.eliminar(municipioEC);
            listarMunicipios(request, response, sesion, action);
        } catch (Exception ex) {
            listarMunicipios(request, response, sesion, "error_eliminar");
            Logger.getLogger(MunicipioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void buscarMunicipio(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
