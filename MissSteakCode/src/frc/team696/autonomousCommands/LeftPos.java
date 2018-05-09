package frc.team696.autonomousCommands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team696.commands.*;

public class LeftPos extends CommandGroup {

    String gameData;

    public LeftPos() {

        gameData = DriverStation.getInstance().getGameSpecificMessage();
        if (gameData.length() > 0) {
            if (gameData.charAt(1) == 'L' && gameData.charAt(0)  == 'L') {

                addSequential(new ZeroElevator());
                addSequential(new ZeroEncoders());
                addSequential(new ZeroYaw());
                addParallel(new Rise());
                addSequential(new DriveCommand(250,0, 0.90, 0.1), 3.5); // 3.5
                addSequential(new Tilt());
                addSequential(new DriveCommand(250, 45, 0.4, 0.6), 1);
                addSequential(new MoveToPos("high"), 1.5);
                addSequential(new DriveCommand( 262, 45, 0.6, 0.1)); //2
                addSequential(new Outtake(0.3));


                addSequential(new Wait(0.1));
                addParallel(new MoveToPos("intake"), 1.5);
                addSequential(new DriveCommand(240, 45, 0.5, 0.1), 1); // 1
                addSequential(new DriveCommand(240, 125, 0.7, 0.6), 2); // 2
                addSequential(new OpenIntake());
                addSequential(new DriveCommand(290, 125, 0.6, 0.1), 2); // 2
                addSequential(new DriveCommand(290, 155, 0.5, 0.35), 3); // 3
                addParallel(new Intake());
                addParallel(new DriveCommand(330, 162, 0.5, 0.35), 3); // 3
                addSequential(new Wait(1));
                addSequential(new MoveToPos("switch"), 0.5);
                addParallel(new DriveCommand(340, 162, 0.6, 0.1));
                addSequential(new Outtake(0.6));





            }

            if (gameData.charAt(1) == 'L' && gameData.charAt(0)  == 'R') {

                addSequential(new ZeroElevator());
                addSequential(new ZeroEncoders());
                addSequential(new ZeroYaw());
                addParallel(new Rise());
                addSequential(new DriveCommand(250 ,0, 0.90, 0.1), 3.5);
                addSequential(new Tilt());
                addSequential(new DriveCommand(250, 45, 0.4, 0.6));
                addSequential(new MoveToPos("high"), 1.5);
                addSequential(new DriveCommand( 265, 45, 0.6, 0.1), 2 );
                addSequential(new Outtake(0.65));

                // spin around code
                addSequential(new DriveCommand(240, 45, 0.5, 0.1), 1);
                addSequential(new MoveToPos("intake"));
                addSequential(new DriveCommand(240, 125, 0.7, 0.6), 4);
                addSequential(new OpenIntake());
                addSequential(new DriveCommand(290, 125, 0.6, 0.1), 2);
                addSequential(new DriveCommand(290, 155, 0.5, 0.35), 3);
                addParallel(new Intake());
                addParallel(new DriveCommand(330-44, 162, 0.6, 0.1 ), 3);
                addSequential(new Wait(2));
                addSequential(new DriveCommand(330-44, 30, 0.4, 0.6));

            }


            if(gameData.charAt(1) == 'R'){
                addSequential(new ZeroElevator());
                addSequential(new ZeroEncoders());
                addSequential(new ZeroYaw());
                addSequential(new DriveCommand(172, 0, 0.90, 0.1), 3);
                addSequential(new DriveCommand(172, 90, 0.4, 0.5), 3);
                addSequential(new DriveCommand ( 386, 90, 0.9, 0.1));
                addSequential(new Tilt());
                addSequential(new DriveCommand(386, -30, 0.4, 0.6), 3);
                addSequential(new MoveToPos("high"), 2);
                addSequential(new DriveCommand(436, -30, 0.5, 0.1));
                addSequential(new Outtake(0.4));
                addSequential(new DriveCommand(406, 0, 0.4, 0.1), 3);
                addSequential(new MoveToPos("intake"));


            }


        }

    }

}
