
package topicosbasedatos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author MizaelGalvez
 */


public class TopicosBaseDatos {
    
    static final String CONTROLADOR = "com.mysql.cj.jdbc.Driver";
    
    static final String  URL = "jdbc:mysql://148.225.60.126/TEBE_ESCOLAR?";

    
    public static void main(String[] args) throws ClassNotFoundException {
        // TODO code application logic here
        
        
        Connection conexion = null;
        Statement instruccion = null;
        ResultSet conjuntoResultados = null;
        Scanner entradaDATO =new Scanner(System.in);
        
        
        String user = "test_user";
        String pass = "P@ssw0rd";;
        
        try {
            
            Class.forName(CONTROLADOR);
            
            conexion = DriverManager.getConnection(URL, user, pass);
            
            
            
            int consulta = 0;
            
            
            switch(consulta){
            
                    /*--------------------------------------------------------------------------------------------------------------------*/
                
                case 1:
                    
                    break;
                    
                    /*--------------------------------------------------------------------------------------------------------------------*/
                    
            }
            
            
                boolean Verificado = false;
                String elecion = "0";
            
            
                System.out.println("usuario :  ");
                String usuario =entradaDATO.nextLine();
                System.out.print("contrasenia :  ");
                String contrasena =entradaDATO.nextLine();
                try {
                    contrasena = hexString(getSHA(contrasena));
                } catch (Exception e) {
                }
                

                String contraseniaCONFIRMACION ="";
                String rolUsuario ="";

                String VERIFICACION = "SELECT * FROM usuarios WHERE username = ?";


                CallableStatement stmt = conexion.prepareCall(VERIFICACION);

                stmt.setString(1, usuario);

                ResultSet rs = stmt.executeQuery();

                while(rs.next()){

                    //System.out.println(rs.getString(1));
                    System.out.println(">>>> contrase;a del Servidor >>>>>   "+rs.getString("password"));
                    contraseniaCONFIRMACION = rs.getString("password");
                    rolUsuario = rs.getString("role");
                    //System.out.println(rs.getString("NOMBRE")+ " :  " + rs.getString("PROMEDIO"));

                }
                
                
                if (contraseniaCONFIRMACION.equals(contrasena)) {


                    System.out.println("VALIDADO, CONTRASE:A CORRECTA");
                    Verificado = true;

                }else{

                        System.out.println("ERROR DE VALIDACION");
                        

                } 
                
                
                
                
                
                
                while (Verificado) {      
                    
                    System.out.println(
                            "Elige la opcion deseada"  + "\n" +
                            "1 agregar alumnop"  + "\n" +
                            "2 mostrar promedio"  + "\n" +
                            "3 agregar maestros"  + "\n" +
                            "4 mostrar maestros"  + "\n" +
                            "5 agregar clases"  + "\n" +
                            "6 mostrar Clases"  + "\n" +
                            "7 agregar calificaciones"  + "\n"+
                            "8 mostrar bitacora"  + "\n"+
                            "9 agregar usuarios"  + "\n"+
                            "0 cerrar conexion"  + "\n");
                    
                    System.out.println("ingresar opcion :");
                    elecion =entradaDATO.nextLine();
                
                    switch (elecion) {
                        case "1":
                            
                            conexion.setAutoCommit(true);
                            System.out.print("ingresar Expediente :  ");
                            String uno =entradaDATO.nextLine();
                            System.out.print("ingresar nombre :  ");
                            String dos =entradaDATO.nextLine();
                            System.out.print("ingresar fecha de nacimiento YYYY-MM-DD :  ");
                            String tres =entradaDATO.nextLine();
                            System.out.print("ingresar genero :  ");
                            String cuatro =entradaDATO.nextLine();
                            
                            String query = "{CALL AgregarAlumno(?,?,?,?) }";
                    
                            CallableStatement stmts = conexion.prepareCall(query);

                            stmts.setString(1, uno);
                            stmts.setString(2, dos);
                            stmts.setString(3, tres);
                            stmts.setString(4, cuatro);
                            stmts.execute();
                            
                            
                            System.out.println("\n"+"\n"+ "\n"+"la transaccion fue :::>>>   " + conexion.getAutoCommit() + "\n");
                            
                            confirmar();
                            

                        break;
                            
                        case "2":
                            
                            
                            
                            String SQL = "SELECT * FROM promedios";
            
                            instruccion = conexion.createStatement();

                            conjuntoResultados  = instruccion.executeQuery(SQL);

                            System.out.println("Tabla de Promedios:\n");

                            ResultSetMetaData metaDatos = conjuntoResultados.getMetaData();

                            int numeroDeColumnas = metaDatos.getColumnCount();

                            
                            for (int i = 1; i <= numeroDeColumnas; i++) {

                                System.out.printf( "%-8s\t", metaDatos.getColumnName(i));
                            
                            

                            System.out.println();

                            while (conjuntoResultados.next()) {

                                for (int j = 1; j <= numeroDeColumnas; j++) {

                                    System.out.printf( "%-8s\t", conjuntoResultados.getObject(j));
                                }
                                        
                                System.out.println("");


                            }
                            }
                            
                            confirmar();
                            
                            break;
                        case "3":
                            
                            System.out.print("ingresar Nombre :  ");
                            uno =entradaDATO.nextLine();
                            
                            query = "{CALL AgregarMaestro(?) }";
                    
                            stmts = conexion.prepareCall(query);

                            stmts.setString(1, uno);
                            stmts.execute();
                            
                            System.out.println("\n"+"\n"+ "\n"+"la transaccion fue :::>>>   " + conexion.getAutoCommit() + "\n");
                            
                            confirmar();
                            
                            break;
                        case "4":
                            
                            SQL = "SELECT * FROM maestros";
            
                            instruccion = conexion.createStatement();

                            conjuntoResultados  = instruccion.executeQuery(SQL);

                            System.out.println("Tabla de Maestros:\n");

                            metaDatos = conjuntoResultados.getMetaData();

                            numeroDeColumnas = metaDatos.getColumnCount();

                            
                            for (int i = 1; i <= numeroDeColumnas; i++) {

                                System.out.printf( "%-8s\t", metaDatos.getColumnName(i));
                            
                            

                            System.out.println();

                            while (conjuntoResultados.next()) {

                                for (int j = 1; j <= numeroDeColumnas; j++) {

                                    System.out.printf( "%-8s\t", conjuntoResultados.getObject(j));
                                }
                                        
                                System.out.println("");


                            }
                            }
                            
                            confirmar();
                            
                            break;
                        case "5":
                            
                            System.out.print("ingresar Clase DDDD :  ");
                            uno = entradaDATO.nextLine();
                            System.out.print("ingresar Creditos de la Materia :  ");
                            dos = entradaDATO.nextLine();
                            System.out.print("ingresar Nombre de la Materia :  ");
                            tres = entradaDATO.nextLine();
                            
                            query = "{CALL AgregarMateria(?,?,?) }";
                    
                            stmts = conexion.prepareCall(query);

                            stmts.setInt(1, Integer.parseInt(uno));
                            stmts.setInt(2, Integer.parseInt(dos));
                            stmts.setString(3, tres);
                            stmts.execute();
                            
                            
                            System.out.println("\n"+"\n"+ "\n"+"la transaccion fue :::>>>   " + conexion.getAutoCommit() + "\n");
                            
                            confirmar();
                            
                            break;
                        case "6":
                            
                            SQL = "SELECT * FROM clases";
            
                            instruccion = conexion.createStatement();

                            conjuntoResultados  = instruccion.executeQuery(SQL);

                            System.out.println("Tabla de CLASES:\n");

                            metaDatos = conjuntoResultados.getMetaData();

                            numeroDeColumnas = metaDatos.getColumnCount();

                            
                            for (int i = 1; i <= numeroDeColumnas; i++) {

                                System.out.printf( "%-8s\t", metaDatos.getColumnName(i));
                            
                            

                            System.out.println();

                            while (conjuntoResultados.next()) {

                                for (int j = 1; j <= numeroDeColumnas; j++) {

                                    System.out.printf( "%-8s\t", conjuntoResultados.getObject(j));
                                }
                                        
                                System.out.println("");


                            }
                            }
                            
                            confirmar();
                            
                            break;
                        case "7":
                            
                            System.out.print("ingresar Expediente :  ");
                            uno = entradaDATO.nextLine();
                            System.out.print("ingresar Clase ID (DDDD) :  ");
                            dos = entradaDATO.nextLine();
                            System.out.print("ingresar Calidicacion Final :  ");
                            tres = entradaDATO.nextLine();
                            
                            query = "{CALL AgregarCalificacion(?,?,?) }";
                    
                            stmts = conexion.prepareCall(query);

                            stmts.setInt(1, Integer.parseInt(uno));
                            stmts.setInt(2, Integer.parseInt(dos));
                            stmts.setDouble(3, Double.valueOf(tres));
                            stmts.execute();
                            
                            
                            System.out.println("\n"+"\n"+ "\n"+"la transaccion fue :::>>>   " + conexion.getAutoCommit() + "\n");
                            
                            confirmar();
                            
                            break;
                        case "8":
                            
                            SQL = "SELECT * FROM bitacora";
            
                            instruccion = conexion.createStatement();

                            conjuntoResultados  = instruccion.executeQuery(SQL);

                            System.out.println("Tabla de BITACORA:\n");

                            metaDatos = conjuntoResultados.getMetaData();

                            numeroDeColumnas = metaDatos.getColumnCount();

                            
                            for (int i = 1; i <= numeroDeColumnas; i++) {

                                System.out.printf( "%-8s\t", metaDatos.getColumnName(i));
                            
                            

                            System.out.println();

                            while (conjuntoResultados.next()) {

                                for (int j = 1; j <= numeroDeColumnas; j++) {

                                    System.out.printf( "%-8s\t", conjuntoResultados.getObject(j));
                                }
                                        
                                System.out.println("");


                            }
                            }
                            
                            confirmar();
                            
                            
                            break;
                        case "9":
                            
                            System.out.print("ingresar Email :  ");
                            uno = entradaDATO.nextLine();
                            System.out.print("ingresar Contrase;a :  ");
                            dos = entradaDATO.nextLine();
                            try {
                                dos = hexString(getSHA(dos));
                            } catch (Exception e) {
                            }
                            System.out.print("ingresar rol (administrador/usuario)  ");
                            tres = entradaDATO.nextLine();
                            System.out.print("ingresar UserName  ");
                            cuatro = entradaDATO.nextLine();
                            
                            query = "{CALL AgregarUsuario(?,?,?,?) }";
                    
                            stmts = conexion.prepareCall(query);

                            stmts.setString(1, uno);
                            stmts.setString(2, dos);
                            stmts.setString(3, tres);
                            stmts.setString(4, cuatro);
                            stmts.execute();
                            
                            
                            System.out.println("\n"+"\n"+ "\n"+"la transaccion fue :::>>>   " + conexion.getAutoCommit() + "\n");
                            
                            confirmar();
                            
                            break;
                        case "0":
                            Verificado = false;
                            break;
                        
                            
                            
                        default:
                            throw new AssertionError();
                    }
                    
                }
                
                
                try {

                    conexion.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                


            
            
        } catch (SQLException e) {
            
            e.printStackTrace();
            
        }
        
        
        
        
    }
    public static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        
            byte[] hash = null;
            
            
            try {
                
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                hash = md.digest(input.getBytes(StandardCharsets.UTF_8));

            } catch (Exception e) {
                
                System.out.println(e.getMessage());
                
            }

            return hash;
        
    }
    
    public static String hexString(byte[] hash){
    
        
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        
        while (hexString.length()<32){
            
            hexString.insert(0, '0');
        
        }
    
        return hexString.toString();
    }
    
    public static void confirmar(){
    
        String ENTER;
        Scanner teclado = new Scanner(System.in);
        System.out.println("\n"+ "\n"+"Presione ENTER para continuar ...."+"\n"+ "\n");
        try
        {
            ENTER = teclado.nextLine();
        }
        catch(Exception e)
        {}
                            
    
    }
    
}
