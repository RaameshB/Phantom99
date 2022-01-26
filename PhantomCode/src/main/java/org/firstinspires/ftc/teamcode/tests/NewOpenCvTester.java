package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.checkerframework.checker.units.qual.C;
import org.firstinspires.ftc.teamcode.util.RobotConfig;
import org.firstinspires.ftc.teamcode.util.RobotControlClasses.CvWebcamController;

@TeleOp
public class NewOpenCvTester extends LinearOpMode {

    RobotConfig robot = new RobotConfig();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap, this);
        CvWebcamController cvWebcamController = new CvWebcamController(robot);
        cvWebcamController.startAsyncStream();
//        cvWebcamController.openSyncCam();
        waitForStart();
        while (!isStopRequested()) {
/*            cvWebcamController.startSyncStream();
            sleep(2000);
            cvWebcamController.stopSyncStream();*/
            telemetry.addData("position: ", cvWebcamController.getPos());
            telemetry.addData("left: ", cvWebcamController.getLeftValue());
            telemetry.addData("mid: ", cvWebcamController.getMidValue());
            telemetry.update();
            sleep(50);
//            idle();
        }
    }
}
