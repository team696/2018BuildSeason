
package frc.team696.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import frc.team696.Robot;

public class NewDriveCommand extends Command {

  PIDController pidController = Robot.autonomousSubsystem.getPIDController();

  // double Kp = 1.0;
  // double Ki = 0.0001;
  // double Kd = 0;
  // double Kf = 1.0;
  // double speed;
  // double myVal;

  // PIDSourceType pidSourceType;
  // PIDSource pidSource;
  // PIDOutput pidOutput;

  double targetAngle;

  public NewDriveCommand(double targetAngle) {
    requires(Robot.autonomousSubsystem);
    this.targetAngle = targetAngle;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

    // pidSourceType = Robot.navX.getPIDSourceType();
    // pidSource = new PIDSource(){
    
    //   @Override
    //   public void setPIDSourceType(PIDSourceType pidSource) {
    //     pidSource = pidSourceType;
    //   }
    
    //   @Override
    //   public double pidGet() {
    //     return Robot.navX.getYaw();
    //   }
    
    //   @Override
    //   public PIDSourceType getPIDSourceType() {
    //     return null;
    //   }
    // };

    // pidOutput = new PIDOutput(){
    
    //   @Override
    //   public void pidWrite(double output) {
    //     Robot.driveTrainSubsystem.leftRear.set(output);
    //     Robot.driveTrainSubsystem.rightFront.set(output);
    //   }
    // };


    // pidController = new PIDController(Kp, Ki, Kd, Kf, pidSource, pidOutput);
    // pidController.setSetpoint(targetAngle);
    // pidController.setOutputRange(-1, 1);
    // pidController.enable();

    Robot.autonomousSubsystem.setSetpoint(targetAngle);
    Robot.autonomousSubsystem.enable();
    

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // pidController.
    // pidController.setPID(Kp, Ki, Kd);
    // System.out.println(pidController.get() + " " + pidController.getError());
    System.out.println(pidController.getError() + " " + pidController.get());

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }

  public double getVal() {
    return 0;
  }

}
