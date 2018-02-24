package frc.team696.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;



public class ClimberSubsystem extends Subsystem {

    WPI_TalonSRX climberA;
    WPI_TalonSRX climberB;
    Solenoid climberSol;

    boolean isDeployed = false;

    int loopNumber = 0;


    public ClimberSubsystem(int climberA, int climberB, int climberSol){

        this.climberA = new WPI_TalonSRX(climberA);
        this.climberB = new WPI_TalonSRX(climberB);
        this.climberSol = new Solenoid(climberSol);

    }

    public void setClimberSpeed(double speed){

        /*
            Both values positive for each climber
         */

        climberA.set(speed);
        climberB.set(speed);
    }

    public void deployHook(boolean bool){

        climberSol.set(bool);

    }

    public void autoClimb(double climberSpeed){

        loopNumber++;

        if(!isDeployed){
            deployHook(true);
            isDeployed = true;
        }

        if(loopNumber >= 10000) {
            setClimberSpeed(climberSpeed);
        }

        System.out.println(loopNumber);
    }

    public void initDefaultCommand() {

    }
}

