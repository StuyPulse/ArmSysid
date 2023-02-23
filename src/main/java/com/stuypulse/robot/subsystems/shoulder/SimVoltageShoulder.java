package com.stuypulse.robot.subsystems.shoulder;

import static com.stuypulse.robot.constants.Settings.Arm.Shoulder.*;

import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.constants.Settings.Arm;
import com.stuypulse.robot.subsystems.Loggable;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SimVoltageShoulder extends SubsystemBase implements Loggable {

    private final SingleJointedArmSim sim;

    private double voltage;

    public SimVoltageShoulder() {
        sim = new SingleJointedArmSim(
            LinearSystemId.identifyPositionSystem(kV, kA),
            DCMotor.getNEO(2),
            REDUCTION,
            LENGTH,
            Double.NEGATIVE_INFINITY,
            Double.POSITIVE_INFINITY,
            true);

        voltage = 0;
    }

    @Override
    public void setVoltage(double voltage) {
        this.voltage = voltage;
    }

    @Override
    public double getVoltage() {
        return voltage;
    }

    @Override
    public double getPosition() {
        return Units.radiansToRotations(sim.getAngleRads()); 
    }

    @Override
    public double getVelocity() {
        return Units.radiansToRotations(sim.getVelocityRadPerSec());
    }

    @Override
    public void simulationPeriodic() {
        SmartDashboard.putNumber("Shoulder/Voltage", getVoltage());
        SmartDashboard.putNumber("Shoulder/Radians", getPosition());
        SmartDashboard.putNumber("Shoulder/Radians Per Second", getVelocity());

        sim.setInput(voltage);
        sim.update(Settings.DT);
    }

}
