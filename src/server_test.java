public class server_test {
    public static void main(String []args){
        try {
            Server server = new Server(4444);
            Thread serverThread = new Thread(server);
            serverThread.start();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
