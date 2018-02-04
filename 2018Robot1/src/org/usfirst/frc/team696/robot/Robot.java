/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team696.robot;

import com.kauailabs.nav6.frc.IMU;
import com.kauailabs.nav6.frc.IMUAdvanced;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team696.robot.commands.ExampleCommand;
import org.usfirst.frc.team696.robot.subsystems.DriveTrainSubsystem;
import org.usfirst.frc.team696.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team696.robot.utilities.Util;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	public static final ExampleSubsystem kExampleSubsystem = new ExampleSubsystem();
	public static final DriveTrainSubsystem driveTrainSubsystem = new DriveTrainSubsystem(RobotMap.leftRear, RobotMap.leftMid, RobotMap.leftFront,
                                                                                          RobotMap.rightRear, RobotMap.rightMid, RobotMap.rightFront);
	public static OI m_oi;

	/*
	 * set up navX
	 */

	public static IMU navX;
	SerialPort port;

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();
	double speed;
	double wheel;
	double speedTurnScale;
	double leftDrive;
	double rightDrive;
	boolean intoDeadZone;
	int loopNumber = 0;


	Timer time = new Timer();

	PowerDistributionPanel PDP = new PowerDistributionPanel(0);

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_oi = new OI();
		m_chooser.addDefault("Default Auto", new ExampleCommand());
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

		/*
		Tank Drive Usage
		 */

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
		driveTrainSubsystem.tankDrive(leftDrive, rightDrive);

	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {

	}
}
