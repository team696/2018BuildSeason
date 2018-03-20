package frc.team696.autonomousCommands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team696.commands.*;

public class LeftPos extends CommandGroup {

    String gameData;

    public LeftPos() {

        gameData = DriverStation.getInstance().getGameSpecificMessage();
        if (gameData.length() > 0) {
            if (gameData.charAt(0) == 'R') {
//                addSequential(new ZeroElevator());
//                addSequential(new ZeroEncoders());
//                addSequential(new ZeroYaw());
//                addParallel(new Rise());
//                addSequential(new DriveCommand(96, 0, 0.75, 0.1), 3);
////                addSequential(new DriveCommand(138, 30, 0.35, 0.35));
////                addSequential(new Tilt());
//////            addSequential(new Wait(-1));
////                addSequential(new MoveToPos("high"));
//////            addSequential(new Wait(1));
////                addSequential(new Outtake(-1));
//////            addParallel(new OpenIntake());
////                addSequential(new OpenIntake());
////                addSequential(new Wait(1));
////                addSequential(new MoveToPos("intake"));
//                addSequential(new DriveCommand(96, 30, 0.1, 0.5), 2);
//                addSequential(new Tilt());
//                addSequential(new DriveCommand(138, 30, 0.4, 0.05), 2);
////            addSequential(new Wait(-1));
//                addSequential(new MoveToPos("high"), 2);
////            addSequential(new Wait(1));
//                addSequential(new Outtake(-1));
////            addParallel(new OpenIntake());
////                addSequential(new OpenIntake());
//                addSequential(new Wait(1));
//                addSequential(new MoveToPos("intake"));

                addSequential(new ZeroElevator());
                addSequential(new ZeroEncoders());
                addSequential(new ZeroYaw());
                addParallel(new Rise());
                addSequential(new DriveCommand(50, 0, 0.75, 0.1), 3);




            }

            if(gameData.charAt(1) == 'L'){
                addSequential(new ZeroElevator());
                addSequential(new ZeroEncoders());
                addSequential(new ZeroYaw());
                addParallel(new Rise());
                addSequential(new DriveCommand(260, 0,0.80, 0.1));
                addSequential(new DriveCommand(260, 45, 0.35, 0.35));
                addSequential(new DriveCommand(295, 45, 0.50, 0.1));
//                addSequential(new DriveCommand(50,0,0.6,0.1), 2);
//                addSequential(new DriveCommand(50, 90, 0.5, 0.5), 2);
//                addSequential(new MoveToPos("switch"));
//                addSequential(new DriveCommand(70, 90, 0.35, 0.1), 2);
//                addSequential(new Outtake(-0.5));
            }

            }


//            if (gameData.charAt(1) == 'R') {
//                addSequential(new ZeroElevator());
//                addSequential(new ZeroEncoders());
//                addSequential(new ZeroYaw());
//                addParallel(new Rise());
//                addSequential(new DriveCommand(87, 0, 0.75, 0.1));
//                addSequential(new DriveCommand(150, 90, 0.25, 0.35));
//                addSequential(new DriveCommand(208, 90, 0.75, 0.1));
//                addSequential(new DriveCommand(235, 0, 0.35, 0.35));
////            addSequential(new DriveCommand(240, 0, 0.25, 0.1), 3);
////            addSequential(new Tilt());
////            addSequential(new Wait(1));
//                addSequential(new MoveToPos("high"), 2);
//                addSequential(new DriveCommand(250, 0, 0.4, 0.1));
//                addSequential(new Outtake(-0.65));
//                addSequential(new DriveCommand(235, 0, 0.1, 0.1));
//                addSequential(new Wait(1));
//                addSequential(new MoveToPos("intake"));
//                addSequential(new Wait(1));
//
//            }


        }
    }


