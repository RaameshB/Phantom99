package org.firstinspires.ftc.teamcode.PhantomCode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.util.HelperClasses.RobotConfig;
import org.firstinspires.ftc.teamcode.util.HelperClasses.TelemetryHelper;
import org.firstinspires.ftc.teamcode.util.HelperClasses.chassisType;

@TeleOp(name = "aaaaaaaaaaaaaaaaaa")
public class aaa extends LinearOpMode {
    TelemetryHelper console = new TelemetryHelper(this);


    @Override
    public void runOpMode() throws InterruptedException {

        RobotConfig robot = new RobotConfig(hardwareMap, this, console, chassisType.MECANUM);

        robot.init();

        waitForStart();

        while (!isStopRequested()) {
            robot.frontLeft.setPower(-0.5);
            robot.frontRight.setPower(0.5);
            robot.backLeft.setPower(0.5);
            robot.backRight.setPower(-0.5);
        }

    }
}
