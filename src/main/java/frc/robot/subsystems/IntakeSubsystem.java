// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  private static TankDriveSubsystem INSTANCE;

  public static TankDriveSubsystem getInstance(){
    if(INSTANCE==null) {
      INSTANCE = new TankDriveSubsystem();
    }
    return INSTANCE;
 
  }

  private SparkMax intakeMotor = new SparkMax(1,MotorType.kBrushless);
  private SparkMax endEffector = new SparkMax(2,MotorType.kBrushless);

  public IntakeSubsystem() {
    MotorConfigs();

  }

  public void MotorConfigs(){
    SparkMaxConfig endEffectorConfig = new SparkMaxConfig();

    endEffectorConfig.inverted(false).idleMode(IdleMode.kBrake);
    endEffectorConfig.closedLoopRampRate(0.3);
    endEffectorConfig.closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder).pid(0.002,0,0).outputRange(-1,1);
    endEffector.configure(endEffectorConfig,ResetMode.kResetSafeParameters,PersistMode.kPersistParameters);

    endEffectorConfig.encoder.positionConversionFactor(14.4);
    endEffectorConfig.encoder.velocityConversionFactor(0.24);

    endEffector.configure(endEffectorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
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
