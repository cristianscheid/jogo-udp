package JogoUDP;

import java.net.*;

public class ServidorUDP {

    public static void main(String[] args) throws Exception {

        DatagramSocket tomadaServidora = new DatagramSocket(5000);
        System.out.println("Servidor em execução...");

        int positionX = 2;
        int positionY = 2;

        while (true) {
            ///////////RECEBER MENSAGEM DO CLIENTE E IMPRIMIR NA TELA
            byte[] cartaAReceber = new byte[100];
            DatagramPacket envelopeAReceber
                    = new DatagramPacket(cartaAReceber,
                            cartaAReceber.length);

            tomadaServidora.receive(envelopeAReceber);
            String textoRecebido = new String(envelopeAReceber.getData());
            System.out.println("Texto recebido:" + textoRecebido);

            if (textoRecebido.contains("W")) {
                if (positionY > 0) {
                    positionY--;
                }
            } else if (textoRecebido.contains("A")) {
                if (positionX > 0) {
                    positionX--;
                }
            } else if (textoRecebido.contains("S")) {
                if (positionY < 4) {
                    positionY++;
                }
            } else if (textoRecebido.contains("D")) {
                if (positionX < 4) {
                    positionX++;
                }
            }

            String[][] matrix = new String[5][5];
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (i == positionY && j == positionX) {
                        matrix[i][j] = "*";
                    } else {
                        matrix[i][j] = "#";
                    }
                }
            }

            String mensagem = "";
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    mensagem += matrix[i][j] + " ";
                    if (j == 4) {
                        mensagem += "\n";
                    }
                }
            }

            byte[] cartaAEnviar = new byte[100];
            cartaAEnviar = mensagem.getBytes();
            //Obtive os dados do remetente (ip e porta) a partir 
            //do envelope recebido anteriormente (envelopeAReceber)
            InetAddress ipCliente = envelopeAReceber.getAddress();
            int portaCliente = envelopeAReceber.getPort();

            DatagramPacket envelopeAEnviar
                    = new DatagramPacket(cartaAEnviar,
                            cartaAEnviar.length,
                            ipCliente,
                            portaCliente);
            tomadaServidora.send(envelopeAEnviar);
        }

        ///////////ENVIAR MENSAGEM DE VOLTA AO CLIENTE
        //SE NÃO TIVER MAIS NADA PARA FAZER
        //finaliza a conexão
//        tomadaServidora.close();
    }
}
