package org.usfirst.frc.team696.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team696.robot.utilities.PID;


public class ElevatorSubsystem extends Subsystem {

    /*
    motor controllers
     */
    TalonSRX motor1;
    TalonSRX motor2;
    TalonSRX motor3;
    TalonSRX motor4;

    /*
    PID values
     */
    public static double kP = 0;
    public static double kI = 0;
    public static double kD = 0;
    public static double kF = 0;
    public static PID pid;

    public static int pidIdx = 0;

    /*
    Preset
     */
    public static double groundTarget = 0;
    public static double switchTarget = 0;
    public static double scaleTarget = 0;
    public static double climbTarget = 0;

    /*

     */


    public ElevatorSubsystem(int motor1, int motor2, int motor3, int motor4){

        this.motor1 = new TalonSRX(motor1);
        this.motor2 = new TalonSRX(motor2);
        this.motor3 = new TalonSRX(motor3);
        this.motor4 = new TalonSRX(motor4);

        this.pid = new PID(kP,kI,kD,kF);


//        this.motor2.set(ControlMode.Position, )
//


    }

    public void presets(){
        motor2.set(ControlMode.Position, kP);




    }



    public void initDefaultCommand() {

    }
}

