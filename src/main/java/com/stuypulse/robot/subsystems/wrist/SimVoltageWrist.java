package com.stuypulse.robot.subsystems.wrist;

import static com.stuypulse.robot.constants.Settings.Arm.Wrist.*;

import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.subsystems.Loggable;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SimVoltageWrist extends SubsystemBase implements Loggable {

    private final SingleJointedArmSim sim;

    private double voltage;

    public SimVoltageWrist() {
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
        voltage = 0;
    }

    @Override
    public double getVoltage() {
        return voltage;
    }

    @Override
    public double getPosition() {
        return Units.radiansToRotations(sim.getAngleRads());// + getShoulderRadians();
    }

    @Override
    public double getVelocity() {
        return Units.radiansToRotations(sim.getVelocityRadPerSec());
    }

    @Override
    public void simulationPeriodic() {
        SmartDashboard.putNumber("Wrist/Voltage", getVoltage());
        SmartDashboard.putNumber("Wrist/Rotations", getPosition());
        SmartDashboard.putNumber("Wrist/Rotations Per Second", getVelocity());
        
        sim.setInput(voltage);
        sim.update(Settings.DT);
    }

}
