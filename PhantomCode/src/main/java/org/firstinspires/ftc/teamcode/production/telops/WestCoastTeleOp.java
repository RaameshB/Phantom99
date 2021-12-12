package org.firstinspires.ftc.teamcode.production.telops;
 
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.util.MotorCluster;
import org.firstinspires.ftc.teamcode.util.RobotConfig;
import org.firstinspires.ftc.teamcode.util.SliddyEnum;


@TeleOp
public class  WestCoastTeleOp extends LinearOpMode {

    RobotConfig robot = new RobotConfig();

    double leftDrivePower;
    double rightDrivePower;
    double tempPower;
    double carouselSpinnerPower;
    double intakePower;


    SliddyEnum Pos;
    DcMotor carousel;
//    DcMotor intake;

    Servo basketDump;
    float powerMult = 7;

    boolean isBumpersPressed = false;
    boolean isAPressed = false;
    boolean isDpadUp2;
    boolean isDpadDown2;
    boolean isBPressed;
    boolean direction = true;
    DcMotor sliderMotor;



    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap, this);
        carousel = hardwareMap.get(DcMotor.class, "carousel");
        waitForStart();

//        switch (Pos){
//            case TOP: //move to top;
//            break;
//            case MID: //move to mid;
//            break;
//            case BOT: //sliiiiiiiiide to bottom;
//            break;
//            default:
//        }

        while(!isStopRequested()) {



            carouselSpinnerPower = gamepad2.left_stick_y;
            intakePower = gamepad2.right_stick_y;



            leftDrivePower = gamepad1.left_stick_y;
            rightDrivePower = gamepad1.right_stick_y;

            if(!gamepad1.right_bumper && !gamepad1.left_bumper) {
                isBumpersPressed = false;
            }

            if (gamepad1.right_bumper && !isBumpersPressed && powerMult < 10) {
                powerMult += 1;
                isBumpersPressed = true;
            }

            if (gamepad1.left_bumper && !isBumpersPressed && powerMult > 1) {
                powerMult -= 1;
                isBumpersPressed = true;
            }

            leftDrivePower *= powerMult/10;
            rightDrivePower *= powerMult/10;

//            if(gamepad1.a && !isAPressed){
//                tempPower = rightDrivePower;
//                rightDrivePower = -leftDrivePower;
//                leftDrivePower = -tempPower;
//                isAPressed = true;
//            }


            robot.leftDrive.setPower(leftDrivePower);
            robot.rightDrive.setPower(rightDrivePower);
            carousel.setPower(carouselSpinnerPower);
//            intake.setPower(intakePower);

            if(isAPressed && !gamepad1.a){
                isAPressed = false;
            }

            if (gamepad1.a && !isAPressed){
                MotorCluster tmp;
                tmp = robot.rightDrive;
                robot.rightDrive = robot.leftDrive;
                robot.leftDrive = tmp;
                isAPressed = true;
            }

            if(isBPressed && !gamepad1.b){
                isBPressed = false;
            }

            if (gamepad1.b && !isBPressed){
                isBPressed = true;

                    leftDrivePower = -leftDrivePower;

                    rightDrivePower = -rightDrivePower;


            }


            telemetry.addData("powerMult: ", powerMult);
            telemetry.addData("intakePower", intakePower);
            telemetry.addData("carouselSpinner", carouselSpinnerPower);
            telemetry.update();
            idle();

        }
    }
}