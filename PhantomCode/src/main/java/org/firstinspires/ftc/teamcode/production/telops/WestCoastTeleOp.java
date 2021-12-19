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
   // double intakePower;


    SliddyEnum Pos;
    DcMotor carousel;
//    DcMotor intake;
    Servo bucket;

    Servo basketDump;
    float powerMult = 10;
    float powerMult2 = 10;

    boolean isBumpersPressed = false;
    boolean isBumpersPressed2 = false;
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
        bucket = hardwareMap.servo.get("bucket");
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
         //   intakePower = gamepad2.right_stick_y;
            bucket.setPosition(gamepad2.right_stick_y);


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

            if(!gamepad2.right_bumper && !gamepad2.left_bumper) {
                isBumpersPressed2 = false;
            }

            if (gamepad2.right_bumper && !isBumpersPressed2 && powerMult2 < 10) {
                powerMult2 += 1;
                isBumpersPressed2 = true;
            }

            if (gamepad2.left_bumper && !isBumpersPressed2 && powerMult2 > 1) {
                powerMult2 -= 1;
                isBumpersPressed = true;
            }
            
            leftDrivePower *= powerMult/10;
            rightDrivePower *= powerMult/10;


            robot.leftDrive.setPower(leftDrivePower);
            robot.rightDrive.setPower(rightDrivePower);
            carousel.setPower(carouselSpinnerPower);

            if(gamepad2.dpad_left) {
                carousel.setPower(1.0 * powerMult2);
            } else {
                if (gamepad2.dpad_right) {
                    carousel.setPower(-1.0 * powerMult2);
                } else {
                    carousel.setPower(0);
                }
            }




            telemetry.addData("powerMult: ", powerMult);
          //  telemetry.addData("intakePower", intakePower);
            telemetry.addData("carouselSpinner", carouselSpinnerPower);
            telemetry.update();
            idle();

        }
    }
}