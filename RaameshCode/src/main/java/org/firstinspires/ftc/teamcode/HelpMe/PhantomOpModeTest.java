package org.firstinspires.ftc.teamcode.HelpMe;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.util.HelperClasses.PhantomOpMode;
import org.firstinspires.ftc.teamcode.util.HelperClasses.chassisType;

@Autonomous(name = "a")
public class PhantomOpModeTest extends PhantomOpMode {

    @Override
    public void opModeCode() throws InterruptedException {
        setChassis(chassisType.MECANUM);
        robot.init();
        waitForStart();

    }
}
