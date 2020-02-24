/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DrivetrainConstants;

public class Drivetrain extends SubsystemBase {
  private final double CPR = 2048;
  private final double WHEEL_CIRCUMFERENCE = Math.PI * 5;
  private final double TRUE_DISTANCE_CONVERSION = WHEEL_CIRCUMFERENCE / CPR;

  private final TalonSRX motorRight1 = new TalonSRX(DrivetrainConstants.motorRight1);
  private final VictorSPX motorRight2 = new VictorSPX(DrivetrainConstants.motorRight2);
  private final VictorSPX motorRight3 = new VictorSPX(DrivetrainConstants.motorRight3);
  private final TalonSRX motorLeft1 = new TalonSRX(DrivetrainConstants.motorLeft1);
  private final VictorSPX motorLeft2 = new VictorSPX(DrivetrainConstants.motorLeft2);
  private final VictorSPX motorLeft3 = new VictorSPX(DrivetrainConstants.motorLeft3);

  private final Encoder rightEncoder = new Encoder(DrivetrainConstants.encoderRight1, DrivetrainConstants.encoderRight2, true);
  private final Encoder leftEncoder = new Encoder(DrivetrainConstants.encoderLeft1, DrivetrainConstants.encoderLeft2, false);

  /**
   * Creates a new Drivetrain.
   */
  public Drivetrain() {
    configMotor(motorRight1, true);
    configMotor(motorRight2, true);
    configMotor(motorRight3, true);
    motorRight2.follow(motorRight1);
    motorRight3.follow(motorRight1);

    configMotor(motorLeft1, false);
    configMotor(motorLeft2, false);
    configMotor(motorLeft3, false);
    motorLeft2.follow(motorLeft1);
    motorLeft3.follow(motorLeft1);
  }

  public void stop() {
    motorLeft1.set(ControlMode.PercentOutput, 0);
    motorRight1.set(ControlMode.PercentOutput, 0);
  }

  public void arcadeDrive(double forward, double turn) {
    motorLeft1.set(ControlMode.PercentOutput, forward, DemandType.ArbitraryFeedForward, +turn);
    motorRight1.set(ControlMode.PercentOutput, forward, DemandType.ArbitraryFeedForward, -turn);
  }

  public void tankDrive(double leftSpeed, double rightSpeed) {
    motorLeft1.set(ControlMode.PercentOutput, leftSpeed);
    motorRight1.set(ControlMode.PercentOutput, rightSpeed);
  }
  
  public double getRightTrueDistance() {
    return rightEncoder.getDistance() * TRUE_DISTANCE_CONVERSION;
  }

  public double getLeftTrueDistance() {
    return leftEncoder.getDistance() * TRUE_DISTANCE_CONVERSION;
  }

  public double getTrueDistance() {
    return (getLeftTrueDistance() + getRightTrueDistance()) / 2;
  }

  public void resetEncoders() {
    rightEncoder.reset();
    leftEncoder.reset();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  private void configMotor(BaseMotorController motor, boolean isRight) {
    motor.configFactoryDefault();

    motor.setNeutralMode(NeutralMode.Brake);

    motor.setInverted(isRight);
  }
}
