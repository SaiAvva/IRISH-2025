// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.TankDriveSubsystem;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class DriveCmd extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  TankDriveSubsystem tankDriveSubsystem = TankDriveSubsystem.getInstance();
  private final Supplier<Double> leftSpd, rightSpd;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public DriveCmd(TankDriveSubsystem tankDriveSubsystem, Supplier<Double> leftSpd, Supplier<Double> rightSpd) {
    this.leftSpd = leftSpd;
    this.rightSpd = rightSpd;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(tankDriveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double LeftSpeeds = leftSpd.get();
    double RightSpeeds = rightSpd.get();
    tankDriveSubsystem.TankDrive(LeftSpeeds, RightSpeeds);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
