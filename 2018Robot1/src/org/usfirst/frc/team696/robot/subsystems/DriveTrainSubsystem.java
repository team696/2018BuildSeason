package org.usfirst.frc.team696.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.GroupMotorControllers;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
<<<<<<< HEAD
import edu.wpi.first.wpilibj.Encoder;
=======
import edu.wpi.first.wpilibj.PowerDistributionPanel;
>>>>>>> 303724418a6d61f39cd163bb002c7a8b6e25305d
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import org.usfirst.frc.team696.robot.OI;
import org.usfirst.frc.team696.robot.Robot;
import org.usfirst.frc.team696.robot.utilities.PID;

public class DriveTrainSubsystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

<<<<<<< HEAD
    public PID driveStraight;
    public double targetDirection = 0;
    public double distanceError = 0;
=======

>>>>>>> 303724418a6d61f39cd163bb002c7a8b6e25305d

    /*
    Left Side of Robot set up
     */

    public WPI_TalonSRX leftRear;
    public WPI_TalonSRX leftMid;
    public WPI_TalonSRX leftFront;
<<<<<<< HEAD
    SpeedControllerGroup leftSide;
=======
    public SpeedControllerGroup leftSide;
//    public PowerDistributionPanel leftRearCurrent;
//    public PowerDistributionPanel leftMidCurrent;
//    public PowerDistributionPanel leftFrontCurrent;
>>>>>>> 303724418a6d61f39cd163bb002c7a8b6e25305d

    public Encoder leftDriveEncoder;

    /*
    Right Side of Robot Set Up
     */

    public WPI_TalonSRX rightRear;
    public WPI_TalonSRX rightMid;
    public WPI_TalonSRX rightFront;
<<<<<<< HEAD
    SpeedControllerGroup rightSide;
=======
    public SpeedControllerGroup rightSide;
//    public PowerDistributionPanel rightRearCurrent;
//    public PowerDistributionPanel rightMidCurrent;
//    public PowerDistributionPanel rightFrontCurrent;
>>>>>>> 303724418a6d61f39cd163bb002c7a8b6e25305d

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
<<<<<<< HEAD

        this.leftFront.set(ControlMode.PercentOutput, 0);
        this.leftMid.set(ControlMode.Follower, this.leftFront.getDeviceID());
        this.leftRear.set(ControlMode.Follower, this.leftFront.getDeviceID());

=======
//        leftRearCurrent = new PowerDistributionPanel(1);
//        leftMidCurrent = new PowerDistributionPanel(2);
//        leftFrontCurrent = new PowerDistributionPanel(3);
>>>>>>> 303724418a6d61f39cd163bb002c7a8b6e25305d
        this.leftSide = new SpeedControllerGroup(this.leftRear, this.leftMid, this.leftFront);


        /*
        Right Side of the Robot Declared and Initialized
         */

        this.rightRear = new WPI_TalonSRX(rightRear);
        this.rightMid = new WPI_TalonSRX(rightMid);
        this.rightFront = new WPI_TalonSRX(rightFront);
<<<<<<< HEAD

        this.rightRear.set(ControlMode.PercentOutput, 0);
        this.rightMid.set(ControlMode.Follower, this.rightRear.getDeviceID());
        this.rightFront.set(ControlMode.Follower, this.rightRear.getDeviceID());

=======
//        rightRearCurrent = new PowerDistributionPanel(1);
//        rightMidCurrent = new PowerDistributionPanel(2);
//        rightFrontCurrent = new PowerDistributionPanel(3);
>>>>>>> 303724418a6d61f39cd163bb002c7a8b6e25305d
        this.rightSide = new SpeedControllerGroup(this.rightRear, this.rightMid, this.rightFront);

        // PID Controller

//        driveStraight = new PID(0.03, 0, 0.001, 0);

        driveStraight = new PID(0.015, 0, 0., 0.001 );

        // Drive Object Declared and Initialized

        this.drive = new DifferentialDrive(this.leftFront, this.rightRear);
    }

    public void initDefaultCommand() {
        //    setDefaultCommand(new MySpecialCommand());
    }

    public void tankDrive(double leftDrive, double rightDrive){

        this.drive.tankDrive(leftDrive, rightDrive);

    }


}

