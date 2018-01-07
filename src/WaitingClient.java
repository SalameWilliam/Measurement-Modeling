import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class WaitingClient implements Runnable{


        private String hostName;
        private int portNumber;

        /* This list will cache the response time of each roundtrip
         * The value is from the message being sent, to the message being received
         * That means both CPU and Network will be in play.
         */
        private ArrayList<Long> timeList;

        public WaitingClient(String hostName, int portNumber, ArrayList<Long> timeList){
            this.hostName = hostName;
            this.portNumber = portNumber;
            this.timeList = timeList;
        }

        @Override
        public void run() {

            //We setup the variables
            int deviance = 2;
            int mean = 17;
            String fromServer,toSend;
            Random random = new Random();

            try{
                while(true) {

                    //We open the socket
                    Socket kkSocket = new Socket(hostName, portNumber);

                    //And the buffers
                    PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));

                    //We don't randomize the matrix so the test_local results won't be affected by the matrix 'complexity'
                    double[][] tmp = utils.randomMatrix(10);

                    //We randomize the value of P following a normal distribution with mean and deviance
                    int p = (int) random.nextGaussian() * deviance + mean;

                    //We stringify the matrix with the coeff
                    toSend = utils.stringify(tmp, p);

                    long startTime = System.currentTimeMillis();

                    //Send it to the socket
                    out.println(toSend);

                    //We delay until a line is sent back
                    fromServer = in.readLine();

                    long endTime = System.currentTimeMillis();
                    //We compute and add the response time of the server
                    timeList.add(endTime - startTime);

                    System.out.println("Client : Message received from server");
                    System.out.println("Client : " + fromServer);

                    kkSocket.close();

                    //We then sleep 1 second~~
                    double sleepTime = random.nextGaussian()*20+100;
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

