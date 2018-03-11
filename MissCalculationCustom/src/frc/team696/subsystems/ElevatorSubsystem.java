package frc.team696.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team696.utilities.PIDController;

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

    double kP = 0.6, // 2.8
            kI = 0, // 0.000000091
            kD = 0, // 0.00000000000000003
            kF = 1.5; // 4.00987442

    public double elevatorTarget;
    private double elevatorTarget2;

    public double intakePosition = 1;
    public double switchPosition = 9090;
    public double lowPosition = 10000;
    public double midPosition;
    public double highPosition = 29000;
    public double climbPosition = 21420;
    public double homePosition = 0;

    String currentMovePos;
    String oldMovePos;

    double error;
    double error2;
    double loopNumber;

    int sensorUnitsPer100ms = 3500;
    int sensorUnitsPer100msPerSec = 3000;

    public PIDController pidController;



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
        this.elevator.setSensorPhase(true); // Sets encoder to inverted, to make values positive.
        this.elevator.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, timeoutMs);
        // Configuration of the Forward Limit Switch Source
        this.elevator.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, timeoutMs);
        // Configuration of the Reverse Limit Switch Source

        // Elevator PID Values Configuration

        this.elevator.config_kP(slotIdx, kP, timeoutMs);
        this.elevator.config_kI(slotIdx, kI, timeoutMs);
        this.elevator.config_kD(slotIdx, kD, timeoutMs);
        this.elevator.config_kF(slotIdx, kF, timeoutMs);

        pidController = new PIDController(kP, kI, kD, kF);

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

            case "intake":
                elevatorTarget = intakePosition;
                break;

            case "switch":
                elevatorTarget = switchPosition;
                break;

            case "low":
                elevatorTarget = lowPosition;
                break;

            case "high":
                elevatorTarget = highPosition;
                break;

            case "climb":
                elevatorTarget = climbPosition;
                break;

            default:
                elevatorTarget = homePosition;
                break;

        }

        error = elevatorTarget - elevator.getSelectedSensorPosition(pidIdx);

        if(error < 200){
            loopNumber++;
            if(loopNumber > 0){
                discBrake.set(false);
                elevator.set(ControlMode.Disabled, 0);
            }
        }

        currentMovePos = position;
        if(currentMovePos == (position) && !(oldMovePos == (position))){
            loopNumber = 0;
            discBrake.set(true);
            elevator.configMotionCruiseVelocity(sensorUnitsPer100ms, timeoutMs);
            elevator.configMotionAcceleration(sensorUnitsPer100msPerSec, timeoutMs);
            elevator.set(ControlMode.MotionMagic, elevatorTarget);
            System.out.println(elevatorTarget);
        }
        oldMovePos = currentMovePos;

    }

    public double elevatorError() {
        return elevator.getClosedLoopError(pidIdx);
    }

    @Override
    public void initDefaultCommand() {

    }

}
