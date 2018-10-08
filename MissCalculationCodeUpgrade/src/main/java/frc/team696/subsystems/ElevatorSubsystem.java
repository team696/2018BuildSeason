package frc.team696.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class
ElevatorSubsystem extends Subsystem {

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

    double kP = 0.5, // 2.8
            kI = 0.001, // 0.000000091
            kD = 0, // 0.00000000000000003
            kF = 0; // 4.00987442

    public double elevatorTarget;

    public double intakePosition = 1;
    public double switchPosition = 9090;
    public double lowPosition = 21068;
    public double midPosition = 23970;
    public double highPosition = 29000;
    public double highTiltPosition = 26000;
    public double climbPosition = 21420;
    public double homePosition = 0;

    String currentMovePos;
    String oldMovePos;

    public double error;
    double loopNumber;

    int sensorUnitsPer100ms = 3500;
    int sensorUnitsPer100msPerSec = 3000;




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
        this.elevator.setNeutralMode(NeutralMode.Brake);
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

            case "intake":
                elevatorTarget = intakePosition;
                break;

            case "switch":
                elevatorTarget = switchPosition;
                break;

            case "low":
                elevatorTarget = lowPosition;
                break;

            case "mid":
                elevatorTarget = midPosition;
                break;

            case "high":
//                elevatorTarget = highPosition;
//                break;
                if(!elevatorSol.get()){
                    elevatorTarget = highTiltPosition;
                }else{
                    elevatorTarget = highPosition;
                }
                break;

            case "climb":
                elevatorTarget = climbPosition;
                break;

            default:
                elevatorTarget = homePosition;
                break;

        }

        error = elevatorTarget - elevator.getSelectedSensorPosition(pidIdx);

        if(Math.abs(error) < 300){
            discBrake.set(false);
            elevator.neutralOutput();
        }

        currentMovePos = position;
        if(currentMovePos == (position) && !(oldMovePos == (position)) || Math.abs(error) > 200){
            loopNumber = 0;
            discBrake.set(true);
            elevator.configMotionCruiseVelocity(sensorUnitsPer100ms, timeoutMs);
            elevator.configMotionAcceleration(sensorUnitsPer100msPerSec, timeoutMs);
            elevator.set(ControlMode.MotionMagic, elevatorTarget);
            if(elevator.getMotorOutputPercent() > 0){
                elevator.config_kI(slotIdx, 0.001, timeoutMs);
            }else if(elevator.getMotorOutputPercent() < 0){
                elevator.config_kI(slotIdx, 0.0001, timeoutMs);
            }
            System.out.println(elevatorTarget);
        }
        oldMovePos = currentMovePos;

    }

    public double elevatorError() {
        return elevator.getClosedLoopError(pidIdx);
    }

    public void zeroElevator() {
        elevator.setSelectedSensorPosition(0, pidIdx, timeoutMs);
    }

    @Override
    public void initDefaultCommand() {

    }

}
