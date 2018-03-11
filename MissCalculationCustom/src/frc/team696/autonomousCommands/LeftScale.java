package frc.team696.autonomousCommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team696.commands.*;

public class LeftScale extends CommandGroup {

    public LeftScale(){


        addSequential(new ZeroElevator());
        addSequential(new ZeroEncoders());
        addSequential(new ZeroYaw());
        addSequential(new Rise());
        addSequential(new DriveCommand(100, 0, 0.75, 0.1));
        addSequential(new DriveCommand(140, 30, 0.35, 0.35), 5);
        addSequential(new Tilt());
        addSequential(new Wait(2));
        addSequential(new MoveToPos("scale"));
        addSequential(new Wait(2));
        addSequential(new Outtake());
        addParallel(new OpenIntake());
        addSequential(new Wait(2));
        addSequential(new MoveToPos("switch"));









    }



}
