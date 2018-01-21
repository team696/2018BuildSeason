package org.usfirst.frc.team696.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team696.robot.OI;
import org.usfirst.frc.team696.robot.utilities.PID;


public class ElevatorSubsystem extends Subsystem {

    /*
    Motor Controllers
     */
    public static TalonSRX leftElevator;
    public static TalonSRX rightElevator;

    /*
    PID Values
     */
    public static double kP = 0;
    public static double kI = 0;
    public static double kD = 0;
    public static double kF = 0;

    public static int pidIdx = 0;

    /*
    Preset
     */

//    public static String movePos;

    public static double groundTarget = 0;
//    public static boolean groundMove = false;


    public static double switchTarget = 0;
//    public static boolean switchMove = false;

    public static double scaleTarget = 0;
//    public static boolean scaleMove = false;

    public static double climbTarget = 0;
//    public static boolean climbMove = false;

    /*
    Manual Override Boolean
     */

    public static boolean manual = false;


    public ElevatorSubsystem(int leftElevator, int rightElevator){

        this.leftElevator = new TalonSRX(leftElevator);
        this.rightElevator = new TalonSRX(rightElevator);

        this.leftElevator.set(ControlMode.Position, 0);
        this.rightElevator.set(ControlMode.Follower, 0);

        this.leftElevator.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, pidIdx, 20);
        this.rightElevator.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, pidIdx, 20);

        this.leftElevator.config_kP(0, kP, 20);
        this.leftElevator.config_kI(0, kI, 20);
        this.leftElevator.config_kD(0, kD, 20);
        this.leftElevator.config_kF(0, kF, 20);

    }

    public void moveToPos(String movePos) {

//        while(groundMove){
//            switchMove = false;
//            scaleMove = false;
//            climbMove = false;
//
//            leftElevator.set(ControlMode.Position, groundTarget);
//            rightElevator.set(ControlMode.Follower, leftElevator.getDeviceID());
//
//
//        }
//
//        while(switchMove){
//            groundMove = false;
//            scaleMove = false;
//            climbMove = false;
//
//            leftElevator.set(ControlMode.Position, switchTarget);
//            rightElevator.set(ControlMode.Follower, leftElevator.getDeviceID());
//        }

        switch(movePos){

            case "switch":
                leftElevator.set(ControlMode.Position, switchTarget);
                rightElevator.set(ControlMode.Follower, leftElevator.getDeviceID());

                if(leftElevator.getClosedLoopError(pidIdx) < 2 && leftElevator.getClosedLoopError(pidIdx) > -2){
                    break;
                }

            case "ground":
                leftElevator.set(ControlMode.Position, groundTarget);
                rightElevator.set(ControlMode.Follower, leftElevator.getDeviceID());

                if(leftElevator.getClosedLoopError(pidIdx) < 2 && leftElevator.getClosedLoopError(pidIdx) > -2){
                    break;
                }

            case "scale":
                leftElevator.set(ControlMode.Position, scaleTarget);
                rightElevator.set(ControlMode.Follower, leftElevator.getDeviceID());

                if(leftElevator.getClosedLoopError(pidIdx) < 2 && leftElevator.getClosedLoopError(pidIdx) > -2){
                    break;
                }

            case "climb":
                leftElevator.set(ControlMode.Position, climbTarget);
                rightElevator.set(ControlMode.Follower, leftElevator.getDeviceID());

                if(leftElevator.getClosedLoopError(pidIdx) < 2 && leftElevator.getClosedLoopError(pidIdx) > -2){
                    break;
                }

            case "manual":
                manualOverride();
                break;
            default:
                leftElevator.set(ControlMode.Disabled, 0);
                rightElevator.set(ControlMode.Disabled, 0);
                break;
        }

    }

    public void manualOverride(){

        leftElevator.set(ControlMode.PercentOutput, OI.joy.getRawAxis(2));
        rightElevator.set(ControlMode.Follower, leftElevator.getDeviceID());

    }

    public double checkError() {

        double leftElevatorError = leftElevator.getClosedLoopError(pidIdx);
        double rightElevatorError = rightElevator.getClosedLoopError(pidIdx);
        double error = (leftElevatorError + rightElevatorError) / 2;

        return error;

    }



    public void initDefaultCommand() {

    }
}

