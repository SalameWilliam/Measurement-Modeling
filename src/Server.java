import java.net.ServerSocket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Server implements Runnable{

    private boolean listening = true;

    private int portNumber;
    public Server(int portNumber){
        this.portNumber = portNumber;
    }

    @Override
    public void run() {

        try{

            //Connection initialization
            ServerSocket serverSocket = new ServerSocket(portNumber);

            //Setting up the single threaded computation executor
            Executor execute = Executors.newSingleThreadExecutor();

            //While we are listening we add a new request to the executor to handle the client
            while(listening) {
                execute.execute(new HandshakeThread(serverSocket.accept()));
                System.out.println("New Client added to queue");
            }
        }
        catch (Exception e){
            if (e instanceof InterruptedException)
                    Thread.currentThread().interrupt();
            else
                e.printStackTrace();
        }
    }
}
