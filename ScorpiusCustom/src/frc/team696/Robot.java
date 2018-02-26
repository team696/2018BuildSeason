/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team696;

import com.kauailabs.nav6.frc.IMU;
import com.kauailabs.nav6.frc.IMUAdvanced;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team696.autonomousCommands.CenterPos;
import frc.team696.autonomousCommands.LeftPos;
import frc.team696.autonomousCommands.RightPos;
import frc.team696.autonomousCommands.RGBSensorTest;
import frc.team696.subsystems.DriveTrainSubsystem;
import frc.team696.subsystems.IntakeSubsystem;
import frc.team696.subsystems.RGBSensorSubsystem;
import frc.team696.subsystems.*;

/**
 * @Author: Ismail Hasan && Justin Gonzales
 *
 * Semi-fully custom-made code, this is where I'm doing things I've never done before,
 * while also making the most of my current abilities. I'm going next level.
 *
 * Time and date of writing this comment: 4:48 AM, 2/5/2018
 *
 */

public class Robot extends TimedRobot {

    /*
        Creation of Class Objects
     */

    // TODO Make class object(s) for the ElevatorSubsystem, finish DriveStraight code

    public static DriveTrainSubsystem driveTrainSubsystem = new DriveTrainSubsystem(RobotMap.leftRear, RobotMap.leftMid, RobotMap.leftFront,
                                                                                    RobotMap.rightRear, RobotMap.rightMid, RobotMap.rightFront);
    public static Constants constants = new Constants();
    public static RGBSensorSubsystem rgbSensorSubsystem = new RGBSensorSubsystem(RobotMap.deviceAddress);
//    public static final JustinElevator justinElevator = new JustinElevator(RobotMap.Elevator);
    public static IntakeSubsystem intakeSubsystem = new IntakeSubsystem(RobotMap.intakeA, RobotMap.intakeB, RobotMap.intakeSol);
    public static ElevatorSubsystem elevatorSubsystem = new ElevatorSubsystem(RobotMap.elevator, RobotMap.elevatorSol, RobotMap.discBrake);


    /*
        Object and Variable Declaration
     */

    public static OI oi;

    private Command autonomousCommand;
    private SendableChooser<Command> chooser = new SendableChooser<>();

    public static IMU navX;
    SerialPort port;

    PowerDistributionPanel PDP = new PowerDistributionPanel();
    public Timer time = new Timer();

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

    // Speed Turn Scale Variables

    double a = 0.170641; // 0.286095    0.170641
    double h = -0.258475; // -0.243151  -0.258475
    double k = 0.364407; // -0.130137    0.364407

    // Nora's currently preferred values for turn scale

    double speedTurnScale;

    /*
        Compressor
     */

    public Compressor compressor = new Compressor();

    /*
        Elevator Variables
     */

    int elevatorLoopNumber = 0;
    boolean runElevator = false;
    boolean oldElevatorState;
    boolean currentElevatorState;

    /*
        Intake Variables
     */

    boolean runIntake = false;
    boolean oldIntakeState;
    boolean currentIntakeState;


