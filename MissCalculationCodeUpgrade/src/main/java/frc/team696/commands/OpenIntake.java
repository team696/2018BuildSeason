package frc.team696.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team696.Robot;

public class OpenIntake extends Command {

    public OpenIntake() {
        requires(Robot.intakeSubsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {

        Robot.intakeSubsystem.intakeSol.set(true);
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}
