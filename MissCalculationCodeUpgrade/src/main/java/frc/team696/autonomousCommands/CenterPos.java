package frc.team696.autonomousCommands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team696.commands.*;

public class CenterPos extends CommandGroup {
    
    String gameData;

    public CenterPos() {

        gameData = DriverStation.getInstance().getGameSpecificMessage();

//        System.out.println( elevatorSubsystem.elevatorTarget);

        /*
            Switch Left WORKING
         */
        if (gameData.length() > 0) {
            if (gameData.charAt(0) == 'R') {
                addSequential(new ZeroElevator());
                addSequential(new ZeroEncoders());
                addSequential(new ZeroYaw());
                addSequential(new Rise(), 0.5);
//                addParallel(new DriveCommand(10, 0, 0.60, 0.1));
//                addSequential(new DriveCommand(40, -90, 0.35, 0.35), 2);
//                addSequential(new DriveCommand(70, 0, 0.35, 0.30), 2);
//                addSequential(new DriveCommand(75, 0, 0.35, 0.35), 2);
//                addSequential(new Tilt());
//                addSequential(new MoveToPos("switch"));
//                addSequential(new OpenIntake());
//                addSequential(new Outtake(0.5));
//                addSequential(new DriveCommand(65, 30, 0.15, 0.35), 2);
//                addSequential(new MoveToPos("intake"));
                addSequential(new DriveCommand(10, 0, 0.9, 0.1), 0.5);
//                addSequential(new Wait(0.5));
                addSequential(new DriveCommand(10, 50, 0.1, 0.7), 1);
//                addSequential(new Wait(0.5));
                addSequential(new DriveCommand(85, 50, 0.7, 0.1));
//                addSequential(new Wait(0.5));
                addSequential(new DriveCommand(85, 0, 0.1, 0.6), 1);
//                addSequential(new Wait(0.5));
//                addSequential(new Tilt());
                addSequential(new MoveToPos("switch"), 1);
//                addSequential(new Wait(0.5));
                addParallel(new DriveCommand(125, 0, 0.8, 0.5));
                addSequential(new Wait(0.5));
                addSequential(new Outtake(1), 1);
                addSequential(new DriveCommand(85, 0, 0.7, 0.1));
                addSequential(new DriveCommand(85, 50, 0.1, 0.5), 1);
                addSequential(new DriveCommand(35, 50, 0.6, 0.1));
                addSequential(new DriveCommand(35, 0, 0.1, 0.5), 1);
                addParallel(new Intake());


            }

        /*
            Switch Right WORKING
         */

            if (gameData.charAt(0) == 'L') {
                addSequential(new ZeroElevator());
                addSequential(new ZeroEncoders());
                addSequential(new ZeroYaw());
                addSequential(new Rise(), 0.5);
//                addSequential(new DriveCommand(45, 45, 0.55, 0.35));
//                addSequential(new Wait(1));
//                addSequential(new DriveCommand(60, 0, 0.25, 0.35));
//                addSequential(new Tilt());
//                addSequential(new MoveToPos("switch"));
//
////            addSequential(new Wait(1));
//                addSequential(new OpenIntake());
//                addSequential(new Outtake(0.5));
////            addParallel(new Wait(1));
//                addSequential(new DriveCommand(40, 0, 0.15, 0.35));
//                addParallel(new MoveToPos("intake"));
                addSequential(new DriveCommand(10, 0, 0.9, 0.1), 0.5);
//                addSequential(new Wait(0.5));
                addSequential(new DriveCommand(10, -70, 0.1, 0.5), 1);
//                addSequential(new Wait(0.5));
                addSequential(new DriveCommand(80, -70, 0.7, 0.1), 1.5);
//                addSequential(new Wait(0.5));
                addSequential(new DriveCommand(80, 0, 0.1, 0.5), 0.5);
//                addSequential(new Wait(0.5));
//                addSequential(new DriveCommand(70, 0, 0.1, 0.7), 0.5);
//                addSequential(new Wait(0.5));
                addSequential(new MoveToPos("switch"), 0.5);
                addSequential(new DriveCommand(150, 0, 0.75, 0.1), 1);
//                addSequential(new Wait(0.5));
                addSequential(new Outtake(0.75));

            }

            if(gameData.equals("Test")){
                addSequential(new ZeroElevator());
                addSequential(new ZeroEncoders());
                addSequential(new ZeroYaw());
//                addSequential(new Rise());
                addSequential(new DriveCommand(0, 45, 0, 0.6));
            }


            if (gameData.equals("BigYay")) {
                addSequential(new ZeroElevator());
                addSequential(new ZeroEncoders());
                addSequential(new ZeroYaw());

                addSequential(new DriveCommand(5, 90 , 0.8, 0.8));
            }

//        addSequential(new DriveCommand( 65, 0, 0.65, 0.4));
//        addSequential(new Wait(2));
//        addSequential(new Outtake(), 2);


//        addSequential(new DriveCommand(70, 0, 0.35, 0.25 ));
//        addSequential(new DriveCommand(70))
//        addSequential(new DriveCommand(65, 0,0.5, 0.35));
//        addSequential(new DriveCommand(40, -90, 0.30, 0.30), 4);
//        addSequential(new DriveCommand( 60, 0, 0.25, 0.45));


//        addSequential(new OpenIntake());
//        addSequential(new Intake());

        }
    }

}
