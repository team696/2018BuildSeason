package frc.team696.autonomousCommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team696.commands.ZeroEncoders;
import frc.team696.commands.ZeroYaw;

public class LeftPos extends CommandGroup {

    public LeftPos() {

        addSequential(new ZeroEncoders());
        addSequential(new ZeroYaw());


    }

}
