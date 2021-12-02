package org.firstinspires.ftc.teamcode.PhantomCode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.util.HelperClasses.PhantomOpMode;
import org.firstinspires.ftc.teamcode.util.HelperClasses.chassisType;

@TeleOp
public class imgStream extends PhantomOpMode {
    @Override
    public void opModeCode() throws InterruptedException {
        setChassis(chassisType.MECANUM);
        robot.init();
        waitForStart();
        while(!isStopRequested()) {
            idle();
        }
    }
}
