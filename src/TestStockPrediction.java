import java.util.Scanner;

/**
 * Created by Hon on 3/3/2015.
 */
public class TestStockPrediction {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        //        System.out.println("(PS: Enter quit to exit.)");

        String symbol = new String();
        while(!(symbol.equals("GOOG") || symbol.equals("YHOO") || symbol.equals("AAPL") || symbol.equals("FB") || symbol.equals("MSFT"))){
            System.out.println("Please enter the ticker of the stock: (GOOG, YHOO, AAPL, FB or MSFT)");
            symbol = sc.nextLine();
            //            if(symbol.equals("quit"))
            //                return 0;
        }

        int offset = -1;
        while((offset < 0) || offset > 100) {
            System.out.println("Please enter the offset of the date: (0 to 100 days before 2/27/2015)");
            offset = sc.nextInt();
        }

        System.out.printf("\n----------The input parameters are:------------\n");
        System.out.printf("ticker = %s\n", symbol);
        System.out.printf("offset = %d days\n", offset);
        System.out.printf("------------------------------------------------\n");

        double[] priceVariance;
        StockPredict Stock = new StockPredict(symbol, offset);
        priceVariance = Stock.getPriceVariance();

        System.out.printf("\nThe predicted date is the next day of %s\n", Stock.date[Stock.date.length - 1]);
        System.out.printf("The predicted price is: %f\n", priceVariance[0]);
        System.out.printf("The predicted variance is: %f\n", priceVariance[1]);
        System.out.printf("The price would be likely in this range: %f ~ %f\n", priceVariance[0] - 3 * priceVariance[1], priceVariance[0] + 3 * priceVariance[1]);

        //        return 0;
    }
}

