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

    /*
    Drive Base
     */
    public static int leftFront = 14;
    public static int leftMid = 15;
    public static int leftRear = 16;
    public static int rightFront = 3;
    public static int rightMid = 2;
    public static int rightRear = 1;

    public static int leftRearCurrent = 13;
    public static int leftMidCurrent = 14;
    public static int leftFrontCurrent = 15;
    public static int rightRearCurrent = 0;
    public static int rightMidCurrent = 1;
    public static int rightFrontCurrent = 2;

    /*
    Elevator
     */

    public static int leftIntake = 4;
    public static int rightIntake = 5;
    public static int leftElevator = 6;
    public static int rightElevator = 8;

    public static int leftIntakeCurrent = 3;
    public static int rightIntakeCurrent = 4;

    /*
    Climber
     */

    public static int leftClimber = 9;
    public static int rightClimber = 10;
    public static int hookDeploy = 1;
    public static int greenLED = 12;


}