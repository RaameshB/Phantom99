package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.util.RobotControlClasses.ChassisController;
import org.firstinspires.ftc.teamcode.util.RobotControlClasses.ImuController;
import org.firstinspires.ftc.teamcode.util.RobotControlClasses.SliderController;

public class RobotControl {

    LinearOpMode linearOpMode;
    RobotConfig robot;
    public ChassisController driveUtils;
    public ImuController imuUtils;
    public SliderController sliderUtils;

    public RobotControl(RobotConfig hardware, LinearOpMode linearOpMode) {
        robot = hardware;
        this.linearOpMode = linearOpMode;
        driveUtils = new ChassisController(robot, this.linearOpMode);
        imuUtils = new ImuController(robot, this.linearOpMode);
        sliderUtils = new SliderController(robot, this.linearOpMode);
    }

}
