import Jama.*;
import java.util.Scanner;

/**
 * Created by Hon on 2/27/2015.
 */
public class stockpredict {

	public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("input the ticker of the stock: ");
        String symbol = sc.nextLine();
        System.out.println("input the n");
        int n = sc.nextInt();

        int i,j;
        int M = 4;
//        int n = 40;
        double alpha = 0.005;
        double beta = 11.1;

        System.out.printf("ticker = %s\n", symbol);
        System.out.printf("M = %d\n", M);
        System.out.printf("n = %d\n", n);
        System.out.printf("Alpha = %f\n", alpha);
        System.out.printf("Beta = %f\n", beta);

        double x[] = new double[n + 1];
        double t[][];
        double a[][] = new double[M + 1][1];
        double b[][] = new double[1][M + 1];
        double s[][];
        double lt[][] = new double[M + 1][1];
        double predictprice[][];

        readCSV readClosePrice = new readCSV(n, symbol);
        t = readClosePrice.read();

        /*---------------initialize the training data---------------*/

        for(i = 0; i <= n; ++i)
        {
            x[i] = i + 1;
        }

        System.out.printf("\nthe recent prices are:\n");
        for(i = 0; i <= n - 1; ++i)
        {
            System.out.printf("t[%d] = %f\n", i, t[i][0]);
        }

        /*--------------calculate SUM-φ(xn)-------------------*/

        for(i = 0; i < n; i++)
        {
          for(j = 0; j <= M; j++)
          {
            a[j][0] += Math.pow(x[i], j);
          }
        }

        System.out.printf("\nthe SUM-Phi(xn) is:\n");
        for(i = 0; i <= M; i++)
        {
            System.out.printf("%f\n", a[i][0]);
        }

        Matrix A = new Matrix(a);

        /*-----------------initialize φ(x)T------------------*/

        for(i = 0; i <= M; i++)
        {
            b[0][i] = Math.pow(x[n], i);
        }

        System.out.printf("\nthe Phi(x)T is:\n");
        for(i = 0; i <= M; i++)
        {
            System.out.printf("%f  ", b[0][i]);
        }
        System.out.printf("\n");

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

        System.out.printf("\nthe Matrix S is:\n");
        for(i = 0; i <= M; i++)
        {
            for(j = 0; j <= M; j++)
            {
                System.out.printf("s[%d][%d] = ", i, j);
                System.out.printf("%4f ", s[i][j]);
            }
            System.out.printf("\n");
        }

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
        System.out.printf("\nthe predict price is: \n%f\n", predictprice[0][0]);

        double variance = 1/beta + B.times(S).times(B.transpose()).get(0, 0);
        variance = Math.sqrt(variance);
        System.out.printf("\nthe predict variance is: \n%f\n", variance);

        System.out.printf("\nthe predict price is likely in this range: \n%f ~ %f\n", predictprice[0][0] - 3 * variance, predictprice[0][0] + 3 * variance);


    }
}