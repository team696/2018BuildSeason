package frc.team696.autonomousCommands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team696.commands.*;

public class LeftPos extends CommandGroup {

    public LeftPos() {

        if(DriverStation.getInstance().getGameSpecificMessage().charAt(1) == 'L'){
            addSequential(new ZeroElevator());
            addSequential(new ZeroEncoders());
            addSequential(new ZeroYaw());
            addParallel(new Rise());
            addSequential(new DriveCommand(100, 0, 0.75, 0.1));
            addSequential(new DriveCommand(140, 30, 0.35, 0.35), 5);
            addSequential(new Tilt());
            addSequential(new Wait(2));
            addSequential(new MoveToPos("high"));
            addSequential(new Wait(2));
            addSequential(new Outtake());
            addParallel(new OpenIntake());
            addSequential(new Wait(2));
            addSequential(new MoveToPos("switch"));
        }

        if(DriverStation.getInstance().getGameSpecificMessage().charAt(1) == 'R'){
            addSequential(new ZeroElevator());
            addSequential(new ZeroEncoders());
            addSequential(new ZeroYaw());
            addParallel(new Rise());
            addSequential(new DriveCommand(100, 0, 1, 0.1));
            addSequential(new DriveCommand(120, 90, 0.35, 0.35));
            addSequential(new DriveCommand(220, 90, 1, 0.1));
            addSequential(new DriveCommand(230, 0, 0.35, 0.35));
            addSequential(new Tilt());
            addSequential(new Wait(1));
            addSequential(new MoveToPos("high"));
            addSequential(new Wait(1));
            addSequential(new Outtake());
            addSequential(new MoveToPos("switch"));
        }


    }

}
