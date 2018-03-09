package frc.team696.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team696.Constants;
import frc.team696.OI;
import frc.team696.Robot;
import frc.team696.RobotMap;

public class AntiTiltSubsystem extends Subsystem {

    /*
        Class Objects
     */

    public Robot robot = new Robot();

    int elevatorPosition;

    double elevatorLow = 0;
    double elevatorMid = 15000;
    double elevatorHigh = 30000;

    public double speed;
    public double wheel;

    /*
        Booleans
     */


    private boolean rampDownLow;
    private boolean rampDownMid;
    private boolean rampDownHigh;
    private boolean rampUpLow;
    private boolean rampUpHigh;
    public boolean preventBack;
    public boolean preventForward;
    private boolean limitMaxLowSpeed;
    private boolean limitMaxMidSpeed;
    private boolean limitMaxHighSpeed;

    /*
        Ramping Variables
     */

    private double commandedSpeed;
    private double commandedWheel;
    private double minimumBackSpeed = -0.2;
    private double minimumForwardSpeed = 0.2;
    private double lowRampRate = 0.05;
    private double midRampRate = 0.03;
    private double highRampRate = 0.02;

    /*
        Delayed Auto Elevator Back
     */

    private double delayLoopNumber;
    private double maxActuatingSpeed;

    /*
        Limiting Variables
     */

    private double maxLowSpeed = 0.75;
    private double maxMidSpeed = 0.5;
    private double maxHighSpeed = 0.4;

    private double maxLowTurn = 0.75;
    private double maxMidTurn = 0.5;
    private double maxHighTurn = 0.4;

    public AntiTiltSubsystem() {

    }

    public void antiTilt() {

        System.out.println("case: " + elevatorPosition + "          speed: " + speed + "      wheel: " + wheel + "            elevatorPositionInches: " + elevatorPositionInches());

        /*
            Forwards Anti-Tilt
         */

        if(elevatorPositionInches() < 0){
            Robot.elevatorSubsystem.elevator.setSelectedSensorPosition(0, 0, 20);
        }

        if(elevatorPositionInches() < 5 && elevatorSol()) {
            elevatorPosition = 1;
        }else if(elevatorPositionInches() < 5 && !elevatorSol() ){
            elevatorPosition = 7;
        }else if(elevatorPositionInches() <= elevatorMid && elevatorPositionInches() > elevatorLow && elevatorSol()) {
            elevatorPosition = 2;
        }else if(elevatorPositionInches() <= elevatorHigh && elevatorPositionInches() > elevatorMid && elevatorSol()) {
            elevatorPosition = 3;
        }else if(elevatorPositionInches() <= elevatorMid && elevatorPositionInches() > elevatorLow && !elevatorSol()) {
            elevatorPosition = 4;
        }else if(elevatorPositionInches() <= elevatorHigh && elevatorPositionInches() > elevatorMid && !elevatorSol()) {
            elevatorPosition = 5;
        } else if(elevatorPositionInches() >= elevatorHigh && !elevatorSol()){
            elevatorPosition = 6;
        }

        switch(elevatorPosition){

            case 1: // Elevator not extended, forward
                rampDownLow = true;
                rampDownMid = false;
                rampDownHigh = false;
                rampUpLow = false;
                rampUpHigh = false;
                preventBack = false;
                preventForward = false;
                limitMaxLowSpeed = true;
                limitMaxMidSpeed = false;
                limitMaxHighSpeed = false;
                break;

            case 2: // Elevator up to halfway extended, forward
                rampDownLow = false;
                rampDownMid = true;
                rampDownHigh = false;
                rampUpLow = true;
                rampUpHigh = false;
                preventBack = false;
                preventForward = false;
                limitMaxLowSpeed = false;
                limitMaxMidSpeed = true;
                limitMaxHighSpeed = false;
                break;

            case 3: // Elevator extended at least halfway to max, forward
                rampDownLow = false;
                rampDownMid = false;
                rampDownHigh = true;
                rampUpLow = false;
                rampUpHigh = true;
                preventBack = true;
                preventForward = false;
                limitMaxLowSpeed = false;
                limitMaxMidSpeed = false;
                limitMaxHighSpeed = true;
                break;

            case 4: // Elevator between low and mid, back
                rampDownLow = false;
                rampDownMid = false;
                rampDownHigh = true;
                rampUpLow = false;
                rampUpHigh = true;
                preventBack = false;
                preventForward = false;
                limitMaxLowSpeed = false;
                limitMaxMidSpeed = false;
                limitMaxHighSpeed = false;
                break;

            case 5: // Elevator between mid and high, back
                rampDownLow = false;
                rampDownMid = false;
                rampDownHigh = true;
                rampUpLow = false;
                rampUpHigh = true;
                preventBack = false;
                preventForward = true;
                limitMaxMidSpeed = true;
                limitMaxHighSpeed = false;
                break;

            case 6: // Elevator Max Extended, back
                rampDownLow = false;
                rampDownMid = false;
                rampDownHigh = true;
                rampUpLow = false;
                rampUpHigh = true;
                preventBack = false;
                preventForward = true;
                limitMaxLowSpeed = false;
                limitMaxMidSpeed = false;
                limitMaxHighSpeed = true;
                break;

            case 7: // Elevator homed, back
                speed = -OI.Stick.getRawAxis(1);
                wheel = OI.wheel.getRawAxis(0);
                rampDownLow = false;
                rampDownMid = false;
                rampDownHigh = false;
                rampUpLow = false;
                rampUpHigh = false;
                preventBack = false;
                limitMaxLowSpeed = false;
                limitMaxMidSpeed = false;
                limitMaxHighSpeed = false;
                break;


        }

        if(rampDownLow){
            rampDownLow();
        }

        if(rampDownMid){
            rampDownMid();
        }

        if(rampDownHigh){
            rampDownHigh();
        }

        if(rampUpLow){
            rampUpLow();
        }

        if(rampUpHigh){
            rampUpHigh();
        }

        if(preventBack){
            preventBack();
        }

        if(preventForward){
            preventForward();
        }

//        if(limitMaxLowSpeed){
//            limitMaxLowSpeed();
//        }
//
//        if(limitMaxMidSpeed){
//            limitMaxMidSpeed();
//        }
//
//        if(limitMaxHighSpeed){
//            limitMaxHighSpeed();
//        }

    }

