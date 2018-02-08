package frc.team696.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ElevatorSubsystem extends Subsystem {

    /*
        Declaration of Elevator Objects
     */

    TalonSRX elevatorA;
    TalonSRX elevatorB;

    /*
        Variable Setup and Initialization
     */

    int pidIdx = 0;
    int slotIdx = 0;
    int timoutMs = 20;

    int elevatorADeviceID;

    double kP = 0,
            kI = 0,
            kD = 0,
            kF = 0;

    public ElevatorSubsystem(int elevatorA, int elevatorB) {

        /*
            Initialization of Elevator Objects
         */

        this.elevatorA = new TalonSRX(elevatorA);
        this.elevatorB = new TalonSRX(elevatorB);

        elevatorADeviceID = this.elevatorA.getDeviceID();

        /*
            TalonSRX Configuration
         */

        // Elevator A Configuration

        this.elevatorA.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, pidIdx, timoutMs);
        this.elevatorA.set(ControlMode.Position, 0);

        this.elevatorA.config_kP(slotIdx, kP, timoutMs);
        this.elevatorA.config_kI(slotIdx, kI, timoutMs);
        this.elevatorA.config_kD(slotIdx, kD, timoutMs);
        this.elevatorA.config_kF(slotIdx, kF, timoutMs);

        // Elevator B Configuration

        this.elevatorB.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, pidIdx, timoutMs);
        this.elevatorB.set(ControlMode.Follower, elevatorADeviceID);
        // Unknown if Elevator B will be using the Follower control mode, or Position. Currently set to Follower.



    }

    @Override
    public void initDefaultCommand() {

    }

}
