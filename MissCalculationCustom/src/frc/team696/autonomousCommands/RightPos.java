package frc.team696.autonomousCommands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team696.commands.*;

public class RightPos extends CommandGroup {

    public RightPos() {

        if(DriverStation.getInstance().getGameSpecificMessage().charAt(1) == 'R'){

            addSequential(new ZeroElevator());
            addSequential(new ZeroEncoders());
            addSequential(new ZeroYaw());
            addParallel(new Rise());
            addSequential(new DriveCommand(135, 0, 0.75, 0.05));
            addSequential(new Wait(1));
            addSequential(new DriveCommand(135, -45, 0.25, 0.45));
            addSequential(new Wait(1));
            addSequential(new DriveCommand(140, -45, 0.35, 0.35));
            addSequential(new Tilt());
//            addSequential(new Wait(-1));
            addSequential(new MoveToPos("high"));
//            addSequential(new Wait(1));
            addSequential(new Outtake(-1));
//            addParallel(new OpenIntake());
            addSequential(new OpenIntake());
            addSequential(new Wait(1));
            addSequential(new MoveToPos("intake"));

        }

    }

}
