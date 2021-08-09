# K40ntrol

K40ntrol your M2 Laser with an android device.

The idea is behind K40ntrol is if you have an old phone or tablet you can use it as a network device to control your laser. Since MeerK40t can connect to a network device over TCP the android phone on your network could do that job. In addition it should be able to record and playback files, with the built-in camera (many apps can stream the camera) and touchscreen, the devices could serve as very helpful computers to drive the laser.

Due to the way the M2 Nano works they must (like Klipper for 3D Printers), be connected to a computer that constantly sends the data. That computer does not need to do anything other than send data to the device. An android device should be able to do that quite well.


# Manager -- Stub

Manager will manage file that were received as a host (or merely downloaded). These should be MeerK40t egv files. Which are EGV commands with a mix of special commands that get slightly different interpretation or do some laser control operations.


# Navigate -- Partial

Navigate will be similar to the navigation code in other laser programs. It moves the laser to the desired start position before Manager executes some code or if simply serving as a host relays the info to the laser.


# Controller -- Stub

Controller manages the connection to the laser.


## Network

MeerK40t has network support for the M2Nano. These are plain text clear TCP connections. K40ntrol uses these to send and receive commands from MeerK40t. This allows for use cases of sending a project to the k40ntrol and sending a project from K40ntrol to a device running meerk40t or directly to the laser via a USB-OTG wire.

## USB

K40ntrol has the ability to send from a USB-OTG wire to the laser. This is needed to connect the phone to the device and controller.

