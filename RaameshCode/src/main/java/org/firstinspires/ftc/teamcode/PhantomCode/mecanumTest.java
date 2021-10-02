package org.firstinspires.ftc.teamcode.PhantomCode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "MecanumTest")

public class mecanumTest extends LinearOpMode {
    DcMotor FR;
    DcMotor FL;
    DcMotor RR;
    DcMotor RL;

    @Override
    public void runOpMode() throws InterruptedException{
        FR = hardwareMap.dcMotor.get("front_right");
        FL = hardwareMap.dcMotor.get("front_left");
        RR = hardwareMap.dcMotor.get("back_right");
        RL = hardwareMap.dcMotor.get("back_left");

        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        RL.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        while(!isStopRequested()){
            FR.setPower(gamepad1.right_stick_y);
            RR.setPower(gamepad1.right_stick_y);
            FL.setPower(gamepad1.left_stick_y);
            RL.setPower(gamepad1.left_stick_y);
        }
    }
}
