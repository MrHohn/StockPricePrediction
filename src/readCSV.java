import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by Hon on 2/28/2015.
 */
public class readCSV {
    private int N;
    private double[] price;
    private String[] info;
    private String symbol;

    public readCSV(int N, String symbol){
        this.N = N;
        price = new double[N];
        this.symbol = symbol;
    }

    public double[] read() {
        info = new String[1241];
        try {
            BufferedReader reader = new BufferedReader(new FileReader("stockhistorical.csv"));
            String line = null;
            int count = 0;
            while((line = reader.readLine()) != null){
                String item[] = line.split(",");
                info[count] = item[3];
//                System.out.println(info[count]);
                ++count;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(symbol == "GOOG"){
            for(int i = N - 1; i >= 0; i--){
                price[i] = Double.parseDouble(info[N - i]);
            }
        }

        info = null;
        return price;
    }
}
