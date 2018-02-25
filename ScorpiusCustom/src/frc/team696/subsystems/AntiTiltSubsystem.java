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
    double elevatorPositionInches;

    double elevatorLow = 0;
    double elevatorMid = 15000;
    double elevatorHigh = 30000;

    public double speed;

    /*
        Booleans
     */


    public boolean rampDownLow;
    public boolean rampDownMid;
    public boolean rampDownHigh;
    public boolean rampUpLow;
    public boolean rampUpHigh;
    public boolean delayedAutoBack;
    public boolean preventBack;
    public boolean limitMaxSpeed;

    /*
        Ramping Variables
     */

    public double commandedSpeed;
    public double minimumBackSpeed = -0.3;
    public double lowRampRate = 0.05;
    public double midRampRate;
    public double highRampRate = 0.02;

    /*
        Delayed Auto Elevator Back
     */

    public double delayLoopNumber;
    public double maxActuatingSpeed;

    /*
        Limiting Variables
     */

    public double maxSpeed;

    public AntiTiltSubsystem() {

    }

    public void antiTilt() {

        /*
            Forwards Anti-Tilt
         */

        elevatorPositionInches = elevatorPositionInches();

        if(elevatorPositionInches() == 0 && elevatorSol()) {
            elevatorPosition = 1;
        }else if(elevatorPositionInches() < elevatorMid && elevatorPositionInches() > elevatorLow && elevatorSol()) {
            elevatorPosition = 2;
        }else if(elevatorPositionInches() < elevatorHigh && elevatorPositionInches() > elevatorMid && elevatorSol()) {
            elevatorPosition = 3;
        }else if(elevatorPositionInches() < elevatorHigh && elevatorPositionInches() > elevatorMid && !elevatorSol()){
            elevatorPosition = 4;
        }else{
            speed = -OI.Psoc.getRawAxis(Robot.constants.psocDriveAxis);
        }

        switch(elevatorPosition){

            case 1:
                rampDownLow = true;
                rampDownMid = false;
                rampDownHigh = false;
                rampUpLow = false;
                rampUpHigh = false;
//                if(Math.abs(intakeOutput()) > 0){
//                    delayedAutoBack = false;
//                }else{
//                    delayedAutoBack = true;
//                }
                preventBack = false;
                limitMaxSpeed = false;
                break;

            case 2:
                rampDownLow = false;
                rampDownMid = true;
                rampDownHigh = false;
                rampUpLow = true;
                rampUpHigh = false;
                delayedAutoBack = false;
                preventBack = true;
                limitMaxSpeed = false;
                break;

            case 3:
                rampDownLow = false;
                rampDownMid = false;
                rampDownHigh = true;
                rampUpLow = false;
                rampUpHigh = true;
                delayedAutoBack = false;
                preventBack = true;
                limitMaxSpeed = true;
                break;

            case 4:
                rampDownLow = false;
                rampDownMid = false;
                rampDownHigh = true;
                rampUpLow = false;
                rampUpHigh = true;
                delayedAutoBack = false;
                limitMaxSpeed = true;
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

        if(delayedAutoBack){
            delayLoopNumber++;
            delayedAutoBack();
        }else{
            delayLoopNumber = 0;
        }

        if(preventBack){
            preventBack();
        }

        if(limitMaxSpeed){
            limitMaxSpeed();
        }

    }

    private double elevatorPositionInches() {
        return Robot.elevatorSubsystem.elevator.getSelectedSensorPosition(0) / 200;
    }

    private double intakeOutput() {
        return Robot.intakeSubsystem.intakeA.getMotorOutputPercent();
    }

    private boolean elevatorSol() {
        return Robot.elevatorSubsystem.elevatorSol.get();
    }

    private void rampDownLow() {
        commandedSpeed = -OI.Psoc.getRawAxis(Robot.constants.psocDriveAxis);
        if(speed > minimumBackSpeed && speed < 0 && commandedSpeed < 0) {
            speed = minimumBackSpeed;
        }else if(speed > commandedSpeed){
            speed -= lowRampRate;
        }else{
            speed = -OI.Psoc.getRawAxis(Robot.constants.psocDriveAxis);
        }
    }

    private void rampDownMid() {
        commandedSpeed = -OI.Psoc.getRawAxis(Robot.constants.psocDriveAxis);
        if(speed > minimumBackSpeed && speed < 0 && commandedSpeed < 0) {
            speed = minimumBackSpeed;
        }else if(speed > commandedSpeed){
            speed -= midRampRate;
        }else{
            speed = -OI.Psoc.getRawAxis(Robot.constants.psocDriveAxis);
        }
    }

    private void rampDownHigh() {
        commandedSpeed = -OI.Psoc.getRawAxis(Robot.constants.psocDriveAxis);
        if(speed > minimumBackSpeed && speed < 0 && commandedSpeed < 0) {
            speed = minimumBackSpeed;
        }else if(speed > commandedSpeed){
            speed -= highRampRate;
        }else{
            speed = -OI.Psoc.getRawAxis(Robot.constants.psocDriveAxis);
        }
    }

    private void rampUpLow() {
        commandedSpeed = -OI.Psoc.getRawAxis(Robot.constants.psocDriveAxis);
        if(speed < -minimumBackSpeed && speed > 0 && commandedSpeed > 0) {
            speed = -minimumBackSpeed;
        }else if(speed < commandedSpeed){
            speed += lowRampRate;
        }else{
            speed = -OI.Psoc.getRawAxis(Robot.constants.psocDriveAxis);
        }
    }

    private void rampUpHigh() {
        commandedSpeed = -OI.Psoc.getRawAxis(Robot.constants.psocDriveAxis);
        if(speed < -minimumBackSpeed && speed > 0 && commandedSpeed > 0) {
            speed = -minimumBackSpeed;
        }else if(speed < commandedSpeed){
            speed += highRampRate;
        }else{
            speed = -OI.Psoc.getRawAxis(Robot.constants.psocDriveAxis);
        }
    }

    private void delayedAutoBack() {
        if(speed > maxActuatingSpeed && delayLoopNumber > 30){
            Robot.elevatorSubsystem.elevatorSol.set(false);
            robot.runElevator = false;
        }
    }
        /*
            Michael Matar is good programmer
         */

    private void preventBack() {
        if(OI.Psoc.getRawButton(15) && robot.runElevator){
            robot.runElevator = false;
        }

    }

    private void limitMaxSpeed() {
        maxSpeed = 0.3;
        if(speed > maxSpeed){
            speed = maxSpeed;
        }
        if(speed < -maxSpeed){
            speed = -maxSpeed;
        }
    }

    @Override
    public void initDefaultCommand() {

    }

}
