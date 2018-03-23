package frc.team696.autonomousCommands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team696.commands.*;

public class LeftPos extends CommandGroup {

    String gameData;

    public LeftPos() {

        gameData = DriverStation.getInstance().getGameSpecificMessage();
        if (gameData.length() > 0) {
            if (gameData.charAt(1) == 'L') {
                
                addSequential(new ZeroElevator());
                addSequential(new ZeroEncoders());
                addSequential(new ZeroYaw());
                addParallel(new Rise());
                addSequential(new DriveCommand(250 ,0, 0.90, 0.1), 3);
                addSequential(new Tilt());
                addSequential(new DriveCommand(250, 45, 0.4, 0.6));
                addSequential(new MoveToPos("high"), 2);
                addSequential(new DriveCommand( 265, 45, 0.6, 0.1), 2 );
                addSequential(new Outtake(0.65));
                addSequential(new DriveCommand(240, 45, 0.3, 0.1), 2);
                addSequential(new MoveToPos("intake"));


            }

            if(gameData.charAt(1) == 'R'){
                addSequential(new ZeroElevator());
                addSequential(new ZeroEncoders());
                addSequential(new ZeroYaw());
                addSequential(new DriveCommand(210, 0, 0.90, 0.1), 3);
                addSequential(new DriveCommand(210, 90, 0.4, 0.5), 3);
                addSequential(new DriveCommand ( 420, 90, 0.9, 0.1));
                addSequential(new Tilt());
                addSequential(new DriveCommand(420, -15, 0.4, 0.6), 3);
                addSequential(new MoveToPos("high"), 2);
                addSequential(new DriveCommand(470, -15, 0.5, 0.1));
                addSequential(new Outtake(-0.4));
                addSequential(new DriveCommand(440, 0, 0.4, 0.1), 3);
                addSequential(new MoveToPos("intake"));


            }


        }

    }

}
