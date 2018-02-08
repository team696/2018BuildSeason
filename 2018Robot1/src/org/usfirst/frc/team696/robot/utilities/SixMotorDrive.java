package org.usfirst.frc.team696.robot.utilities;

import edu.wpi.first.wpilibj.RobotDrive;

public class SixMotorDrive {
	RobotDrive driveA;
	RobotDrive driveB;
	public SixMotorDrive(int frontLeftMotor, 
					int midLeftMotor,
					int rearLeftMotor,
					int frontRightMotor,
					int midRightMotor,
					int rearRightMotor){
		driveA = new RobotDrive(frontLeftMotor, 
								rearLeftMotor, 
								frontRightMotor, 
								rearRightMotor);
		driveB = new RobotDrive(midLeftMotor,
								midRightMotor);
	}
	
	public void tankDrive(double leftValue, double rightValue){
		driveA.tankDrive(leftValue, rightValue);
		driveB.tankDrive(leftValue, rightValue);
	}
}
