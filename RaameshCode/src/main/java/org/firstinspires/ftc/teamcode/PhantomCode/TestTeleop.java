package org.firstinspires.ftc.teamcode.PhantomCode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.util.HelperClasses.PhantomOpMode;

@TeleOp(name="mecanumTeleop")
public class TestTeleop extends PhantomOpMode {
    @Override
    public void opModeCode() throws InterruptedException {
        robot.init();
        robot.calibrate();
        waitForStart();
        double deg;
        while (!isStopRequested()) {
            deg = Math.atan(gamepad1.left_stick_y/gamepad1.left_stick_x);
//            chassis.correctionAngleMod = Math.atan(gamepad1.left_stick_y/gamepad1.left_stick_x);
//            chassis.drive((float) deg,1,0.2);
            sleep(20);
        }
    }
}
