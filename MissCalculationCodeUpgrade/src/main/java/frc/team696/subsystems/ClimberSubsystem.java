package frc.team696.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

import static frc.team696.Robot.elevatorSubsystem;

public class ClimberSubsystem extends Subsystem {


    WPI_TalonSRX climberA;
    WPI_TalonSRX climberB;
    Solenoid climberSol;

    boolean isDeployed = false;
    boolean isHomed = false;

    public int loopNumber = 0;
    public double manualMoveSpeed = -0.25;
    public int resetValue = 10;


    public ClimberSubsystem(int climberA, int climberB, int climberSol){

        this.climberA = new WPI_TalonSRX(climberA);
        this.climberB = new WPI_TalonSRX(climberB);
        this.climberSol = new Solenoid(climberSol);

    }

    public void setClimberSpeed(double speed){

        climberA.set(speed);
        climberB.set(-speed);

    }

    public void deployHook(boolean bool) {

        climberSol.set(bool);

    }

    public void autoClimb(double climberSpeed){

        loopNumber++;

        deployHook(true);
        isDeployed = true;

        if(isDeployed && loopNumber > 15) {
            if(revLimitSwitch()){
                elevatorSubsystem.manualMoveElevator(0);
                elevatorSubsystem.discBrake.set(false);
                isHomed = true;
            }else{
                isHomed = false;

            }
            elevatorSubsystem.discBrake.set(true);
            elevatorSubsystem.manualMoveElevator(-0.5);
            setClimberSpeed(climberSpeed);
        }
    }

    public boolean revLimitSwitch() {
        return elevatorSubsystem.elevator.getSensorCollection().isRevLimitSwitchClosed();
    }

    public void initDefaultCommand() {

    }
}

