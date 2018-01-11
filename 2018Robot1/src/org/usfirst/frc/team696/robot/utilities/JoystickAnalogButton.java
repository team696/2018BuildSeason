package org.usfirst.frc.team696.robot.utilities;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Button;

public class JoystickAnalogButton extends Button {

	double minVal = 0;
	double maxVal = 0;
	GenericHID joystick;
	int axisNum;
	
	public JoystickAnalogButton(GenericHID joystick, int axisNum, double minVal, double maxVal) {
		this.joystick = joystick;
		this.axisNum = axisNum;
		System.out.println();
		this.minVal = minVal;
		this.maxVal = maxVal;
	}
	
//	public boolean grab(){
//		return (minVal < joystick.getRawAxis(axisNum)) && (joystick.getRawAxis(axisNum) < maxVal);
//	}

	public boolean get() {
		// TODO Auto-generated method stub
		return (minVal < joystick.getRawAxis(axisNum) && joystick.getRawAxis(axisNum) < maxVal);

	}

}
