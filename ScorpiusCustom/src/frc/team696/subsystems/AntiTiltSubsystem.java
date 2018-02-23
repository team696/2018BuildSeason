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
    double elevatorMid = 75;
    double elevatorHigh = 130;

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
    public double lowRampRate;
    public double midRampRate;
    public double highRampRate;

    /*
        Delayed Auto Elevator Back
     */

    public double delayLoopNumber;
    public double maxActuatingSpeed;

    public AntiTiltSubsystem() {

    }

    public void antiTilt() {

        /*
            Forwards Anti-Tilt
         */

        if(elevatorPositionInches() == 0 && elevatorSol()) {
            elevatorPosition = 1;
        }else if(elevatorPositionInches() < elevatorMid && elevatorPositionInches() > elevatorLow && elevatorSol()) {
            elevatorPosition = 2;
        }else if(elevatorPositionInches() < elevatorHigh && elevatorPositionInches() > elevatorMid && elevatorSol()) {
            elevatorPosition = 3;
        }

        switch(elevatorPosition){

            case 1:
                rampDownLow = true;
                rampDownMid = false;
                rampDownHigh = false;
                rampUpLow = false;
                rampUpHigh = false;
                if(Math.abs(intakeOutput()) > 0){
                    delayedAutoBack = false;
                }else{
                    delayedAutoBack = true;
                }
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
        if(robot.speed > minimumBackSpeed && robot.speed < 0 && commandedSpeed < 0) {
            robot.speed = minimumBackSpeed;
        }else if(robot.speed > commandedSpeed){
            robot.speed -= lowRampRate;
        }else{
            robot.speed = -OI.Psoc.getRawAxis(Robot.constants.psocDriveAxis);
        }
    }

    private void rampDownMid() {
        commandedSpeed = -OI.Psoc.getRawAxis(Robot.constants.psocDriveAxis);
        if(robot.speed > minimumBackSpeed && robot.speed < 0 && commandedSpeed < 0) {
            robot.speed = minimumBackSpeed;
        }else if(robot.speed > commandedSpeed){
            robot.speed -= midRampRate;
        }else{
            robot.speed = -OI.Psoc.getRawAxis(Robot.constants.psocDriveAxis);
        }
    }

    private void rampDownHigh() {
        commandedSpeed = -OI.Psoc.getRawAxis(Robot.constants.psocDriveAxis);
        if(robot.speed > minimumBackSpeed && robot.speed < 0 && commandedSpeed < 0) {
            robot.speed = minimumBackSpeed;
        }else if(robot.speed > commandedSpeed){
            robot.speed -= highRampRate;
        }else{
            robot.speed = -OI.Psoc.getRawAxis(Robot.constants.psocDriveAxis);
        }
    }

    private void rampUpLow() {
        commandedSpeed = -OI.Psoc.getRawAxis(Robot.constants.psocDriveAxis);
        if(robot.speed < -minimumBackSpeed && robot.speed > 0 && commandedSpeed > 0) {
            robot.speed = -minimumBackSpeed;
        }else if(robot.speed < commandedSpeed){
            robot.speed += lowRampRate;
        }else{
            robot.speed = -OI.Psoc.getRawAxis(Robot.constants.psocDriveAxis);
        }
    }

    private void rampUpHigh() {
        commandedSpeed = -OI.Psoc.getRawAxis(Robot.constants.psocDriveAxis);
        if(robot.speed < -minimumBackSpeed && robot.speed > 0 && commandedSpeed > 0) {
            robot.speed = -minimumBackSpeed;
        }else if(robot.speed < commandedSpeed){
            robot.speed += highRampRate;
        }else{
            robot.speed = -OI.Psoc.getRawAxis(Robot.constants.psocDriveAxis);
        }
    }

    private void delayedAutoBack() {
        if(robot.speed > maxActuatingSpeed && delayLoopNumber > 30){
            Robot.elevatorSubsystem.elevatorSol.set(false);
            robot.runElevator = false;
        }
    }
        /*
            Michael Matar is good programmer
         */

    private void preventBack() {


    }

    @Override
    public void initDefaultCommand() {

    }

}
