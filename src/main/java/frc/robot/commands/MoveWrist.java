/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Wrist;
import frc.robot.subsystems.Wrist.WristPosition;

public class MoveWrist extends CommandBase {
  private final double POSITION_TOLLERANCE = .027;
  private final double SPEED = .75;
  
  private final Wrist wrist;
  private final WristPosition position;

  private double wristError;

  /**
   * Creates a new MoveWrist.
   */
  public MoveWrist(Wrist wrist, WristPosition position) {
    this.wrist = wrist;
    addRequirements(wrist);

    this.position = position;
  } 

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Don't do extra work if the sensors are triggered
    if (position == WristPosition.Collect && wrist.getWristDown() || position == WristPosition.Home && wrist.getWristUp()) {
      return;
    }

    /**
     * Calculate the offset from the target position
     * 
     * Positive = Below Target
     * Negative = Above Target
     */
    wristError = position.position - wrist.getCurrentAngle();

    // If above the target we must go down to reach it
    if (wristError < -POSITION_TOLLERANCE) {
      wrist.set(-SPEED);
    } else if (wristError > POSITION_TOLLERANCE) {
      wrist.set(SPEED);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    wrist.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(wristError) <= POSITION_TOLLERANCE;
  }
}
