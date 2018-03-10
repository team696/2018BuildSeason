package frc.team696.autonomousCommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team696.commands.*;

public class CenterPos extends CommandGroup {

    public CenterPos() {

//        addSequential(new ZeroEncoders());
//        addSequential(new ZeroYaw());
////        addSequential(new DriveCommand(10, 0, 0.5, 0), 2);
////        addSequential(new DriveCommand(50, -45, 0.5, 0.25), 2);
//        addSequential(new DriveCommand(0, 90, 0, 0.5), 10);
//        addSequential(new DriveCommand(0, -90, 0, 0.5), 10);
//        addSequential(new DriveCommand(0, 0, 0, 0.75), 10);
//        addSequential(new Wait(5));

        addSequential(new OpenIntake());
        addSequential(new Intake());

    }

}
