package org.usfirst.frc.team696.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team696.robot.commands.DriveCommand;
import org.usfirst.frc.team696.robot.subsystems.DriveTrainSubsystem;
import org.usfirst.frc.team696.robot.subsystems.ElevatorSubsystem;

public class RobotTesting extends TimedRobot {

    DriveTrainSubsystem driveTrainSubsystem = new DriveTrainSubsystem(RobotMap.leftRear, RobotMap.leftMid, RobotMap.leftFront,
                                                                    RobotMap.rightRear, RobotMap.rightMid, RobotMap.rightFront);
    ElevatorSubsystem elevatorSubsystem = new ElevatorSubsystem(RobotMap.leftElevator, RobotMap.rightElevator);


    boolean testDriveMotors = false;
    boolean testElevator = false;

    int leftRearCurrent = RobotMap.leftRearCurrent;
    int leftMidCurrent = RobotMap.leftMidCurrent;
    int leftFrontCurrent = RobotMap.leftFrontCurrent;
    int rightRearCurrent = RobotMap.rightRearCurrent;
    int rightMidCurrent = RobotMap.rightMidCurrent;
    int rightFrontCurrent = RobotMap.rightFrontCurrent;

    int num = 1;
    Timer time = new Timer();

    double minCurrent = 50;
    double halfSpeed = 0.5;
    double noSpeed = 0;

