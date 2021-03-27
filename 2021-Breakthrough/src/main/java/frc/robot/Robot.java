/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2020 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/* import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.DigitalInput; */
/*import com.ctre.phoenix.motorcontrol.can.*;*/

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.PWMTalonFX;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.PWMVictorSPX;
/* import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.PowerDistributionPanel; */
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
/* import edu.wpi.first.wpilibj.Talon; */
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
/* import edu.wpi.first.wpilibj.Victor; */
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.ScheduleCommand;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */

 enum MODE {
   VICTOR, FALCON, TALON
 }
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private SpeedController frontLeft, frontRight, rearLeft, rearRight, shootingMotor_1, shootingMotor_2, elevator_1, elevator_2;
  private SpeedControllerGroup leftMotors;
  private SpeedControllerGroup rightMotors;
  private DifferentialDrive drive;
  private XboxController controller;
  // private boolean done = false;
  private MODE mode = MODE.VICTOR;

  private double xLeft, yLeft, xRight, yRight;
  private int frontLeft_port = 03;
  private int frontRight_port = 02;
  private int rearLeft_port = 01;
  private int rearRight_port = 04;
  private final double fourFive = Math.sqrt(2)/2;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    if(mode.equals(MODE.VICTOR)) {
      frontLeft = (SpeedController) new PWMVictorSPX(frontLeft_port);
      frontRight = (SpeedController) new PWMVictorSPX(frontRight_port);
      rearLeft = (SpeedController) new PWMVictorSPX(rearLeft_port);
      rearRight = (SpeedController) new PWMVictorSPX(rearRight_port);
    } else {
      frontLeft = (SpeedController) new PWMVictorSPX(frontLeft_port);
      frontRight = (SpeedController) new PWMVictorSPX(frontRight_port);
      rearLeft = (SpeedController) new PWMVictorSPX(rearLeft_port);
      rearRight = (SpeedController) new PWMVictorSPX(rearRight_port);
    }
    shootingMotor_1 = new PWMTalonFX(10);
    shootingMotor_2 = new PWMTalonFX(11);
    elevator_1 = new PWMTalonSRX(12);
    elevator_2 = new PWMTalonSRX(13);
    leftMotors = new SpeedControllerGroup(frontLeft, rearLeft);
    rightMotors = new SpeedControllerGroup(frontRight, rearRight);
    drive = new DifferentialDrive(leftMotors, rightMotors);
    drive.setExpiration(0.1);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   * 
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    drive.check();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    
  }

  /**
   * This function is called periodically during autonomous.
   */


  @Override
  public void autonomousPeriodic() {
    double now = 1000*Timer.getFPGATimestamp();
    if(now > 0 && now < 15000) {
     
    }
  }

  /**
   * This function is called once when teleop is enabled.
   */
  @Override
  public void teleopInit() {
    controller = new XboxController(0);
    drive.setSafetyEnabled(true);
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    // if(done) {
    //   leftMotors.setVoltage(0);
    //   rightMotors.setVoltage(0);
    //   return;
    // }
    // if(controller.getXButtonPressed()) {
    //   if(!done) {
    //     done = true;
    //     leftMotors.setVoltage(0);
    //     rightMotors.setVoltage(0);
    //     return;
    //   }
    // }
    xLeft = controller.getX(Hand.kLeft);
    yLeft = controller.getY(Hand.kLeft);
    xRight = controller.getX(Hand.kRight);
    yRight = controller.getY(Hand.kRight);
    if(yLeft > 0 && yLeft <= 1 && xLeft < fourFive && xLeft > (-1*fourFive)) {
      drive.tankDrive(yLeft, yLeft);
    } else if(yLeft >= -1 && yLeft < 0 && xLeft < fourFive && xLeft > (-1*fourFive)) {
      drive.tankDrive(-1*yLeft, -1*yLeft);
    } else if(yRight < fourFive && yRight > (-1*fourFive) && xRight > 0 && xRight <= 1) {
      drive.tankDrive(xRight/2, -1*xRight/2);
    } else if(yRight < fourFive && yRight > (-1*fourFive) && xRight >= -1 && xRight < 0) {
      drive.tankDrive(-1*xRight, xRight);
    } else if(yRight == 0 && xRight == 0 && yLeft == 0 && xLeft == 0) {
      drive.tankDrive(0, 0);
    }

    if(controller.getTriggerAxis(Hand.kRight) == 1) {
      shootingMotor_1.set(1);
      shootingMotor_2.set(1);
    } else {
      shootingMotor_1.set(0);
      shootingMotor_2.set(0);
    }
    if(controller.getAButtonPressed()) {
      elevator_1.set(0.25);
      elevator_2.set(0.25);
    } else if(controller.getBButtonPressed()) {
      elevator_1.set(-0.25);
      elevator_2.set(-0.25);
    } else {
      elevator_1.set(0);
      elevator_2.set(0);
    }
    // Timer.delay(0.005);
  }

  /**
   * This function is called once when the robot is disabled.
   */
  @Override
  public void disabledInit() {
    leftMotors.setVoltage(0);
    rightMotors.setVoltage(0);
  }

  /**
   * This function is called periodically when disabled.
   */
  @Override
  public void disabledPeriodic() {
  }

  /**
   * This function is called once when test mode is enabled.
   */
  @Override
  public void testInit() {
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  
  
}
