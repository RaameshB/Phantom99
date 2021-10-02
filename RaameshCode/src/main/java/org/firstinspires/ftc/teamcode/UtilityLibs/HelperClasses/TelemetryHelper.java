package org.firstinspires.ftc.teamcode.UtilityLibs.HelperClasses;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.opmode.TelemetryImpl;

import java.util.ArrayList;

public class TelemetryHelper {



    LinearOpMode ln;

    int length;

    public ArrayList<String> captionsAndLines = new ArrayList<>();
    public ArrayList<Object> dataPts = new ArrayList<>();
    public ArrayList<Object> whatIsAdded = new ArrayList<>();

    public TelemetryHelper (LinearOpMode lnOpMd) {
        ln = lnOpMd;
    }

    boolean telemEnabled = false;

    helperThing threader = new helperThing();

    @Deprecated
    public void enableTelemetry() {
        telemEnabled = true;
        threader.start();
    }

    int i = 0;

    public void addData(String caption, Object data) {
//        if (captionsAndLines.contains(caption)) {
//            throw new IllegalArgumentException();
//        }
        captionsAndLines.add(caption);
        dataPts.add(data);
        update();
    }

    public void addLine(String caption) {
//        if (!captionsAndLines.contains(caption)) {
//            throw new IllegalArgumentException();
//        }
        captionsAndLines.add(caption);
        dataPts.add("null");
        update();
    }

    public void modData(String caption, Object data) {
//        if (!captionsAndLines.contains(caption)) {
//            throw new IllegalArgumentException();
//        }
        dataPts.set(captionsAndLines.indexOf(caption), data);
        update();
    }

    public void modData(String oldCaption, String newCaption, Object data) {
//        if (!captionsAndLines.contains(oldCaption)) {
//            throw new IllegalArgumentException();
//        }
        captionsAndLines.set(captionsAndLines.indexOf(oldCaption), newCaption);
        dataPts.set(captionsAndLines.indexOf(oldCaption), data);
        update();
    }

    public void modLine(String oldCaption, String newCaption) {
//        if (!captionsAndLines.contains(oldCaption)) {
//            throw new IllegalArgumentException();
//        }
        captionsAndLines.set(captionsAndLines.indexOf(oldCaption), newCaption);
        update();
    }

    public void removeData(String caption) {
//        if (!captionsAndLines.contains(caption)) {
//            throw new IllegalArgumentException();
//        }
        int a = captionsAndLines.indexOf(caption);
        captionsAndLines.remove(a);
        dataPts.remove(a);
        update();
    }
    void compose() {
        length = captionsAndLines.size();
        while (i < length) {
            if(dataPts.get(i).toString() !=  "null"){
                ln.telemetry.addData(captionsAndLines.get(i), dataPts.get(i));
                whatIsAdded.add(captionsAndLines.get(i));
                whatIsAdded.add(dataPts.get(i));
                whatIsAdded.add("case1");
            }
            if(dataPts.get(i).toString() == "null") {
                ln.telemetry.addLine(captionsAndLines.get(i));
                whatIsAdded.add(captionsAndLines.get(i));
                whatIsAdded.add("case2");
            }
            i+=1;
            whatIsAdded.add(i);
        }
        whatIsAdded.add("finish");
        i = 0;
    }

    public void removeLine(String caption) {
        removeData(caption);
        update();
    }

    void update() {
        compose();
        ln.telemetry.update();
    }

    class helperThing extends Thread{
        @Override
        public void run() {
            while (!ln.isStopRequested()) {
                compose();
                ln.telemetry.update();
                yield();
            }
        }
    }

}

