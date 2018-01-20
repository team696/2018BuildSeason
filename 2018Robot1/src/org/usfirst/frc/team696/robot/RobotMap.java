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
    public static int leftFront;
    public static int leftMid;
    public static int leftRear;
    public static int rightFront;
    public static int rightMid;
    public static int rightRear;

    /*
    Elevator
     */

    public static int leftIntake = 1;
    public static int rightIntake = 2;
    public static int leftElevator = 3;
    public static int rightElevator = 4;

    /*
    Climber
     */

    public static int leftClimber = 1;
    public static int rightClimber = 3;

}