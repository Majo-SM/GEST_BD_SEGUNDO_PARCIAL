
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.swing.JOptionPane;


/*
 * Creación de CRUD tutor.
 */

/**
 *
 *Santana Minaya María José
 * Quinto A
 * Gestión de Base de Datos
 */
public class crudmadre {
     java.sql.Connection db = null;
    Statement st = null;
    ResultSet rs = null;
    PreparedStatement pst=null;
    
String cedu, nom, ape, celular, telefono, ocupa,dire;
String sql;
Integer id;
boolean a;
//Conexion base datos    
public void conecciondb() {
  try {
      db=DriverManager.getConnection("jdbc:postgresql://localhost:5432/KINDER","postgres","Mariajo1131"); 
          //ruta .../basedatos,usuario,contraseña
      } catch (SQLException e) {System.out.println("Ocurrio un error : "+e.getMessage());}
}

//Ingreso (unico) tabla 
public void guardar_madre () throws SQLException {
 if (a==false){
     conecciondb();
     id = Integer.valueOf(CRUD_MADRE.id.getText());
     cedu = CRUD_MADRE.cedula.getText();
     nom = CRUD_MADRE.nombre.getText();
     ape = CRUD_MADRE.apellido.getText();
     celular = CRUD_MADRE.celular.getText();
     telefono = CRUD_MADRE.telfo.getText();
     ocupa = CRUD_MADRE.ocupacion.getText();
     dire = CRUD_MADRE.dire.getText();
     
     sql = "insert into madre (id_madre, ced_madre, nom_ma, apellido_ma, direccion_ma, celular_ma, telf_hog_ma, ocupacion_ma) "
             + "values (?,?,?,?,?,?,?,?)";
   pst = db.prepareStatement(sql);
   pst.setInt(1,id);
   pst.setString(2,cedu);
   pst.setString(3,nom);
   pst.setString(4,ape);
   pst.setString(5,dire);
   pst.setString(6,celular);
   pst.setString(7,telefono);
   pst.setString(8,ocupa);
   pst.executeUpdate();
 JOptionPane.showMessageDialog(null,"Se Registro...");
}
}
//Consultar por dato
public void consultar_madre() throws SQLException {
    conecciondb();
    st = db.createStatement();
    id = Integer.valueOf(CRUD_MADRE.id.getText());
    rs = st.executeQuery("select * from madre where id_madre='"+id+"'");
    if (rs.next()) {
        a=true;
        CRUD_MADRE.cedula.setText(rs.getString(2));
        CRUD_MADRE.nombre.setText(rs.getString(3));
        CRUD_MADRE.apellido.setText(rs.getString(4));
        CRUD_MADRE.dire.setText(rs.getString(5));
        CRUD_MADRE.celular.setText(rs.getString(6)); 
        CRUD_MADRE.telfo.setText(rs.getString(7)); 
        CRUD_MADRE.ocupacion.setText(rs.getString(8)); 
    }else {JOptionPane.showMessageDialog(null,"No existen datos relacionados...");
    a=false;}
}
//Actualizar datos de la tabla
public void actualizar_madre() throws SQLException {
    if (a==true){
     conecciondb();
     id = Integer.valueOf(CRUD_MADRE.id.getText());     
     cedu = CRUD_MADRE.cedula.getText();
     nom = CRUD_MADRE.nombre.getText();
     ape = CRUD_MADRE.apellido.getText();
     celular = CRUD_MADRE.celular.getText();
     telefono = CRUD_MADRE.telfo.getText();
     ocupa = CRUD_MADRE.ocupacion.getText();
     dire = CRUD_MADRE.dire.getText();
     
     sql = "update madre set ced_madre=?, nom_ma=?, apellido_ma=?, direccion_ma=?, celular_ma=?, telf_hog_ma=?, ocupacion_ma=? "
             + "where id_madre='"+id+"'";
   pst = db.prepareStatement(sql);
   pst.setString(1,cedu);
   pst.setString(2,nom);
   pst.setString(3,ape);
   pst.setString(4,dire);
   pst.setString(5,celular);
   pst.setString(6,telefono);
   pst.setString(7,ocupa);
   pst.executeUpdate();
 JOptionPane.showMessageDialog(null,"Se Actualizo correctamente...");
}
}

//Eliminar registro
public void elimina_madre() throws SQLException {
 try {
 if (a==true) { 
     conecciondb();
     int resp = JOptionPane.showConfirmDialog(null, "Lo elimina","ALERTA",JOptionPane.YES_NO_OPTION);
     if (resp!=1){
         st.execute("delete from madre where id_madre='"+id+"'");
   JOptionPane.showMessageDialog(null,"SE ELIMINO, ya que no tiene relación tabla"); } } 
    }catch (SQLException e) 
    {JOptionPane.showMessageDialog(null,"No se puede eliminar, tiene relación tabla");} }

//limpiar registros
public void limpia() {
        CRUD_MADRE.id.setText("");
        CRUD_MADRE.cedula.setText("");
        CRUD_MADRE.dire.setText("");
        CRUD_MADRE.nombre.setText("");
        CRUD_MADRE.apellido.setText("");
        CRUD_MADRE.celular.setText("");         
        CRUD_MADRE.telfo.setText(""); 
        CRUD_MADRE.ocupacion.setText(""); 
}

}
