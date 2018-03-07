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

    // TODO Make class object(s) for the LEDSubsystem, finish DriveStraight code, and tune PID.

    public static DriveTrainSubsystem driveTrainSubsystem = new DriveTrainSubsystem(RobotMap.leftRear, RobotMap.leftMid, RobotMap.leftFront,
                                                                                    RobotMap.rightRear, RobotMap.rightMid, RobotMap.rightFront);
    public static Constants constants = new Constants();
    public static RGBSensorSubsystem rgbSensorSubsystem = new RGBSensorSubsystem(RobotMap.deviceAddress);
    public static IntakeSubsystem intakeSubsystem = new IntakeSubsystem(RobotMap.intakeA, RobotMap.intakeB, RobotMap.intakeSol);
    public static ElevatorSubsystem elevatorSubsystem = new ElevatorSubsystem(RobotMap.elevator, RobotMap.elevatorSol, RobotMap.discBrake);
    public static ClimberSubsystem climberSubsystem = new ClimberSubsystem(RobotMap.climberA, RobotMap.climberB, RobotMap.climberSol);
    public static AntiTiltSubsystem antiTiltSubsystem = new AntiTiltSubsystem();


    /** Object and Variable Declaration **/

    public static OI oi;
    private Command autonomousCommand;
    private SendableChooser<Command> chooser = new SendableChooser<>();
    public static IMU navX;
    SerialPort port;
    public Timer time = new Timer();
    public PowerDistributionPanel PDP = new PowerDistributionPanel();

    /** Drive Variables **/

    // Drive Straight and Deadzone variables

    double wheelDeadZoneMin = -0.1;
    double wheelDeadZoneMax = 0.1;
    double stickDeadZoneMin = -0.25;
    double stickDeadZoneMax = 0.3;

    int loopNumber = 0;
    double directionError;
    double currentDirection;
    double targetDirection;

    public double speed;
    public double wheel;
    double leftDrive;
    double rightDrive;

    // Speed Turn Scale Variables

    double a = 0.170641; // 0.286095    0.170641
    double h = -0.258475; // -0.243151  -0.258475
    double k = 0.364407; // -0.130137    0.364407

    // Nora's currently preferred values for turn scale

    double speedTurnScale;

    // Ramping Variables

    double lowElevatorRampRate = 0.05;
    double highElevatorRampRate = 0.02;
    double centerElevatorRampRate = 0.05;
    double forwardRampingRate = 0.03;
    double commandedSpeed;
    double elevatorMaxHeight = 70;
    double minimumSpeed = -0.3;
    double elevatorActuatorLoopNumber;

    /*
        Compressor
     */

    public Compressor compressor = new Compressor();

    /*
        Elevator Variables
     */

    int elevatorLoopNumber = 0;
    public boolean runElevator = false;
    public boolean oldElevatorState;
    public boolean currentElevatorState;
    double elevatorPositionInches;
    boolean elevatorSolState;
    double maxForwardSpeed = 0.5;
    double maxActuatingSpeed = 0.8;
    public boolean isHomed = false;
    int homeLoopNumber;

    public boolean currentHome;
    public boolean oldHome;

    /*
        Intake Variables
     */

    public boolean runIntake = false;
    public boolean oldIntakeState;
    public boolean currentIntakeState;
    double intakeOutputValue;

    /*
        SPI Testing
     */

    public SPI spiTest = new SPI(SPI.Port.kOnboardCS0);

    /*
        Anti-Tilt Variables
     */

    public boolean antiTilt = true;

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

    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();


        /**
         * Homing Button
         */


        /** Drive Functionality **/


        if(wheel < 0){
            wheel = wheel - wheelDeadZoneMax;
        }else{
            wheel = wheel + wheelDeadZoneMax;
        }

//        if(OI.wheel.getRawButton(2)){
//            antiTilt = false;
//        }else{
//            antiTilt = true;
//        }
//
//        if(antiTilt){
//            antiTiltSubsystem.antiTilt();
//        }

        speedTurnScale = a*(1/((speed*speed)-h))+k;
        speed = antiTiltSubsystem.speed;
        wheel = (antiTiltSubsystem.wheel * speedTurnScale) - wheelDeadZoneMax;


        /** RGB Sensor **/

