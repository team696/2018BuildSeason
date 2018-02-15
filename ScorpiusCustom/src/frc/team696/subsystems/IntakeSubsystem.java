package frc.team696.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class IntakeSubsystem extends Subsystem {

    /*
        Declaration of TalonSRX Objects
     */

    public WPI_TalonSRX intakeA;
    public WPI_TalonSRX intakeB;
    public Solenoid intakeSol;

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

    public void toggleIntake(boolean bool) {

        intakeSol.set(bool);

    }

    @Override
    public void initDefaultCommand() {

    }

}
