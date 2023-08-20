package Server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.Map;


public class ServerConn {
    String Hex = "0000"; //noop(manter a conexao)
    private static OutputStream outputStream;
    private static InputStream inputStream;
    private static Map config;
    
    public static void main(String[] args) throws IOException {
        if(args.length > 0 && args[0].equals("/?")){
            System.out.println("\nUso: server-xx.exe [caminho do arquivo de configuração (ServerConfig.prop)] ou vazio para ./ServerConfig.prop");
            return;
        }else if(args.length > 0)
            config = ServerConfigRead(args[0]);
        else{
            config = ServerConfigRead("./ServerConfig.prop");
        }
        
        
        int port = Integer.parseInt(config.get("ServerPort").toString());

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
                            
                    Responce responce = new Responce(inputStream, outputStream, "c618", config);
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
    public static Map ServerConfigRead(String filePath) {
        String ipAddress = null;
        Map<String, String> keyValueMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                
                if (parts[0] != null) {
                    
                    if(parts.length == 1){
                        keyValueMap.put(parts[0].trim(), "");
                    }else{
                        keyValueMap.put(parts[0].trim(), parts[1].trim());
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return keyValueMap;
    }
    
}