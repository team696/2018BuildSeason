package frc.team696.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team696.Robot;
import frc.team696.RobotMap;

public class IntakeSubsystem extends Subsystem {

    /*
        Class Objects
     */

    public static Robot robot = new Robot();

    /*
        Declaration of TalonSRX Objects and PDP Channels
     */

    public WPI_TalonSRX intakeA;
    public WPI_TalonSRX intakeB;
    public Solenoid intakeSol;

    /*
        Current Minimum and Average
     */

    public int currentMin = 50; // TODO Check for actual value and adjust correspondingly.
    public double currentAverage;

    public IntakeSubsystem(int intakeA, int intakeB, int intakeSol) {



        /*
            Initialization of TalonSRX Objects
         */

        this.intakeA = new WPI_TalonSRX(intakeA);
        this.intakeB = new WPI_TalonSRX(intakeB);
        this.intakeSol = new Solenoid(intakeSol);

        /*
            TalonSRX Configuration
         */

        this.intakeA.set(ControlMode.PercentOutput, 0);
        this.intakeB.set(ControlMode.PercentOutput, 0);

    }

    public void runIntake(double speed) {

        currentAverage = (intakeACurrent() + intakeBCurrent()) / 2;
        if(currentAverage > currentMin){
            intakeA.set(0);
            intakeB.set(0);
        }else{
            intakeA.set(-speed);
            intakeB.set(speed);
        }

    }

    public double intakeACurrent() {
        return robot.PDP.getCurrent(RobotMap.intakeAChannel);
    }

    public double intakeBCurrent() {
        return robot.PDP.getCurrent(RobotMap.intakeBChannel);
    }

    public void toggleIntake(boolean bool) {

        intakeSol.set(bool);

    }

    @Override
    public void initDefaultCommand() {

    }

}
