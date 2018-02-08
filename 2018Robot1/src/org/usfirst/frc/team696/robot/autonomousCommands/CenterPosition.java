package org.usfirst.frc.team696.robot.autonomousCommands;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team696.robot.commands.DriveCommand;

public class CenterPosition extends CommandGroup {

    String gameData = DriverStation.getInstance().getGameSpecificMessage();

    public CenterPosition(){

        if(gameData.charAt(0) == 'L' && gameData.charAt(1) == 'R'){

//          addSequential(new DriveCommand(1, 1, 1, 1), 1);
            addSequential(new DriveCommand(90, -30, 1, 0.05), 4);
            addSequential(new DriveCommand(20, 0, 0.75, 0.05), 4);
            // Drop the object
            addSequential(new DriveCommand(-20, -90, 0.75, 0.05), 4);
            addSequential(new DriveCommand( 30, 0, 0.75, 0.05), 4);
            addSequential(new DriveCommand( 90, 0, 0.75, 0.05), 4);
            addSequential(new DriveCommand( 250, 0 , 0.75, 0.1), 4 );




        }
        /*
        if(gameData.charAt(0) == 'R' && gameData.charAt(1) == 'R');

        if(gameData.charAt(0) == 'R' && gameData.charAt(1) == 'L');

        if(gameData.charAt(0) == 'L' && gameData.charAt(1) == 'L');

         */

    }

}
