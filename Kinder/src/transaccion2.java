
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
public class transaccion2 {
Connection db = null;
Statement st = null;
ResultSet rs = null;
PreparedStatement pst=null;

//planteamiento de variables
Integer cod_rendimiento,cod_prog,id_maestro,idmatricula;
Float nota_parcial_dos,nota_primer_par,nota_general;
String sql;
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
    transaccion02.cod_rendimiento.setText("");
    transaccion02.cod_prog.setText("");
    transaccion02.id_maestro.setText("");
    transaccion02.idmatricula.setText(""); 
    transaccion02.nota_parcial_dos.setText("");
    transaccion02.nota_primer_par.setText("");
    transaccion02.nota_general.setText("");
}

//consulta de los datos 
public void consultar_pago () throws SQLException{
    conecciondb();
    st = db.createStatement();
    cod_rendimiento =Integer.valueOf(transaccion02.cod_rendimiento.getText());
    rs = st.executeQuery("Select * from rendimiento where cod_rendimiento='"+cod_rendimiento+"'");
if (rs.next()) {
    a = true;
    transaccion02.cod_prog.setText(rs.getString(2));
     transaccion02.id_maestro.setText(rs.getString(3));
     transaccion02.idmatricula.setText(rs.getString(4)); 
    transaccion02.nota_parcial_dos.setText(rs.getString(5));
    transaccion02.nota_primer_par.setText(rs.getString(6));     
    transaccion02.nota_general.setText(rs.getString(7)); 
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
           cod_rendimiento = Integer.valueOf(transaccion02.cod_rendimiento.getText());
            cod_prog = Integer.valueOf(transaccion02.cod_prog.getText());
            id_maestro = Integer.valueOf(transaccion02.id_maestro.getText());
            idmatricula = Integer.valueOf(transaccion02.idmatricula.getText());
            nota_parcial_dos = Float.valueOf(transaccion02.nota_parcial_dos.getText());
            nota_primer_par = Float.valueOf(transaccion02.nota_primer_par.getText());
            nota_general = Float.valueOf(transaccion02.nota_general.getText());
            sql = "insert into rendimiento (cod_rendimiento, cod_prog, id_maestro, idmatricula, nota_parcial_dos, nota_primer_par, nota_general) values (?,?,?,?,?,?,?)";
            pst = db.prepareStatement(sql);
            pst.setInt(1,cod_rendimiento);
            pst.setInt(2,cod_prog);
            pst.setInt(3, id_maestro);
            pst.setInt(4, idmatricula);
            pst.setFloat(5, nota_parcial_dos);
            pst.setFloat(6, nota_primer_par);
            pst.setFloat(7, nota_general);
            pst.executeUpdate();
            //invocamos el procedimiento almacenado*/
            
          CallableStatement cst = db.prepareCall("call procrendimiento()"); 
            cst.execute();
            PreparedStatement pstnt = db.prepareStatement("select max(cod_rendimiento) from rendimiento where nota_general is null");
            rs=pstnt.executeQuery();
            rs.close();
            JOptionPane.showMessageDialog(null,"SE REALIZÓ EL COMMIT, TRANSACCIÓN RESUELTA");}
        
    }catch (SQLException e){
        JOptionPane.showMessageDialog(null,"NO SE REALIZÓ LA TRANSACCIÓN,ROLLBACK ACTIVADO");    
    }
}

}
