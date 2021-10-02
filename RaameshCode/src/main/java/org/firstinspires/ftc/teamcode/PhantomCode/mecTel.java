package org.firstinspires.ftc.teamcode.PhantomCode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.UtilityLibs.HelperClasses.PhantomOpMode;
import org.firstinspires.ftc.teamcode.UtilityLibs.HelperClasses.chassisType;

@TeleOp
public class mecTel extends PhantomOpMode {
    @Override
    public void opModeCode() throws InterruptedException {
        setChassis(chassisType.MECANUM);
        robot.init();
        while(!isStopRequested()) {

        }
    }
}
