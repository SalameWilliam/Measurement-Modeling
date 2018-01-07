import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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

        try{
            Executor execute = Executors.newSingleThreadExecutor();
            Random random = new Random();
            //noinspection InfiniteLoopStatement
            while(true) {

                //We open the socket
                Socket kkSocket = new Socket(hostName, portNumber);

                //We add the computation and request sending thread to the queue
                execute.execute(new ClientRequestHandler(kkSocket,timeList));

                //We then sleep 1 second~~
                double sleepTime = random.nextGaussian()*200+1000;
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
