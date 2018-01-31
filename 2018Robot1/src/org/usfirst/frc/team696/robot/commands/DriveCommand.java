package org.usfirst.frc.team696.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team696.robot.Robot;
import org.usfirst.frc.team696.robot.utilities.PID;


public class DriveCommand extends Command {

    public static double targetDistance = 0;
    double errorDistance = 0;
    double currentDistance = 0;
    double errorDirection = 0;
    public static double tempTargetDirection = 0;
    double maxSpeed = 0;
    double rampSpeed = 0;


    double speed = 0;
    double turn = 0;
    double leftValue = 0;
    double rightValue = 0;
    Timer isFinished = new Timer();

    /*
    distance PID
     */
    public static double kPa = 0;
    public static double kIa = 0;
    public static double kDa = 0;
    public static double kFa = 0;

        /*
        direction PID
         */

    public static double kPb = 0;
    public static double kIb = 0;
    public static double kDb = 0;
    public static double kFb = 0;

    PID distancePID = new PID(kPa, kIa, kDa, kFa);
    PID directionPID = new PID(kPb , kIb , kDb, kFa);


    public DriveCommand(double distance, double direction, double maxSpeed, double rampSpeed) {

        this.targetDistance = distance;
        this.tempTargetDirection = direction;
        this.maxSpeed = maxSpeed;
        this.rampSpeed = rampSpeed;

    }



    /**
     * The initialize method is called just before the first time
     * this Command is run after being started.
     */
    @Override
    protected void initialize() {
//        Robot.leftDriveEncoder.reset();
//        Robot.rightDriveEncoder.reset();
        tempTargetDirection = Robot.targetDirection +  Robot.navX.getYaw();


    }


    /**
     * The execute method is called repeatedly when this Command is
     * scheduled to run until this Command either finishes or is canceled.
     */
    @Override
    protected void execute() {

    }


    @Override
    protected boolean isFinished() {
        return false;
    }


    /**
     * Called once when the command ended peacefully; that is it is called once
     * after {@link #isFinished()} returns true. This is where you may want to
     * wrap up loose ends, like shutting off a motor that was being used in the
     * command.
     */
    @Override
    protected void end() {

    }


    /**
     * <p>
     * Called when the command ends because somebody called {@link #cancel()} or
     * another command shared the same requirements as this one, and booted it out. For example,
     * it is called when another command which requires one or more of the same
     * subsystems is scheduled to run.
     * </p><p>
     * This is where you may want to wrap up loose ends, like shutting off a motor that was being
     * used in the command.
     * </p><p>
     * Generally, it is useful to simply call the {@link #end()} method within this
     * method, as done here.
     * </p>
     */
    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
