package frc.team696.autonomousCommands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team696.commands.*;

import java.sql.Driver;

import static frc.team696.Robot.elevatorSubsystem;

public class CenterPos extends CommandGroup {

    String gameData;

    public CenterPos() {

        gameData = DriverStation.getInstance().getGameSpecificMessage();

//        System.out.println( elevatorSubsystem.elevatorTarget);

        /*
            Switch Left
         */
        if (gameData.length() > 0) {
            if (gameData.charAt(0) == 'R') {
//                addSequential(new ZeroElevator());
//                addSequential(new ZeroEncoders());
//                addSequential(new ZeroYaw());
//                addSequential(new Rise());
////                addParallel(new DriveCommand(10, 0, 0.60, 0.1));
////                addSequential(new DriveCommand(40, -90, 0.35, 0.35), 2);
////                addSequential(new DriveCommand(70, 0, 0.35, 0.30), 2);
////                addSequential(new DriveCommand(75, 0, 0.35, 0.35), 2);
////                addSequential(new Tilt());
////                addSequential(new MoveToPos("switch"));
////                addSequential(new OpenIntake());
////                addSequential(new Outtake(0.5));
////                addSequential(new DriveCommand(65, 30, 0.15, 0.35), 2);
////                addSequential(new MoveToPos("intake"));
//                addSequential(new DriveCommand(10, 0, 0.6, 0.1), 2);
////                addSequential(new Wait(0.5));
//                addSequential(new DriveCommand(10, 45, 0.1, 0.6), 2);
////                addSequential(new Wait(0.5));
//                addSequential(new DriveCommand(70, 45, 0.70, 0.05), 2);
////                addSequential(new Wait(0.5));
//                addSequential(new DriveCommand(70, 0, 0.1, 0.4), 2);
////                addSequential(new Wait(0.5));
////                addSequential(new Tilt());
//                addParallel(new MoveToPos("switch"), 0.5);
////                addSequential(new Wait(0.5));
//                addSequential(new DriveCommand(110, 0, 0.6, 0.5), 2);
//                addSequential(new Wait(0.5));
//                addSequential(new Outtake(0.75));



                addSequential(new ZeroElevator());
                addSequential(new ZeroEncoders());
                addSequential(new ZeroYaw());
//                addSequential(new Rise());
                addParallel(new DriveCommand(10, 0, 0.75, 0.1), 2);
//                addSequential(new Wait(0.5));
                addSequential(new DriveCommand(10, 45, 0.1, 0.50), 2);
//                addSequential(new Wait(0.5));
                addSequential(new DriveCommand(70, 48, 0.75, 0.1), 2);
//                addSequential(new Wait(0.5));
                addSequential(new DriveCommand(70, 0, 0.80, 0.5),2);
//                addSequential(new Wait(0.5));
                addSequential(new DriveCommand(70, 0, 0.90, 0.5), 2);
//                addSequential(new Wait(0.5));
                addParallel(new MoveToPos("switch"), 0.5);
                addSequential(new DriveCommand(115, 0, 0.80, 0.1), 2);
                addSequential(new Wait(0.3));
                addSequential(new Outtake(0.60));



            }

        /*
            Switch Right
         */

            if (gameData.charAt(0) == 'L') {



                addSequential(new ZeroElevator());
                addSequential(new ZeroEncoders());
                addSequential(new ZeroYaw());
//                addSequential(new Rise());
                addParallel(new DriveCommand(10, 0, 0.75, 0.1), 2);
//                addSequential(new Wait(0.5));
                addSequential(new DriveCommand(10, -45, 0.1, 0.70), 2);
//                addSequential(new Wait(0.5));
                addSequential(new DriveCommand(90, -48, 0.90, 0.1), 2);
//                addSequential(new Wait(0.5));
                addSequential(new DriveCommand(90, 0, 0.90, 0.3), 2);
//                addSequential(new Wait(0.5));
                addSequential(new DriveCommand(90, 0, 0.90, 0.5), 2);
//                addSequential(new Wait(0.5));
                addParallel(new MoveToPos("switch"), 0.5);
                addSequential(new DriveCommand(135, 0, 0.80, 0.1), 2);
                addSequential(new Wait(0.3));
                addSequential(new Outtake(0.75));





//
//                addSequential(new ZeroElevator());
//                addSequential(new ZeroEncoders());
//                addSequential(new ZeroYaw());
////                addSequential(new Rise());
//                addParallel(new DriveCommand(10, 0, 0.75, 0.1), 2);
////                addSequential(new Wait(0.5));
//                addSequential(new DriveCommand(10, -70, 0.1, 0.6), 2);
////                addSequential(new Wait(0.5));
//                addSequential(new DriveCommand(80, -70, 0.75, 0.1), 2);
////                addSequential(new Wait(0.5));
//                addSequential(new DriveCommand(80, 0, 0.75, 0.5), 2);
////                addSequential(new Wait(0.5));
//                addSequential(new DriveCommand(80, 0, 0.75, 0.5), 2);
////                addSequential(new Wait(0.5));
//                addParallel(new MoveToPos("switch"), 0.5);
//                addSequential(new DriveCommand(150, 0, 0.7, 0.1), 2);
//                addSequential(new Wait(0.5));
//                addSequential(new Outtake(0.5));
//

//
//                addSequential(new ZeroEncoders());
//                addSequential(new ZeroYaw());
////                addSequential(new Rise());
//                addParallel(new DriveCommand(10, 0, 0.6, 0.1), 2);
////                addSequential(new Wait(0.5));
//                addSequential(new DriveCommand(10, -70, 0.1, 0.6), 2);
////                addSequential(new Wait(0.5));
//                addSequential(new DriveCommand(80, -70, 0.5, 0.1), 2);
////                addSequential(new Wait(0.5));
//                addSequential(new DriveCommand(80, 0, 0.5, 0.5), 2);
////                addSequential(new Wait(0.5));
//                addSequential(new DriveCommand(80, 0, 0.5, 0.5), 2);
////                addSequential(new Wait(0.5));
//                addSequential(new MoveToPos("switch"), 0.5);
//                addSequential(new DriveCommand(150, 0, 0.7, 0.1), 2);
//                addSequential(new Wait(0.5));
//                addSequential(new Outtake(0.5));


            }


        }
    }

}
