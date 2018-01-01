import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class Client implements Runnable{

    private String hostName;
    private int portNumber;

    /* This list will cache the response time of each roundtrip
     * The value is from the message being sent, to the message being received
     * That means both CPU and Network will be in play.
     */
    private ArrayList<Long> timeList;

    public Client(String hostName, int portNumber, ArrayList<Long> timeList){
        this.hostName = hostName;
        this.portNumber = portNumber;
        this.timeList = timeList;
    }

    @Override
    public void run() {

        //We setup the execution variables
        int deviance = 2;
        int mean = 50;
        String fromServer,toSend;
        Random random = new Random();
        long startTime;

        //int matrix_size = 5;

        try{
            //noinspection InfiniteLoopStatement
            while(true) {

                //We start the timer
                startTime = System.currentTimeMillis();

                //We open the socket and setup the buffers
                Socket kkSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));

                //We don't randomize the matrix so the test results won't be affected by the matrix 'complexity'
                double[][] tmp = {{1, 2, 3, 4, 5}, {5, 6, 7, 8, 9}, {9, 10, 11, 12, 13}, {13, 14, 15, 16, 17},{17,18,19,20,21}};

                //We randomize the value of P following a normal distribution with mean and deviance
                int p = (int) random.nextGaussian() * deviance + mean;

                //We stringify the matrix with the coeff
                toSend = utils.stringify(tmp, p);

                //Send it to the socket
                out.println(toSend);

                System.out.println("Client : Message sent to server");
                System.out.println("Client : "+toSend);

                //We delay until a line is sent back
                fromServer = in.readLine();

                long endTime = System.currentTimeMillis();
                //We compute and add the response time of the server
                timeList.add(endTime - startTime);

                System.out.println("Client : Message received from server");
                System.out.println("Client : "+fromServer);

                //TODO: Checker si la matrice est bien calculée ?
                kkSocket.close();

                System.out.println("Client : Socket closed entering sleep");
                double sleepTime = random.nextGaussian()*500+1200;
                Thread.sleep((long)sleepTime);
                System.out.println("Client : Sleep ended");
            }
        }
        catch(Exception e){
            if (e instanceof InterruptedException)
                Thread.currentThread().interrupt();
            else
                e.printStackTrace();
        }
    }
}
