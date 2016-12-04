
package controlador;

import dao.UsuarioCRUD;
import entidades.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lmarcoss
 */
public class IniciarSesion extends HttpServlet {

    @Override
    public void init(ServletConfig conf) throws ServletException{
        super.init(conf);
        
        
    }
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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServletConvocatorias</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServletConvocatorias at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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

        // cerrar sesión
        String action = request.getParameter("action");
        if(action.equals("cerrar_sesion")){
            HttpSession sesion = request.getSession(true);
            sesion.invalidate();
            response.sendRedirect("/aserradero/");
        }else{
            response.sendRedirect("/aserradero/");
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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");// Forzar a usar codificación UTF-8 iso-8859-1

        // Se reciben formularios del médoto post
        
        // Form de inicio de sesión
        String nombre_usuario = request.getParameter("nombre_usuario");
        String contrasenia = request.getParameter("contrasenia");
        UsuarioCRUD usuarioCRUD = new UsuarioCRUD();
        try {
            Usuario usuario = usuarioCRUD.validarUsuario(nombre_usuario, contrasenia);
            if(usuario!= null){
                //Creamos la sesión
                HttpSession sesion = request.getSession(true);
                sesion.setAttribute("nombre_usuario", nombre_usuario);
                response.sendRedirect("/aserradero/inicio/inicio.jsp");
            }else{
                response.sendRedirect("/aserradero/");
            }
        } catch (Exception ex) {
            response.sendRedirect("/aserradero/");
            //Logger.getLogger(IniciarSesion.class.getName()).log(Level.SEVERE, null, ex);
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

}
