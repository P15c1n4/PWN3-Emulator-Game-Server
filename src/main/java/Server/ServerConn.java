package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HexFormat;


public class ServerConn {
    String Hex = "0000"; //noop(manter a conexao)
    static OutputStream outputStream;
    static InputStream inputStream;
    
    public static void main(String[] args) throws IOException {

        int port = 3001;

        ServerSocket serverPoxySocket = new ServerSocket(port);
        System.out.println("Servidor ouvindo na porta:"+port+"..."); 
        System.out.println("$Server Ready$");
            while (true) {
                try {

                    Socket socket = serverPoxySocket.accept();
                    System.out.println("Conexão recebida de: " + socket.getInetAddress().getHostAddress()+":"+socket.getLocalPort());
                    
                    inputStream = socket.getInputStream();
                    outputStream = socket.getOutputStream();

                    SendData("c6180000"+"82cb17c7"+"00489bc6"+"2ac31a45"+"000000000000");// ID-player / Cood-X / Cood-Y / Cood-Z
                            
                    Responce responce = new Responce(inputStream, outputStream, "c618");
                    responce.start();
                    
                }catch(Exception e){
                    System.out.println(e);
                }    
            }
    }
    private static void SendData(String Hex){
        byte[] bytes = HexFormat.of().parseHex(Hex);
        int bytesTotal = bytes.length;
        try {
            outputStream.write(bytes, 0, bytesTotal);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}