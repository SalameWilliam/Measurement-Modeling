import java.awt.desktop.SystemSleepEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class client_test {
    public static void main(String []args){
        try {
            //We setup the data arrays
            int n = 5;
            Thread[] clients = new Thread[n];
            ArrayList<Long>[] timeLists = new ArrayList[n];

            //We launch n client threads
            for (int i = 0; i < n; i++) {
                timeLists[i] = new ArrayList<Long>();
                Client client = new Client("192.168.1.2", 4444, timeLists[i]);
                clients[i] = new Thread(client);
                clients[i].start();
            }

            //We collect ten seconds of data
            Thread.sleep(20000);

            System.out.println("================== Results ==================");

            double average = 0;
            double max = 0;
            double min = 0;

            for(int j=0;j<n;j++) {
                //We interrupt the thread
                clients[j].interrupt();

                //We collect the data from each ArrayList and print them (We can write them to a file too)
                long[] timeArray = timeLists[j].stream().mapToLong(i -> i).toArray();
                average = average + Arrays.stream(timeArray).average().getAsDouble()/1e6;
                max = max + Arrays.stream(timeArray).max().getAsLong()/1e6;
                min = min + Arrays.stream(timeArray).min().getAsLong()/1e6;
            }
            Thread.sleep(1000);
            System.out.println("=============================================================================");
            System.out.print(n+" "+average/n+" "+min/n+" "+max/n);
            System.exit(0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
