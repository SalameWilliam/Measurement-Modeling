import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/*  Handshake thread is a runnable that will (with a clientSocket as an argument)
 *  process the client request, sends back an answer then will close the TCP connection
 *  (Because the client must only send one request by TCP connection)
 */

public class HandshakeThread implements Runnable{

    //The client socket
    private Socket clientSocket;

    public HandshakeThread(Socket client) {
        this.clientSocket = client;
    }

    public void run() {
        try {

            //Initialize the buffers
            PrintWriter out =
                    new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            //Variables
            String fromClient, toSend;
            double[][] tmp_matrix;

            //Read what client sent
            fromClient = in.readLine();

            System.out.println("Message received from client");
            System.out.println(fromClient);

            //Compute the matrix
            tmp_matrix = utils.process(fromClient);

            //Resend the stringified matrix
            toSend = utils.stringify(tmp_matrix, 1);
            out.println(toSend);

            System.out.println("Message sent to client");
            System.out.println(toSend);

            //Finally closes the connection
            clientSocket.close();

            System.out.println("Connection closed");
        }
        catch (Exception e){
            System.err.println(e);
        }
    }
}
