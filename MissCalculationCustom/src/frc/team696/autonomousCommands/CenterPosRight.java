package frc.team696.autonomousCommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team696.commands.*;

public class CenterPosRight extends CommandGroup {

    public CenterPosRight(){

        addSequential(new ZeroElevator());
        addSequential(new ZeroEncoders());
        addSequential(new ZeroYaw());
        addSequential(new Rise());
        addSequential(new DriveCommand(45, 45, 0.55, 0.12), 3);
        addSequential(new DriveCommand(60, 0,0.25, 0.35), 3);
        addSequential(new Tilt());
        addSequential(new MoveToPos("switch"));
        addSequential(new Wait(1));
        addSequential(new OpenIntake());
        addSequential(new Outtake(), 2);

//        addSequential(new DriveCommand( 70, 0, 0.35, 0.30), 2);
//        addSequential(new DriveCommand(75, 0, 0.35, 0.35), 2);

    }
}
