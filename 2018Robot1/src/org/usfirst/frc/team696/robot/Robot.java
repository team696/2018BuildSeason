/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team696.robot;

import com.kauailabs.nav6.frc.IMU;
import com.kauailabs.nav6.frc.IMUAdvanced;
<<<<<<< HEAD
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
=======
import edu.wpi.first.wpilibj.*;
>>>>>>> 303724418a6d61f39cd163bb002c7a8b6e25305d
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
<<<<<<< HEAD
import org.usfirst.frc.team696.robot.commands.ExampleCommand;
import org.usfirst.frc.team696.robot.subsystems.DriveTrainSubsystem;
import org.usfirst.frc.team696.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team696.robot.utilities.Util;
=======
import org.usfirst.frc.team696.robot.autonomousCommands.CenterPosition;
import org.usfirst.frc.team696.robot.subsystems.*;
import org.usfirst.frc.team696.robot.utilities.RGBSensor;
import org.usfirst.frc.team696.robot.utilities.Constants;
>>>>>>> 303724418a6d61f39cd163bb002c7a8b6e25305d

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

	/*
	 * set up navX
	 */

	public static IMU navX;
	SerialPort port;

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
	double speedTurnScale;
	double leftDrive;
	double rightDrive;
<<<<<<< HEAD
	boolean intoDeadZone;
	int loopNumber = 0;


	Timer time = new Timer();

	PowerDistributionPanel PDP = new PowerDistributionPanel(0);
=======
	public static double targetDirection;
//	double rampSpeed = 0.015;

	/**
	 * Constants
	 */

	public double GripperIntakeSpeed = 0;
	public double climberSpeed = 0;



>>>>>>> 303724418a6d61f39cd163bb002c7a8b6e25305d

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

<<<<<<< HEAD
=======

>>>>>>> 303724418a6d61f39cd163bb002c7a8b6e25305d
		/*
		 * initialize navX
		 */
		try {
			byte UpdateRateHz = 50;
			port = new SerialPort(57600, SerialPort.Port.kMXP);
			navX = new IMUAdvanced(port, UpdateRateHz);
		} catch(Exception ex){System.out.println("NavX not working");}

<<<<<<< HEAD
=======
		targetDirection = navX.getYaw();
		rgbSensor = new I2C(I2C.Port.kOnboard, 0x29);

>>>>>>> 303724418a6d61f39cd163bb002c7a8b6e25305d
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

		navX.zeroYaw();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
        System.out.println(navX.getYaw() + "          " + loopNumber + "            " + wheel + "                 " + driveTrainSubsystem.distanceError);

		speed = -OI.Psoc.getRawAxis(0);
		wheel = OI.wheel.getRawAxis(0) * 0.75;


		/*
		Wheel Deadzone
		 */

		if(OI.wheel.getRawAxis(0) > -0.1 && OI.wheel.getRawAxis(0) < 0.1){
		    loopNumber++;
		    if(loopNumber >= 1 && loopNumber <= 8){
		        driveTrainSubsystem.targetDirection = navX.getYaw();
            }
//            while(intoDeadZone){
//                int num = 0;
//                switch(num){
//                    case 0:
//                        time.start();
//                        navX.zeroYaw();
//                        if(time.get() > 1){
//                            time.stop();
//                            time.reset();
//                            intoDeadZone = false;
//                            break;
//                    }
//                }
//                break;
//            }



//			driveTrainSubsystem.targetDistance = navX.getYaw();
			driveTrainSubsystem.distanceError = driveTrainSubsystem.targetDirection - navX.getYaw();

			driveTrainSubsystem.driveStraight.setError(driveTrainSubsystem.distanceError);

			wheel = driveTrainSubsystem.driveStraight.getValue();

//            wheel = 0;
		}else{
		    loopNumber = 0;
//		    wheel = OI.Psoc.getRawAxis(0);

        }

		if(OI.joy.getRawButton(2)){

		}else{

		}


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

<<<<<<< HEAD
//        speed = -OI.Psoc.getRawAxis(0);
//        wheel = OI.wheel.getRawAxis(0);
//        speed = Util.smoothDeadZone(speed, -0.1, 0.1, -1, 1, 0);
//        speed = Util.deadZone(speed, -0.1, 0.1, 0);
//        speedTurnScale = 1/(Math.abs(speed)*1.2 + 1.5);
//        wheel = Util.smoothDeadZone(wheel, -0.15, 0.15, -1, 1, 0) * Math.abs(speedTurnScale);


        leftDrive = speed + wheel;
		rightDrive = speed - wheel;

//		System.out.println(speed + "           " + wheel);
//		System.out.println(driveTrainSubsystem.rightFront.get());
//		System.out.println(PDP.getTemperature());
//        System.out.println(PDP.getCurrent(15) + "               " + PDP.getCurrent(0));
//		driveTrainSubsystem.rightFront.set(-0.5);
//		driveTrainSubsystem.rightRear.set(-0.5);
//		driveTrainSubsystem.rightMid.set(-0.5);
=======
		commandedTurn = OI.joy.getRawAxis(constants.rightXAxis);
		commandedDrive = OI.joy.getRawAxis(constants.leftYAxis);

		leftDrive = speed + wheel;
		rightDrive = speed - wheel;

		System.out.println(rgbSensorUtility.read16(0x18));


>>>>>>> 303724418a6d61f39cd163bb002c7a8b6e25305d
		driveTrainSubsystem.tankDrive(leftDrive, rightDrive);



	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {

	}
}
