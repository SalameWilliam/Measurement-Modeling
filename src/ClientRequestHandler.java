import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

/*  This thread takes a socket and sends a matrix computation request
 *  This thread exists because we need to keep the inter-arrival rate
 *  constant and if we wait to receive an answer before sending a new request
 *  the inter-arrival rate will depend on the response time and makes the
 *  model computation difficult. We could have done it without a thread but
 *  this facilitates the response time data management as the thread waits for the
 *  response and puts it in a data array instead of ignoring it.
 */

public class ClientRequestHandler implements Runnable{

    private Socket kkSocket;
    private ArrayList timeList;

    public ClientRequestHandler(Socket socket, ArrayList timeList){
        kkSocket = socket;
        this.timeList = timeList;
    }

    @Override
    public void run() {
        try {

            //We setup the variables
            int deviance = 2;
            int mean = 5;
            String fromServer,toSend;
            Random random = new Random();

            //And the buffers
            PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));

            //We don't randomize the matrix so the test_local results won't be affected by the matrix 'complexity'
            double[][] tmp = utils.randomMatrix(40);

            long startTime = System.nanoTime();

            //We randomize the value of P following a normal distribution with mean and deviance
            int p = (int) random.nextGaussian() * deviance + mean;

            //We stringify the matrix with the coeff
            toSend = utils.stringify(tmp, p);

            //Send it to the socket
            out.println(toSend);

            System.out.println("Client : Message sent to server");

            //We delay until a line is sent back
            fromServer = in.readLine();

            long endTime = System.nanoTime();
            //We compute and add the response time of the server
            timeList.add(endTime - startTime);

            System.out.println("Client : Message received from server");

            kkSocket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
