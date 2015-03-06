/**
 * Created by Hon on 3/5/2015.
 */
public class AutoStockPrediction {

    public void runPrediction() {
        System.out.printf("\n-------------------- AUTO MODE --------------------\n");
        String[] symbolList = {"AAPL", "AMZN", "CAJ", "FB", "GOOG", "MSFT", "NINOY", "SNE", "WMT", "YHOO"};
        double[] error = new double[10];
        double meanError = 0;
        double relativeError = 0;
        double overallRelativeError = 0;
        for(int i = 0; i < symbolList.length; ++i){
            String symbol = symbolList[i];
            System.out.printf("\n--------------------- %s ---------------------\n", symbol);
            for(int j = 0; j <= 9; ++j){
                double[] priceVariance;
                StockPredict Stock = new StockPredict(symbol, j * 10, 10);
                priceVariance = Stock.getPriceVariance();
                error[j] = Math.abs(Stock.getActualPrice() - priceVariance[0]);
                meanError += error[j];
                relativeError += error[j] / Stock.getActualPrice();
                System.out.printf("For %s on %s\n", symbol, Stock.getActualDate());
                System.out.printf("The predicted price is: %f\n", priceVariance[0]);
                System.out.printf("The actual price is: %f\n", Stock.getActualPrice());
                System.out.printf("Error is: %f\nRelative error is: %f\n\n", error[j], error[j] / Stock.getActualPrice());
            }
            meanError /= error.length;
            relativeError /= error.length;
            System.out.printf("For %s:\n", symbol);
            System.out.printf("The absolute mean error is: %f\n", meanError);
            System.out.printf("The average relative error is: %f\n", relativeError);
            System.out.printf("\n---------------------------------------------------\n\n");
            overallRelativeError += relativeError;
            meanError = 0;
            relativeError = 0;
        }
        System.out.printf("The overall relative error is: %f\n", overallRelativeError);
        System.out.printf("\n---------------------- END ----------------------\n\n");
    }
}