//        rgbSensorSubsystem.rgbGetLux();


        /** Climber Functions **/


        /** Auto Climb and Unwind - Once pressed: Hook will deploy, Elevator will retract, and hold to keep climbing **/


        if(OI.ControlPanel.getRawButton(1)){
            climberSubsystem.autoClimb(0.75);
        }else if (OI.ControlPanel.getRawButton(15)) {
            climberSubsystem.setClimberSpeed(-0.25);
        } else {
            climberSubsystem.loopNumber = 0;
            climberSubsystem.setClimberSpeed(0);
        }
        /** Elevator Functions **/

        // Toggle Elevator Solenoid

        /**
         * Checks to see the current boolean state for the elevator button, and compares it to another boolean, oldElevatorState.
         * The if statement checks to see if the two boolean states are opposite of each other, and if they are, flip
         * the state of the runElevator boolean, which controls the pneumatic solenoid of the elevator.
         * After the check, oldElevatorState is set equal to currentElevatorState to keep it consistent with the robot,
         * and to be able to check again in the future.
         */

        currentElevatorState = OI.ControlPanel.getRawButton(6);
        if(antiTiltSubsystem.preventBack) {
            runElevator = true;
        }else if(antiTiltSubsystem.preventForward){
            runElevator = false;
        }else if(currentElevatorState && !oldElevatorState){
            runElevator = !runElevator;
        }
        oldElevatorState = currentElevatorState;


        if(runElevator){
            elevatorSubsystem.toggleElevatorPos(true);
        }else{
            elevatorSubsystem.toggleElevatorPos(false);
        }

        /**
         *  Manual-Move Elevator
         */

        if(!isHomed){
            homeLoopNumber++;
            elevatorSubsystem.discBrake.set(true);
            if(homeLoopNumber > 2){
                elevatorSubsystem.manualMoveElevator(-0.1);
                System.out.println("Homing...");
                if(elevatorSubsystem.elevator.getSensorCollection().isRevLimitSwitchClosed()){
                    elevatorSubsystem.discBrake.set(false);
                    elevatorSubsystem.manualMoveElevator(0);
                    elevatorSubsystem.elevator.setSelectedSensorPosition(0, 0, 20);
                    System.out.println("Homed");
                    isHomed = true;
                }
            }
        }else{
            homeLoopNumber = 0;
        }
        if(OI.ControlPanel.getRawAxis(0) < -0.1 && isHomed){
            elevatorLoopNumber++;
            elevatorSubsystem.discBrake.set(true);
                if(elevatorLoopNumber > 2){
                    elevatorSubsystem.manualMoveElevator(-OI.ControlPanel.getRawAxis(0));
            }
        }else if(OI.ControlPanel.getRawAxis(0) > 0.1 && isHomed){
            elevatorLoopNumber++;
            elevatorSubsystem.discBrake.set(true);
                if(elevatorLoopNumber > 2){
                    elevatorSubsystem.manualMoveElevator(-OI.ControlPanel.getRawAxis(0) * 0.5);
            }
        }else{
            elevatorLoopNumber = 0;
//            elevatorSubsystem.discBrake.set(false);   already set in auto-climb, would cause overlaps.
            elevatorSubsystem.manualMoveElevator(0);
            elevatorSubsystem.discBrake.set(false);
        }

        /** Intake Functions **/

        if(OI.ControlPanel.getRawButton(7)){
            intakeSubsystem.runIntake(0.8);
        }else if(OI.ControlPanel.getRawButton(8)){
            intakeSubsystem.runIntake(-0.8);
        }else{
            intakeSubsystem.runIntake(0);
        }

        currentIntakeState = OI.ControlPanel.getRawButton(12);
        if(currentIntakeState && !oldIntakeState){
            runIntake = !runIntake;
        }
        oldIntakeState = currentIntakeState;

        if(runIntake){
            intakeSubsystem.toggleIntake(true);
        }else{
            intakeSubsystem.toggleIntake(false);
        }




        /** Any Testing/Temporary Code will Go here **/












        /** VERY WIP, DOESN'T FULLY FUNCTION CURRENTLY
            Drive-straight and Testing code.
                                                      **/

        if(wheel >= wheelDeadZoneMin && wheel <= wheelDeadZoneMax){

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

//        /**
//         * Speed Deadzone
//         */
//
//        if(speed >= stickDeadZoneMin && speed <= stickDeadZoneMax){
//            speed = 0;
//        }






        leftDrive = speed + wheel;
        rightDrive = speed - wheel;

        driveTrainSubsystem.tankDrive(leftDrive, rightDrive);

        /** Outputs to Console **/

//        System.out.println("speed                                                                                " + speed);
//        System.out.println("loopNumber = " + (loopNumber) + "                time.get: " + time.get());
//        System.out.println(elevatorSubsystem.elevatorSol.get() + "              " + speed);
//        System.out.println(elevatorLoopNumber);
//        System.out.println(runElevator);
//        System.out.println("intakeOutputValue = " + intakeOutputValue);
//        System.out.println(elevatorSubsystem.elevator.getSelectedSensorPosition(0));
        System.out.println(isHomed + "      " + elevatorSubsystem.elevator.getSelectedSensorPosition(0) + "          " + elevatorLoopNumber);

    }

    @Override
    public void testPeriodic() {

        
    }

}
