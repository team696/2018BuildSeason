package org.usfirst.frc.team696.robot.utilities;


public class Timer {

    edu.wpi.first.wpilibj.Timer time = new edu.wpi.first.wpilibj.Timer();
    double seconds;

    public Timer(double seconds){

        this.seconds = seconds;

    }

    public void runTimer(double seconds){

        time.start();
        if(time.get() > seconds){
            time.stop();
            time.reset();
        }

    }

}
