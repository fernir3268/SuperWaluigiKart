// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
// it shall work with 100% efficiency 70% of the time
package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the
 * build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static Talon driveleftFront;
  public static Talon driveleftBack;
  public static Talon driverightFront;
  public static Talon driverightBack;
  public static MotorControllerGroup driveright;
  public static MotorControllerGroup driveleft;
  public static DifferentialDrive drive;
  public static Joystick stick;
  public static DigitalInput forwardlimit, reverselimit;
  public static PowerDistribution PD;
  public static double basepower;

  /**
   * This function is run when the robot is first started up and should be used
   * for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer. This will perform all our button bindings,
    // and put our
    // autonomous chooser on the dashboard.

    // motors
    driveleftFront = new Talon(0);
    driveleftBack = new Talon(1);
    driverightFront = new Talon(2);
    driverightBack = new Talon(3);
    driveleft = new MotorControllerGroup(driveleftFront, driveleftBack);
    driveright = new MotorControllerGroup(driverightFront, driverightBack);
    drive = new DifferentialDrive(driveleft, driveright);

    // outputs and inputs
    stick = new Joystick(0);
    //inversion
    //driveleft.setInverted(true);

    //camera
    CameraServer.startAutomaticCapture();

    //power
    PD = new PowerDistribution(0, ModuleType.kCTRE);

  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and
   * test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler. This is responsible for polling buttons, adding
    // newly-scheduled
    // commands, running already-scheduled commands, removing finished or
    // interrupted commands,
    // and running subsystem periodic() methods. This must be called from the
    // robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your
   * {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {

  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    basepower = PD.getVoltage();
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    if(PD.getVoltage() < 0.75 * basepower){
      //brownout prevention
      drive.arcadeDrive(0.25* stick.getY(), stick.getX());
    }
    else{
    drive.arcadeDrive(stick.getY(), stick.getX());
    }
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
  }
}
