package frc.team696.utilities;

public class PIDController {

    /*
        Declaration and Initialization of Variables
     */

    private double kP = 0;
    private double kI = 0;
    private double kD = 0;
    private double kAlpha = 1;
    private double error = 0;
    private double oldError = 0;
    private double cumulativeError = 0;
    private double outputValue = 0;

    public PIDController(double kP, double kI, double kD, double kAlpha) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kAlpha = kAlpha;
    }

    public void setError(double error){
        oldError = this.error;
        this.error = error;
        run();
    }

    public void setPID(double kP, double kI, double kD, double kAlpha) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kAlpha = kAlpha;
    }

    public void setPID(double kP, double kI, double kAlpha) {
        this.kP = kP;
        this.kI = kI;
        this.kAlpha = kAlpha;
    }

    public void setPID(double kP, double kAlpha){
        this.kP = kP;
        this.kAlpha = kAlpha;
    }

    public double P(){
        return error * kP;
    }

    public double I() {
        cumulativeError *= kAlpha;
        cumulativeError += error;
        return cumulativeError * kI;
    }

    public double D() {
        return (error - oldError) * kD;
    }

    public double getValue(){
        return outputValue;
    }

    public void run() {
        outputValue = P() + I() + D();
    }

}