    private double elevatorPositionInches() {
        return Robot.elevatorSubsystem.elevator.getSelectedSensorPosition(0);
    }

    private double intakeOutput() {
        return Robot.intakeSubsystem.intakeA.getMotorOutputPercent();
    }

    private boolean elevatorSol() {
        return Robot.elevatorSubsystem.elevatorSol.get();
    }

    private void rampDownLow() {
        System.out.println("Running rampDownLow");
        System.out.println("Running rampDownLow");
        commandedSpeed = -OI.Psoc.getRawAxis(1);
        commandedWheel = OI.wheel.getRawAxis(Robot.constants.wheelDriveAxis);

//        if(speed > minimumBackSpeed && speed < 0 && commandedSpeed < 0) {
//            speed = minimumBackSpeed;
//        }else if(speed > commandedSpeed && commandedSpeed < 0){
//            speed -= lowRampRate;
//        }else{
//            speed = -OI.Psoc.getRawAxis(1);
//        }
//        wheel = OI.wheel.getRawAxis(Robot.constants.wheelDriveAxis);
//
//        if(limitMaxLowSpeed){
//            System.out.println("running limitMaxLowSpeed");
//            if(commandedSpeed > maxMidSpeed){
//                speed = maxMidSpeed;
//            }else if(commandedSpeed < -maxMidSpeed){
//                speed = -maxMidSpeed;
//            }else{
//                speed = commandedSpeed;
//            }
//
//            if(commandedWheel > maxMidTurn){
//                wheel = maxMidTurn;
//            }else if(commandedSpeed < -maxMidTurn){
//                wheel = -maxMidTurn;
//            }else{
//                wheel = commandedWheel;
//            }
//        }

        if(speed > minimumBackSpeed && speed < 0 && commandedSpeed < 0){
            speed = minimumBackSpeed;
        }else if(speed > maxLowSpeed && limitMaxLowSpeed){
            speed = maxLowSpeed;
        }else if(speed < -maxLowSpeed && limitMaxLowSpeed){
            speed = -maxLowSpeed;
        }else if(speed > commandedSpeed && commandedSpeed < 0){
            speed -= lowRampRate;
        }else{
            speed = commandedSpeed;
        }

        if(commandedWheel > maxLowTurn){
            wheel = maxLowTurn;
        }else if(commandedSpeed < -maxLowTurn){
            wheel = -maxLowTurn;
        }else{
            wheel = commandedWheel;
        }
    }

    private void rampDownMid() {
        System.out.println("Running rampDownMid");
        commandedSpeed = -OI.Stick.getRawAxis(1);
        commandedWheel = OI.wheel.getRawAxis(Robot.constants.wheelDriveAxis);
//        if(speed > commandedSpeed){
//            speed -= midRampRate;
//        }else{
//            speed = -OI.Stick.getRawAxis(1);
//        }
//        wheel = OI.wheel.getRawAxis(Robot.constants.wheelDriveAxis);

        if(speed > minimumBackSpeed && speed < 0 && commandedSpeed < 0){
            speed = minimumBackSpeed;
        }else if(speed > maxMidSpeed && limitMaxMidSpeed){
            speed = maxMidSpeed;
        }else if(speed < -maxMidSpeed && limitMaxMidSpeed){
            speed = -maxMidSpeed;
        }else if(speed > commandedSpeed && commandedSpeed < 0){
            speed -= midRampRate;
        }else{
            speed = commandedSpeed;
        }

        if(commandedWheel > maxMidTurn){
            wheel = maxMidTurn;
        }else if(commandedSpeed < -maxMidTurn){
            wheel = -maxMidTurn;
        }else{
            wheel = commandedWheel;
        }

    }

