package frc.team696.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team696.Robot;

public class Outtake extends Command {

    int loopNumber = 0;

    public Outtake() {
        requires(Robot.intakeSubsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {

        loopNumber++;
        Robot.intakeSubsystem.runIntake(-0.5);


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
