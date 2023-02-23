package com.stuypulse.robot.subsystems.shoulder;

import static com.stuypulse.robot.constants.Motors.Arm.*;
import static com.stuypulse.robot.constants.Ports.Arm.*;

import com.stuypulse.robot.constants.Settings.Arm.Shoulder;
import com.stuypulse.robot.subsystems.Loggable;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMaxLowLevel.PeriodicFrame;
import com.revrobotics.SparkMaxAbsoluteEncoder.Type;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class VoltageShoulder extends SubsystemBase implements Loggable {

    private final CANSparkMax left;
    private final CANSparkMax right;

    private final AbsoluteEncoder encoder;

    private double voltage;
    
    public VoltageShoulder() {
        left = new CANSparkMax(SHOULDER_LEFT, MotorType.kBrushless);
        right = new CANSparkMax(SHOULDER_RIGHT, MotorType.kBrushless);

        encoder = right.getAbsoluteEncoder(Type.kDutyCycle);

        configureMotors();
    }

    private void configureMotors() {
        encoder.setZeroOffset(0);

        encoder.setInverted(true);
        right.setPeriodicFramePeriod(PeriodicFrame.kStatus5, 20);
        right.setPeriodicFramePeriod(PeriodicFrame.kStatus6, 20);

        SHOULDER_LEFT_CONFIG.configure(left);
        SHOULDER_RIGHT_CONFIG.configure(right);
    }
    
    @Override
    public void setVoltage(double voltage) {
        this.voltage = voltage;

        left.setVoltage(voltage);
        right.setVoltage(voltage);
    }

    @Override
    public double getVoltage() {
        return voltage;
    }

    @Override
    public double getPosition() {
        return encoder.getPosition() - Shoulder.ZERO_ANGLE.getRotations();
    }

    @Override
    public double getVelocity() {
        return encoder.getVelocity();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Shoulder/Voltage", getVoltage());
        SmartDashboard.putNumber("Shoulder/Rotations", getPosition());
        SmartDashboard.putNumber("Shoulder/Rotations Per Second", getVelocity());
    }
}
