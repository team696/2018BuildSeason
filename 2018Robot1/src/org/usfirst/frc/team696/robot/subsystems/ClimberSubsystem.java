package org.usfirst.frc.team696.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;



public class ClimberSubsystem extends Subsystem {

    WPI_TalonSRX motor1;
    WPI_TalonSRX motor2;
    SpeedControllerGroup motors;


    public ClimberSubsystem(int motorA, int motorB){

        this.motor1 =  new WPI_TalonSRX(motorA);
        this.motor2 = new WPI_TalonSRX(motorB);
        this.motors = new SpeedControllerGroup(motor1, motor2);

    }

    public void setClimberSpeed(double speed){
        this.motors.set(speed);

    }


    public void initDefaultCommand() {

    }
}

