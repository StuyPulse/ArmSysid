/************************ PROJECT PHIL ************************/
/* Copyright (c) 2023 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot.constants;

import com.stuypulse.stuylib.network.SmartBoolean;
import com.stuypulse.stuylib.network.SmartNumber;

import edu.wpi.first.math.geometry.Rotation2d;

/*-
 * File containing tunable settings for every subsystem on the robot.
 *
 * We use StuyLib's SmartNumber / SmartBoolean in order to have tunable
 * values that we can edit on Shuffleboard.
 */
public interface Settings {

    double DT = 0.02;
    
    public interface Arm {
        public interface Shoulder {
            int MOTORS = 2;
            double REDUCTION = 63.0;
            double MASS = 3.054; // kg
            double LENGTH = 1.1557; // m, length
            double MOI = 0.369; // kg m^2
            double RADIUS = 0.305; // m, radius to cg
            
            double kV = 1.8;
            double kA = 0.02;

            Rotation2d ZERO_ANGLE = Rotation2d.fromDegrees(129.6 + 90);
        }
        
        public interface Wrist {
            int MOTORS = 1;
            double REDUCTION = 70.0;
            double MASS = 1.317; // kg
            double LENGTH = 0.44298; // m, length
            double MOI = 0.033; // kg m^2
            double RADIUS = 0.2443 + 1.065; // m, radius to cg
            
            double kV = 1.31;
            double kA = 0.02;

            Rotation2d ZERO_ANGLE = Rotation2d.fromDegrees(117 + 180);
        }
    }
}
