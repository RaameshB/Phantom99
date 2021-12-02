package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.util.HelperClasses.PhantomOpMode;
import org.firstinspires.ftc.teamcode.util.HelperClasses.chassisType;

//Comment out this line if you want to run the program
@Disabled

//Comment out this line if you want to run the program
@TeleOp(name = "BasicPushBotTeleOp")

public class BasicPushBotTeleOp extends PhantomOpMode {

    //The code in this function are the commands that are sent to the robot
    @Override
    public void runOpMode() throws InterruptedException {

        //Tell that you are using a pushbot
        setChassis(chassisType.PUSHBOT);

        //Code that automagically finds your motors if they are named RM and LM
        robot.init();

        //Wait until the user hits start
        waitForStart();

        //Basically how much you want to limit the power to, using proportional limiting
        //NOTE: This will be more useful in the future if you want to use acceleration
        double powerMultiplier = 0.7;

        //Pre-define the variables that tell the motors how much power we want to set to them
        double rightPower;
        double leftPower;

        //Define our own loop and make run while nobody has requested a stop
        while(!isStopRequested()) {

            //Set the power variables to their respective gamepad sticks and mult their power by the powerMultiplier
            rightPower = gamepad1.right_stick_y * powerMultiplier;
            leftPower = gamepad1.left_stick_y * powerMultiplier;

            //Tell the motors to go at the power you told them to go at
            robot.rightMotor.setPower(rightPower);
            robot.leftMotor.setPower(leftPower);

            //This is just to give the processor a 20 ms break so its load is less heavy
            sleep(20);
        }

    }
}
