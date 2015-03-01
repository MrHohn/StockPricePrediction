import Jama.*;
import java.util.Scanner;

public class stockpredict {

	public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("input the value of M: ");
        int M = sc.nextInt();
        System.out.println("input the n");
        int n = sc.nextInt();
//        System.out.println("input the value of alpha: ");
//        double alpha = sc.nextDouble();
//        System.out.println("input the value of beta: ");
//        double beta = sc.nextDouble();

        int i,j;
        // int M = 3;
//        int n = 20;
        double alpha = 0.005;
        double beta = 11.1;

        System.out.printf("M = %d\n", M);
        System.out.printf("n = %d\n", n);
        System.out.printf("Alpha = %f\n", alpha);
        System.out.printf("Beta = %f\n", beta);

        double x[] = new double[n + 1];
//        double t[] = new double[n];
        double t[];
        double a[] = new double[M + 1];
        double b[] = new double[M + 1];
        double s[][] = new double[M + 1][M + 1];
        double lt[] = new double[M + 1];
        double ft[] = new double[M + 1];
        double predictprice = 0;

        readCSV readClosePrice = new readCSV(n, "GOOG");
        t = readClosePrice.read();

        /*---------------initialize the training data---------------*/

        for(i = 0; i <= n; i++)
        {
            x[i] = i + 1;
            // x[i] = 1;
            // x[i] = 40 - i;
        }

        // x[0] = 112.54;
        // x[1] = 112.01;
        // x[2] = 113.99;
        // x[3] = 113.91;
        // x[4] = 112.52;
        // x[5] = 110.38;
        // x[6] = 109.33;
        // x[7] = 106.26;
        // x[8] = 106.26;
        // x[9] = 107.75;
        // x[10] = 111.89;
        // x[11] = 112.01;
        // x[12] = 109.25;
        // x[13] = 110.16;
        // x[14] = 109.7;
        // x[15] = 106.83;
        // x[16] = 105.94;
        // x[17] = 108.78;
        // x[18] = 109.55;
        // x[19] = 112.39;
        // x[20] = 112.98;

        // x[0] = 112.98;
        // x[1] = 113.1;
        // x[2] = 109.13;
        // x[3] = 115.31;
        // x[4] = 118.89;
        // x[5] = 117.16;
        // x[6] = 118.63;
        // x[7] = 118.57;
        // x[8] = 119.56;
        // x[9] = 119.94;
        // x[10] = 118.93;
        // x[11] = 119.7;
        // x[12] = 122.02;
        // x[13] = 124.88;
        // x[14] = 126.42;
        // x[15] = 127.08;
        // x[16] = 127.83;
        // x[17] = 128.71;
        // x[18] = 128.45;
        // x[19] = 129.49;
        // x[20] = 133;

//        t[0] = 113.1;
//        t[1] = 109.13;
//        t[2] = 115.31;
//        t[3] = 118.89;
//        t[4] = 117.16;
//        t[5] = 118.63;
//        t[6] = 118.57;
//        t[7] = 119.56;
//        t[8] = 119.94;
//        t[9] = 118.93;
//        t[10] = 119.7;
//        t[11] = 122.02;
//        t[12] = 124.88;
//        t[13] = 126.42;
//        t[14] = 127.08;
//        t[15] = 127.83;
//        t[16] = 128.71;
//        t[17] = 128.45;
//        t[18] = 129.49;
//        t[19] = 133;

        System.out.printf("\nthe recent prices are:\n");
        for(i = 0; i <= n-1; i++)
        {
            System.out.printf("t[%d] = %f\n", i, t[i]);
        }

        /*--------------calculate SUM-φ(xn)-------------------*/

        for(i = 0; i <= M; i++)
        {
            a[i]=0;
        }
        for(i = 0; i < n; i++)
        {
          for(j = 0; j <= M; j++)
          {
            a[j] += Math.pow(x[i], j);
          }
        }

        System.out.printf("\nthe SUM-Phi(xn) is:\n");
        for(i = 0; i <= M; i++)
        {
            // System.out.printf("Phi[%d]T = %f\n", i, a[i]);
            System.out.printf("%f\n", a[i]);
        }

        /*-----------------initialize φ(x)T------------------*/

        for(i = 0; i <= M; i++)
        {
            b[i] = Math.pow(x[n], i);
        }

        System.out.printf("\nthe Phi(x)T is:\n");
        for(i = 0; i <= M; i++)
        {
            // System.out.printf("b[%d] = %f  ", i, b[i]);
            System.out.printf("%f  ", b[i]);
        }
        System.out.printf("\n");

        /*------------calculate the matrix S-------------*/

        for(i = 0; i <= M; i++)
        {
            for(j = 0; j <= M; j++)
            {
                s[i][j] = a[i] * b[j];
            }
        }

        for(i = 0; i <= M; i++)
        {
            for(j = 0; j <= M; j++)
            {
                s[i][j] *= beta;
                if(i == j)
                {
                    s[i][j] += alpha; // + Alpha * I
                }
            }
        }

        System.out.printf("\nthe s is:\n");
        for(i = 0; i <= M; i++)
        {
            for(j = 0; j <= M; j++)
            {
                System.out.printf("s[%d][%d] = ", i, j);
                System.out.printf("%4f ", s[i][j]);
            }
            System.out.printf("\n");
        }
        System.out.printf("\n");

        /*------------calculate the inversion of matrix S-------------*/

        Matrix S = new Matrix(s);
        s = S.inverse().getArray();

        System.out.printf("the inversion of matrix is:\n");
        for(i = 0; i <= M; i++)
        {
            for(j = 0; j <= M; j++)
            {
                System.out.printf("s[%d][%d] = ", i, j);
                System.out.printf("%4f ", s[i][j]);
            }
            System.out.printf("\n");
        }

        /*-------------last two parts matrix multiply------------*/

        for(i = 0; i <= M; i++)
        {
            lt[i] = 0;
        }
        for(i = 0; i < n; i++)
        {
            for(j = 0; j <= M; j++)
            {
                lt[j] += Math.pow(x[i], j) * t[i];
            }
        }

        /*-------------first two parts matrix multiply------------*/

        for(i = 0; i <= M; i++)
        {
            ft[i] = 0;
        }
        for(i = 0; i <= M; i++)
        {
            for(j = 0; j <= M; j++)
            {
                ft[i] += b[j] * s[j][i];
            }
        }


        // double ft[][] = new double[M + 1][1];
        // double b1[][] = new double[1][M + 1];
        // for(i = 0; i <= M; ++i){
        // 	b1[0][i] = b[i];
        // }
        // Matrix B = new Matrix(b1);
        //   Matrix FT = B.arrayTimes(S);
            // ft = FT.getArray();

        /*----------combine together--------------*/

        for(i = 0; i <= M; i++)
        {
            // predictprice = beta * (predictprice + ft[i] * lt[i]);
            predictprice += beta * ft[i] * lt[i];
            // predictprice += beta * ft[i][0] * lt[i];
        }
        System.out.printf("\nthe predict price is: \n%f", predictprice);

	}
}