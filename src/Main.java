public class Main {

    public static void main(String[] args) {

        UdpServer server = new UdpServer();
        UdpClient client = new UdpClient();
        Archive arquivo = new Archive();
        arquivo.upload("/Users/rangel/Documents", "agesEntrevista");


        byte[]bytes = new byte[arquivo.getTamByte()];
        bytes = arquivo.bytesArq();


        System.out.println(bytes.length);











    }
}
