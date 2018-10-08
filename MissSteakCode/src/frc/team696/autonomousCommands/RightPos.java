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

//                addSequential(new ZeroElevator());
//                addSequential(new ZeroEncoders());
//                addSequential(new ZeroYaw());
//                addParallel(new Rise());
//                addSequential(new DriveCommand(135, 0, 0.75, 0.05));
//                addSequential(new Wait(1));
//                addSequential(new DriveCommand(135, -45, 0.25, 0.45));
//                addSequential(new Wait(1));
//                addSequential(new DriveCommand(140, -45, 0.35, 0.35));
//                addSequential(new Tilt());
////            addSequential(new Wait(-1));
//                addSequential(new MoveToPos("high"));
////            addSequential(new Wait(1));
//                addSequential(new Outtake(-1));
////            addParallel(new OpenIntake());
//                addSequential(new OpenIntake());
//                addSequential(new Wait(1));
//                addSequential(new MoveToPos("intake"));

                addSequential(new ZeroElevator());
                addSequential(new ZeroEncoders());
                addSequential(new ZeroYaw());
                addParallel(new Rise());
                addSequential(new DriveCommand(240,0, 0.90, 0.1), 3.5); // 3.5
                addSequential(new Tilt());
                addSequential(new DriveCommand(240, -45, 0.4, 0.6));
                addSequential(new MoveToPos("high"), 1.5);
                addSequential(new DriveCommand( 270, -45, 0.6, 0.1), 2); //2
                addSequential(new Outtake(0.5));

                addSequential(new DriveCommand(240, -45, 0.5, 0.1), 1); // 1
                addSequential(new MoveToPos("intake"));
                addSequential(new DriveCommand(240, -125, 0.7, 0.6), 2); // 2
                addSequential(new OpenIntake());
                addSequential(new DriveCommand(290, -125, 0.6, 0.1), 2); // 2
                addSequential(new DriveCommand(290, -155, 0.5, 0.35), 3); // 3
                addParallel(new Intake());
                addParallel(new DriveCommand(330, -162, 0.5, 0.1 ), 3); // 3
                addSequential(new Wait(1));
                addParallel(new DriveCommand(325, -162, 0.5, 0.1), 0.5);
                addSequential(new MoveToPos("switch"));
                addSequential(new DriveCommand(340, -162, 0.6, 0.1), 0.5);
                addSequential(new Outtake(0.6));


            }

            if (gameData.charAt(1) == 'L' ) {

                addSequential(new ZeroElevator());
                addSequential(new ZeroEncoders());
                addSequential(new ZeroYaw());
                addSequential(new DriveCommand(172, 0, 0.90, 0.1), 3);
                addSequential(new DriveCommand(172, -90, 0.4, 0.5), 3);
                addSequential(new DriveCommand (402, -90, 0.9, 0.1));
                addSequential(new Tilt());
                addSequential(new DriveCommand(402, 20, 0.4, 0.6));
                addSequential(new MoveToPos("high"), 2);
                addSequential(new DriveCommand(446, 20, 0.5, 0.1), 2);
                addSequential(new Outtake(0.4));
                addSequential(new DriveCommand(406, 0, 0.4, 0.1), 3);
                addSequential(new MoveToPos("intake"));






            }

            if(gameData.charAt(1) == 'R') {

                addSequential(new ZeroElevator());
                addSequential(new ZeroEncoders());
                addSequential(new ZeroYaw());
                addParallel(new Rise());
                addSequential(new DriveCommand(240,0, 0.90, 0.1), 3.5); // 3.5
                addSequential(new Tilt());
                addSequential(new DriveCommand(240, -45, 0.4, 0.6));
                addSequential(new MoveToPos("high"), 1.5);
                addSequential(new DriveCommand( 270, -45, 0.6, 0.1), 2); //2
                addSequential(new Outtake(0.5));

            }
        }
    }

}
