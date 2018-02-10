package frc.team696.subsystems;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class RGBSensorSubsystem extends Subsystem {

    public int loopNumber = 0;
    public boolean prevValueSet = false;
    double prevValue;
    double nowValue;
    double nullNowPlus = nowValue + 1;
    double nullNowMinus = nowValue - 1;

    public I2C rgbSensor;
    public Timer time = new Timer();

    /*
        Buffer
     */

    byte[] data = new byte[8];

    /*
        Channels Declaration
     */

    int cData;
    int red;
    int green;
    int blue;

    double luminance;

    double gbAverage;
    double redNoLuminance;
    double greenNoLuminance;
    double blueNoLuminance;

    double difference;

    /*
        Addresses
     */

    byte commandBit =  (byte) 0x80;
    byte sensorWaitTime = 0x03;
    byte aTimeAddress = (byte) 0x81;
    byte integrationTime_50ms = (byte)0xEB;

    public RGBSensorSubsystem(byte enableAddress){

        this.rgbSensor = new I2C(I2C.Port.kOnboard, enableAddress);


    }

    public void rgbGetLux() {

        // Select enable register
        // Power ON, RGBC enable, wait time disable
        rgbSensor.write(commandBit, sensorWaitTime);
        // Select ALS time register
        // Atime = 700 ms
        rgbSensor.write(aTimeAddress, integrationTime_50ms);
        // Select Wait Time register
        // WTIME : 50ms
        rgbSensor.write(0x83, integrationTime_50ms);
        // Select control register
        // AGAIN = 1x
        rgbSensor.write(0x8F, (byte)0x00);

        // Thread.sleep here to account for wait time register

        rgbSensor.write(0x80, (byte) 0x03);
        rgbSensor.write(0x81, (byte) 0xC0);
        rgbSensor.write(0x83, (byte) 0xFF);
        rgbSensor.write(0x8F, (byte) 0x00);

        try{
            Thread.sleep(50);
        }catch(InterruptedException e){
            System.out.println("Mission failed. We'll get em next time");
        }

        /*
            Read 8 Bytes of Data
         */

        rgbSensor.read(0x94, 8, data);

        /*
            Conversion of Data Read
         */

        cData = ((data[1] & 0xFF) * 256) + (data[0] & 0xFF);
        red = ((data[3] & 0xFF) * 256) + (data[2] & 0xFF);
        green = ((data[5] & 0xFF) * 256) + (data[4] & 0xFF);
        blue = ((data[7] & 0xFF) * 256) + (data[6] & 0xFF);

        /*
            Luminance Final Calculation
         */

        luminance = (-0.32466 * red) + (1.57837 * green) + (-0.73191 * blue);

        /*
            Data Output
         */

//        System.out.printf("Red Color Luminance   : %d lux %n", red); // no u
//        System.out.printf("Green Color Luminance : %d lux %n", green);
//        System.out.printf("Blue Color Luminance  : %d lux %n", blue);
//        System.out.printf("IR Luminance          : %d lux %n", cData);
//        System.out.printf("Ambient Light Luminance : %d lux %n", luminance);

        gbAverage = ((blue + green) / 3);
        redNoLuminance = (red / luminance);
        blueNoLuminance = (blue / luminance);
        greenNoLuminance = (green / luminance);

        difference = (red - gbAverage);

        System.out.println("redNoLuminance " + redNoLuminance);
        System.out.println("greenNoLuminance" + greenNoLuminance);
        System.out.println("blueNoLuminance" + blueNoLuminance);

        System.out.println(prevValueSet);

        if(time.get() == 0) {
            time.start();
        }

        System.out.println(time.get());

        if(time.get() >= 0.5 && !prevValueSet) {
            prevValueSet = true;
            prevValue = redNoLuminance;
        }
        if(time.get() >= 4.0) {
            nowValue = redNoLuminance;
//            System.out.println("nowValue: " + nowValue);
        }

        if(time.get() >= 10.0) {
            prevValueSet = false;
            time.reset();
        }

        System.out.println("prevValue : " + prevValue);
        System.out.println("nowValue: " + nowValue);


        if(prevValue >= nullNowMinus && prevValue <= nullNowPlus) {
            System.out.println("in Nullzone" );
            nowValue = prevValue;
        }
        if(prevValue != nowValue && nowValue != 0 && prevValue != 0) {
            System.out.println("WWWOOOOOAAAAHHHHH!!! ITS A CHANGE!!!!!");
        }
    }

            public void initDefaultCommand () {


            }
        }
