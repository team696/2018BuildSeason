<<<<<<< HEAD
//package org.usfirst.frc.team696.robot.utilities;
//
//import edu.wpi.first.wpilibj.I2C;
//
//public class RGBSensor {
//
//    static double seconds;
//    public static Timer timer = new Timer(seconds);
//
//    I2C rgbSensor;
//    byte sensorAddress = 0x29;
//    byte sensorEnableAddress = 0x00;
//    byte sensorEnable_PON = 0x01;
//    byte sensorEnable_AEN = 0x02;
//    byte sensorCommand_Bit = (byte) 0x80;
//    public byte sensorClearL = 0x14;
//    public byte sensorRedL = 0x16;
//    public byte sensorGreenL = 0x18;
//    public byte sensorBlueL = 0x1A;
//    byte[] buffer1 = new byte[1];
//    byte[] buffer2 = new byte[2];
//    int[] RGBC = new int[4];
//
//    public RGBSensor(I2C rgbSensor) {
//        this.rgbSensor = rgbSensor;
//
//        RGBC[0] = read16(sensorClearL);
//        RGBC[1] = read16(sensorRedL);
//        RGBC[2] = read16(sensorGreenL);
//        RGBC[3] = read16(sensorBlueL);
//    }
//
//    public void enable() {
//        rgbSensor.write(sensorEnableAddress, sensorEnable_PON);
=======
package org.usfirst.frc.team696.robot.utilities;

import edu.wpi.first.wpilibj.I2C;

public class   RGBSensor {

    static double seconds;
    public static Timer timer = new Timer(seconds);

    I2C rgbSensor;
    byte sensorAddress = 0x29;
    byte sensorEnableAddress = 0x00;
    byte sensorEnable_PON = 0x01;
    byte sensorEnable_AEN = 0x02;
    byte sensorCommand_Bit = (byte) 0x80;
    public byte sensorClearL = 0x14;
    public byte sensorRedL = 0x16;
    public byte sensorGreenL = 0x18;
    public byte sensorBlueL = 0x1A;
    byte[] buffer1 = new byte[1];
    byte[] buffer2 = new byte[2];
    int[] RGBC = new int[4];

    public RGBSensor(I2C rgbSensor) {
        this.rgbSensor = rgbSensor;

        RGBC[0] = read16(sensorClearL);
        RGBC[1] = read16(sensorRedL);
        RGBC[2] = read16(sensorGreenL);
        RGBC[3] = read16(sensorBlueL);
    }

    public void enable() {
        rgbSensor.write(sensorEnableAddress, sensorEnable_PON);
        timer.runTimer(3);
        rgbSensor.write(sensorEnableAddress, sensorEnable_PON | sensorEnable_AEN);

    }

    public byte read8(byte reg) {
//        rgbSensor.write(sensorCommand_Bit, reg);
        timer.runTimer(3);
        rgbSensor.read(sensorCommand_Bit | reg, 1, buffer1);

        return reg;
    }

    public int read16(int reg){

        int t;
        int x;

//        rgbSensor.write(sensorCommand_Bit | reg, 2);
>>>>>>> Testing_RGBSensor
//        timer.runTimer(3);
//        rgbSensor.write(sensorEnableAddress, sensorEnable_PON | sensorEnable_AEN);
//
//    }
//
//    public byte read8(byte reg) {
////        rgbSensor.write(sensorCommand_Bit, reg);
//        timer.runTimer(3);
//        rgbSensor.read(sensorCommand_Bit | reg, 1, buffer1);
//
//        return reg;
//    }
//
//    public int read16(int reg){
//
//        int t;
//        int x;
//
////        rgbSensor.write(sensorCommand_Bit | reg, 2);
////        timer.runTimer(3);
//        rgbSensor.read(sensorCommand_Bit | reg, 2, buffer2);
//
//        t = buffer2[0];
//        x = buffer2[1];
//
//        x <<=8;
//        x |= t;
//
//        return x;
//
//
//    }
//
//}
