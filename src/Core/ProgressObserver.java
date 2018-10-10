package Core;

import java.text.DecimalFormat;
import java.util.Date;

public class ProgressObserver implements Runnable
{
    private Solver solver;

    public ProgressObserver(Solver s)
    {
        this.solver = s;
    }

    public static void launch(Solver solver)
    {
        Thread t = new Thread(new ProgressObserver(solver));
        t.start();
    }

    public void run()
    {
        double completion = 0;
        while(completion < 1)
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            completion = solver.completion();
            DecimalFormat df = new DecimalFormat("#");
            System.out.println(new Date() +  " - " + df.format(completion * 100) + "% - " + solver.getMeilleurScore());
        }

        System.out.println("Meilleur score : " + solver.getMeilleurScore());
    }
}
