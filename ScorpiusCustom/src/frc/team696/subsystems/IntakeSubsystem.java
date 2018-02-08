package frc.team696.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

public class IntakeSubsystem extends Subsystem {

    /*
        Declaration of TalonSRX Objects
     */

    WPI_TalonSRX intakeA;
    WPI_TalonSRX intakeB;

    public IntakeSubsystem(int intakeA, int intakeB) {

        /*
            Initialization of TalonSRX Objects
         */

        this.intakeA = new WPI_TalonSRX(intakeA);
        this.intakeB = new WPI_TalonSRX(intakeB);

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

    @Override
    public void initDefaultCommand() {

    }

}
