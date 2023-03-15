import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HiloClienteParlante implements Runnable {

    static final int MAX_HILOS = 10;
    public static final String HOST = "localhost";

    public static void main(String[] args) throws IOException, InterruptedException {
        ArrayList<Thread> clients = new ArrayList<Thread>();
        for (int i = 0; i < 5; i++) {
            clients.add(new Thread(new HiloClienteParlante(i)));
        }
        for (Thread thread : clients) {
            thread.start();
        }
    }

    private Socket sk;
    private DataOutputStream dos;
    private DataInputStream dis;
    private int id;

    public HiloClienteParlante(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        try {
            sk = new Socket("127.0.0.1", 8080);
            dos = new DataOutputStream(sk.getOutputStream());
            dis = new DataInputStream(sk.getInputStream());
            System.out.println(id + "Envia saludo");
            dos.writeUTF("Hola");
            String respuesta = "";
            respuesta = dis.readUTF();
            System.out.println(id + "Servidor devuelve saludo:" + respuesta);
            dis.close();
            dos.close();
            sk.close();
        } catch (IOException ex) {
            Logger.getLogger(HiloClienteParlante.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}