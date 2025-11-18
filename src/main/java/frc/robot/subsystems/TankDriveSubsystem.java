// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;

import java.util.function.Supplier;

import com.revrobotics.spark.SparkBase.ControlType;
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
  private SparkMax frontLeft = new SparkMax(Constants.DriveConstants.kFrontLeftMotorId, MotorType.kBrushless);
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
  SparkMaxConfig backRightConfig = new SparkMaxConfig();
  SparkMaxConfig backLeftConfig = new SparkMaxConfig();
 
  frontRightConfig.inverted(true).idleMode(IdleMode.kCoast);
  frontLeftConfig.inverted(false).idleMode(IdleMode.kCoast);

  frontRightConfig.closedLoopRampRate(0.3);
  frontLeftConfig.closedLoopRampRate(0.3);

  frontRightConfig.closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder).pid(0.002,0,0).outputRange(-1,1);
  frontLeftConfig.closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder).pid(0.002,0,0).outputRange(-1, 1);

  frontRight.configure(frontRightConfig,ResetMode.kResetSafeParameters, PersistMode.kPersistParameters); //Persist keeps PID Values and other config values even after robot is turned off. 
  frontLeft.configure(frontLeftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters); //ResetMode clears motor's default settings before setting new configs to it. 

  backRightConfig.follow(frontRight);
  backLeftConfig.follow(frontLeft);

  backRight.configure(backRightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  backLeft.configure(backLeftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

  }

  private double applyDeadband(double input){
  double deadband = 0.05;
  if(Math.abs(input) < deadband){
    input = 0;
  }
  return input;
  }

  public void TankDrive(double leftSpeed, double rightSpeed){
    leftSpeed = applyDeadband(leftSpeed);
    rightSpeed = applyDeadband(rightSpeed);
    
    double leftRPM = leftSpeed*Constants.DriveConstants.kMaxRPM;
    double rightRPM = rightSpeed*Constants.DriveConstants.kMaxRPM;

    frontLeft.getClosedLoopController().setReference(leftRPM, ControlType.kVelocity);
    frontRight.getClosedLoopController().setReference(rightRPM, ControlType.kVelocity);

    //frontLeft.set(leftSpeed);
   //frontRight.set(rightSpeed);
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
