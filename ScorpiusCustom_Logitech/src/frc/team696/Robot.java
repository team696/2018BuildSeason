/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team696;

import com.kauailabs.nav6.frc.IMU;
import com.kauailabs.nav6.frc.IMUAdvanced;
import edu.wpi.cscore.AxisCamera;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team696.autonomousCommands.CenterPos;
import frc.team696.autonomousCommands.LeftPos;
import frc.team696.autonomousCommands.RightPos;
import frc.team696.subsystems.*;
import frc.team696.autonomousCommands.RGBSensorTest;
import frc.team696.subsystems.DriveTrainSubsystem;
import frc.team696.subsystems.IntakeSubsystem;
import frc.team696.subsystems.RGBSensorSubsystem;

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
    public static IntakeSubsystem intakeSubsystem = new IntakeSubsystem(RobotMap.intakeA, RobotMap.intakeB, RobotMap.intakeSol);
    public static ElevatorSubsystem elevatorSubsystem = new ElevatorSubsystem(RobotMap.elevator, RobotMap.elevatorSol, RobotMap.discBrake);
    public static ClimberSubsystem climberSubsystem = new ClimberSubsystem(RobotMap.climberA, RobotMap.climberB, RobotMap.climberSol);


    /*
        Object and Variable Declaration
     */

    public static OI oi;

    private Command autonomousCommand;
    private SendableChooser<Command> chooser = new SendableChooser<>();

    public static IMU navX;
    SerialPort port;

    public Timer time = new Timer();
    public PowerDistributionPanel PDP = new PowerDistributionPanel();

    public Compressor compressor = new Compressor();

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


    /*
        Elevator Variables
     */

    int elevatorLoopNumber = 0;
    boolean runElevator = false;
    boolean oldElevatorState;
    boolean currentElevatorState;
    boolean currentElevatorPosition;
    boolean oldElevatorPosition;
    private boolean moveToSwitch;
    private boolean moveToScale;
    private boolean moveToClimb;
    private boolean moveToGround;
    String moveToPos = "home";

    /*
        Intake Variables
     */

    boolean runIntake = false;
    boolean oldIntakeState;
    boolean currentIntakeState;

    /*
        SPI Testing
     */

    public SPI spiTest = new SPI(SPI.Port.kOnboardCS0);


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
            Start Compressor
         */

        compressor.start();

        /*
            Zero Elevator
         */

        elevatorSubsystem.elevator.setSelectedSensorPosition(0, 0, 20);

        /*
            Camera
         */

        CameraServer.getInstance().addAxisCamera("10.6.96.3");

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
        elevatorSubsystem.homeElevator();

        navX.zeroYaw();

    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();

        /*
            Climber Functions
         */

//        if(OI.Psoc.getRawButton(10)){
//            climberSubsystem.setClimberSpeed(1);
//        }else{
//            climberSubsystem.setClimberSpeed(0);
//        }

        if(elevatorSubsystem.elevator.getSensorCollection().isRevLimitSwitchClosed()){
            elevatorSubsystem.elevator.setSelectedSensorPosition(0, 0, 20);
        }

        /*
            Elevator Functions
         */

        elevatorSubsystem.moveToPos(moveToPos);

        currentElevatorPosition = OI.Psoc.getRawButton(10);

        if(currentElevatorPosition && !oldElevatorPosition){
            moveToSwitch = !moveToSwitch;
        }

        oldElevatorPosition = currentElevatorPosition;

        if(moveToSwitch){
            moveToPos = "switch";
            moveToScale = false;
            moveToClimb = false;
            moveToGround = false;
        }else if(moveToScale){
            moveToPos = "scale";
            moveToSwitch = false;
            moveToClimb = false;
            moveToGround = false;
        }else{
            moveToPos = "home";
            moveToSwitch = false;
            moveToScale = false;
            moveToClimb = false;
            moveToGround = false;
        }

        // Toggle Elevator Solenoid


        /**
         * Checks to see the current boolean state for the elevator button, and compares it to another boolean.
         * The if statement checks to see if the two boolean states are opposite of each other, and if they are, flip
         * the state of the runElevator boolean, which controls the pneumatic solenoid of the elevator.
         * After the check, oldElevatorState is set equal to currentElevatorState to keep it consistent with the robot,
         * and to be able to check again in the future.
         */

        currentElevatorState = OI.Psoc.getRawButton(2);
        if(currentElevatorState && !oldElevatorState){
            runElevator = !runElevator;
        }
        oldElevatorState = currentElevatorState;

        if(runElevator){
            elevatorSubsystem.toggleElevatorPos(true);
        }else{
            elevatorSubsystem.toggleElevatorPos(false);
        }

        /**
         * TESTING DISC BRAKE
         */
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

//        elevatorSubsystem.discBrake.set(OI.Psoc.getRawButton(6));

//        if(OI.Psoc.getRawButton(7)){
//            elevatorLoopNumber++;
//            elevatorSubsystem.discBrake.set(true);
//            if(elevatorLoopNumber > 5){
//                elevatorSubsystem.manualMoveElevator(0.5);
//            }
//        }else if(OI.Psoc.getRawButton(8)){
//            elevatorLoopNumber++;
//            elevatorSubsystem.discBrake.set(true);
//            if(elevatorLoopNumber > 5){
//                elevatorSubsystem.manualMoveElevator(-0.3);
//            }
//        }else{
//            elevatorLoopNumber = 0;
//            elevatorSubsystem.discBrake.set(false);
//            elevatorSubsystem.manualMoveElevator(0);
//        }

        /**
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

        /**
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

        // Logitech Controller

        OI.controlPanel.setOutput(0, true);
        OI.controlPanel.setOutput(1, true);
        OI.controlPanel.setOutput(2, true);
        OI.controlPanel.setOutput(3, true);
        OI.controlPanel.setOutput(5, true);
        OI.controlPanel.setOutput(7, true);
        OI.controlPanel.setOutput(8, true);
        OI.controlPanel.setOutput(9, true);
        OI.controlPanel.setOutput(10, true);
        OI.controlPanel.setOutput(11, true);
        OI.controlPanel.setOutput(12, true);
        OI.controlPanel.setOutput(13, true);
        OI.controlPanel.setOutput(15, true);

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

        driveTrainSubsystem.tankDrive(leftDrive, rightDrive);
//        driveTrainSubsystem.testMotors(0.5);

        /*
            Outputs to console
         */

//        System.out.println("speed                                                                                " + speed);
//        System.out.println("loopNumber = " + (loopNumber) + "                time.get: " + time.get());
        System.out.println(elevatorSubsystem.elevator.getMotorOutputPercent()
//        + "               " + moveToSwitch + "             " + elevatorSubsystem.discBrake.get());
        System.out.println(navX.getYaw());

    }

    @Override
    public void testPeriodic() {

        
    }

}
