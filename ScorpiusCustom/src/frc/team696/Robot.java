/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team696;

import com.kauailabs.nav6.frc.IMU;
import com.kauailabs.nav6.frc.IMUAdvanced;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team696.autonomousCommands.CenterPos;
import frc.team696.autonomousCommands.LeftPos;
import frc.team696.autonomousCommands.RightPos;
import frc.team696.subsystems.DriveTrainSubsystem;

/**
 * @Author: Ismail Hasan
 *
 * Semi-fully custom-made code, this is where I'm doing things I've never done before,
 * while also making the most of my current abilities. I'm going next level.
 *
 * Time and date of writing this comment: 4:48 AM, 2/5/2018
 */

public class Robot extends TimedRobot {

    /*
        Creation of Class Objects
     */

    // TODO Make class objects for the IntakeSubsystem, ElevatorSubsystem,

    public static DriveTrainSubsystem driveTrainSubsystem = new DriveTrainSubsystem(RobotMap.leftRear, RobotMap.leftMid, RobotMap.leftFront,
                                                                                    RobotMap.rightRear, RobotMap.rightMid, RobotMap.rightFront);
    public static Constants constants = new Constants();

    /*
        Object and Variable Declaration
     */

    public static OI oi;

    private Command autonomousCommand;
    private SendableChooser<Command> chooser = new SendableChooser<>();

    public static IMU navX;
    SerialPort port;

    PowerDistributionPanel PDP = new PowerDistributionPanel();

    /*
        Drive Variables
     */

    // Drive Straight and Deadzone variables

    double deadZoneMin = -0.1;
    double deadZoneMax = 0.1;

    int loopNumber = 0;
    double directionError;
    double currentDirection;
    double targetDirection;

    double speed;
    double wheel;
    double leftDrive;
    double rightDrive;

    /*
        RGB Sensor Declaration
     */

//    SerialPort rgbSerial = new SerialPort(SerialPort.Port.kOnboard);
    I2C rgbSensor;
    byte rgbDeviceAddress = 0x29;

    @Override
    public void robotInit() {
        oi = new OI();

        chooser.addObject("Left Position", new LeftPos());
        chooser.addObject("Center Position", new CenterPos());
        chooser.addObject("Right Position", new RightPos());
        chooser.addDefault("Center Position", new CenterPos());

        SmartDashboard.putData("Auto mode", chooser);

        /*
            Initialization of the NavX Gyro
         */

        try {
            byte UpdateRateHz = 50;
            port = new SerialPort(57600, SerialPort.Port.kMXP);
            navX = new IMUAdvanced(port, UpdateRateHz);
        } catch(Exception ex){System.out.println("NavX not working");}

        /*
            Initialization of RGB Sensor
         */

        rgbSensor = new I2C(I2C.Port.kOnboard, rgbDeviceAddress);
        System.out.println(rgbSensor.addressOnly());
        // Unknown if we're able to talk to the RGB sensor, this print out will tell us.
        // True = not talking, False = talking
    }

    @Override
    public void disabledInit() {
        
    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        autonomousCommand = chooser.getSelected();

        if (autonomousCommand != null) {
            autonomousCommand.start();
        }
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {

        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();

        /*
            Drive Functionality
         */

        speed = -OI.Psoc.getRawAxis(constants.psocDriveAxis);
        wheel = OI.wheel.getRawAxis(constants.wheelDriveAxis);

        // Drive Straight Code / Deadzone
        // VERY WIP, DOESN'T FULLY FUNCTION CURRENTLY

        if(wheel > deadZoneMin && wheel < deadZoneMax){

            loopNumber++;
            currentDirection = navX.getYaw();
            if(loopNumber == 1){
                targetDirection = navX.getYaw();
            }
            directionError = targetDirection - currentDirection;
            driveTrainSubsystem.driveStraightPID.setError(directionError);
            wheel = driveTrainSubsystem.driveStraightPID.getValue();

        }else{
            loopNumber = 0;
        }

        leftDrive = speed + wheel;
        rightDrive = speed - wheel;

        driveTrainSubsystem.tankDrive(leftDrive, rightDrive);

    }

    @Override
    public void testPeriodic() {
        
    }
}
