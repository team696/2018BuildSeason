package org.usfirst.frc.team696.robot.autonomousCommands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftPosition extends CommandGroup {

    String gameData = DriverStation.getInstance().getGameSpecificMessage();

    public LeftPosition() {



    }

}
