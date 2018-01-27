package org.usfirst.frc.team696.robot.subsystems;


import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.command.Subsystem;

public class
GreenLEDClimber extends Subsystem {


    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    DigitalOutput greenLED;

    public GreenLEDClimber(int greenLED) {
        this.greenLED = new DigitalOutput(greenLED);
    }


    public void initDefaultCommand() {
        // TODO: Set the default command, if any, for a subsystem here. Example:
        //    setDefaultCommand(new MySpecialCommand());
    }
    public void set(boolean state) {
        greenLED.set(state);


        }

    public boolean get() { return greenLED.get(); }
}