    @Override
    public void robotInit() {
        oi = new OI();

        chooser.addObject("Left Position", new LeftPos());
        chooser.addObject("Center Position", new CenterPos());
        chooser.addObject("Right Position", new RightPos());
        chooser.addObject("Test Taper", new RGBSensorTest());
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
            Zero Elevator
         */

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

        rgbSensorSubsystem.rgbSensor.write(0xC0, 1); // set Integration Time
        rgbSensorSubsystem.rgbSensor.write(0x02, 1); // set Gain

        /*
            Start Compressor
         */

        compressor.start();
        time.start();

//        elevatorSubsystem.homeElevator();
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();


        rgbSensorSubsystem.rgbGetLux();
        /*
            Elevator Functions
         */

        // Toggle Elevator Solenoid

        currentElevatorState = OI.Psoc.getRawButton(2);
        if(currentElevatorState && !oldElevatorState){
            runElevator = !runElevator;
        }
        oldElevatorState = currentElevatorState;
//        if(OI.Psoc.getRawButton(2) && !oldPsoc[2])runElevator = true;              // Adjusted for Logitech Controller
//        if(!OI.Psoc.getRawButton(2) && oldPsoc[2])runElevator = false;

        if(runElevator){
            elevatorSubsystem.toggleElevatorPos(true);
        }else{
            elevatorSubsystem.toggleElevatorPos(false);
        }

        if(OI.Psoc.getRawButton(7)){
            elevatorLoopNumber++;
            elevatorSubsystem.discBrake.set(true);
            elevatorSubsystem.manualMoveElevator(0.5);
        }else if(OI.Psoc.getRawButton(8)){
            elevatorLoopNumber++;
            elevatorSubsystem.discBrake.set(true);
            elevatorSubsystem.manualMoveElevator(-0.5);
        }else{
            elevatorLoopNumber = 0;
            elevatorSubsystem.discBrake.set(false);
            elevatorSubsystem.manualMoveElevator(0);
        }

//        elevatorSubsystem.manualMoveElevator(OI.Psoc.getRawAxis(1));

        /*
            Run Intake (Forward/Backward)
         */

        if(OI.Psoc.getRawButton(4)){                           // Adjusted for Logitech Controller
            intakeSubsystem.runIntake(0.6);
        }else if(OI.Psoc.getRawButton(3)){
            intakeSubsystem.runIntake(-0.5);
        }else{
            intakeSubsystem.runIntake(0);
        }

        currentIntakeState = OI.Psoc.getRawButton(1);
        if(currentIntakeState && !oldIntakeState){
            runIntake = !runIntake;
        }
        oldIntakeState = currentIntakeState;

        if(runIntake){
            intakeSubsystem.toggleIntake(true);
        }else{
            intakeSubsystem.toggleIntake(false);
        }

        /*
            Speed Turn Scale Stuff
         */

//        speedTurnScale = a*(1/((speed*speed)-h))+k;

        /*
            Drive Functionality
         */

        if(wheel < 0){
            wheel = wheel - deadZoneMax;
        }else{
            wheel = wheel + deadZoneMax;
        }
        speedTurnScale = a*(1/((speed*speed)-h))+k;
//        speed = -OI.Psoc.getRawAxis(constants.psocDriveAxis);
//        wheel = (OI.wheel.getRawAxis(constants.wheelDriveAxis) * speedTurnScale) - deadZoneMax;
        speed = -OI.Psoc.getRawAxis(1);
        wheel = OI.Psoc.getRawAxis(2);

        // Drive Straight Code / Deadzone
        /** VERY WIP, DOESN'T FULLY FUNCTION CURRENTLY */

        if(wheel > deadZoneMin && wheel < deadZoneMax){

//            loopNumber++;
//            currentDirection = navX.getYaw();
//            if(loopNumber == 1){
//                targetDirection = navX.getYaw();
//            }
//            directionError = targetDirection - currentDirection;
//            driveTrainSubsystem.driveStraightPID.setError(directionError);
//            wheel = driveTrainSubsystem.driveStraightPID.getValue();
            wheel = 0;

        }else{
            loopNumber = 0;
        }

        leftDrive = speed + wheel;
        rightDrive = speed - wheel;

//        System.out.println(rgbSensor.addressOnly());

//        rgbSensorSubsystem.getRawData();

//        System.out.println(wheel);
//        rgbSensorSubsystem.rgbGetLux();
//        rgbSensorSubsystem.rgbGetLux();

        driveTrainSubsystem.tankDrive(leftDrive, rightDrive);


        /*
            Get Old Button Values
         */

        /*
            Outputs to console
         */

//        System.out.println("speed                                                                                " + speed);
//        System.out.println("loopNumber = " + (loopNumber) + "                time.get: " + time.get());

    }

    @Override
    public void testPeriodic() {

        
    }

}
