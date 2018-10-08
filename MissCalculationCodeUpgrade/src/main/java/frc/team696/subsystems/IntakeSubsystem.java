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

    public int currentMin = 50;
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

        intakeA.set(speed);
        intakeB.set(speed);
    }

    public double intakeACurrent() {
        return Robot.PDP.getCurrent(RobotMap.intakeAChannel);
    }

    public double intakeBCurrent() {
        return Robot.PDP.getCurrent(RobotMap.intakeBChannel);
    }

    public void toggleIntake(boolean bool) {

        intakeSol.set(bool);

    }

    public double getIntakeValue() {
        return intakeA.getMotorOutputPercent();
    }

    @Override
    public void initDefaultCommand() {

    }

}
