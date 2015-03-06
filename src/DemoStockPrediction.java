import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Hon on 3/5/2015.
 */
public class DemoStockPrediction {

    public void runPrediction() {
        System.out.printf("\n-------------------- DEMO MODE --------------------\n");

        String[] symbolList = {"1", "2", "3", "4", "5"};

        Scanner sc = new Scanner(System.in);

        String cont = "yes"; // this variable is used to judge whether the user want to continue
        while(cont.equals("yes")){

            String symbol = "";
            while(!(Arrays.asList(symbolList).contains(symbol))){
                System.out.println("Please enter the number of the data set you want to use: (1 - 5)");
                symbol = sc.nextLine();
            }

            System.out.printf("\n---------- The input parameters is: ----------\n\n");
            System.out.printf("You choose Data %s\n", symbol);

            double[] priceVariance;
            StockPredict Stock = new StockPredict(symbol, 0, 10);
            priceVariance = Stock.getPriceVariance();

            System.out.printf("\n-------------- The prediction is: --------------\n\n");
            System.out.printf("The predicted price is: %f\n", priceVariance[0]);
            System.out.printf("The predicted variance is: %f\n", priceVariance[1]);
            System.out.printf("The price would be likely in this range: %f ~ %f\n", priceVariance[0] - 3 * priceVariance[1], priceVariance[0] + 3 * priceVariance[1]);
            System.out.printf("\n---------------------- END ----------------------\n");

            System.out.println("Do you want to continue? (yes/anything)");
            cont = sc.nextLine();
            System.out.printf("\n");
        }

    }
}