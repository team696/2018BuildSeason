package frc.team696.autonomousCommands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team696.commands.*;

public class RightPos extends CommandGroup {

    String gameData;

    public RightPos() {

        gameData = DriverStation.getInstance().getGameSpecificMessage();
        if (gameData.length() > 0) {
            if (gameData.charAt(1) == 'R') {

                //October 7, 2018, works! probably wont work during beach blitz though so...

                addSequential(new ZeroElevator());
                addSequential(new ZeroEncoders());
                addSequential(new ZeroYaw());
                addParallel(new Rise());
                addSequential(new DriveCommand(250, 0, 0.85, 0.1));
                addSequential(new DriveCommand(250, -45, 0.25, 0.45), 1);
                addSequential(new Tilt());
                addSequential(new MoveToPos("high"));
                addSequential(new DriveCommand(265, -45, 0.45, 0.35), 0.5 );

                addSequential(new Outtake(0.5), 0.3);

                addParallel(new DriveCommand(250, -45, 0.45, 0.1), 0.5);
                addSequential(new MoveToPos("intake"));

            }

            if (gameData.charAt(1) == 'L') {

                //roughly works. good path. bad timing. 

                addSequential(new ZeroElevator());
                addSequential(new ZeroEncoders());
                addSequential(new ZeroYaw());
                addSequential(new Rise());
                addSequential(new DriveCommand(260, 0, 0.9, 0.1));
                addSequential(new DriveCommand(260, -90, 0.3, 0.7));
                addSequential(new DriveCommand(490, -90, 0.85, 0.1));
                addSequential(new DriveCommand(490, 15, 0.3, 0.7));
                addSequential(new Tilt());
                addParallel(new MoveToPos("high"));
                addSequential(new DriveCommand(530, 15, 0.55, 0.1));
                addSequential(new Outtake(0.5), 1);
                addSequential(new MoveToPos("intake"));
                addSequential(new DriveCommand(520, 15, 0.5, 0.1));

                


            }

        }
    }

}
