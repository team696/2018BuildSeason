package org.usfirst.frc.team696.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team696.robot.Robot;
import org.usfirst.frc.team696.robot.RobotMap;
import org.usfirst.frc.team696.robot.utilities.Constants;


public class ClimberSubsystemPID extends Subsystem {

    TalonSRX master;
    TalonSRX slave;
    Solenoid hookDeploy;
    int pidIdx = 0;


   double kP = 0;
   double kI = 0;
   double kD = 0;
   double kF = 0;

   double speed = 0;


   public ClimberSubsystemPID(int motor1, int motor2, int hookDeploy){

       this.master = new TalonSRX(motor1);
       this.slave = new TalonSRX(motor2);
       this.hookDeploy = new Solenoid(hookDeploy);


       this.master.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, pidIdx, 20);
       this.slave.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, pidIdx, 20);

       this.master.set(ControlMode.Velocity, 0 );
       this.slave.set(ControlMode.Follower, 0);

       this.master.config_kP(0, kP, 20);
       this.master.config_kI(0, kI, 20);        /**i, d, f probably not necessary */
       this.master.config_kD(0, kD, 20);
       this.master.config_kF(0,kF,20);


   }

   public void setClimberSpeed(double targetSpeed) {

       master.set(ControlMode.Velocity, targetSpeed);
       slave.set(ControlMode.Follower, master.getDeviceID());

       Constants.isClimbing = true;

   }

   public void setClimberOff(){

       master.set(ControlMode.Disabled, 0);

       Constants.isClimbing = false;

       }



    public void setPID(double p, double i, double d, double f){

        master.config_kP(0, p, 20);
        master.config_kI(0, i, 20);
        master.config_kD(0, d, 20);
        master.config_kF(0, f, 20);
    }


    public void autoClimb(){

       if(!Constants.isDeployed){
           hookDeploy();
       }
       setClimberSpeed(speed);

    }


    public void hookDeploy(){
       hookDeploy.set(true);
       Constants.isDeployed = true;

    }

    public void initDefaultCommand() {

    }
}

