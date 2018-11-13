/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.AnalogGyro;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.TimedRobot;

/*
 * Simplest program to drive a robot with mecanum drive using a single Logitech
 * Extreme 3D Pro joystick and 4 drive motors connected as follows:
 *     - PWM 0 - Connected to front left drive motor
 *     - PWM 1 - Connected to rear left drive motor
 *     - PWM 2 - Connected to front right drive motor
 *     - PWM 3 - Connected to rear right drive motor
 */
public class Robot extends TimedRobot {
  private class TalonSRXSpeedController extends TalonSRX implements SpeedController {
    public TalonSRXSpeedController(int port) {
      super(port);
    }

    @Override
    public void pidWrite(double output) {
      set(output);
    }

    @Override
    public void set(double speed) {
      super.set(ControlMode.PercentOutput, speed);
    }

    @Override
    public double get() {
      return super.getMotorOutputPercent();
    }

    @Override
    public void disable() {
      super.set(ControlMode.Disabled, 0);
    }

    @Override
    public void stopMotor() {
      set(0);
    }
    

  }
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  //Create a robot drive object using PWMs 0, 1, 2 and 3
  SpeedController m_frontLeft, m_rearLeft, m_frontRight, m_rearRight;
  MecanumDrive robotDrive;
  Gyro g;
  Joystick m_driveStick;
  public void robotInit() {
    m_frontLeft = new TalonSRXSpeedController(5);
    m_rearLeft = new TalonSRXSpeedController(4);
    m_frontRight = new TalonSRXSpeedController(3);
    m_rearRight = new TalonSRXSpeedController(12);
    g = new AnalogGyro(0);
    //Define joystick being used at USB port 1 on the Driver Station
    m_driveStick = new Joystick(0);
    robotDrive = new MecanumDrive(m_frontLeft, m_rearLeft, m_frontRight, m_rearRight);

  }

  public void autonomousPeriodic() {
    SmartDashboard.putNumber("Gyro", g.getAngle());
  }

  /**
   * This function is called periodically during operator control.
   */

    @Override
  public void teleopPeriodic() {
        robotDrive.driveCartesian(m_driveStick.getX(), m_driveStick.getY(), m_driveStick.getZ());
  }



  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
