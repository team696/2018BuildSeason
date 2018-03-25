package frc.team696.autonomousCommands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team696.commands.*;

public class RightPos extends CommandGroup {

    String gameData;

    public RightPos() {

        gameData = DriverStation.getInstance().getGameSpecificMessage();
        if (gameData.length() > 0) {
            if (gameData.charAt(1) == 'R' && gameData.charAt(0) == 'R') {


                addSequential(new ZeroElevator());
                addSequential(new ZeroEncoders());
                addSequential(new ZeroYaw());
                addParallel(new Rise());
                addSequential(new DriveCommand(250 ,0, 0.90, 0.1), 3.5);
                addSequential(new Tilt());
                addSequential(new DriveCommand(250, -45, 0.4, 0.6));
                addSequential(new MoveToPos("high"), 1.5);
                addSequential(new DriveCommand( 270, -45, 0.6, 0.1), 2 );
                addSequential(new Outtake(0.4));


                addParallel(new MoveToPos("intake"));
                addSequential(new DriveCommand(250, -45, 0.5, 0.1), 1);
                addSequential(new DriveCommand(240, -125, 0.7, 0.6), 4);
                addSequential(new OpenIntake());
                addSequential(new DriveCommand(290, -125, 0.6, 0.1), 2);
                addSequential(new DriveCommand(290, -155, 0.5, 0.35), 3);
                addParallel(new Intake());
                addSequential(new DriveCommand(330, -155, 0.5, 0.1 ), 3);
                addSequential(new Wait(1));//162
                addSequential(new MoveToPos("switch"));
                addSequential(new Outtake(0.6));


            }

            if (gameData.charAt(1) == 'L' ) {

                addSequential(new ZeroElevator());
                addSequential(new ZeroEncoders());
                addSequential(new ZeroYaw());
                addSequential(new DriveCommand(210, 0, 0.90, 0.1), 3);
                addSequential(new DriveCommand(210, -90, 0.4, 0.5), 3);
                addSequential(new DriveCommand ( 420, -90, 0.9, 0.1));
                addSequential(new Tilt());
                addSequential(new DriveCommand(420, 15, 0.4, 0.6), 3);
                addSequential(new MoveToPos("high"), 2);
                addSequential(new DriveCommand(470, 15, 0.5, 0.1));
                addSequential(new Outtake(0.4));
                addParallel(new MoveToPos("intake"));
                addSequential(new DriveCommand(440, 15, 0.5, 0.1), 3);






            }
        }
    }

}
