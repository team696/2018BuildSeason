/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team696;

public class RobotMap {

    /*
        Drive Train Ports
     */

    // Left Side

    public static int leftRear = 16;
    public static int leftMid = 15;
    public static int leftFront = 14;

    // Right Side

    public static int rightRear = 3;
    public static int rightMid = 2;
    public static int rightFront = 1;

    /*
        RGB Address
     */

    public static byte deviceAddress = 0x29;
    public static byte enableAddress = 0x00;
    public static byte commandBit = (byte) 0x80;
    public static byte enable_AEN = 0x02;
    public static byte enable_PON = 0x01;

    // Channel Data

    public static byte clearDataL = 0x14;
    public static byte clearDataH = 0x15;
    public static byte redDataL = 0x16;
    public static byte redDataH = 0x17;
    public static byte greenDataL = 0x18;
    public static byte greenDataH = 0x19;
    public static byte blueDataL = 0x1A;
    public static byte blueDataH = 0x1B;

    /*
        Intake Ports
     */

    public static int intakeA = 8;
    public static int intakeB = 9;
    public static int intakeSol = 1;

    /*
        Elevator Ports
     */

    public static int elevator = 13;
    public static int elevatorSol = 0;
    public static int discBrake = 3;

    /*
        Climber Ports
     */

    public static int climberA = 12;
    public static int climberB = 5;
    public static int climberSol = 2;

}

