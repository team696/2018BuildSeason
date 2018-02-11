package frc.team696.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class Wait extends Command {

    Timer timer = new Timer();
    double time;

    public Wait(double time) {
        this.time = time;
    }

    @Override
    public void initialize() {
        timer.start();
    }

    @Override
    public void execute() {

        if(timer.get() > time){
            timer.stop();
            timer.reset();
        }

    }

    @Override
    public boolean isFinished() {
        return true;
    }

}
