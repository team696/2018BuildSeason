package frc.team696.autonomousCommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team696.commands.*;

import static frc.team696.Robot.elevatorSubsystem;

public class CenterPos extends CommandGroup {



    public CenterPos() {

//        System.out.println( elevatorSubsystem.elevatorTarget);

        addSequential(new ZeroElevator());
        addSequential(new ZeroEncoders());
        addSequential(new ZeroYaw());
        addSequential(new Rise());
        addParallel(new DriveCommand(10, 0, 0.60 ,0.1));
        addSequential(new DriveCommand(40, -90, 0.35, 0.35), 2);
        addSequential(new DriveCommand( 70, 0, 0.35, 0.30), 2);
        addSequential(new DriveCommand(75, 0, 0.35, 0.35), 2);
        addSequential(new Tilt());
        addSequential(new MoveToPos("switch"));
        addSequential(new OpenIntake());
        addSequential(new Outtake(), 2);

//        addSequential(new DriveCommand( 65, 0, 0.65, 0.4));
//        addSequential(new Wait(2));
//        addSequential(new Outtake(), 2);




//        addSequential(new DriveCommand(70, 0, 0.35, 0.25 ));
//        addSequential(new DriveCommand(70))
//        addSequential(new DriveCommand(65, 0,0.5, 0.35));
//        addSequential(new DriveCommand(40, -90, 0.30, 0.30), 4);
//        addSequential(new DriveCommand( 60, 0, 0.25, 0.45));




//        addSequential(new OpenIntake());
//        addSequential(new Intake());

    }

}
