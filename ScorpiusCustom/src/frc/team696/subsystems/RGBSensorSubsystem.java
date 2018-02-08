package frc.team696.subsystems;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team696.RobotMap;

public class RGBSensorSubsystem extends Subsystem {

    public I2C rgbSensor;
    public Timer time = new Timer();

    /*
        Buffers
     */

    public byte[] buffer1 = new byte[10];
    public int[] buffer2 = new int[10];

    public RGBSensorSubsystem(byte enableAddress){

        this.rgbSensor = new I2C(I2C.Port.kOnboard, enableAddress);


    }

    public void enable() {

        time.start();
        rgbSensor.write(RobotMap.enableAddress, RobotMap.enable_PON);
        if(time.get() > 3){
            time.stop();
            time.reset();
            rgbSensor.write(RobotMap.enableAddress, RobotMap.enable_PON | RobotMap.enable_AEN);
        }

    }

    public byte read8(byte reg) {
//        rgbSensor.write(sensorCommand_Bit, reg);
        rgbSensor.read(RobotMap.commandBit | reg, 1, buffer1);
        return reg;
    }

    public int read16(byte reg){

        int t;
        int x;

//        rgbSensor.write(sensorCommand_Bit | reg, 2);
//        timer.runTimer(3);
        rgbSensor.read(RobotMap.commandBit | reg, 2, buffer1);

        t = buffer1[0];
        x = buffer1[1];

        x <<=8;
        x |= t;

        return x;


    }

    public void getRawData() {

        buffer2[0] = read16(RobotMap.redDataL);
        buffer2[1] = read16(RobotMap.greenDataL);
        buffer2[2] = read16(RobotMap.blueDataL);
        buffer2[3] = read16(RobotMap.clearDataL);
        try {
            Thread.sleep(154);
        } catch (InterruptedException e) {
            System.out.println("FAILED HELP");
        }

        System.out.println(buffer2[1]);

    }

    public void ismailIsALoser() {

        rgbSensor.write(0x80, (byte) 0x03);
        rgbSensor.write(0x81, (byte)0xEB);
        rgbSensor.write(0x83, (byte)0xFF);
        rgbSensor.write(0x8F, (byte)0x00);
        try{
            Thread.sleep(50);
        }catch(InterruptedException e){

        }

        byte[] data = new byte[8];
        rgbSensor.read(0x94, 8, data);
        int cData = ((data[1] & 0xFF) * 256) + (data[0] & 0xFF);
        int red = ((data[3] & 0xFF) * 256) + (data[2] & 0xFF);
        int green = ((data[5] & 0xFF) * 256) + (data[4] & 0xFF);
        int blue = ((data[7] & 0xFF) * 256) + (data[6] & 0xFF);

        double luminance = (-0.32466 * red) + (1.57837 * green) + (-0.73191 * blue);

        System.out.printf("Red Color Luminance   : %d lux %n", red);
        System.out.printf("Green Color Luminance : %d lux %n", green);
        System.out.printf("Blue Color Luminance  : %d lux %n", blue);
        System.out.printf("IR Luminance          : %d lux %n", cData);
//        System.out.printf("Ambient Light Luminance : %d lux %n", luminance);

//        System.out.println(red);

        /* experimental */

        double greenBlueAverage = ((green+blue) / 2);
        System.out.println("greenBlue" + greenBlueAverage);
        System.out.println("red" + red);

        /**/
        if(red > greenBlueAverage){ //50
            System.out.println("YOU DID IT!");

        }

    }

    public void initDefaultCommand() {

    }

}
