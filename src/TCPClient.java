import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class TCPClient{

    private Socket socket;
    private ObjectOutputStream outputStream;

    public TCPClient() throws IOException{
        this.socket = new Socket("localhost", 5555);
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());

        new Thread(new ListnerSocket(socket)).start();
        menu();
    }

    private void menu() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite seu nome ");
        String nome = scanner.nextLine();
        this.outputStream.writeObject(new FileMessage(nome));

        int option = 0;
        while (option !=1) {
            System.out.println("1- sair\n2-Enviar");
            option = scanner.nextInt();
            if(option == 2){
                send(nome);
            }else if (option == 1){
                System.exit(0);
            }
        }

    }

    private void send(String nome) throws IOException {
        FileMessage fileMessage = new FileMessage();
        JFileChooser fileChooser = new JFileChooser();
        int opt = fileChooser.showOpenDialog(null);
        if(opt == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            this.outputStream.writeObject(new FileMessage(nome, file));
        }
    }

    private class ListnerSocket implements  Runnable {
        private ObjectInputStream inputStream;

        public ListnerSocket(Socket socket) throws IOException {
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        }

        public void  run(){
            FileMessage message = null;

            try {
                while ((message = (FileMessage) inputStream.readObject()) != null) {
                    System.out.println("Voce recebeu um arquivo de" + message.getCliente());
                    System.out.println("O arquivi é "+ message.getFile().getName());

                    imprime(message);

                }
            }catch (IOException e){
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        private void imprime(FileMessage message) throws IOException {
            FileReader fileReader = new FileReader(message.getFile());
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String linha;
            while ((linha = bufferedReader.readLine()) !=null){
                System.out.println(linha);
            }
        }
    }

    public static void main(String[] args) {
        try {
            new TCPClient();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}