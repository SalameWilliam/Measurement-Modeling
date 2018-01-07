import java.util.ArrayList;

public class service_test {

    public static void main(String []args) {
        try {
            ArrayList<Long> times = new ArrayList<>();
            WaitingClient client = new WaitingClient("192.168.1.2", 4444, times);
            Thread clientThread = new Thread(client);
            clientThread.start();
            Thread.sleep(20000);
            clientThread.interrupt();
            long[] timeArray = times.stream().mapToLong(i -> i).toArray();
            System.out.println("First moment E[S]: "+utils.firstMoment(timeArray));
            System.out.println("Second moment E[S²]: "+utils.secondMoment(timeArray));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
