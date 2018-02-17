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

    double elevatorTarget;

    double groundPosition;
    double switchPosition;
    double scalePosition;
    double climbPosition;
    double homePosition = 0;

    int sensorUnitsPer100ms;
    int sensorUnitsPer100msPerSec;


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

        this.elevator.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, pidIdx, timeoutMs); // Feedback Sensor Config
        this.elevator.setNeutralMode(NeutralMode.Brake); // What happens when the limit switch is closed
        this.elevator.configMotionCruiseVelocity(sensorUnitsPer100ms, timeoutMs); // How fast it moves at terminal velocity
        this.elevator.configMotionAcceleration(sensorUnitsPer100msPerSec, timeoutMs); // How fast it gets to terminal velocity
        this.elevator.set(ControlMode.MotionMagic, 0); // Configuring the control mode to motion magic
        this.elevator.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, timeoutMs);
        // Configuration of the Forward Limit Switch Source
        this.elevator.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, timeoutMs);
        // Configuration of the Reverse Limit Switch Source

        // Elevator PID Values Configuration

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

    public void manualMoveElevator(double speed) {

        /*
            Moves elevator up or down using percent output. No PID Controller involved
         */

        elevator.set(ControlMode.PercentOutput, speed);

    }

    public void toggleElevatorPos(boolean bool){

        /*
            Toggles the solenoid value for the elevator, moving it back or forward
         */

        elevatorSol.set(bool);

    }

    public void moveToPos(String position) {

        switch(position){

            case "ground":
                elevatorTarget = groundPosition;

            case "switch":
                elevatorTarget = switchPosition;

            case "scale":
                elevatorTarget = scalePosition;

            case "climb":
                elevatorTarget = climbPosition;

            default:
                elevatorTarget = homePosition;

        }

        elevator.configMotionCruiseVelocity(sensorUnitsPer100ms, timeoutMs);
        elevator.configMotionAcceleration(sensorUnitsPer100msPerSec, timeoutMs);
        elevator.set(ControlMode.MotionMagic, elevatorTarget);

    }

    @Override
    public void initDefaultCommand() {

    }

}
