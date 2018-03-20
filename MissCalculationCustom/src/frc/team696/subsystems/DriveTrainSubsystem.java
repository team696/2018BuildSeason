package frc.team696.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.team696.utilities.PIDController;

public class DriveTrainSubsystem extends Subsystem {

    public WPI_TalonSRX leftRear;
    public  WPI_TalonSRX leftMid;
    public  WPI_TalonSRX leftFront;

    public  WPI_TalonSRX rightRear;
    public  WPI_TalonSRX rightMid;
    public  WPI_TalonSRX rightFront;

    private DifferentialDrive drive;

//    public int leftRearDeviceID = leftRear.getDeviceID();
//    public int rightRearDeviceID = rightRear.getDeviceID();

    private int timeoutMs = 20;
    private int pidIdx = 0;

    /*
        PID Values
     */

    public PIDController driveStraightPID;

    private double kP = 0.07;
    private double kI = 0.01;
    private double kD = 0;
    private double kAlpha = 0.05;

    public DriveTrainSubsystem(int leftRear, int leftMid, int leftFront,
                               int rightRear, int rightMid, int rightFront) {

        /*
            Initializing TalonSRX Objects and PID Controller
         */

        this.leftRear = new WPI_TalonSRX(leftRear);
        this.leftMid = new WPI_TalonSRX(leftMid);
        this.leftFront = new WPI_TalonSRX(leftFront);

        this.rightRear = new WPI_TalonSRX(rightRear);
        this.rightMid = new WPI_TalonSRX(rightMid);
        this.rightFront = new WPI_TalonSRX(rightFront);

        this.driveStraightPID = new PIDController(kP, kI, kD, kAlpha);

        /*
            Left Side Configuration
         */

        this.leftRear.configPeakOutputForward(1, timeoutMs);
        this.leftRear.configPeakOutputReverse(-1, timeoutMs);
        this.leftRear.set(ControlMode.PercentOutput, 0);
        this.leftMid.set(ControlMode.Follower, this.leftRear.getDeviceID());
        this.leftFront.set(ControlMode.Follower, this.leftRear.getDeviceID());

        this.leftRear.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, pidIdx, timeoutMs);
//        this.leftRear.setInverted(true);
        this.leftRear.setSensorPhase(true);

        /*
            Right Side Configuration
         */

        this.leftRear.configPeakOutputReverse(-1, timeoutMs);
        this.leftRear.configPeakOutputForward(1, timeoutMs);
        this.rightFront.set(ControlMode.PercentOutput, 0);
        this.rightRear.set(ControlMode.Follower, this.rightFront.getDeviceID());
        this.rightMid.set(ControlMode.Follower, this.rightFront.getDeviceID());

        this.rightFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, pidIdx, timeoutMs);

        /*
            Differential Drive Configuration
         */

        this.drive = new DifferentialDrive(this.leftRear, this.rightFront);



    }

    public void tankDrive(double leftDrive, double rightDrive) {

        drive.tankDrive(leftDrive, rightDrive);

    }

    @Override
    public void initDefaultCommand() {

    }

}
