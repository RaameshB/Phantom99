package org.firstinspires.ftc.teamcode.production.telops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.util.MotorCluster;
import org.firstinspires.ftc.teamcode.util.RobotConfig;
import org.firstinspires.ftc.teamcode.util.SliddyEnum;


@TeleOp(group = "final")
public class  comTestWIntake extends LinearOpMode {

    RobotConfig robot = new RobotConfig();

    double leftDrivePower;
    double rightDrivePower;
    double tempPower;
    double carouselSpinnerPower;
    double intakePower;


    SliddyEnum Pos;
    DcMotor carousel;
//    DcMotor intake;

    CRServo SSR, SSL;

    Servo basketDump, IRS;
    float powerMult = 10;
    float powerMult2 = 10;

    boolean isBumpersPressed = false;
    boolean isBumpersPressed2 = false;
    boolean isIntakeOn = false;
    boolean isIntakeSucking = false;
    //boolean isAPressed = false;
    //boolean isDpadUp2;
    //boolean isDpadDown2;
    //boolean isBPressed;
    //boolean direction = true;
    //DcMotor sliderMotor;


    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap, this);
        carousel = hardwareMap.get(DcMotor.class, "carousel");
        carousel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        SSL = hardwareMap.crservo.get("L_SUC_SRV");
        SSR = hardwareMap.crservo.get("R_SUC_SRV");
        IRS = hardwareMap.servo.get("INTK_ROT_SRV");

        SSR.setDirection(DcMotorSimple.Direction.REVERSE);

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

        while (!isStopRequested()) {


//            carouselSpinnerPower = gamepad1.left_stick_y;
            //intakePower = gamepad1.right_stick_y;


            leftDrivePower = gamepad1.left_stick_y; // Drive power variables
            rightDrivePower = gamepad1.right_stick_y;

            if (!gamepad1.right_bumper && !gamepad1.left_bumper) {  //Power Muliplier
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

            if (!gamepad1.right_bumper && !gamepad1.left_bumper) {
                isBumpersPressed2 = false;
            }

            if (gamepad1.right_bumper && !isBumpersPressed2 && powerMult2 < 10) {
                powerMult2 += 1;
                isBumpersPressed2 = true;
            }

            if (gamepad1.left_bumper && !isBumpersPressed2 && powerMult2 > 1) {
                powerMult2 -= 1;
                isBumpersPressed = true;
            }
            //==============================================================================================
            if (gamepad1.a) { //put intake down
                IRS.setPosition(0);
            } else if (gamepad1.x) { //lift intake halfway
                IRS.setPosition(0.125);
            } else if (gamepad1.y) { //lift intake completly
                IRS.setPosition(1);
            }


            if (gamepad1.dpad_down && (!isIntakeOn || !isIntakeSucking)) { //suck in freight
                SSL.setPower(1);
                SSR.setPower(1);
                isIntakeSucking = true;
                isIntakeOn = true;
            } else if (gamepad1.dpad_up && (!isIntakeOn || isIntakeSucking)) { //spit out freight
                SSL.setPower(-1);
                SSR.setPower(-1);
                isIntakeSucking = false;
                isIntakeOn = true;
            } else if (gamepad1.dpad_left && isIntakeOn) { //turn off intake
                SSL.setPower(0);
                SSR.setPower(0);
                isIntakeOn = false;

                leftDrivePower *= powerMult / 10;
                rightDrivePower *= powerMult / 10;

//            if(gamepad1.a && !isAPressed){
//                tempPower = rightDrivePower;
//                rightDrivePower = -leftDrivePower;
//                leftDrivePower = -tempPower;
//                isAPressed = true;
//            }


                robot.leftDrive.setPower(leftDrivePower);
                robot.rightDrive.setPower(rightDrivePower);
//            carousel.setPower(carouselSpinnerPower);

                if (gamepad1.right_trigger > 0.5) {
                    carousel.setPower(-1.0);
                } else {
                    carousel.setPower(0);
                }

                //
//
//            intake.setPower(intakePower);

//            if(isAPressed && !gamepad1.a){
//                isAPressed = false;
//            }
//
//            if (gamepad1.a && !isAPressed){
//                MotorCluster tmp;
//                tmp = robot.rightDrive;
//                robot.rightDrive = robot.leftDrive;
//                robot.leftDrive = tmp;
//                isAPressed = true;
//            }
//
//            if(isBPressed && !gamepad1.b){
//                isBPressed = false;
//            }
//
//            if (gamepad1.b && !isBPressed){
//                isBPressed = true;
//
//                    leftDrivePower = -leftDrivePower;
//
//                    rightDrivePower = -rightDrivePower;
//
//
//            }


                telemetry.addData("powerMult: ", powerMult);
                telemetry.addData("intakePower", intakePower);
                telemetry.addData("carouselSpinner", carouselSpinnerPower);
                telemetry.update();
                idle();

            }
        }
    }
}