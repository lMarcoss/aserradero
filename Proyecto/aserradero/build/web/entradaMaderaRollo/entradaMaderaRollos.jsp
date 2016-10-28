<%--
    Document   : compras
    Created on : 26/09/2016, 06:08:14 PM
    Author     : rcortes
--%>

<%@page import="entidades.EntradaMaderaRollo"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% 
    List <EntradaMaderaRollo> entradas = (List<EntradaMaderaRollo>) request.getAttribute("entradas");
    String mensaje = (String)request.getAttribute("mensaje"); 
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <title>EntradaMaderaRollos</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <input type="hidden" name="mensaje" id="mensaje" value="<%=mensaje%>"

        <!-- ************************** opción de búsqueda-->
            <form method="POST" action="/aserradero/EntradaMaderaRolloController?action=buscar">
                <table>
                    <tr>
                        <td>
                            <select name="nombre_campo" >
                                <option value="fecha">Fecha</option>
                                <option value="id_empleado">Id de empleado</option>
                                <option value="id_compra">Id compra</option>
                                <option value="id_proveedor">Id proveedor</option>
                                <option value="id_chofer">Id chofer</option>
                                <option value="num_piezas">Cant. Piezas</option>
                                <option value="estatus">Estatus</option>
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
                        <th>Proveedor</th>
                        <th>Chofer</th>
                        <th>Empleado</th>
                        <th>Num. piezas</th>
                        <th>Vol. primario</th>
                        <th>Vol. secundario</th>
                        <th>Vol. Terciario</th>
                        <th>Vol. total</th>
                        <th>Costo total</th>
                        
                    </tr>
                    <%                        
                        int i=0;
                        for (EntradaMaderaRollo entrada : entradas) {
                            out.print("<tr>"
                                +"<td>"+(i+1)+"</td>"
                                +"<td>"+entrada.getFecha()+"</td>"
                                +"<td>"+entrada.getProveedor()+"</td>"
                                +"<td>"+entrada.getChofer()+"</td>"
                                +"<td>"+entrada.getEmpleado()+"</td>"
                                +"<td>"+entrada.getNum_piezas()+"</td>"
                                +"<td>"+entrada.getVolumen_primario()+"</td>"
                                +"<td>"+entrada.getVolumen_secundario()+"</td>"
                                +"<td>"+entrada.getVolumen_terciario()+"</td>"
                                +"<td>"+entrada.getVolumen_total()+"</td>"
                                +"<td>"+entrada.getMonto_total()+"</td>"
                                +"<td><a href=\"/aserradero/EntradaMaderaRolloController?action=modificar&id_entrada="+entrada.getId_entrada()+"\">Modificar</a></td>"
//                                + "<td><a href=\"javascript:if (confirm('¿Estás seguro de eliminar?')){parent.location='/aserradero/EntradaMaderaRolloController?action=eliminar&id_entrada="+entrada.getId_entrada()+"';};\">Eliminar</a></td>"
                            + "</tr>" );
                            i++;
                        }
                    %>
            </table>
            <div>
                <input type="button" value="Registrar entrada" onClick=" window.location.href='/aserradero/EntradaMaderaRolloController?action=nuevo_entrada'">
            </div>
        </div><!-- Resultado Consulta-->
    </body>
</html>
