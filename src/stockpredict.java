import Jama.*;

/**
 * Created by Hon on 2/27/2015.
 */
public class StockPredict {

    private String symbol;
    private int offset;
    private int n = 40;
    private int M = 4;
    private double alpha = 0.005;
    private double beta = 11.1;
    private double t[][];
    private double actualPrice;
    private String actualDate;

    public String[] date = new String[n];

    public StockPredict(String symbol, int offset){
        this.symbol = symbol;
        this.offset = offset;
        ReadCSV readData = new ReadCSV(n, symbol, offset);
        t = readData.readPrice();
        date = readData.readDate();
        actualPrice = readData.readActualPrice();
        actualDate = readData.readActualDate();
    }

    public double[] getPriceVariance() {

        int i,j;

//        System.out.printf("M = %d\n", M);
//        System.out.printf("Alpha = %f\n", alpha);
//        System.out.printf("Beta = %f\n", beta);

        double x[] = new double[n + 1];
        double a[][] = new double[M + 1][1];
        double b[][] = new double[1][M + 1];
        double s[][];
        double lt[][] = new double[M + 1][1];
        double predictprice[][];


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

        double variance = 1/beta + B.times(S).times(B.transpose()).get(0, 0);
        variance = Math.sqrt(variance);

        double[] priceVariance = {predictprice[0][0], variance};

        return priceVariance;
    }

    public String getActualDate(){
        return actualDate;
    }

    public double getActualPrice(){
        return actualPrice;
    }
}