package frc.team696.autonomousCommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team696.commands.DriveCommand;
import frc.team696.commands.ZeroEncoders;
import frc.team696.commands.ZeroYaw;

public class LeftPos extends CommandGroup {

    public LeftPos() {

        addSequential(new ZeroEncoders());
        addSequential(new ZeroYaw());
        addSequential(new DriveCommand(70, 0, 0.75, 0.6), 4);
        addSequential(new DriveCommand( 90,90, 0.5,0.5), 5);


    }

}
