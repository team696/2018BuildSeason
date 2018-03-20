package frc.team696.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team696.Robot;

public class Outtake extends Command {

    int loopNumber = 0;
    double speed;

    public Outtake(double speed) {
        requires(Robot.intakeSubsystem);
        this.speed = speed;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {

        loopNumber++;
        Robot.intakeSubsystem.runIntake(-speed);


    }

    @Override
    public boolean isFinished() {

        if(loopNumber > 100){
            Robot.intakeSubsystem.runIntake(0);
            return true;
        }


        return false;
    }

}
