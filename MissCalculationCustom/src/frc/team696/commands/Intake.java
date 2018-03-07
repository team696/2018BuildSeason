package frc.team696.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team696.Robot;

public class Intake extends Command {

    int loopNumber = 0;

    public Intake() {
        requires(Robot.intakeSubsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {

        Robot.intakeSubsystem.runIntake(0.5);


    }

    @Override
    public boolean isFinished() {
        if(loopNumber >= 500){
            loopNumber = 0;
            return true;
        }
        return false;
    }

}
