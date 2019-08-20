import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;



// L� uma linha do teclado
// Envia o pacote (linha digitada) ao servidor


class UdpClient {
    public static void main(String args[]) throws Exception
    {

        Archive arquivo = new Archive();
        arquivo.upload("/Users/rangel/Documents", "agesEntrevista");

        byte[]bytes = new byte[arquivo.getTamByte()];
        bytes = arquivo.bytesArq();


        // cria o stream do teclado
       // BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        // declara socket cliente
        DatagramSocket clientSocket = new DatagramSocket();

        // obtem endere�o IP do servidor com o DNS
        InetAddress IPAddress = InetAddress.getByName("localhost");

        byte[] sendData = new byte[arquivo.getTamByte()];
        //byte[] receiveData = new byte[1024];

        // l� uma linha do teclado
        // String sentence = inFromUser.readLine();
        sendData = bytes;

        System.out.println(sendData.length);


        // cria pacote com o dado, o endere�o do server e porta do servidor
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);

        //envia o pacote
        clientSocket.send(sendPacket);

        // fecha o cliente
        clientSocket.close();
    }
}
