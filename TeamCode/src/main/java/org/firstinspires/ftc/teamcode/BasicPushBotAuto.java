package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.UtilityLibs.HelperClasses.PhantomOpMode;
import org.firstinspires.ftc.teamcode.UtilityLibs.HelperClasses.chassisType;

//Comment out this line if you want to run the program
//@Disabled
@Disabled
//List this program as an autonomous with the name "BasicPushBotAuto"
@Autonomous(name = "BasicPushBotAuto")

public class BasicPushBotAuto extends PhantomOpMode {

    //The code in this function are the commands that are sent to the robot
    @Override
    public void runOpMode() throws InterruptedException{

        //Tell that you are using a pushbot
        setChassis(chassisType.PUSHBOT);

        //Initialize the robot's motors using PhantomOpMode magic
        robot.init();

        //Wait until the user hits start
        waitForStart();

        //Drive forwards for two seconds
        robot.leftMotor.setPower(0.7);
        robot.rightMotor.setPower(0.7);
        sleep(2000);

        //Turn left for one second
        robot.leftMotor.setPower(-0.7);
        robot.rightMotor.setPower(0.7);
        sleep(1000);

        //Drive forwards for two seconds
        robot.leftMotor.setPower(0.7);
        robot.rightMotor.setPower(0.7);
        sleep(2000);

        //Stop the motors using PhantomOpMode magic
        robot.stopMotors();
    }
}
