package frc.team696.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ElevatorSubsystem extends Subsystem {

    /**
     * POSITIVE OUTPUT -> UP
     * NEGATIVE OUTPUT -> DOWN
     */

    /*
        Declaration of Elevator Objects
     */

    public TalonSRX elevator;
    public Solenoid elevatorSol;
    public Solenoid discBrake;

    /*
        Variable Setup and Initialization
     */

    int pidIdx = 0;
    int slotIdx = 0;
    int timeoutMs = 20;

    int elevatorDeviceID;

    double kP = 0,
            kI = 0,
            kD = 0,
            kF = 0;

    public ElevatorSubsystem(int elevator, int elevatorSol, int discBrake) {

        /*
            Initialization of Elevator Objects
         */

        this.elevator = new TalonSRX(elevator);
        this.elevatorSol = new Solenoid(elevatorSol);
        this.discBrake = new Solenoid(discBrake);

        elevatorDeviceID = this.elevator.getDeviceID();

        /*
            TalonSRX Configuration
         */

        // Elevator A Configuration

        this.elevator.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, pidIdx, timeoutMs);
        this.elevator.setNeutralMode(NeutralMode.Brake);
        this.elevator.set(ControlMode.Position, 0);
        this.elevator.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, timeoutMs);
        this.elevator.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, timeoutMs);

        this.elevator.config_kP(slotIdx, kP, timeoutMs);
        this.elevator.config_kI(slotIdx, kI, timeoutMs);
        this.elevator.config_kD(slotIdx, kD, timeoutMs);
        this.elevator.config_kF(slotIdx, kF, timeoutMs);

    }

    public void homeElevator() {

//        elevator.set(ControlMode.PercentOutput, 0.15);

        elevator.set(ControlMode.PercentOutput, -0.3);
        if(elevator.getSensorCollection().isRevLimitSwitchClosed()) {
            elevator.set(ControlMode.PercentOutput, 0);
            elevator.setSelectedSensorPosition(0, pidIdx, timeoutMs);
            elevator.set(ControlMode.Position, 0);
        }

    }

    public void manualMove(double speed) {

        elevator.set(ControlMode.PercentOutput, speed);

    }

    public void toggleElevatorPos(boolean bool){

        elevatorSol.set(bool);

    }

    @Override
    public void initDefaultCommand() {

    }

}
