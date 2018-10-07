package frc.team696.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import frc.team696.Robot;

/**
 * Add your docs here.
 */
public class AutonomousSubsystem extends PIDSubsystem {
  /**
   * Add your docs here.
   */

  Robot robot = new Robot();

  public AutonomousSubsystem(double setpoint) {
    // Intert a subsystem name and PID values here
    super("AutonomousSubsystem", 1, 0, 0);
    // Use these to get going:
    // setSetpoint() - Sets where the PID controller should move the system
    // to
    // enable() - Enables the PID controller.
    System.out.println(getPIDController().getP());
    setSetpoint(setpoint);
    enable();
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  @Override
  protected double returnPIDInput() {
    // Return your input value for the PID loop
    // e.g. a sensor, like a potentiometer:
    // yourPot.getAverageVoltage() / kYourMaxVoltage;
    return Robot.navX.getYaw();
  }

  @Override
  protected void usePIDOutput(double output) {
    // Use output to drive your system, like a motor
    // e.g. yourMotor.set(output);
    Robot.driveTrainSubsystem.leftRear.set(output);
    System.out.println(output);
  }
}
