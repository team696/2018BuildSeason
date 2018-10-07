/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team696;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.kauailabs.nav6.frc.IMU;
import com.kauailabs.nav6.frc.IMUAdvanced;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team696.autonomousCommands.*;
import frc.team696.subsystems.*;
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
    public static AutonomousSubsystem autonomousSubsystem = new AutonomousSubsystem(0);
    public static AnalogInput ultra = new AnalogInput(0);





    /** Object and Variable Declaration **/

    public static OI oi;
    private String autonomousCommand;
    private SendableChooser<String> chooser = new SendableChooser<>();
    public static IMU navX;
    SerialPort port;
    public Timer time = new Timer();
    public static PowerDistributionPanel PDP = new PowerDistributionPanel();

    /** Drive Variables **/

    // Drive Straight and Deadzone variables

    double wheelDeadZoneMin = -0.14;
    double wheelDeadZoneMax = 0.14;
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

    boolean oldWheelAntiTilt;
    boolean wheelAntiTilt;
    boolean toggleWheelAntiTilt = true;

    // Speed Turn Scale Variables

    //analog


//    double ultrasonicVoltage = ultra.getVoltage();

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

    /* Elevator Positioning Booleans */

    public boolean lowButton;
    public boolean lowButtonOld;
    public boolean midButton;
    public boolean midButtonOld;
    public boolean highButton;
    public boolean highButtonOld;
    public boolean switchButton;
    public boolean switchButtonOld;
    public boolean intakeButton;
    public boolean intakeButtonOld;
    public boolean climbButton;
    public boolean climbButtonOld;
    public boolean moveLow;
    public boolean moveMid;
    public boolean moveHigh;
    public boolean moveSwitch;
    public boolean moveIntake;
    public boolean moveClimb;

    /*
        Compressor
     */

    public Compressor compressor = new Compressor();

    /*
        Elevator  Variables
     */

    int elevatorLoopNumber = 0;
    double elevatorPositionInches;
    boolean elevatorSolState;
    double maxForwardSpeed = 0.5;
    double maxActuatingSpeed = 0.8;
    public boolean isHomed = true;
    String controlMode;
    int homeLoopNumber;

    public boolean runElevator;
    public boolean currentElevatorState;
    public boolean oldElevatorState;

    public boolean homeState;
    public boolean oldHomeState;



    /*
        Intake Variables
     */

    boolean intakeSolButton;
    boolean oldIntakeSolButton;
    boolean toggleIntake;
    double intakeSpeed = 0.7;
    double intakeOutputValue;

    /*
        SPI Testing
     */

    public SPI spiTest = new SPI(SPI.Port.kOnboardCS0);

    /*
        Anti-Tilt Variables
     */

    public boolean antiTilt = false;

    @Override
    public void robotInit() {
        oi = new OI();

        chooser.addObject("Left Position","LeftPos");
        chooser.addObject("Center Position", "CenterPos");
        chooser.addObject("Right Position", "RightPos");
        chooser.addDefault("Center Position", "CenterPos");
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

        /*
            Camera
         */

        CameraServer.getInstance().addAxisCamera("10.6.96.11");

        /*
            Turn on LEDS
         */




    }

    @Override
    public void disabledInit() {
        
    }

    @Override
    public void disabledPeriodic() {

        Scheduler.getInstance().run();
        SmartDashboard.putString("Selected Auto", chooser.getSelected());
    }

    @Override
    public void autonomousInit() {
        autonomousCommand = chooser.getSelected();
        if(autonomousCommand.equals("LeftPos")){
            new LeftPos().start();
        }

        if(autonomousCommand.equals("CenterPos")){
            new CenterPos().start();
        }

        if(autonomousCommand.equals("RightPos")){
            new RightPos().start();
        }

//        if (autonomousCommand != null) {
//            autonomousCommand.start();
//        }
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {

//        if (autonomousCommand != null) {
//            autonomousCommand.cancel();
//        }

        rgbSensorSubsystem.rgbSensor.write(0xC0, 1); // set Integration Time
        rgbSensorSubsystem.rgbSensor.write(0x02, 1); // set Gain

        /*
            Start Compressor
         */

        compressor.start();
        time.start();
        navX.zeroYaw();

        driveTrainSubsystem.leftRear.setNeutralMode(NeutralMode.Coast);
        driveTrainSubsystem.leftFront.setNeutralMode(NeutralMode.Coast);
        driveTrainSubsystem.leftMid.setNeutralMode(NeutralMode.Coast);
        driveTrainSubsystem.rightRear.setNeutralMode(NeutralMode.Coast);
        driveTrainSubsystem.rightMid.setNeutralMode(NeutralMode.Coast);
        driveTrainSubsystem.rightFront.setNeutralMode(NeutralMode.Coast);

    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();

        /*
            LED Outputs
         */

        if(elevatorSubsystem.elevator.getSensorCollection().isRevLimitSwitchClosed()){
//            elevatorSubsystem.elevator.setSelectedSensorPosition(0, 0, 20);
            OI.ControlPanel.setOutput(3, true);
        }else{
            OI.ControlPanel.setOutput(3, false);
        }

        if(elevatorSubsystem.elevator.getSensorCollection().isFwdLimitSwitchClosed()){
            OI.ControlPanel.setOutput(5, true);
        }else{
            OI.ControlPanel.setOutput(5, false);
        }

        if(OI.ControlPanel.getRawButton(2)){
            OI.ControlPanel.setOutput(6, true);
        }else{
            OI.ControlPanel.setOutput(7, true);
        }

        OI.ControlPanel.setOutput(15, true);


        /** Drive Functionality **/


        if(wheel < 0){
            wheel = wheel - wheelDeadZoneMax;
        }else{
            wheel = wheel + wheelDeadZoneMax;
        }

        wheelAntiTilt = OI.wheel.getRawButton(2);
        if(wheelAntiTilt && !oldWheelAntiTilt){
            toggleWheelAntiTilt = !toggleWheelAntiTilt;
        }
        oldWheelAntiTilt = wheelAntiTilt;

        if(OI.ControlPanel.getRawButton(14) && toggleWheelAntiTilt){
            antiTilt = true;
        }else {
            antiTilt = false;
        }


        if(antiTilt){
            antiTiltSubsystem.antiTilt();
            speed = antiTiltSubsystem.speed;
            wheel = ((antiTiltSubsystem.wheel * speedTurnScale) - wheelDeadZoneMax) ;
        }else{
            speed = -OI.Stick.getRawAxis(1);
            wheel = ((OI.wheel.getRawAxis(constants.wheelDriveAxis) * speedTurnScale) - wheelDeadZoneMax);
        }

        speedTurnScale = a*(1/((speed*speed)-h))+k;


        /**
            Climber Functions
         */

//        if(OI.Psoc.getRawButton(16)) {
//            climberSubsystem.setClimberSpeed(1);
//        }
//        else if(OI.wheel.getRawButton(2)){
//            climberSubsystem.setClimberSpeed(-0.25);
//        }
//        else{
//
////            climberSubsystem.deployHook(true);
//        }



        /** RGB Sensor **/

//        rgbSensorSubsystem.rgbGetLux();


        /** Climber Functions **/
//        if(OI.Psoc.getRawButton(16)){
//            climberSubsystem.setClimberSpeed(1);
//        }else{
//            climberSubsystem.setClimberSpeed(0);
//        }





        /** Elevator Functions **/

        // Toggle Elevator Solenoid

        /**
         * Checks to see the current boolean state for the elevator button, and compares it to another boolean, oldElevatorState.
         * The if statement checks to see if the two boolean states are opposite of each other, and if they are, flip
         * the state of the runElevator boolean, which controls the pneumatic solenoid of the elevator.
         * After the check, oldElevatorState is set equal to currentElevatorState to keep it consistent with the robot,
         * and to be able to check again in the future.
         */

        // Homing of the elevator

        homeState = OI.ControlPanel.getRawButton(constants.elevatorHome);
        if(homeState && !oldHomeState){
            isHomed = !isHomed;
        }
        oldHomeState = homeState;

        currentElevatorState = OI.ControlPanel.getRawButton(6);
        if(antiTiltSubsystem.preventBack && antiTilt) {
            runElevator = true;
        }else if(antiTiltSubsystem.preventForward && antiTilt){
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
         * PID Move Elevator
         */

        lowButton = OI.ControlPanel.getRawButton(constants.scaleLow);
        if(lowButton && !lowButtonOld){
            moveLow = !moveLow;
            moveMid = false;
            moveHigh = false;
            moveIntake = false;
            moveSwitch = false;
            moveClimb = false;
        }
        lowButtonOld = lowButton;

        switchButton = OI.ControlPanel.getRawButton(constants.switchPos);
        if(switchButton && !switchButtonOld){
            moveLow = false;
            moveMid = false;
            moveHigh = false;
            moveIntake = false;
            moveSwitch = !moveSwitch;
            moveClimb = false;
        }
        switchButtonOld = switchButton;


        climbButton = OI.ControlPanel.getRawButton(constants.climbPos);
        if(climbButton && !climbButtonOld){
            moveLow = false;
            moveMid = false;
            moveHigh = false;
            moveIntake = false;
            moveSwitch = false;
            moveClimb = !moveClimb;
        }
        climbButtonOld = climbButton;
//
        intakeButton = OI.ControlPanel.getRawButton(constants.intakePos);
        if(intakeButton && !intakeButtonOld){
            moveLow = false;
            moveMid = false;
            moveHigh = false;
            moveIntake = !moveIntake;
            moveSwitch = false;
            moveClimb = false;
        }
        intakeButtonOld = intakeButton;

        highButton = OI.ControlPanel.getRawButton(constants.scaleHigh);
        if(highButton && !highButtonOld){
            moveLow = false;
            moveMid = false;
            moveHigh = !moveHigh;
            moveIntake = false;
            moveSwitch = false;
            moveClimb = false;
        }
        highButtonOld = highButton;

        midButton = OI.ControlPanel.getRawButton(constants.scaleMid);
        if(midButton && !midButtonOld){
            moveLow = false;
            moveMid = !moveMid;
            moveHigh = false;
            moveIntake = false;
            moveSwitch = false;
            moveClimb = false;
        }
        midButtonOld = midButton;

        intakeSolButton = OI.ControlPanel.getRawButton(constants.intakeSol);
        if(intakeSolButton && !oldIntakeSolButton){
            toggleIntake = !toggleIntake;
        }
        oldIntakeSolButton = intakeSolButton;



//        if(moveLow){
//            elevatorSubsystem.moveToPos("low");
//        }
//        if(moveSwitch){
//            elevatorSubsystem.moveToPos("switch");
//        }
//        if(moveClimb) {
//            elevatorSubsystem.moveToPos("climb");
//        }

//        lo = OI.ControlPanel.getRawButton(constants.lowButton);
//        if(lowButton && !lowButtonOld){
//            moveLow = !moveLow;
//        }
//        lowButtonOld = lowButton;
//
//        if(moveLow){
//            movePos = "low";
//        }
//
//
//


        /**
         *  Manual-Move Elevator
         */

        controlMode = elevatorSubsystem.elevator.getControlMode().toString();

        if(!isHomed) {
            elevatorSubsystem.discBrake.set(true);
            elevatorSubsystem.manualMoveElevator(-0.25);
            System.out.println("Homing...");
//            elevatorSubsystem.elevator.setSelectedSensorPosition(0, 0, 20);
            elevatorSubsystem.zeroElevator();
            if (elevatorSubsystem.elevator.getSensorCollection().isRevLimitSwitchClosed()) {
                elevatorSubsystem.discBrake.set(false);
                elevatorSubsystem.manualMoveElevator(0);
                System.out.println("Homed");
                isHomed = true;
                elevatorSubsystem.zeroElevator();
            }
        }else if(!elevatorSubsystem.elevatorSol.get() && elevatorSubsystem.elevator.getSelectedSensorPosition(0) >= 26000 && OI.ControlPanel.getRawAxis(0) < -0.1){
            elevatorSubsystem.discBrake.set(false);
            elevatorSubsystem.manualMoveElevator(0);
            System.out.println("hi");
        }else if(OI.ControlPanel.getRawAxis(0) < -0.1 && isHomed){
            elevatorLoopNumber++;
            elevatorSubsystem.discBrake.set(true);
            moveLow = false;
            moveMid = false;
            moveHigh = false;
            moveIntake = false;
            moveSwitch = false;
            moveClimb = false;
            if(elevatorLoopNumber > 2){
                    elevatorSubsystem.manualMoveElevator(-OI.ControlPanel.getRawAxis(0));
            }
        }else if(OI.ControlPanel.getRawAxis(0) > 0.1 && isHomed) {
            elevatorLoopNumber++;
            elevatorSubsystem.discBrake.set(true);
            moveLow = false;
            moveMid = false;
            moveHigh = false;
            moveIntake = false;
            moveSwitch = false;
            moveClimb = false;
            if (elevatorLoopNumber > 2) {
                elevatorSubsystem.manualMoveElevator(-OI.ControlPanel.getRawAxis(0) * 0.5);
            }
        }else if(moveSwitch) {
            elevatorSubsystem.moveToPos("switch");
        }else if(moveClimb) {
            elevatorSubsystem.moveToPos("climb");
        }else if(moveIntake) {
            elevatorSubsystem.moveToPos("intake");
        }else if(moveHigh) {
            elevatorSubsystem.moveToPos("high");
        }else if(moveLow) {
            elevatorSubsystem.moveToPos("low");
        }else if(moveMid) {
            elevatorSubsystem.moveToPos("mid");
        }else if(OI.ControlPanel.getRawButton(1)){
            /** Auto Climb and Unwind - Once pressed: Hook will deploy, Elevator will retract, and hold to keep climbing **/
            moveMid = false;
            moveHigh = false;
            moveIntake = false;
            moveSwitch = false;
            moveClimb = false;
            moveLow = false;
            climberSubsystem.autoClimb(1);
        }else if (OI.ControlPanel.getRawButton(15)) {
            climberSubsystem.setClimberSpeed(-0.25);
        }else if (OI.ControlPanel.getRawButton(10)){

        }else{
            elevatorLoopNumber = 0;
            climberSubsystem.loopNumber = 0;
            climberSubsystem.setClimberSpeed(0);
//            elevatorSubsystem.discBrake.set(false);   already set in auto-climb, would cause overlaps.
            elevatorSubsystem.manualMoveElevator(0);
            elevatorSubsystem.discBrake.set(false);
            homeLoopNumber = 0;
            climberSubsystem.deployHook(false);
        }

        /** Intake Functions **/

        if(OI.ControlPanel.getRawButton(8)){
            intakeSubsystem.runIntake(-0.35);
        }else if(OI.ControlPanel.getRawButton(7)){
            intakeSubsystem.runIntake(0.6);
        }else{
            intakeSubsystem.runIntake(0);
        }


        /**
         * Intake Solenoid Toggling
         */

       intakeSubsystem.toggleIntake(toggleIntake);



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




//        if(elevatorSubsystem.elevator.getSensorCollection().isRevLimitSwitchClosed()){
//            elevatorSubsystem.elevator.setSelectedSensorPosition(0, 0, 20);
//        }

        leftDrive = speed + wheel;
        rightDrive = speed - wheel;

        driveTrainSubsystem.tankDrive(leftDrive , rightDrive );

        /** Outputs to Console **/

//        System.out.println("speed                                                                                " + speed);
//        System.out.println("loopNumber = " + (loopNumber) + "                time.get: " + time.get());
//        System.out.println(elevatorSubsystem.elevatorSol.get() + "              " + speed);
//        System.out.println(elevatorLoopNumber);
//        System.out.println(runElevator);
//        System.out.println("intakeOutputValue = " + intakeOutputValue);
//        System.out.println(elevatorSubsystem.elevator.getSelectedSensorPosition(0));
//        System.out.println(isHomed + "      " + elevatorSubsystem.elevator.getSelectedSensorPosition(0) + "          " + elevatorLoopNumber);

//        System.out.println(PDP.getCurrent(7) + "                                " + PDP.getCurrent(8));
//        System.out.println(elevatorSubsystem.elevator.getSelectedSensorPosition(0));
//        System.out.println(elevatorSubsystem.elevator.getSelectedSensorPosition(0) + " " + elevatorSubsystem.elevatorTarget + " " + isHomed);
//        System.out.println("TARGET" + elevatorSubsystem.elevator.getClosedLoopTarget(0));


//        System.out.println("TARGET" + elevatorSubsystem.elevatorTarget);

//        System.out.println(movePos + "   " + moveClimb + "   " + climbButton + "   " + elevatorSubsystem.elevatorSol.get() + "  " + elevatorSubsystem.elevator.getSelectedSensorPosition(0) +
//        " " + elevatorSubsystem.elevator.getClosedLoopError(0));
//        System.out.println(elevatorSubsystem.elevator.getSelectedSensorPosition(0));
//        System.out.println(controlMode + " " + moveSwitch +  "   " + moveClimb + " " + elevatorSubsystem.elevator.getClosedLoopError(0) + " " + elevatorSubsystem.elevatorTarget);

//        System.out.println(navX.getYaw());
        System.out.println(elevatorSubsystem.elevator.getSelectedSensorPosition(0));


//        System.out.println("ultra voltage" + ultrasonicVoltage);

//        System.out.println(speed + "    " + wheel);

//        System.out.println(elevatorSubsystem.elevator.getSelectedSensorPosition(0));


    }


    @Override
    public void testPeriodic() {

        
    }

}
