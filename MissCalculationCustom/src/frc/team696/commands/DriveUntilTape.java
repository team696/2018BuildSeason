package frc.team696.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team696.Robot;

public class DriveUntilTape extends Command {

    double speed;

    public DriveUntilTape(double speed) {
        requires(Robot.rgbSensorSubsystem);
        requires(Robot.driveTrainSubsystem);
        this.speed = speed;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {

        Robot.rgbSensorSubsystem.rgbGetLux();
        Robot.driveTrainSubsystem.tankDrive(speed, speed);
        System.out.println(Robot.rgbSensorSubsystem.passedTape);

    }

    @Override
    public boolean isFinished() {
        if(Robot.rgbSensorSubsystem.passedTape){
            return true;
        }
        return false;
    }

}
