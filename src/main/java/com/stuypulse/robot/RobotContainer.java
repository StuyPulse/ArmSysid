/************************ PROJECT PHIL ************************/
/* Copyright (c) 2023 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot;

import com.stuypulse.robot.commands.DynamicTest;
import com.stuypulse.robot.commands.QuasistaticTest;
import com.stuypulse.robot.commands.auton.DoNothingAuton;
import com.stuypulse.robot.constants.Ports;
import com.stuypulse.robot.subsystems.Loggable;
import com.stuypulse.robot.subsystems.Logger;
import com.stuypulse.robot.subsystems.shoulder.SimVoltageShoulder;
import com.stuypulse.robot.subsystems.shoulder.VoltageShoulder;
import com.stuypulse.robot.subsystems.wrist.SimVoltageWrist;
import com.stuypulse.robot.subsystems.wrist.VoltageWrist;
import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.input.gamepads.AutoGamepad;
import com.stuypulse.stuylib.network.SmartNumber;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

public class RobotContainer {

    public final boolean SHOULDER = true;

    // Gamepads
    public final Gamepad driver = new AutoGamepad(Ports.Gamepad.DRIVER);
    public final Gamepad operator = new AutoGamepad(Ports.Gamepad.OPERATOR);
    
    // Subsystem
    public Loggable mech = SHOULDER ? (Robot.isReal() ? new VoltageShoulder() : new SimVoltageShoulder())
        : (Robot.isReal() ? new VoltageWrist() : new SimVoltageWrist());
    public Logger logger = new Logger(mech);

    // Autons
    private static SendableChooser<Command> autonChooser = new SendableChooser<>();

    private static SmartNumber RAMP_RATE = new SmartNumber("Quasistatic Ramp Rate (V per s)", 0.25);
    private static SmartNumber DYNAMIC_STEP = new SmartNumber("Dynamic Step Voltage (V)", 7);

    // Robot container

    public RobotContainer() {
        configureDefaultCommands();
        configureButtonBindings();
        configureAutons();
    }

    /****************/
    /*** DEFAULTS ***/
    /****************/

    private void configureDefaultCommands() {}

    /***************/
    /*** BUTTONS ***/
    /***************/

    private void configureButtonBindings() {}

    /**************/
    /*** AUTONS ***/
    /**************/

    public void configureAutons() {
        autonChooser.setDefaultOption("Do Nothing", new DoNothingAuton());
        autonChooser.addOption("Quasistatic Forward", new QuasistaticTest(this, RAMP_RATE).forward());
        autonChooser.addOption("Quasistatic Backward", new QuasistaticTest(this, RAMP_RATE).reverse());
        autonChooser.addOption("Dynamic Forward", new DynamicTest(this, DYNAMIC_STEP).forward());
        autonChooser.addOption("Dynamic Backward", new DynamicTest(this, DYNAMIC_STEP).reverse());

        SmartDashboard.putData("Autonomous", autonChooser);
    }

    public Command getAutonomousCommand() {
        return autonChooser.getSelected();
    }
}
