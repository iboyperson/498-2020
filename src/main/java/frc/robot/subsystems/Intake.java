/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class Intake extends SubsystemBase {
  private final VictorSP frontMotor = new VictorSP(IntakeConstants.frontMotor);
  private final VictorSP backMotor = new VictorSP(IntakeConstants.backMotor);

  /**
   * Creates a new Intake.
   */
  public Intake() {
  }

  public void set(double frontSpeed, double backSpeed) {
    frontMotor.set(frontSpeed);
    backMotor.set(backSpeed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
