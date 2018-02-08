/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team696.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

<<<<<<< HEAD
    public static int leftFront = 16;
    public static int leftMid = 15;
    public static int leftRear = 14;
    public static int rightFront = 4;
    public static int rightMid = 2;
    public static int rightRear = 1;
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
}
=======
    /*
    Drive Base
     */
    public static int leftFront;
    public static int leftMid;
    public static int leftRear;
    public static int rightFront;
    public static int rightMid;
    public static int rightRear;

    public static int leftRearCurrent;
    public static int leftMidCurrent;
    public static int leftFrontCurrent;
    public static int rightRearCurrent;
    public static int rightMidCurrent;
    public static int rightFrontCurrent;

    /*
    Elevator
     */

    public static int leftIntake = 1;
    public static int rightIntake = 2;
    public static int leftElevator = 3;
    public static int rightElevator = 4;

    public static int leftIntakeCurrent = 1;
    public static int rightIntakeCurrent = 2;

    /*
    Climber
     */

    public static int leftClimber = 1;
    public static int rightClimber = 3;
    public static int hookDeploy = 2;
    public static int greenLED = 0;


}
>>>>>>> 303724418a6d61f39cd163bb002c7a8b6e25305d