    @Override
    public void testPeriodic() {


        /**
         * Elevator PIDF Values
         */


        SmartDashboard.putNumber("Elevator P Value", ElevatorSubsystem.kP);
        SmartDashboard.putNumber("Elevator I Value", ElevatorSubsystem.kI);
        SmartDashboard.putNumber("Elevator D Value", ElevatorSubsystem.kD);
        SmartDashboard.putNumber("Elevator F Value", ElevatorSubsystem.kF);

        /**
         * Autonomous Drive PID Values
         */

        SmartDashboard.putNumber("Distance P Value", DriveCommand.kPa);
        SmartDashboard.putNumber("Distance I Value", DriveCommand.kIa);
        SmartDashboard.putNumber("Distance D Value", DriveCommand.kDa);
        SmartDashboard.putNumber("Distance F Value", DriveCommand.kFa);

        SmartDashboard.putNumber("Direction P Value", DriveCommand.kPb);
        SmartDashboard.putNumber("Direction I Value", DriveCommand.kIb);
        SmartDashboard.putNumber("Direction D Value", DriveCommand.kDb);
        SmartDashboard.putNumber("Direction F Value", DriveCommand.kFb);

        SmartDashboard.putNumber("Target Distance (Testing)", DriveCommand.targetDistance);
        SmartDashboard.putNumber("Target Direction (Testing)", DriveCommand.tempTargetDirection);


        /**
         * Testing Talons
         */

        SmartDashboard.putBoolean("Test Drive Motors", testDriveMotors);
        SmartDashboard.putBoolean("Test Elevator", testElevator);

        //Drive Talons


        // TODO Implement current checking of each motor

        if(testDriveMotors){
            switch (num) {
                case 1:
                    driveTrainSubsystem.leftRear.set(halfSpeed);
                    time.start();
                    if (time.get() > 3 && Math.abs(driveTrainSubsystem.leftRear.get()) > 0 && getCurrent(leftRearCurrent) > minCurrent) {
                        driveTrainSubsystem.leftRear.set(noSpeed);
                        restartTimer();
                        if (time.get() > 1) {
                            time.stop();
                            time.reset();
                            driveTrainSubsystem.leftRear.set(-halfSpeed);
                            time.start();
                        }
                        if (time.get() > 3 && driveTrainSubsystem.leftRear.get() > 0 && getCurrent(leftRearCurrent) > minCurrent) {
                            driveTrainSubsystem.leftRear.set(noSpeed);
                            num++;
                            time.stop();
                            time.reset();
                            break;
                        } else if (time.get() > 3 && !(Math.abs(driveTrainSubsystem.leftRear.get()) > 0) || time.get() > 3 && !(getCurrent(leftRearCurrent) > minCurrent)) {
                            System.out.println("Left Rear Drive Motor " + driveTrainSubsystem.leftRear.getDeviceID() + " is not functioning properly. ");
                            driveTrainSubsystem.leftRear.set(noSpeed);
                            time.stop();
                            time.reset();
                            testDriveMotors = false;
                            resetNum();
                            break;
                        }
                    } else if (time.get() > 3 && !(Math.abs(driveTrainSubsystem.leftRear.get()) > 0) || time.get() > 3 && !(getCurrent(leftRearCurrent) > minCurrent)) {
                        System.out.println("Left Rear Drive Motor " + driveTrainSubsystem.leftRear.getDeviceID() + " is not functioning properly.");
                        driveTrainSubsystem.leftRear.set(noSpeed);
                        time.stop();
                        time.reset();
                        testDriveMotors = false;
                        resetNum();
                        break;
                    }

                case 2:
                    driveTrainSubsystem.leftMid.set(halfSpeed);
                    time.start();
                    if (time.get() > 3 && Math.abs(driveTrainSubsystem.leftRear.get()) > 0 && getCurrent(leftMidCurrent) > minCurrent) {
                        driveTrainSubsystem.leftMid.set(noSpeed);
                        restartTimer();
                        if (time.get() > 1) {
                            time.stop();
                            time.reset();
                            driveTrainSubsystem.leftMid.set(-halfSpeed);
                            time.start();
                        }
                        if (time.get() > 3 && Math.abs(driveTrainSubsystem.leftRear.get()) > 0 && getCurrent(leftMidCurrent) > minCurrent) {
                            driveTrainSubsystem.leftMid.set(noSpeed);
                            num++;
                            time.stop();
                            time.reset();
                            break;
                        } else if (time.get() > 3 && !(Math.abs(driveTrainSubsystem.leftRear.get()) > 0)) {
                            System.out.println("Left Mid Drive Motor " + driveTrainSubsystem.leftMid.getDeviceID() + " is not functioning properly. ");
                            driveTrainSubsystem.leftMid.set(0);
                            time.stop();
                            time.reset();
                            testDriveMotors = false;
                            resetNum();
                            break;
                        }
                    } else if (time.get() > 3 && !(Math.abs(driveTrainSubsystem.leftRear.get()) > 0)) {
                        System.out.println("Left Mid Drive Motor " + driveTrainSubsystem.leftMid.getDeviceID() + " is not functioning properly.");
                        driveTrainSubsystem.leftMid.set(0);
                        time.stop();
                        time.reset();
                        testDriveMotors = false;
                        resetNum();
                        break;
                    }

                case 3:
                    driveTrainSubsystem.leftFront.set(0.5);
                    time.start();
                    if (time.get() > 3 && driveTrainSubsystem.leftFront.get() > 0) {
                        driveTrainSubsystem.leftFront.set(0);
                        restartTimer();
                        if (time.get() > 1) {
                            time.stop();
                            time.reset();
                            driveTrainSubsystem.leftFront.set(-0.5);
                            time.start();
                        }
                        if (time.get() > 3 && driveTrainSubsystem.leftFront.get() > 0) {
                            driveTrainSubsystem.leftFront.set(0);
                            num++;
                            time.stop();
                            time.reset();
                            break;
                        } else if (time.get() > 3 && !(driveTrainSubsystem.leftFront.get() > 0)) {
                            System.out.println("Left Front Drive Motor " + driveTrainSubsystem.leftFront.getDeviceID() + " is not functioning properly. ");
                            driveTrainSubsystem.leftMid.set(0);
                            time.stop();
                            time.reset();
                            testDriveMotors = false;
                            resetNum();
                            break;
                        }
                    } else if (time.get() > 3 && !(driveTrainSubsystem.leftFront.get() > 0)) {
                        System.out.println("Left Front Drive Motor " + driveTrainSubsystem.leftFront.getDeviceID() + " is not functioning properly.");
                        driveTrainSubsystem.leftFront.set(0);
                        time.stop();
                        time.reset();
                        testDriveMotors = false;
                        resetNum();
                        break;
                    }

                case 4:
                    driveTrainSubsystem.rightRear.set(0.5);
                    time.start();
                    if (time.get() > 3 && driveTrainSubsystem.rightRear.get() > 0) {
                        driveTrainSubsystem.rightRear.set(0);
                        restartTimer();
                        if (time.get() > 1) {
                            time.stop();
                            time.reset();
                            driveTrainSubsystem.rightRear.set(-0.5);
                            time.start();
                        }
                        if (time.get() > 3 && driveTrainSubsystem.rightRear.get() > 0) {
                            driveTrainSubsystem.rightRear.set(0);
                            num++;
                            time.stop();
                            time.reset();
                            break;
                        } else if (time.get() > 3 && !(driveTrainSubsystem.rightRear.get() > 0)) {
                            System.out.println("Right Rear Drive Motor " + driveTrainSubsystem.rightRear.getDeviceID() + " is not functioning properly. ");
                            driveTrainSubsystem.rightRear.set(0);
                            time.stop();
                            time.reset();
                            testDriveMotors = false;
                            resetNum();
                            break;
                        }
                    } else if (time.get() > 3 && !(driveTrainSubsystem.rightRear.get() > 0)) {
                        System.out.println("Right Rear Drive Motor " + driveTrainSubsystem.leftMid.getDeviceID() + " is not functioning properly.");
                        driveTrainSubsystem.rightRear.set(0);
                        time.stop();
                        time.reset();
                        testDriveMotors = false;
                        resetNum();
                        break;
                    }

                case 5:
                    driveTrainSubsystem.rightMid.set(0.5);
                    time.start();
                    if (time.get() > 3 && driveTrainSubsystem.rightMid.get() > 0) {
                        driveTrainSubsystem.rightMid.set(0);
                        restartTimer();
                        if (time.get() > 1) {
                            time.stop();
                            time.reset();
                            driveTrainSubsystem.rightMid.set(-0.5);
                            time.start();
                        }
                        if (time.get() > 3 && driveTrainSubsystem.rightMid.get() > 0) {
                            driveTrainSubsystem.rightMid.set(0);
                            num++;
                            time.stop();
                            time.reset();
                            break;
                        } else if (time.get() > 3 && !(driveTrainSubsystem.rightMid.get() > 0)) {
                            System.out.println("Right Mid Drive Motor " + driveTrainSubsystem.leftMid.getDeviceID() + " is not functioning properly. ");
                            driveTrainSubsystem.rightMid.set(0);
                            time.stop();
                            time.reset();
                            testDriveMotors = false;
                            resetNum();
                            break;
                        }
                    } else if (time.get() > 3 && !(driveTrainSubsystem.rightMid.get() > 0)) {
                        System.out.println("Right Mid Drive Motor " + driveTrainSubsystem.rightMid.getDeviceID() + " is not functioning properly.");
                        driveTrainSubsystem.rightMid.set(0);
                        time.stop();
                        time.reset();
                        testDriveMotors = false;
                        resetNum();
                        break;
                    }

                case 6:
                    time.start();
                    driveTrainSubsystem.rightFront.set(0.5);
                    time.start();
                    if (time.get() > 3 && driveTrainSubsystem.rightFront.get() > 0) {
                        driveTrainSubsystem.rightFront.set(0);
                        restartTimer();
                        if (time.get() > 1) {
                            time.stop();
                            time.reset();
                            driveTrainSubsystem.rightFront.set(-0.5);
                            time.start();
                        }
                        if (time.get() > 3 && driveTrainSubsystem.rightFront.get() > 0) {
                            driveTrainSubsystem.rightMid.set(0);
                            num++;
                            time.stop();
                            time.reset();
                            break;
                        } else if (time.get() > 3 && !(driveTrainSubsystem.rightFront.get() > 0)) {
                            System.out.println("Right Front Drive Motor " + driveTrainSubsystem.rightFront.getDeviceID() + " is not functioning properly. ");
                            driveTrainSubsystem.rightFront.set(0);
                            time.stop();
                            time.reset();
                            testDriveMotors = false;
                            resetNum();
                            break;
                        }
                    } else if (time.get() > 3 && !(driveTrainSubsystem.rightFront.get() > 0)) {
                        System.out.println("Right Front Drive Motor " + driveTrainSubsystem.rightFront.getDeviceID() + " is not functioning properly.");
                        driveTrainSubsystem.rightFront.set(0);
                        time.stop();
                        time.reset();
                        testDriveMotors = false;
                        resetNum();
                        break;
                    }

                default:
                    System.out.println("All drive motors functional.");
                    resetNum();
                    time.stop();
                    time.reset();
                    testDriveMotors = false;
                    break;
            }
        }

        // Elevator Talons

        if(testElevator){

            switch(num){

                case 1:

                    time.start();
                    elevatorSubsystem.moveToPos("switch");
                    if(elevatorSubsystem.checkError() < 2 && time.get() > 5){
                        time.stop();
                        time.reset();
                        time.start();
                        num++;
                        break;
                    }else if(!(elevatorSubsystem.checkError() < 2) && time.get() > 10){
                        System.out.println("Move to Switch movement not functioning properly.");
                        time.stop();
                        time.reset();
                        num = 1;
                        testElevator = false;
                        break;
                    }

                case 2:
                    elevatorSubsystem.moveToPos("ground");
                    if(elevatorSubsystem.checkError() < 2 && time.get() > 5){
                        time.stop();
                        time.reset();
                        time.start();
                        num++;
                        break;
                    }else if(!(elevatorSubsystem.checkError() < 2) && time.get() > 5){
                        System.out.println("Move to Ground movement not functioning properly.");
                        time.stop();
                        time.reset();
                        num = 1;
                        testElevator = false;
                        break;
                    }

                case 3:
                    elevatorSubsystem.moveToPos("scale");
                    if(elevatorSubsystem.checkError() < 2 && time.get() > 5){
                        time.stop();
                        time.reset();
                        time.start();
                        break;
                    }else if(!(elevatorSubsystem.checkError() < 2) && time.get() > 10){
                        System.out.println("Move to Scale movement not functioning properly.");
                        time.stop();
                        time.reset();
                        num = 1;
                        testElevator = false;
                        break;
                    }

                case 4:
                    elevatorSubsystem.moveToPos("climb");
                    if(elevatorSubsystem.checkError() < 2 && time.get() > 5){
                        time.stop();
                        time.reset();
                        time.start();
                        num++;
                        break;
                    }else if(!(elevatorSubsystem.checkError() < 2) && time.get() > 10){
                        System.out.println("Move to Climb movement not functioning properly");
                        time.stop();
                        time.reset();
                        num = 1;
                        testElevator = false;
                        break;
                    }

                default:
                    System.out.println("Elevator motion and control good to go!");
                    time.stop();
                    time.reset();
                    num = 1;
                    testElevator = false;

            }

        }

        // Intake Motors



    }

    public double getCurrent(int currentPort){
        return Robot.PDP.getCurrent(currentPort);
    }

    public void resetNum() {
        num = 1;
    }

    public void restartTimer() {
        time.stop();
        time.reset();
        time.start();
    }

}
