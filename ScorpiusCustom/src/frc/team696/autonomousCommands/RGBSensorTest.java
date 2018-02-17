package frc.team696.autonomousCommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team696.commands.DriveUntilTape;

public class RGBSensorTest extends CommandGroup {

    public RGBSensorTest() {

        addSequential(new DriveUntilTape(-0.5));

    }

}
