package frc.team696.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team696.Robot;

public class ZeroEncoders extends Command {

    int pidIdx = 0;
    int timeoutMs = 20;

    public ZeroEncoders() {
        requires(Robot.driveTrainSubsystem);
    }

    @Override
    public void initialize() {

        Robot.driveTrainSubsystem.leftRear.setSelectedSensorPosition(0, pidIdx, timeoutMs);
        Robot.driveTrainSubsystem.rightFront.setSelectedSensorPosition(0, pidIdx, timeoutMs);

    }

    @Override
    public void execute() {

    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}
