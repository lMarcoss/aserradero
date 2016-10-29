<%--
    Document   : pagosluz
    Created on : 26/09/2016, 12:30:04 PM
    Author     : rcortes
--%>

<%@page import="entidades.PagoLuz"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <PagoLuz> pagosluz = (List<PagoLuz>) request.getAttribute("pagosluz");
    String mensaje = (String)request.getAttribute("mensaje");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <title>Pagos de luz</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        
        <input type="hidden" name="mensaje" id="mensaje" value="<%=mensaje%>"
        
        <!-- ************************** opción de búsqueda-->
        <div>
            <form method="POST" action="/aserradero/PagoLuzController?action=buscar">
                <table>
                    <tr>
                        <td>
                          <select name="nombre_campo" >
                            <option value="fecha">Fecha</option>
                            <option value="id_empleado">Id empleado</option>
                            <option value="monto">Monto</option>
                            <option value="observcion">Observación</option>
                          </select>
                        </td>
                        <td><input type="text" name="dato" placeholder="Escriba su búsqueda"></td>
                        <td colspan="2"><input type="submit" value="Buscar"></td>
                    </tr>
                </table>
            </form>
        </div> <!-- Fin opción de búsqueda-->

        <!-- ************************* Resultado Consulta-->
        <div>
            <table class="table-condensed">
                    <tr>
                        <th>N°</th>
                        <th>Fecha</th>
                        <th>Id empleado</th>
                        <th>Monto</th>
                        <th>Observación</th>
                    </tr>
                    <%
                        int i=0;
                        for (PagoLuz pagoluz : pagosluz) {
                            out.print("<tr>"
                                +"<td>"+(i+1)+"</td>"
                                +"<td>"+pagoluz.getFecha()+"</td>"
                                +"<td>"+pagoluz.getId_empleado()+"</td>"
                                +"<td>"+pagoluz.getMonto()+"</td>"
                                +"<td>"+pagoluz.getObservacion()+"</td>"
                                + "<td><a href=\"javascript:if (confirm('¿Estás seguro de eliminar?')){parent.location='/aserradero/PagoLuzController?action=eliminar&id_pago_luz="+pagoluz.getId_pago_luz()+"';};\">Eliminar</a></td>"
                            + "</tr>" );
                            i++;
                        }
                    %>
            </table>
            <div>
                <input type="button" value="Agregar pago" onClick=" window.location.href='/aserradero/PagoLuzController?action=nuevo' ">
            </div>
        </div><!-- Resultado Consulta-->
    </body>
</html>