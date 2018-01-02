import java.util.ArrayList;
import java.util.Arrays;

public class client_test {
    public static void main(String []args){
        try {
            //We setup the data arrays
            int n = 100;
            Thread[] clients = new Thread[n];
            ArrayList<Long>[] timeLists = new ArrayList[n];

            //We launch n client threads
            for (int i = 0; i < n; i++) {
                timeLists[i] = new ArrayList<Long>();
                Client client = new Client("192.168.1.139", 4444, timeLists[i]);
                clients[i] = new Thread(client);
                clients[i].start();
            }

            //We collect ten seconds of data
            Thread.sleep(10000);

            System.out.println("================== Results ==================");

            for(int j=0;j<n;j++) {
                //We interrupt the thread
                clients[j].interrupt();

                //We collect the data from each ArrayList and print them (We can write them to a file too)
                long[] timeArray = timeLists[j].stream().mapToLong(i -> i).toArray();
                double average = Arrays.stream(timeArray).average().getAsDouble();
                long max = Arrays.stream(timeArray).max().getAsLong();
                long min = Arrays.stream(timeArray).min().getAsLong();
                System.out.println(average + " " + min + " " + max);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
