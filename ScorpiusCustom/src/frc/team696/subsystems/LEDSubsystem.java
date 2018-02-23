package frc.team696.subsystems;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;

public class LEDSubsystem extends Subsystem {

    public SPI ledStrip;

    public LEDSubsystem() {
        this.ledStrip = new SPI(SPI.Port.kOnboardCS2);
    }

    @Override
    public void initDefaultCommand() {

    }

}
