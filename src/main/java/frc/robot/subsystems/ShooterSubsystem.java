// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Utils.Preset;

public class ShooterSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  private static ShooterSubsystem INSTANCE;

  public static ShooterSubsystem getInstance(){
    if(INSTANCE == null){
      INSTANCE = new ShooterSubsystem();
    }
    return INSTANCE;
  }
  
  private SparkMax shootingMotorPivot = new SparkMax(Constants.ShooterConstants.kShooterMotorId, MotorType.kBrushless);
  private SparkMax shootingOutake = new SparkMax(Constants.ShooterConstants.kOutakeMotorId, MotorType.kBrushless);

  public ShooterSubsystem() {
    ShooterConfigs();
    
  }

  public void ShooterConfigs(){
    SparkMaxConfig shooterMotorPivotConfig = new SparkMaxConfig();
    shooterMotorPivotConfig.inverted(false).idleMode(IdleMode.kBrake);
    shooterMotorPivotConfig.closedLoopRampRate(0.2); //Arbitary number change later
    shooterMotorPivotConfig.closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder).pid(0.0002,0,0); //Change later

    shooterMotorPivotConfig.encoder.positionConversionFactor(0);
    shooterMotorPivotConfig.encoder.velocityConversionFactor(0);

    shooterMotorPivotConfig.softLimit.forwardSoftLimit(Constants.ShooterConstants.kShooterForwardSoftLimit);
    shooterMotorPivotConfig.softLimit.reverseSoftLimit(Constants.ShooterConstants.kShooterBackwardSoftLimit);
    shooterMotorPivotConfig.softLimit.forwardSoftLimitEnabled(true);
    shooterMotorPivotConfig.softLimit.reverseSoftLimitEnabled(true);

    shootingMotorPivot.configure(shooterMotorPivotConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    SparkMaxConfig outakeMotorConfig = new SparkMaxConfig();
    outakeMotorConfig.inverted(false).idleMode(IdleMode.kCoast);
    
    shootingOutake.configure(outakeMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  public void Shoot(){
    shootingOutake.set(Constants.ShooterConstants.shootingOutakeMotorSpeed);
  }

  public void suckBall(){
    shootingOutake.set(Constants.ShooterConstants.kShooterSuctionLevel);
  }

  public void setShooterPosition(Preset preset){
    shootingOutake.getClosedLoopController().setReference(preset.position, ControlType.kPosition);
  }

  public double getShooterPivotPosition(){
    return shootingMotorPivot.getEncoder().getPosition();
  }

  public void stopOutake(){
    shootingOutake.set(0);
  }

  public void controlShooterPivot(double speed){
  shootingMotorPivot.getClosedLoopController().setReference(speed, ControlType.kPosition);
  }



  
  /**
   * Example command factory method.
   *
   * @return a command
   */
  public Command exampleMethodCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
        });
  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
