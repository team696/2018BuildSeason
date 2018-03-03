package frc.team696.autonomousCommands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team696.commands.DriveCommand;
import frc.team696.commands.ZeroEncoders;
import frc.team696.commands.ZeroYaw;

public class LeftPos extends CommandGroup {

    public LeftPos() {

        if(DriverStation.getInstance().getGameSpecificMessage().equals("LRL")){

            addSequential(new ZeroYaw());
            addSequential(new ZeroEncoders());
            addSequential(new DriveCommand(0, -90, 0, 0.5));

        }

    }

}
