/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Maria Jose Santana Minaya
 */
public class transaccion01 {
Connection db = null;
Statement st = null;
ResultSet rs = null;
PreparedStatement pst=null;

//planteamiento de variables
Integer cod_pag,numm,id_ma;
Float tpago,pag;
String descrip,sql;
boolean a;
public void conecciondb() {
  try {
      db=DriverManager.getConnection("jdbc:postgresql://localhost:5432/KINDER2.0","postgres","Mariajo1131"); 
          //ruta .../basedatos,usuario,contraseña
      } catch (SQLException e) {
          System.out.println("Ocurrio un error : "+e.getMessage());
      }
}
//planteamos el metodo para realizar la limpieza de los archivos
public void limpiar(){
    transaccion1.cod_pago.setText("");
    transaccion1.precio.setText("");
    transaccion1.descripcion.setText("");
    transaccion1.numdemes.setText(""); 
    transaccion1.idmatricula.setText("");
    transaccion1.tpago.setText("");
}

//consulta de los datos 
public void consultar_pago () throws SQLException{
    conecciondb();
    st = db.createStatement();
    cod_pag=Integer.valueOf(transaccion1.cod_pago.getText());
    rs = st.executeQuery("Select * from pago where cod_pag='"+cod_pag+"'");
if (rs.next()) {
    a = true;
    transaccion1.idmatricula.setText(rs.getString(2));
     transaccion1.descripcion.setText(rs.getString(3));
     transaccion1.numdemes.setText(rs.getString(4)); 
    transaccion1.precio.setText(rs.getString(5));
    transaccion1.tpago.setText(rs.getString(6));   
}else{
    JOptionPane.showMessageDialog(null, "Los datos no existen en la transacción");
    a=false;}   
}


//transacción utilizando commit_rollback, ademas de ejecutar y llevar a cabo
//el procedimiento almacenado

public void transaccion() throws SQLException{
    try{
        if (a==false){
            conecciondb();
            cod_pag = Integer.valueOf(transaccion1.cod_pago.getText());
            id_ma = Integer.valueOf(transaccion1.idmatricula.getText());
            descrip = transaccion1.descripcion.getText();
            numm = Integer.valueOf(transaccion1.numdemes.getText());
            pag = Float.valueOf(transaccion1.precio.getText());
            tpago = Float.valueOf(transaccion1.tpago.getText()); 
            sql = "insert into pago (cod_pag,idmatricula,descripcion_pag, num_mes, precio,total_pago) values (?,?,?,?,?,?)";
            pst = db.prepareStatement(sql);
            pst.setInt(1,cod_pag);
            pst.setInt(2,id_ma);
            pst.setString(3,descrip);
            pst.setInt(4, numm);
            pst.setFloat(5, pag);
            pst.setFloat(6, tpago);
            pst.executeUpdate();
            //invocamos el procedimiento almacenado
            
          CallableStatement cst = db.prepareCall("call proce_pago()"); 
            cst.execute();
            PreparedStatement pstnt = db.prepareStatement("select max(cod_pag) from pago where total_pago is null");
            rs=pstnt.executeQuery();
            rs.close();
            JOptionPane.showMessageDialog(null,"SE REALIZÓ EL COMMIT, TRANSACCIÓN RESUELTA");}
        
    }catch (SQLException e){
        JOptionPane.showMessageDialog(null,"NO SE REALIZÓ LA TRANSACCIÓN,ROLLBACK ACTIVADO");    
    }
}

}
