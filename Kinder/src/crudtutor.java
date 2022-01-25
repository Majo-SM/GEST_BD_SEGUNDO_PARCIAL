
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
public class crudtutor {
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
      db=DriverManager.getConnection("jdbc:postgresql://localhost:5432/KINDER","postgres","Mariajo1131"); 
          //ruta .../basedatos,usuario,contraseña
      } catch (SQLException e) {System.out.println("Ocurrio un error : "+e.getMessage());} }

//Ingreso (unico) tabla tutor
public void guardar_tutor () throws SQLException {
 if (a==false){
     conecciondb();
     id = Integer.valueOf(CRUD_TUTOR.id.getText());
     cedu = CRUD_TUTOR.cedula.getText();
     relacion = CRUD_TUTOR.relacion.getText();
     nom = CRUD_TUTOR.nombre.getText();
     ape = CRUD_TUTOR.apellido.getText();
     celular = CRUD_TUTOR.celular.getText();
     
     sql = "insert into tutor_representante (id_tutor, ced_tutor, relacion_con_el_nino, nombre_tutor, apellido_tutor, celular_tutor) "
             + "values (?,?,?,?,?,?)";
   pst = db.prepareStatement(sql);
   pst.setInt(1,id);
   pst.setString(2,cedu);
   pst.setString(3,relacion);
   pst.setString(4,nom);
   pst.setString(5,ape);
   pst.setString(6,celular);
   pst.executeUpdate();
 JOptionPane.showMessageDialog(null,"Se Registro...");
}
}
//Consultar por dato
public void consultar_tutor() throws SQLException {
    conecciondb();
    st = db.createStatement();
    id = Integer.valueOf(CRUD_TUTOR.id.getText());
    rs = st.executeQuery("select * from tutor_representante where id_tutor='"+id+"'");
    if (rs.next()) {
        a=true;
        CRUD_TUTOR.cedula.setText(rs.getString(2));
        CRUD_TUTOR.relacion.setText(rs.getString(3));
        CRUD_TUTOR.nombre.setText(rs.getString(4));
        CRUD_TUTOR.apellido.setText(rs.getString(5));
        CRUD_TUTOR.celular.setText(rs.getString(6));        
    }else {JOptionPane.showMessageDialog(null,"No existen datos relacionados...");
    a=false;}
}
//Actualizar datos de la tabla
public void actualizar_tutor() throws SQLException {
    if (a==true){
     conecciondb();
     id = Integer.valueOf(CRUD_TUTOR.id.getText());
     cedu = CRUD_TUTOR.cedula.getText();
     relacion = CRUD_TUTOR.relacion.getText();
     nom = CRUD_TUTOR.nombre.getText();
     ape = CRUD_TUTOR.apellido.getText();
     celular = CRUD_TUTOR.celular.getText();
     
     sql = "update tutor_representante set ced_tutor=?, relacion_con_el_nino=?, nombre_tutor=?, apellido_tutor=?, celular_tutor=? "
             + "where id_tutor='"+id+"'";
   pst = db.prepareStatement(sql);
   pst.setString(1,cedu);
   pst.setString(2,relacion);
   pst.setString(3,nom);
   pst.setString(4,ape);
   pst.setString(5,celular);
   pst.executeUpdate();
 JOptionPane.showMessageDialog(null,"Se Actualizo correctamente...");
}
}

//Eliminar registro
public void elimina_tutor() throws SQLException {
 try {
 if (a==true) { 
     conecciondb();
     int resp = JOptionPane.showConfirmDialog(null, "Lo elimina","ALERTA",JOptionPane.YES_NO_OPTION);
     if (resp!=1){
         st.execute("delete from tutor_representante where id_tutor='"+id+"'");
   JOptionPane.showMessageDialog(null,"SE ELIMINO, ya que no tiene relación tabla"); } } 
    }catch (SQLException e) 
    {JOptionPane.showMessageDialog(null,"No se puede eliminar, tiene relación tabla");} }

//limpiar registros
public void limpia() {
        CRUD_TUTOR.id.setText("");
        CRUD_TUTOR.cedula.setText("");
        CRUD_TUTOR.relacion.setText("");
        CRUD_TUTOR.nombre.setText("");
        CRUD_TUTOR.apellido.setText("");
        CRUD_TUTOR.celular.setText(""); 
}


}
