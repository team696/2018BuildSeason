/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team696.robot;

import com.kauailabs.nav6.frc.IMU;
import com.kauailabs.nav6.frc.IMUAdvanced;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team696.robot.autonomousCommands.CenterPosition;
import org.usfirst.frc.team696.robot.subsystems.*;
import org.usfirst.frc.team696.robot.utilities.RGBSensor;
import org.usfirst.frc.team696.robot.utilities.Constants;

/**
 * @Authors Ismail Hasan, Justin Gonzales, Ruben Erkanian
 */
public class Robot extends TimedRobot {

	public static I2C rgbSensor;

	public static final DriveTrainSubsystem driveTrainSubsystem = new DriveTrainSubsystem(RobotMap.leftRear, RobotMap.leftMid, RobotMap.leftFront,
                                                                                          RobotMap.rightRear, RobotMap.rightMid, RobotMap.rightFront);
	public static final IntakeSubsystem intakeSubsystem = new IntakeSubsystem(RobotMap.leftIntake, RobotMap.rightIntake);
	public static final ElevatorSubsystem elevatorSubsystem = new ElevatorSubsystem(RobotMap.leftElevator, RobotMap.rightElevator);
	public static final ClimberSubsystem climberSubsystem = new ClimberSubsystem(RobotMap.leftClimber, RobotMap.rightClimber);
	public static final ClimberSubsystemPID climberSubsystemPID = new ClimberSubsystemPID(RobotMap.leftClimber, RobotMap.rightClimber, RobotMap.hookDeploy);
	public static final GreenLEDClimber greenLEDClimber = new GreenLEDClimber(RobotMap.greenLED);
	public static final RGBSensor rgbSensorUtility = new RGBSensor(rgbSensor);
	public static final Constants constants = new Constants();




	public static OI oi;

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	public static IMU navX;
	SerialPort port;


	public static PowerDistributionPanel PDP = new PowerDistributionPanel();






	/**
	Drive variables and Objects
	 */

	public static Encoder leftDriveEncoder = new Encoder(1, 1);
	public static Encoder rightDriveEncoder = new Encoder(2, 2);

	double commandedTurn;
	double commandedDrive;
	double speed;
	double wheel;
	double leftDrive;
	double rightDrive;
	public static double targetDirection;
//	double rampSpeed = 0.015;

	/**
	 * Constants
	 */

	public double GripperIntakeSpeed = 0;
	public double climberSpeed = 0;




	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();
		m_chooser.addDefault("CenterPos", new CenterPosition());
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", m_chooser);


		/*
		 * initialize navX
		 */
		try {
			byte UpdateRateHz = 50;
			port = new SerialPort(57600, SerialPort.Port.kMXP);
			navX = new IMUAdvanced(port, UpdateRateHz);
		} catch(Exception ex){System.out.println("NavX not working");}

		targetDirection = navX.getYaw();
		rgbSensor = new I2C(I2C.Port.kOnboard, 0x29);

	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		m_autonomousCommand = m_chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();


//		/**
//		 * Hook Release LED indicator
//		 */
//
//		if(isDeployed == true){
//			greenLEDClimber.set(true);
//		}else{
//			greenLEDClimber.set(false);
//		}


		/*
		Climber
		 */

		if(OI.joy.getRawButtonPressed(constants.buttonA)) {
			climberSubsystemPID.setClimberSpeed(constants.intakeSpeed);
		}else{
			climberSubsystemPID.setClimberOff();
		}








		/*
		Drive
		 */

		commandedTurn = OI.joy.getRawAxis(constants.rightXAxis);
		commandedDrive = OI.joy.getRawAxis(constants.leftYAxis);

		leftDrive = speed + wheel;
		rightDrive = speed - wheel;

		System.out.println(rgbSensorUtility.read16(0x18));


		driveTrainSubsystem.tankDrive(leftDrive, rightDrive);



	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
