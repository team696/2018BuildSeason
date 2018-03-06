package frc.team696.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team696.Robot;
import frc.team696.utilities.PIDController;

/**
 * @Author: Ismail Hasan
 */

public class DriveCommand extends Command {

    /*
        Declaration of Variables and Objects
     */

    // Constructor Variables

    public double targetDistance;
    public double targetDirection;
    public double maxSpeed;
    public double maxTurn;

    // Execution Variables

    public double currentDistance;
    public double currentDirection;
    public double distanceError;
    public double directionError;

    public int pidIdx = 0;
    public double leftRearEnc;
    public double rightRearEnc;

    public double speed;
    public double wheel;
    public double leftDrive;
    public double rightDrive;

    /*
        PID Controller Creation and Configuration Variables
     */

    // Distance PID Values

    private double kPA = 0.07;
    private double kIA = 0.0001;
    private double kDA = 0;
    private double kAlphaA = 0.5;

    // Working tests

    // Direction PID Values

    private double kPB = 0.025; // try 0.1, with all others at zero, and 1 with I at 0.025, rest at 0
    private double kIB = 0;
    private double kDB = 0;
    private double kAlphaB = 0.5;

    PIDController distancePID;
    PIDController directionPID;

    /*
        Constructor
     */

    public DriveCommand(double targetDistance, double targetDirection, double maxSpeed, double maxTurn) {
        this.targetDirection = targetDirection;
        this.targetDistance = targetDistance;
        this.maxSpeed = maxSpeed;
        this.maxTurn = maxTurn;
    }

    @Override
    protected void initialize() {

        /*
            Set PID Values for Direction and Distance
         */

        distancePID = new PIDController(kPA, kIA, kDA, kAlphaA);
        directionPID = new PIDController(kPB, kIB, kDB, kAlphaB);

    }

    @Override
    protected void execute() {

        /*
            Current Distance and Error initialization
         */

        leftRearEnc = (double) Robot.driveTrainSubsystem.leftRear.getSelectedSensorPosition(pidIdx) / 200;
        // Divided by 200 to convert encoder units to inches
        rightRearEnc = (double) -Robot.driveTrainSubsystem.rightRear.getSelectedSensorPosition(pidIdx) / 200;
        // Diviced by 200 to convert encoder units to inches, and multiplied by -1 to account for inversion of encoder.

        currentDistance = -(leftRearEnc + rightRearEnc) / 2;
        // Making currentDistance negative to account for inversion.
        distanceError = targetDistance - currentDistance;

        /*
            Current Direction and Error Initialization
         */

        currentDirection = Robot.navX.getYaw();
        directionError = targetDirection - currentDirection;

//        /*
//            Accounting for Gyro reaching it's limits at -180 and 180
//         */
//
//        if(directionError > 180){
//            directionError = directionError - 360;
//        }
//
//        if(directionError < 180) {
//            directionError = directionError + 360;
//        }

        /*
            Setting errors to the PID Controllers
         */

        distancePID.setError(distanceError);
        directionPID.setError(directionError);

        /*
            Getting PID Values for both Distance and Direction
         */

        speed = distancePID.getValue();
        wheel = directionPID.getValue();

        /*
            Accounting for maxSpeed and maxTurn constructor variables
         */

        if(speed > maxSpeed) {
            speed = maxSpeed;
        }

        if(speed < -maxSpeed) {
            speed = -maxSpeed;
        }

        if(wheel > maxTurn) {
            wheel = maxTurn;
        }

        if(wheel < -maxTurn) {
            wheel = -maxTurn;
        }

        /*
            Account for if error is too big and PID Values are too small
         */

        if(speed < 0.1){
            speed = speed * 4;
        }

        /*
            Setting up the drive functionality
         */

        leftDrive = speed + wheel;
        rightDrive = speed - wheel;

        Robot.driveTrainSubsystem.tankDrive(leftDrive, rightDrive);

        /*
            Output to DriverStation
         */

        System.out.println(directionError + "             " + currentDistance);

    }

    @Override
    protected boolean isFinished() {
        /*
            Making it possible for command to finish
         */

        // Can only finish the command if the error for both distance and direction are less than 2

        if(Math.abs(distanceError) < 2 && Math.abs(directionError) < 2){
            return true;
        }
        return false;
    }

}
