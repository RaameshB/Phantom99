package org.firstinspires.ftc.teamcode.PhantomCode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.UtilityLibs.HelperClasses.PhantomOpMode;
import org.firstinspires.ftc.teamcode.UtilityLibs.HelperClasses.chassisType;

@TeleOp
public class GetPorts extends PhantomOpMode {
    @Override
    public void opModeCode() throws InterruptedException {
        setChassis(chassisType.MECANUM);
        robot.init();
        telemetry.addData("FR", robot.frontRight.getPortNumber());
        telemetry.addData("FL", robot.frontLeft.getPortNumber());
        telemetry.addData("BR", robot.backRight.getPortNumber());
        telemetry.addData("BL", robot.backLeft.getPortNumber());
        telemetry.update();

    }
}
