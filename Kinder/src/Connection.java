

import java.sql.CallableStatement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author LENOVO
 */
public class Connection {
     Connection db = null;
    Statement st = null;
    ResultSet rs = null;
    PreparedStatement pst=null;
    
String cedu, nom, ape, relacion, celular;
String sql;
Integer id;
boolean a;
//Conexion base datos    
public void conecciondb() {
  try {
      db=(Connection) DriverManager.getConnection("jdbc:postgresql://localhost:5432/KINDER","postgres","Mariajo1131"); 
          //ruta .../basedatos,usuario,contraseña
      } catch (SQLException e) {System.out.println("Ocurrio un error : "+e.getMessage());} }

    Statement createStatement() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    CallableStatement prepareCall(String call_proce_pago) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    PreparedStatement prepareStatement(String select_maxcod_pag_from_pago_where_total_p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}