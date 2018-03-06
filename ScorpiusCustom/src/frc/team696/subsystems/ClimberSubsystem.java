package frc.team696.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

import static frc.team696.Robot.elevatorSubsystem;


public class ClimberSubsystem extends Subsystem {

    WPI_TalonSRX climberA;
    WPI_TalonSRX climberB;
    Solenoid climberSol;

    boolean isDeployed = false;
    boolean isHomed = false;

    int loopNumber = 0;


    public ClimberSubsystem(int climberA, int climberB, int climberSol){

        this.climberA = new WPI_TalonSRX(climberA);
        this.climberB = new WPI_TalonSRX(climberB);
        this.climberSol = new Solenoid(climberSol);

    }

    public void setClimberSpeed(double speed){

        climberA.set(speed);
        climberB.set(-speed);
    }

    public void deployHook(boolean bool){

        climberSol.set(bool);

    }

//    public void autoClimb(double climberSpeed){
//
////        loopNumber++;
////        Timer time = new Timer();
//
////        time.start();
//
//
//        if(!isDeployed){
//            deployHook(true);
//            isDeployed = true;
//
//        }
//
//        if(isDeployed && elevatorSubsystem.elevator.getSelectedSensorPosition(0) > 5) {
//            elevatorSubsystem.manualMoveElevator(-0.5);
//            if(elevatorSubsystem.elevator.getSelectedSensorPosition(0) < 5){
//                elevatorSubsystem.manualMoveElevator(0);
//                isHomed = true;
//            }
//        }
//
//        if(isDeployed && isHomed ){
//            setClimberSpeed(climberSpeed);
//            deployHook(false);
//
//
//        }
//
//        System.out.println(loopNumber);
//    }

    public void initDefaultCommand() {

    }
}

