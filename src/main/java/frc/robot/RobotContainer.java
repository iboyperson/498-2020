/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import frc.robot.commands.MoveWrist;
import frc.robot.commands.TeleopIntake;
import frc.robot.commands.TeleopTankDrive;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Wrist;
import frc.robot.subsystems.Wrist.WristPosition;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Climber climber = new Climber();
  private final Drivetrain drivetrain = new Drivetrain();
  private final Intake intake = new Intake();
  private final Wrist wrist = new Wrist();

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    XboxController xbox = new XboxController(0);

    intake.setDefaultCommand(new TeleopIntake(intake, 
      () -> xbox.getTriggerAxis(Hand.kLeft) , () -> xbox.getTriggerAxis(Hand.kRight),
      () -> xbox.getTriggerAxis(Hand.kLeft), () -> xbox.getTriggerAxis(Hand.kRight)
      )
    );

    new Button(() -> xbox.getBumper(Hand.kRight)).whenPressed(new MoveWrist(wrist, WristPosition.Collect));
    new Button(() -> xbox.getBumper(Hand.kLeft)).whenPressed(new MoveWrist(wrist, WristPosition.Home));
    new Button(xbox::getYButton).whenPressed(new MoveWrist(wrist, WristPosition.Load));
    new Button(xbox::getAButton).whenPressed(new MoveWrist(wrist, WristPosition.Score));

    new Button(xbox::getXButton)
      .whenPressed(new InstantCommand(() -> climber.set(.5)))
      .whenReleased(new InstantCommand(() -> climber.set(0)));

    new Button(xbox::getBButton)
      .whenPressed(new InstantCommand(() -> climber.set(-.95)))
      .whenReleased(new InstantCommand(() -> climber.set(0)));

    drivetrain.setDefaultCommand(new TeleopTankDrive(drivetrain, 
      () -> -xbox.getY(Hand.kLeft),
      () -> xbox.getX(Hand.kRight)
    ));
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    // TODO: Add auton command
    return null;
  }
}
