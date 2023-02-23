package com.stuypulse.robot.subsystems.wrist;

import static com.stuypulse.robot.constants.Motors.Arm.*;
import static com.stuypulse.robot.constants.Ports.Arm.*;
import com.stuypulse.robot.constants.Settings.Arm.*;
import com.stuypulse.robot.subsystems.Loggable;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMaxLowLevel.PeriodicFrame;
import com.revrobotics.SparkMaxAbsoluteEncoder.Type;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class VoltageWrist extends SubsystemBase implements Loggable {

    private final CANSparkMax wrist;

    private final AbsoluteEncoder encoder;

    private double voltage;

    public VoltageWrist() {
        wrist = new CANSparkMax(WRIST, MotorType.kBrushless);

        encoder = wrist.getAbsoluteEncoder(Type.kDutyCycle);

        configureMotors();
    }

    private void configureMotors() {
        encoder.setZeroOffset(0);

        encoder.setInverted(true);
        wrist.setPeriodicFramePeriod(PeriodicFrame.kStatus5, 20);
        // wrist.setPeriodicFramePeriod(PeriodicFrame.kStatus6, 20);

        WRIST_CONFIG.configure(wrist);
    }
    
    @Override
    public void setVoltage(double voltage) {
        this.voltage = voltage;
        wrist.setVoltage(voltage);
    }

    @Override
    public double getVoltage() {
        return voltage;
    }

    @Override
    public double getPosition() {
        return encoder.getPosition() - Wrist.ZERO_ANGLE.getRotations();
    }

    @Override
    public double getVelocity() {
        return encoder.getVelocity();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Wrist/Voltage", getVoltage());
        SmartDashboard.putNumber("Wrist/Rotations", getPosition());
        SmartDashboard.putNumber("Wrist/Rotations Per Second", getVelocity());
    }
}