    private void rampDownHigh() {
        System.out.println("Running rampDownHigh");
        commandedSpeed = -OI.Stick.getRawAxis(1);
        commandedWheel = OI.wheel.getRawAxis(Robot.constants.wheelDriveAxis);
//        if(speed > commandedSpeed){
//            speed -= highRampRate;
//        }else{
//            speed = -OI.Stick.getRawAxis(4);
//        }
//        wheel = OI.wheel.getRawAxis(Robot.constants.wheelDriveAxis);

        if(speed > minimumBackSpeed && speed < 0 && commandedSpeed < 0){
            speed = minimumBackSpeed;
        }else if(speed > maxHighSpeed && limitMaxHighSpeed){
            speed = maxHighSpeed;
        }else if(speed < -maxHighSpeed && limitMaxHighSpeed){
            speed = -maxHighSpeed;
        }else if(speed > commandedSpeed && commandedSpeed < 0){
            speed = highRampRate;
        }else{
            speed = commandedSpeed;
        }

        if(commandedWheel > maxHighTurn){
            wheel = maxHighTurn;
        }else if(commandedWheel < -maxHighTurn){
            wheel = -maxHighTurn;
        }else{
            wheel = commandedWheel;
        }
    }

    private void rampUpLow() {
        System.out.println("Running rampUpLow");
        commandedSpeed = -OI.Stick.getRawAxis(1);
        commandedWheel = OI.wheel.getRawAxis(Robot.constants.wheelDriveAxis);
//        if(speed < -minimumBackSpeed && speed > 0 && commandedSpeed > 0) {
//            speed = -minimumBackSpeed;
//        }else if(speed < commandedSpeed){
//            speed += lowRampRate;
//        }else{
//            speed = -OI.Stick.getRawAxis(1);
//        }
//        wheel = OI.wheel.getRawAxis(Robot.constants.wheelDriveAxis);
        if(speed < minimumForwardSpeed && speed > 0 && commandedSpeed > 0){
            speed = minimumForwardSpeed;
        }
    }

    private void rampUpHigh() {
        System.out.println("Running rampUpHigh");
        commandedSpeed = -OI.Stick.getRawAxis(1);
        commandedWheel = OI.wheel.getRawAxis(Robot.constants.wheelDriveAxis);
        if(speed < -minimumBackSpeed && speed > 0 && commandedSpeed > 0) {
            speed = -minimumBackSpeed;
        }else if(speed < commandedSpeed){
            speed += highRampRate;
        }else{
            speed = -OI.Stick.getRawAxis(1);
        }
        wheel = OI.wheel.getRawAxis(Robot.constants.wheelDriveAxis);
    }

        /*
           ! Michael Matar is good programmer
         */

    private void preventBack() {
        System.out.println("Running preventBack");
        if(OI.Psoc.getRawButton(15) && robot.runElevator){
            robot.runElevator = false;
        }
    }

    private void preventForward() {
        System.out.println("Running preventFoward");
        if(OI.Psoc.getRawButton(15) && !robot.runElevator){
            robot.runElevator = false;
        }
    }

    private void limitMaxLowSpeed() {
        System.out.println("Running limitMaxLowSpeed");
        commandedSpeed = -OI.Psoc.getRawAxis(1);
        commandedWheel = OI.wheel.getRawAxis(Robot.constants.wheelDriveAxis);
        if(commandedSpeed > maxLowSpeed){
            speed = maxLowSpeed;
        }else if(commandedSpeed < -maxLowSpeed){
            speed = -maxLowSpeed;
        }else{
            speed = commandedSpeed;
        }

        if(commandedWheel > maxLowTurn){
            wheel = maxLowTurn;
        }else if(commandedWheel < -maxLowTurn){
            wheel = -maxLowTurn;
        }else{
            wheel = commandedWheel;
        }

    }

    private void limitMaxMidSpeed() {
        System.out.println("Running limitMaxMidSpeed");
        commandedSpeed = -OI.Psoc.getRawAxis(1);
        commandedWheel = OI.wheel.getRawAxis(Robot.constants.wheelDriveAxis);

    }

    private void limitMaxHighSpeed() {
        System.out.println("Running limitMaxHighSpeed");
        commandedSpeed = -OI.Psoc.getRawAxis(1);
        commandedWheel = OI.wheel.getRawAxis(Robot.constants.wheelDriveAxis);
        if(commandedSpeed > maxHighSpeed){
            speed = maxHighSpeed;
        }else if(commandedSpeed < -maxHighSpeed){
            speed = -maxHighSpeed;
        }else{
            speed = commandedSpeed;
        }

        if(commandedWheel > maxHighTurn){
            wheel = maxHighTurn;
        }else if(commandedWheel < -maxHighTurn){
            wheel = -maxHighTurn;
        }else{
            wheel = commandedWheel;
        }

    }

    @Override
    public void initDefaultCommand() {

    }

}
