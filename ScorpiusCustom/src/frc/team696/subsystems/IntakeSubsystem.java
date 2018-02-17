package frc.team696.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team696.Robot;

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

<<<<<<< HEAD
    public IntakeSubsystem(int intakeA, int intakeB, int intakeSol) {
=======
    public int intakeAChannel;
    public int intakeBChannel;

    /*
        Current Minimum and Average
     */

    public int currentMin = 50; // TODO Check for actual value and adjust correspondingly.
    public double currentAverage;

    public IntakeSubsystem(int intakeA, int intakeB, int intakeAChannel, int intakeBChannel) {
>>>>>>> a0b6b6e79612ab780799d21d55311b52f4848f7d

        /*
            Initialization of TalonSRX Objects
         */

        this.intakeA = new WPI_TalonSRX(intakeA);
        this.intakeB = new WPI_TalonSRX(intakeB);
        this.intakeSol = new Solenoid(intakeSol);

        this.intakeAChannel = intakeAChannel;
        this.intakeBChannel = intakeBChannel;

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
            intakeA.set(speed);
            intakeB.set(speed);
        }

    }

    public double intakeACurrent() {
        return robot.PDP.getCurrent(intakeAChannel);
    }

    public double intakeBCurrent() {
        return robot.PDP.getCurrent(intakeBChannel);
    }

    public void toggleIntake(boolean bool) {

        intakeSol.set(bool);

    }

    @Override
    public void initDefaultCommand() {

    }

}
