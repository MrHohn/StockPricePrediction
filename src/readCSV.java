import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by Hon on 2/28/2015.
 */
public class ReadCSV {
    private int N;
    private double[][] price;
    private String[] date;
    private String[][] info;
    private String symbol;
    private int offset;

    public ReadCSV(int N, String symbol, int offset){
        this.N = N;
        price = new double[N][1];
        date = new String[N];
        this.symbol = symbol;

        info = new String[1241][2];
        try {
            BufferedReader reader = new BufferedReader(new FileReader("stockhistorical.csv"));
            String line = null;
            int count = 0;
            while((line = reader.readLine()) != null){
                String item[] = line.split(",");
                info[count][0] = item[3];
                info[count][1] = item[1];
//                System.out.printf("%s %s\n", info[count][0], info[count][1]);
                ++count;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int position = 0;
        if(symbol.equals("GOOG")){
            position = 0;
        }
        else if(symbol.equals("YHOO")){
            position = 233;
        }
        else if(symbol.equals("AAPL")){
            position = 485;
        }
        else if(symbol.equals("FB")){
            position = 737;
        }
        else if(symbol.equals("MSFT")){
            position = 989;
        }

        for(int i = N - 1; i >= 0; i--){
            price[i][0] = Double.parseDouble(info[position + N - i + offset][0]);
            date[i] = info[position + N - i + offset][1];
        }
    }

    public double[][] readPrice() {

        return price;
    }

    public String[] readDate() {

        return date;
    }
}
