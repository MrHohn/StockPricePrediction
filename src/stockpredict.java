import Jama.*;
import java.util.Scanner;

/**
 * Created by Hon on 2/27/2015.
 */
public class StockPredict {

    private String symbol;
    private int n;
    private int offset;

    public String[] date;

    public StockPredict(String symbol, int n, int offset){
        this.symbol = symbol;
        this.n = n;
        this.offset = offset;
        date = new String[n];
    }

    public double[] getPriceVariance() {

        int i,j;
        int M = 4;
//        int n = 40;
        double alpha = 0.005;
        double beta = 11.1;

//        System.out.printf("M = %d\n", M);
//        System.out.printf("Alpha = %f\n", alpha);
//        System.out.printf("Beta = %f\n", beta);

        double x[] = new double[n + 1];
        double t[][];
        double a[][] = new double[M + 1][1];
        double b[][] = new double[1][M + 1];
        double s[][];
        double lt[][] = new double[M + 1][1];
        double predictprice[][];
//        String[] date;

        ReadCSV readClosePrice = new ReadCSV(n, symbol, offset);
        t = readClosePrice.readPrice();
        date = readClosePrice.readDate();

        /*---------------initialize the training data---------------*/

        for(i = 0; i <= n; ++i)
        {
            x[i] = i + 1;
        }

//        System.out.printf("\nthe recent prices are:\n");
//        for(i = n - 1; i >= 0; --i)
//        {
////            System.out.printf("t[%d] = %f\n", i, t[i][0]);
//            System.out.printf("%s: %f\n", date[i], t[i][0]);
//        }

        /*--------------calculate SUM-φ(xn)-------------------*/

        for(i = 0; i < n; i++)
        {
            for(j = 0; j <= M; j++)
            {
                a[j][0] += Math.pow(x[i], j);
            }
        }

//        System.out.printf("\nthe SUM-Phi(xn) is:\n");
//        for(i = 0; i <= M; i++)
//        {
//            System.out.printf("%f\n", a[i][0]);
//        }

        Matrix A = new Matrix(a);

        /*-----------------initialize φ(x)T------------------*/

        for(i = 0; i <= M; i++)
        {
            b[0][i] = Math.pow(x[n], i);
        }

//        System.out.printf("\nthe Phi(x)T is:\n");
//        for(i = 0; i <= M; i++)
//        {
//            System.out.printf("%f  ", b[0][i]);
//        }
//        System.out.printf("\n");

        Matrix B = new Matrix(b);

        /*------------calculate the matrix S-------------*/

        Matrix S = A.times(B).times(beta);
        s = S.getArray();

        for(i = 0; i <= M; i++)
        {
            for(j = 0; j <= M; j++)
            {
                if(i == j)
                {
                    s[i][j] += alpha; // + Alpha * I
                }
            }
        }

        /*------------calculate the inversion of matrix S-------------*/

        S = S.inverse();
        s = S.getArray();

//        System.out.printf("\nthe Matrix S is:\n");
//        for(i = 0; i <= M; i++)
//        {
//            for(j = 0; j <= M; j++)
//            {
//                System.out.printf("s[%d][%d] = ", i, j);
//                System.out.printf("%4f ", s[i][j]);
//            }
//            System.out.printf("\n");
//        }

        /*-------------last two parts multiply------------*/

        for(i = 0; i < n; i++)
        {
            for(j = 0; j <= M; j++)
            {
                lt[j][0] += Math.pow(x[i], j) * t[i][0];
            }
        }

        Matrix LT = new Matrix(lt);

        /*-------------first two parts matrix multiply------------*/

        Matrix FT = B.times(S);

        /*----------combine together--------------*/

        Matrix PP = FT.times(LT).times(beta);
        predictprice = PP.getArray();
//        System.out.printf("\nthe predict price is: \n%f\n", predictprice[0][0]);

        double variance = 1/beta + B.times(S).times(B.transpose()).get(0, 0);
        variance = Math.sqrt(variance);
//        System.out.printf("\nthe predict variance is: \n%f\n", variance);

//        System.out.printf("\nthe predict price is likely in this range: \n%f ~ %f\n", predictprice[0][0] - 3 * variance, predictprice[0][0] + 3 * variance);

        double[] priceVariance = {predictprice[0][0], variance};

        return priceVariance;
    }

	public static int main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("(PS: Enter quit to exit.)");

        String symbol = new String();
        while(!(symbol.equals("GOOG") || symbol.equals("YHOO") || symbol.equals("AAPL") || symbol.equals("FB") || symbol.equals("MSFT"))){
            System.out.println("Please enter the ticker of the stock: (GOOG, YHOO, AAPL, FB or MSFT)");
            symbol = sc.nextLine();
            if(symbol.equals("quit"))
                return 0;
        }

        int n = 0;
        while((n < 10) || n > 60){
            System.out.println("Please enter the N: (10 to 60)");
            n = sc.nextInt();
//            if(symbol.equals("quit"))
//                return 0;
        }

        int offset = -1;
        while((offset < 0) || offset > 100) {
            System.out.println("Please enter the offset of the date: (0 to 100)");
            offset = sc.nextInt();
//            if(symbol.equals("quit"))
//                return 0;
        }

        System.out.printf("\n----------The input parameters are:------------\n");
        System.out.printf("ticker = %s\n", symbol);
        System.out.printf("N      = %d\n", n);
        System.out.printf("offset = %d days\n", offset);
        System.out.printf("------------------------------------------------\n");

        double[] priceVariance;
        StockPredict Stock = new StockPredict(symbol, n, offset);
        priceVariance = Stock.getPriceVariance();

        System.out.printf("\nThe predicted date is the next day of %s\n", Stock.date[n - 1]);
        System.out.printf("The predicted price is: %f\n", priceVariance[0]);
        System.out.printf("The predicted variance is: %f\n", priceVariance[1]);
        System.out.printf("The price would be likely in this range: %f ~ %f\n", priceVariance[0] - 3 * priceVariance[1], priceVariance[0] + 3 * priceVariance[1]);

        return 0;
    }
}