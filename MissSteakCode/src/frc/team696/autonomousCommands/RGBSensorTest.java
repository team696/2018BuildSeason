package frc.team696.autonomousCommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team696.commands.DriveUntilTape;
import frc.team696.commands.Outtake;

public class RGBSensorTest extends CommandGroup {

    public RGBSensorTest() {

        addSequential(new Outtake(0.5));



    }

}
