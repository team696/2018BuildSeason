package org.usfirst.frc.team696.robot.subsystems;

import com.ctre.phoenix.motorcontrol.GroupMotorControllers;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrainSubsystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    /*
    Left Side of Robot set up
     */

    WPI_TalonSRX leftRear;
    WPI_TalonSRX leftMid;
    WPI_TalonSRX leftFront;
    SpeedControllerGroup leftSide;

    /*
    Right Side of Robot Set Up
     */

    WPI_TalonSRX rightRear;
    WPI_TalonSRX rightMid;
    WPI_TalonSRX rightFront;
    SpeedControllerGroup rightSide;

    /*
    Final Drive Setup
     */

    DifferentialDrive drive;

    public DriveTrainSubsystem(int leftRear, int leftMid, int leftFront,
                               int rightRear, int rightMid, int rightFront){

        /*
        Left Side of Robot Declared and Initialized
         */

        this.leftRear = new WPI_TalonSRX(leftRear);
        this.leftMid = new WPI_TalonSRX(leftMid);
        this.leftFront = new WPI_TalonSRX(leftFront);
        this.leftSide = new SpeedControllerGroup(this.leftRear, this.leftMid, this.leftFront);

        /*
        Right Side of the Robot Declared and Initialized
         */

        this.rightRear = new WPI_TalonSRX(rightRear);
        this.rightMid = new WPI_TalonSRX(rightMid);
        this.rightFront = new WPI_TalonSRX(rightFront);
        this.rightSide = new SpeedControllerGroup(this.rightRear, this.rightMid, this.rightFront);

        // Drive Object Declared and Initialized

        this.drive = new DifferentialDrive(this.leftSide, this.rightSide);
    }

    public void initDefaultCommand() {
        //    setDefaultCommand(new MySpecialCommand());
    }

    public void tankDrive(double leftDrive, double rightDrive){

        this.drive.tankDrive(leftDrive, rightDrive);

    }


}

