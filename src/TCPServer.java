import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class TCPServer {

    private ServerSocket serverSocket;
    private Socket socket;
    private Map<String, ObjectOutputStream> streamMap = new HashMap<String, ObjectOutputStream>();

    public TCPServer() {
        try {
            serverSocket = new ServerSocket(5555);
            System.out.println("Servidor on!");
            while (true){
                socket = serverSocket.accept();

                new Thread(new ListenerScoket(socket)).start();
            }

        }catch (IOException e){
            e.printStackTrace();
        }

    }



    private class ListenerScoket implements Runnable {
        private ObjectOutputStream outputStream;
        private ObjectInputStream inputStream;

        public ListenerScoket(Socket socket) throws IOException {
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());

        }

        public void run() {
            FileMessage message = null;

            try {
                while ((message = (FileMessage) inputStream.readObject()) != null) {
                    streamMap.put(message.getCliente(), outputStream);

                    if(message.getFile() != null){
                        System.out.println("arquivo recebido");

                        for(Map.Entry<String, ObjectOutputStream> kv : streamMap.entrySet()) {
                            System.out.println(kv.toString());
                            System.out.println(message.getFile().toString());
                            try{

                                BufferedReader br = new BufferedReader(new FileReader(message.getFile().toString()));
                                while(br.ready()){
                                    String linha = br.readLine();
                                    System.out.println(linha);
                                }
                                br.close();
                            }catch(IOException ioe){
                                ioe.printStackTrace();
                            }
                            if (!message.getCliente().equals(kv.getKey())) {
                                System.out.println(message.getCliente());
                                kv.getValue().writeObject(message);

                            }
                        }
                    }

                }
            }catch (IOException e){
                e.printStackTrace();
                System.out.println(message.getCliente() + "desconectou");
            }catch (ClassCastException e){
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        }

    }

    public static void main(String[] args){
        new TCPServer();
    }

}