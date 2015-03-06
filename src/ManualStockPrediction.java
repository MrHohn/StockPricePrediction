import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Hon on 3/3/2015.
 */
public class ManualStockPrediction {

    public void runPrediction() {
        System.out.printf("\n------------------- MANUAL MODE --------------------\n");

        String[] symbolList = {"AAPL", "AMZN", "CAJ", "FB", "GOOG", "MSFT", "NINOY", "SNE", "WMT", "YHOO"};

        Scanner sc = new Scanner(System.in);

        String cont = "yes"; // this variable is used to judge whether the user want to continue
        while(cont.equals("yes")){

            String symbol = "";
            while(!(Arrays.asList(symbolList).contains(symbol))){
                System.out.println("Please enter the ticker of the stock: (AAPL, AMZN, CAJ, FB, GOOG, MSFT, NINOY, SNE, WMT, or YHOO)");
                symbol = sc.nextLine();
            }

            int offset = -1;
            while((offset < 0) || offset > 100) {
                System.out.println("Please enter the offset of the date: (0 to 100 days before 2/27/2015)");
                String judge;
                judge = sc.nextLine();
                // judge whether user input integer
                try {
                    offset = Integer.parseInt(judge);
                } catch (NumberFormatException e) {

                }
            }

            System.out.printf("\n---------- The input parameters are: ----------\n\n");
            System.out.printf("ticker = %s\n", symbol);
            System.out.printf("offset = %d days\n", offset);

            double[] priceVariance;
            StockPredict Stock = new StockPredict(symbol, offset, 10);
            priceVariance = Stock.getPriceVariance();

            System.out.printf("\n-------------- The prediction is: --------------\n\n");
            System.out.printf("The predicted date is: %s\n", Stock.getActualDate());
            System.out.printf("The predicted price is: %f\n", priceVariance[0]);
            System.out.printf("The predicted variance is: %f\n", priceVariance[1]);
            System.out.printf("The price would be likely in this range: %f ~ %f\n", priceVariance[0] - 3 * priceVariance[1], priceVariance[0] + 3 * priceVariance[1]);

            System.out.printf("\n-------- The actual price and error are: --------\n\n");
            System.out.printf("The actual price is: %f\n", Stock.getActualPrice());
            System.out.printf("The prediction error is: %f\n", Math.abs(priceVariance[0] - Stock.getActualPrice()));
            System.out.printf("The relative error is: %f\n", Math.abs(priceVariance[0] - Stock.getActualPrice()) / Stock.getActualPrice());
            System.out.printf("\n---------------------- END ----------------------\n");

            System.out.println("Do you want to continue? (yes/anything)");
            cont = sc.nextLine();
            System.out.printf("\n");
        }

    }
}

