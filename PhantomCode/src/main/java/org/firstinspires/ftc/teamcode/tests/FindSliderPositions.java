package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.util.RobotConfig;

import java.util.ArrayList;

@Autonomous
@TeleOp
public class FindSliderPositions extends LinearOpMode {

    RobotConfig robot = new RobotConfig();
    int positionOne;
    int positionTwo;
    int positionThree;

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        telemetry.addLine("Set Slider to the bottom position, press A on gamepad 1 to continue...");
        telemetry.update();
        while (!gamepad1.a && !isStopRequested()) {
            idle();
        }
        while (gamepad1.a && !isStopRequested()) {
            idle();
        }
        robot.init(hardwareMap, this);
        positionOne = robot.slider.getCurrentPosition();
        telemetry.addLine("Set Slider to the middle position, press A on gamepad 1 to continue...");
        telemetry.update();
        while (!gamepad1.a && !isStopRequested()) {
            idle();
        }
        while (gamepad1.a && !isStopRequested()) {
            idle();
        }
        positionTwo = robot.slider.getCurrentPosition();
        telemetry.addLine("Set Slider to the top position, press A on gamepad 1 to continue...");
        telemetry.update();
        while (!gamepad1.a && !isStopRequested()) {
            idle();
        }
        while (gamepad1.a && !isStopRequested()) {
            idle();
        }
        positionThree = robot.slider.getCurrentPosition();
        ArrayList<Integer> positions = new ArrayList<Integer>();
        positions.add(positionOne);
        positions.add(positionTwo);
        positions.add(positionThree);
        telemetry.addData("positions (in order from bot to top: ", positions);
        telemetry.update();

        while(!isStopRequested());
    }
}
