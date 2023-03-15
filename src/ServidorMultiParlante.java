import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorMultiParlante implements Runnable{

    static final int PUERTO = 8080;

    public static void main(String[] args) throws IOException{
        ServerSocket ss;
        System.out.print("Inicializando servidor...");
        try{
            ss = new ServerSocket(PUERTO);
            System.out.println("\t[OK]");
            int idSession = 0;
            while (true){
                Socket socket;
                socket = ss.accept();
                System.out.println("Nueva conexion entrante"+ socket);
                ((ServidorMultiParlante) new ServidorMultiParlante(socket, idSession)).start();
                idSession++;
            }
        }catch (IOException ex){
            //Logger.getLogger(ServirUnParlante.class.getName()).log(Level.SEVERE, null ex);
        }
    }

    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private int idSessio;

    public ServidorMultiParlante(Socket socket, int id){
        this.socket = socket;
        this.idSessio = id;
        try{
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
        }catch (IOException ex){
            // Logger.getLogger(ServidorMultiParlante.class.getName()).log(Level.SEVERE, null ex):

        }

    }


    public void desconectar(){
        try{
            socket.close();
        }catch (IOException ex){
            //  Logger.getLogger(ServidorMultiParlante.class.getName()).log(Level.SEVERE, nullm ex);
        }
    }



    @Override
    public void run(){
        String accion = "";
        try{
            accion = dis.readUTF();
            if(accion.equals("Hola")){
                System.out.println("El cliente con idSesion"+this.idSessio+"saluda");
                dos.writeUTF("Adios");
            }
        }catch(IOException ex){
            Logger.getLogger(ServidorMultiParlante.class.getName()).log(Level.SEVERE, null, ex);
        }
        desconectar();
    }

    private void start() {
        String accion = "";
        try{
            accion = dis.readUTF();
            if(accion.equals("Hola")){
                System.out.println("El cliente con idSesion"+this.idSessio+"saluda");
                dos.writeUTF("Adios");
            }
        }catch(IOException ex){
            Logger.getLogger(ServidorMultiParlante.class.getName()).log(Level.SEVERE, null, ex);
        }
        desconectar();
    }

}