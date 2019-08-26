import java.io.*;
import java.util.ArrayList;

public class Archive {



    File novoArquivo = null;

  public void upload(String pasta, String nomeDoArquivo) {
      String caminhoArquivo = pasta + "/" + nomeDoArquivo;
      novoArquivo = new File(caminhoArquivo);

  }

    public byte[] bytesArq(){
      return getBytes(novoArquivo);

    }

    public int getTamByte(){
      int len = (int)novoArquivo.length();
      return len;
    }


    public byte[] getBytes(File file) {
        int len = (int)file.length();
        byte[]sendBuf = new byte[len];
        FileInputStream inFile  = null;
        try {
            inFile = new FileInputStream(file);
            inFile.read(sendBuf, 0, len);

        } catch (FileNotFoundException fnfex) {

        } catch (IOException ioex) {

        }
        return sendBuf;
    }


}
