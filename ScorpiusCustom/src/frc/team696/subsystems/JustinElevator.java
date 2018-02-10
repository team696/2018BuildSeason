package frc.team696.subsystems;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;


public class JustinElevator extends Subsystem {

    /**
     * @Author: Justin Gonzales
     */

    public static TalonSRX elevatorMotor;

    /* Motion Magic */
    int motionCruiseVelocity = 0;   /** Sensor Units Per 100 ms **/
    int motionAcceleration = 0;


    /* PID */
    double kP = 0;
    double kI = 0;
    double kD = 0;
    double kF = 0;

    int slotIdx = 0;
    int pidX = 0;

    /* Targets */

    int Ground;
    int Target2;


    public JustinElevator(int elevator){


        this.elevatorMotor = new TalonSRX(elevator);
        this.elevatorMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0,20);

        /* Motion Magic- Magic */

        this.elevatorMotor.set(ControlMode.MotionMagic, 0);
        this.elevatorMotor.configMotionCruiseVelocity(motionCruiseVelocity, 20);
        this.elevatorMotor.configMotionAcceleration(motionAcceleration, 20);

        /* PID */

        this.elevatorMotor.config_kP(slotIdx, kP, 20);
        this.elevatorMotor.config_kI(slotIdx, kI, 20);
        this.elevatorMotor.config_kD(slotIdx, kD, 20);
        this.elevatorMotor.config_kF(slotIdx, kF, 20);



    }

    public void setPosition(String position){
       String Position = position;

        switch(Position){

            case "Ground":
                elevatorMotor.set(ControlMode.MotionMagic, Ground);
//                this.elevatorMotor.configMotionCruiseVelocity(motionCruiseVelocity, 20);
//                this.elevatorMotor.configMotionAcceleration(motionAcceleration, 20);
                break;


            default:
                System.out.println("Error");
                break;

        }
    }


    public void initDefaultCommand() {

    }
}

