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


        if (gameData.length() > 0) {
            if (gameData.charAt(0) == 'R') {

                addSequential(new ZeroElevator());
                addSequential(new ZeroEncoders());
                addSequential(new ZeroYaw());
                addParallel(new Rise());
                addSequential(new DriveCommand(10, 0, 0.75, 0.1), 0.5);
                addSequential(new DriveCommand(10, 45, 0.1, 0.60), 2);
                addSequential(new DriveCommand(70, 45, 0.75, 0.1), 2);
                addSequential(new DriveCommand(70, 0, 0.80, 0.6),2);
                addSequential(new DriveCommand(70, 0, 0.90, 0.6), 2);
                addParallel(new MoveToPos("switch"), 0.5);
                addSequential(new DriveCommand(125, 0, 0.50, 0.1), 2);
                addSequential(new Wait(0.3));
                addSequential(new Outtake(0.60));
                addParallel(new MoveToPos("intake"), 0.5);
                addSequential(new DriveCommand(115, 0, 0.6 ,0.1), 0.5);

                /*
             2nd cube
                 */

                addParallel(new MoveToPos("intake"), 0.7);
                addSequential(new DriveCommand(110, 0, 0.5, 0.1), 2);
                addSequential(new DriveCommand(110, 45, 0.5, 0.7), 2);
                addParallel(new Tilt());
                addSequential(new DriveCommand(45, 45,  0.75, 0.1), 2);
                addParallel(new OpenIntake());
                addSequential(new DriveCommand(45, 0, 0.5, 0.6), 2);
                addSequential(new DriveCommand(90, 0, 0.5, 0.6), 2);
                addParallel(new Intake());
//                addSequential(new Wait(1));
                addSequential(new DriveCommand( 58, 0, 0.6, 0.1), 2);
                addSequential(new DriveCommand(58 , 45, 0.5, 0.6), 2);
                addSequential(new Tilt());
                addParallel(new MoveToPos("switch"), 0.5);
                addSequential(new DriveCommand(120, 20, 0.60, 0.1), 3.5);
                addSequential(new Wait(0.3));
                addSequential(new Outtake(0.5));
                addSequential(new Wait(0.5));
                addSequential(new DriveCommand(80, 35, 0.5, 0.1));
//                addParallel(new MoveToPos("intake"));
                addSequential(new DriveCommand(80, 0, 0.5, 0.5));


//

            }

        /*
            Switch Left
         */

            if (gameData.charAt(0) == 'L') {



                addSequential(new ZeroElevator());
                addSequential(new ZeroEncoders());
                addSequential(new ZeroYaw());
                addParallel(new Rise());
                addSequential(new DriveCommand(10, 0, 0.75, 0.1), 0.5);
                addSequential(new DriveCommand(10, -45, 0.1, 0.60), 2);
                addSequential(new DriveCommand(77, -45, 0.90, 0.1), 2);
                addSequential(new DriveCommand(77, 0, 0.90, 0.5), 2);
                addSequential(new DriveCommand(77, 0, 0.90, 0.5), 2);
                addParallel(new MoveToPos("switch"), 0.5);
                addSequential(new DriveCommand(135, 0, 0.50, 0.1), 2);
//                addSequential(new Wait(0.3));
                addSequential(new Outtake(0.75));
                addSequential(new DriveCommand(130, 0, 0.3, 0.1), 0.3);


                /*
               2nd Cube
                 */

                addParallel(new MoveToPos("intake"), 0.5);
                addSequential(new DriveCommand(135, -45, 0.5, 0.7), 2);
                addParallel(new Tilt());
                addSequential(new DriveCommand(60, -45,  0.75, 0.1), 2);
                addParallel(new OpenIntake());
                addSequential(new DriveCommand(50, 0, 0.5, 0.5), 2);
                addSequential(new DriveCommand(90, 0, 0.5, 0.6), 2);
                addParallel(new Intake());
                addSequential(new Intake(), 0.5);
//                addSequential(new Wait(1));
                addSequential(new DriveCommand( 60, 0, 0.6, 0.1), 2);
                addSequential(new DriveCommand(60, -35, 0.5, 0.5), 2);
                addSequential(new Tilt());
                addParallel(new MoveToPos("switch"), 1);
                addSequential(new DriveCommand(135, -23, 0.75, 0.1), 3.5);
                addSequential(new Outtake(0.5));
                addSequential(new Wait(0.75));
                addSequential(new DriveCommand(125, -23, 0.5, 0.1));
                addSequential(new DriveCommand(125, 23, 0.5, 0.5));


            }


        }
    }

}
