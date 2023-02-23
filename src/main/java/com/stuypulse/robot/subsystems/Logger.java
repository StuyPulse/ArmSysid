package com.stuypulse.robot.subsystems;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Logger extends SubsystemBase {

	private static final int LOGGED_VALUES = 4;

	// 200 samples/s * 20 seconds * 4 doubles per sample
	private static final int LEN_VALUES = 200 * 20 * LOGGED_VALUES;

	private final List<Double> values;
	private final Loggable mech;

	public Logger(Loggable mechanism) {
		values = new ArrayList<>(LEN_VALUES);
        mech = mechanism;
	}

	public void clear() {
		values.clear();
	}

	public void publish(String test, boolean forwards) {
		String path = test + (forwards ? "-forward" : "-backward");
		String data = "";

		for (int i = 0; i < values.size(); i += LOGGED_VALUES) {
			if (i != 0)
				data += ",";

			data += "[";

			for (int j = 0; j < LOGGED_VALUES; j++) {
				if (j != 0)
					data += ",";

				data += values.get(i + j).toString();
			}

			data += "]";
		}

		SmartDashboard.putString(path, data);
	}

	@Override
	public void periodic() {
		values.add(Timer.getFPGATimestamp());
		values.add(mech.getVoltage());
		values.add(mech.getPosition());
		values.add(mech.getVelocity());
	}
}
