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

        loopNumber++;

        Robot.intakeSubsystem.runIntake(0.8);
        System.out.println(Robot.PDP.getCurrent(7));


    }

    @Override
    public boolean isFinished() {
        if(Robot.PDP.getCurrent(7) > 20 && loopNumber > 20){
            Robot.intakeSubsystem.runIntake(0);
            Robot.intakeSubsystem.intakeSol.set(false);
            return true;
        }
        return false;
    }

}
