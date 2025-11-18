// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;

import java.util.function.Supplier;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.studica.frc.AHRS;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class TankDriveSubsystem extends SubsystemBase {

  public static TankDriveSubsystem INSTANCE;

  public static TankDriveSubsystem getInstance(){
    if(INSTANCE == null){
      INSTANCE = new TankDriveSubsystem();
    }
      return INSTANCE;
  }

  private SparkMax frontRight = new SparkMax(Constants.DriveConstants.kFrontRightMotorId, MotorType.kBrushless);
  private SparkMax frontLeft = new SparkMax(Constants.DriveConstants.kBackLeftMotorId, MotorType.kBrushless);
  private SparkMax backRight = new SparkMax(Constants.DriveConstants.kBackRightMotorId,MotorType.kBrushless);
  private SparkMax backLeft = new SparkMax(Constants.DriveConstants.kBackLeftMotorId, MotorType.kBrushless);

  private AHRS navx = new AHRS(null);

  

  /** Creates a new ExampleSubsystem. */
  public TankDriveSubsystem() {
    configureMotors();

  }



  private void configureMotors(){
  SparkMaxConfig frontRightConfig = new SparkMaxConfig();
  SparkMaxConfig frontLeftConfig = new SparkMaxConfig();
 
  frontRightConfig.inverted(true).idleMode(IdleMode.kCoast);
  frontLeftConfig.inverted(false).idleMode(IdleMode.kCoast);

  frontRightConfig.closedLoopRampRate(0.3);
  frontLeftConfig.closedLoopRampRate(0.3);

  frontRightConfig.closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder).pid(0.002,0,0).outputRange(-1,1);
  frontLeftConfig.closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder).pid(0.002,0,0).outputRange(-1, 1);

  frontRight.configure(frontRightConfig,ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  frontLeft.configure(frontLeftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

  }

  public void TankDrive(Supplier<Double> leftSpeed, Supplier<Double> rightSpeed){
    double LeftSpeed = leftSpeed.get();
    double RightSpeed = rightSpeed.get();

    frontLeft.set(LeftSpeed);
    frontRight.set(RightSpeed);

    backLeft.set(frontLeft.get());
    backRight.set(frontRight.get());
  }

  public void resetGyro(){
  navx.reset();
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
