package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.util.enums.initArgs;

public abstract class PhantomOpMode2 extends LinearOpMode {

    public abstract void opModeCode() throws InterruptedException;

//    static LinearOpMode linearOpMode;

    public static RobotConfig hardware = new RobotConfig();
    public RobotControl robotControl;


    @Override
    public void runOpMode() throws InterruptedException {
//        linearOpMode = this;
        hardware.init(hardwareMap, this, initArgs.CALIBRATE_IMU);
        robotControl = new RobotControl(hardware, this);
        onInit();
        waitForStart();
        opModeCode();
    }

    public void onInit() {

    }
}
