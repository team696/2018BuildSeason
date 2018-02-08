package org.usfirst.frc.team696.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;



public class IntakeSubsystem extends Subsystem {

    WPI_TalonSRX leftIntake;
    WPI_TalonSRX rightIntake;
    SpeedControllerGroup intake;
//    double intakeSpeed = 0;
//    double outtakeSpeed = 0;



    public IntakeSubsystem(int left, int right){

        this.leftIntake = new WPI_TalonSRX(left);
        this.rightIntake = new WPI_TalonSRX(right);
        this.intake = new SpeedControllerGroup(leftIntake, rightIntake);

    }

    public void run(double speed){
        intake.set(speed);
    }

    public void runLeftIntake(double speed){
        leftIntake.set(speed);
    }

    public void runRightIntake(double speed){
        rightIntake.set(speed);
    }


    public void initDefaultCommand() {



    }
}

