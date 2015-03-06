import java.util.Scanner;

/**
 * Created by Hon on 3/5/2015.
 */
public class TestPrediction {

    public static void main(String[] args) {

        Scanner scMain = new Scanner(System.in);
        String mode = new String(); // This variable is used to judge which mode the user select

        while(!(mode.equals("auto") || mode.equals("manual") || mode.equals("demo"))){
            System.out.println("Please select runing mode(auto/manual/demo). Enter quit to exit.");
            mode = scMain.nextLine();

            if(mode.equals("manual")){
                ManualStockPrediction predict = new ManualStockPrediction();
                predict.runPrediction(); // If enter manual, run the manual mode
            }
            else if(mode.equals("auto")){
                AutoStockPrediction predict = new AutoStockPrediction();
                predict.runPrediction(); // If enter auto, run the auto mode
            }
            else if(mode.equals("demo")){
                DemoStockPrediction predict = new DemoStockPrediction();
                predict.runPrediction(); // If enter demo, run the demo mode
            }
            else if(mode.equals("quit")){
                break; // If the user enter quit, exit
            }

            mode = "reset"; // reset the mode variable to continue looping, unless the user want to exit
        }
    }
}
