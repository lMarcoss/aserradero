<%--
    Document   : clientes
    Created on : 27-sep-2016, 1:03:55
    Author     : lmarcoss
--%>

<%@page import="entidades.Cliente"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <Cliente> clientes = (List<Cliente>) request.getAttribute("clientes");
    String mensaje = (String)request.getAttribute("mensaje");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <title>Clientes</title>
        <script>
            $(document).ready(function ($){
                 $("#registros").css("background","#448D00");
                 $("#clientes").css("background","#448D00");
            });
        </script>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        <input type="hidden" name="mensaje" id="mensaje" value="<%=mensaje%>" />
        <div class="container" style="margin-top:60px;">
            <div class="row">
                <div class="col-md-12">
                    <h1 class="page-header">LISTADO DE CLIENTES</h1>
                </div>
            </div>
            <div class="row">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Si el cliente que busca no aparece, agréguelo</h3>
                    </div>
                    <div class="panel-body">
                        <div class="form-group form-busc" ><!-- Formulario para realizar búsquedas en la base de datos -->
                            <form method="POST" action="/aserradero/ClienteController?action=buscar">
                                <select name="nombre_campo" class="input-busc">
                                    <option value="id_cliente">Id cliente</option>
                                    <option value="id_jefe">Id jefe</option>
                                </select>
                                <input type="text" name="dato" placeholder="Escriba su búsqueda" class="input-busc">
                                <input type="submit" value="Buscar" class="btn btn-success">
                            </form>                            
                        </div><!-- Fin formulario de búsqueda -->
                        <table id="tabla" class="display cell-border" cellspacing="0" width="100%"><!-- Tabla que muestra los resultados de la consulta a la base de datos-->
                            <thead>
                                <tr>
                                  <th>N°</th>
                                  <th>Id Cliente</th>
                                  <th>Id jefe</th>
                                  <th></th>
                                </tr>
                            </thead>
                            <tbody>
                              <%
                                int i=0;
                                for (Cliente cliente : clientes) {
                                    out.print("<tr>"
                                        +"<td>"+(i+1)+"</td>"
                                        +"<td><a href=\"/aserradero/PersonaController?action=buscar_persona&id_persona="+cliente.getId_cliente()+"\">"+cliente.getId_cliente()+"</a></td>"
                                        +"<td><a href=\"/aserradero/PersonaController?action=buscar_persona&id_persona="+cliente.getId_jefe()+"\">"+cliente.getId_jefe()+"</a></td>"
                                        + "<td><a class=\"btn btn-danger\" href=\"javascript:if (confirm('¿Estás seguro de eliminar?')){parent.location='/aserradero/ClienteController?action=eliminar&id_cliente="+cliente.getId_cliente()+"&id_jefe="+cliente.getId_jefe()+"';};\">Eliminar</a></td>"
                                    + "</tr>" );
                                    i++;
                                }
                                %>
                            </tbody>
                        </table><!-- Fin de tabla -->
                        <div class="agregar_element"><!-- Botón agregar elementos -->
                            <input type="button" class="btn btn-primary" value="Agregar cliente" onClick=" window.location.href='/aserradero/ClienteController?action=nuevo' ">
                        </div><!-- Fin Agregar elementos-->
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>