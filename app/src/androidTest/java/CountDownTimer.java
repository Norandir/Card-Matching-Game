import java.util.Set;
import java.util.concurrent.TimeUnit;

public class CountDownTimer extends Thread{

    int time;
    //FIXME: Timer view variable here
    //FIXME: Add object variable so I can call a lose condition when needed

    /**
     * Default Constructor: Nothing Special
     */
    public CountDownTimer(int time) { //FIXME: Add timer view variable to input parameters. FIXME: Add object reference so I can call lose condition when needed.
        this.time = time;
        //FIXME: Timer view variable here
    }

    @Override
    public void run() {
        //FIXME: Set timer view to equal time
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (time >= 0) {
            time = time - 1;
            //FIXME: Set timer view to equal time
            if (time == 0) {
                //FIXME: Fire lose condition because time is up
                break;
            } else {
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
