import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class utils {
    public static double[][] multiply(double[][] a,double[][] b){
        int n = a.length;
        double [][] c = new double[n][n];
        for (int i=0; i<n ;++i)
            for(int j=0; j<n;++j)
                for (int k=0;k<n;++k)
                    c[i][j] += a[i][k] * b[k][j];

        return c;
    }

    public static double[][] matrix_pow(double[][]a, int p){
        double[][] tmp = a.clone();
        for(int i = 1;i<p;i++){
            tmp = multiply(tmp,a);
        }
        return tmp;
    }

    public static double[][] process(String input){
        String[] splited = input.split("\\s+");
        int p = Integer.parseInt(splited[0]);
        int n = Integer.parseInt(splited[1]);
        double[][] result = new double[n][n];
        for(int i = 0; i < n;i++)
            for(int j = 0;j < n; j++)
                result[i][j] = Double.parseDouble(splited[2 + i*n + j]);

        result = matrix_pow(result,p);
        return result;
    }

    public static String stringify(double[][] matrix, int p){
        int n = matrix.length;
        StringBuilder builder = new StringBuilder();
        builder.append(p);
        builder.append(" ");
        builder.append(n);
        for(int i=0; i<n; i++)
            for(int j=0;j<n;j++) {
                builder.append(" ");
                builder.append(matrix[i][j]);
            }

        return  builder.toString();
    }

    public  static double[][] randomMatrix(int size){
        double[][] matrix = new double[size][size];
        for(int i=0;i<size;i++)
            for(int j=0;j<size;j++)
                matrix[i][j] = ThreadLocalRandom.current().nextDouble(0,200);
        return  matrix;
    }

    public static double firstMoment(long[] times){
        return Arrays.stream(times).average().getAsDouble()/1e6;
    }

    public static double secondMoment(long[] times){
        double tmp = 0;
        for (double time:times) {
            tmp += Math.pow(time,2);
        }
        tmp =  tmp /times.length;
        tmp = tmp / 1e12;
        return tmp;
    }
}
