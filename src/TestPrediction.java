import java.util.Scanner;

/**
 * Created by Hon on 3/5/2015.
 */
public class TestPrediction {

    public static void main(String[] args) {

        Scanner scMain = new Scanner(System.in);
        String mode = new String();

        while(!(mode.equals("auto") || mode.equals("manual") || mode.equals("demo"))){
            System.out.println("Please select runing mode(auto/manual/demo). Enter quit to exit.");
            mode = scMain.nextLine();

            if(mode.equals("manual")){
                ManualStockPrediction predict = new ManualStockPrediction();
                predict.runPrediction();
            }
            else if(mode.equals("auto")){
                AutoStockPrediction predict = new AutoStockPrediction();
                predict.runPrediction();
            }
            else if(mode.equals("demo")){
                DemoStockPrediction predict = new DemoStockPrediction();
                predict.runPrediction();
            }
            else if(mode.equals("quit")){
                break;
            }

            mode = "reset"; // reset the mode variable to continue looping, unless the user want to exit
        }
    }
}
