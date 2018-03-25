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
                addSequential(new DriveCommand(10, 0, 0.5, 0.1), 2);
                addSequential(new DriveCommand(10, 45, 0.1, 0.60), 2);
                addSequential(new DriveCommand(70, 45, 0.70, 0.1), 2);
                addSequential(new DriveCommand(70, 0, 0.80, 0.5),2);
                addSequential(new DriveCommand(70, 0, 0.90, 0.5), 2);
                addParallel(new MoveToPos("switch"), 0.5);
                addSequential(new DriveCommand(120, 0, 0.70, 0.1), 2);
                addSequential(new Wait(0.3));
                addSequential(new Outtake(0.60));
                addParallel(new MoveToPos("intake"), 0.5);
                addSequential(new DriveCommand(115, 0, 0.6 ,0.1), 0.5);

                /*
             2nd cube
                 */

                addSequential(new DriveCommand(115, 45, 0.5, 0.5));
                addParallel(new Tilt());
                addSequential(new DriveCommand(48, 45,  0.75, 0.1), 2);
                addParallel(new OpenIntake());
                addSequential(new DriveCommand(48, 0, 0.5, 0.5));
                addParallel(new Intake());
                addSequential(new DriveCommand(90, 0, 0.5, 0.6), 2);
                addSequential(new DriveCommand( 60, 0, 0.6, 0.1), 2);
                addSequential(new DriveCommand(60 , 26, 0.5, 0.5), 2);
                addSequential(new Tilt());
                addParallel(new MoveToPos("switch"));
                addSequential(new DriveCommand(130, 26, 0.70, 0.1), 3.5);
//                addSequential(new Wait(0.3));
                addSequential(new Outtake(0.5));
                addSequential(new Wait(0.5));
                addSequential(new DriveCommand(80, 23, 0.5, 0.1));
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
                addSequential(new DriveCommand(10, 0, 0.75, 0.1));
                addSequential(new DriveCommand(10, -45, 0.1, 0.50), 2);
                addSequential(new DriveCommand(85, -45, 0.90, 0.1), 2);
                addSequential(new DriveCommand(85, 0, 0.90, 0.3), 2);
                addSequential(new DriveCommand(90, 0, 0.90, 0.5), 2);
                addParallel(new MoveToPos("switch"), 0.5);
                addSequential(new DriveCommand(135, 0, 0.6, 0.1), 2);
                addSequential(new Wait(0.3));
                addSequential(new Outtake(0.75));
                addSequential(new DriveCommand(130, 0, 0.3, 0.1), 0.3);


                /*
               2nd Cube
                 */

                addParallel(new MoveToPos("intake"), 0.5);
                addSequential(new DriveCommand(130, -35, 0.5, 0.5), 0.4);
                addSequential(new Tilt());
                addSequential(new DriveCommand(50, -35,  0.75, 0.1), 2);
                addParallel(new OpenIntake());
                addSequential(new DriveCommand(50, 0, 0.5, 0.5), 2);
                addSequential(new DriveCommand(90, 0, 0.5, 0.6), 2);
                addParallel(new Intake());
                addSequential(new Wait(1));
                addSequential(new DriveCommand( 60, 0, 0.6, 0.1), 2);
                addSequential(new DriveCommand(60 , -30, 0.5, 0.5), 2);
                addSequential(new Tilt());
                addParallel(new MoveToPos("switch"), 1);
                addSequential(new DriveCommand(135, -30, 0.6, 0.1), 3.5);
                addSequential(new Outtake(0.4));
                addSequential(new Wait(0.75));
                addSequential(new DriveCommand(80, -23, 0.5, 0.1));
                addSequential(new DriveCommand(80, 23, 0.5, 0.5));


            }


        }
    }

}
