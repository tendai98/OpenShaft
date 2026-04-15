package com.tinkertech.ttr2;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    private static final byte NO_OPERATION = (byte) 0x88;
    private static final byte STOP_MOTORS = (byte) 0x58;
    private static final byte FORWARD_DRIVE = (byte) 0x31;
    private static final byte REVERSE_DRIVE = (byte) 0x32;

    private static final byte RETURN_TURNER = (byte) 0x44;
    private static final byte LEFT_TURN = (byte) 0x41;
    private static final byte RIGHT_TURN = (byte) 0x42;
    private static final byte NEUTRAL = (byte) 0x34;
    private static final byte E_BOOST_MODE = (byte) 0x35;
    private static final byte TEST_DRIVE = (byte) 0x43;
    private static final byte RESET_SYSTEM = (byte) 0x33;

    private ImageView statusIndicator, leftButton, rightButton, forwardButton, reverseButton, stopButton, eBoostButton;
    private EditText ipAddress ,port;
    private InetAddress destinationAddress;
    private DatagramSocket datagramSocket;
    private ColorFilter colorFilter;

    boolean isConnected = false;
    private  int portNumber;
    private byte [] commandBytes = {0,0};
    private boolean [] controlChecks = {false,false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ImageView connectButton = findViewById(R.id.connect);
        ImageView resetButton = findViewById(R.id.reset);
        ImageView testDriveButton = findViewById(R.id.test_run);
        ImageView logoButton = findViewById(R.id.logo);

        statusIndicator = findViewById(R.id.status_indicator);
        leftButton = findViewById(R.id.left_turn);
        rightButton = findViewById(R.id.right_turn);
        forwardButton = findViewById(R.id.forward);
        reverseButton = findViewById(R.id.reverse);
        eBoostButton = findViewById(R.id.e_boost);
        stopButton = findViewById(R.id.stop_car);
        ipAddress = findViewById(R.id.address);
        port = findViewById(R.id.port);

        eBoostButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(isConnected) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        commandBytes[1] = NEUTRAL;
                        controlChecks[1] = false;
                        eBoostButton.setImageResource(R.mipmap.e_boost_off);
                    } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        commandBytes[1] = E_BOOST_MODE;
                        controlChecks[1] = true;
                        eBoostButton.setImageResource(R.mipmap.e_boost_on);
                    }
                }else{
                    Toast.makeText(getApplicationContext(), getResources().getText(R.string.not_connected) , Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });



        forwardButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(isConnected) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                         commandBytes[1] = NEUTRAL;
                         controlChecks[1] = false;
                         forwardButton.setImageResource(R.mipmap.drive_off);
                    } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        commandBytes[1] = FORWARD_DRIVE;
                        controlChecks[1] = true;
                        forwardButton.setImageResource(R.mipmap.drive_on);
                    }
                }else{
                    Toast.makeText(getApplicationContext(), getResources().getText(R.string.not_connected) , Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });


        reverseButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(isConnected) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        commandBytes[1] = NEUTRAL;
                        controlChecks[1] = false;
                        reverseButton.setImageResource(R.mipmap.reverse_off);
                    } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        commandBytes[1] = REVERSE_DRIVE;
                        controlChecks[1] = true;
                        reverseButton.setImageResource(R.mipmap.reverse_on);
                    }
                }else{
                    Toast.makeText(getApplicationContext(), getResources().getText(R.string.not_connected) , Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });


        leftButton.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(isConnected) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        commandBytes[0] = RETURN_TURNER;
                        controlChecks[0] = false;
                        leftButton.setImageResource(R.mipmap.left_off);
                    } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        commandBytes[0] = LEFT_TURN;
                        controlChecks[0] = true;
                        leftButton.setImageResource(R.mipmap.left_on);
                    }
                }else{
                    Toast.makeText(getApplicationContext(), getResources().getText(R.string.not_connected) , Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        rightButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(isConnected) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        commandBytes[0] = RETURN_TURNER;
                        controlChecks[0] = false;
                        rightButton.setImageResource(R.mipmap.right_off);
                    } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        commandBytes[0] = RIGHT_TURN;
                        controlChecks[0] = true;
                        rightButton.setImageResource(R.mipmap.right_on);
                    }
                }else{
                    Toast.makeText(getApplicationContext(), getResources().getText(R.string.not_connected) , Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });


        stopButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(isConnected) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        commandBytes[0] = commandBytes[1] = NO_OPERATION;
                        controlChecks[0] = controlChecks[1] = false;
                        toggleColorFilter(MotionEvent.ACTION_UP, stopButton);
                    } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        commandBytes[0] = commandBytes[1] = STOP_MOTORS;
                        controlChecks[0] = controlChecks[1] = true;
                        toggleColorFilter(MotionEvent.ACTION_DOWN, stopButton);
                    }
                }else{
                    Toast.makeText(getApplicationContext(), getResources().getText(R.string.not_connected) , Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });


        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusIndicator.setImageResource(R.mipmap.on);
                try {
                    portNumber = Integer.parseInt(port.getText().toString());
                    destinationAddress = InetAddress.getByName(ipAddress.getText().toString());
                    isConnected = true;
                    try {
                        datagramSocket = new DatagramSocket();
                        startCommandTransmissionThread();
                    } catch (SocketException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), getResources().getText(R.string.connected) ,Toast.LENGTH_SHORT).show();

                } catch (UnknownHostException e) {
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                }

            }
        });

        logoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), getResources().getText(R.string.app_banner) ,Toast.LENGTH_SHORT).show();
            }
        });

        testDriveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), getResources().getText(R.string.tests) ,Toast.LENGTH_SHORT).show();
                commandBytes[0] = commandBytes[1] = TEST_DRIVE;
                controlChecks[0] = controlChecks[1] = true;

            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), getResources().getText(R.string.resetting) ,Toast.LENGTH_SHORT).show();
                statusIndicator.setImageResource(R.mipmap.off);
                commandBytes[0] = commandBytes[1] = RESET_SYSTEM;
                controlChecks[0] = controlChecks[1] = true;

            }
        });

    }

    private void toggleColorFilter(int action, ImageView imageView) {

            if(action == MotionEvent.ACTION_DOWN){
                colorFilter = imageView.getColorFilter();
                imageView.setColorFilter(Color.argb(0x50, 0xFF,0xFF,0xFF));

            }else if(action == MotionEvent.ACTION_UP){
                imageView.setColorFilter(colorFilter);
            }
    }


    private void startCommandTransmissionThread() {

        final DatagramPacket[] commandPayload = new DatagramPacket[1];

        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    commandPayload[0] = new DatagramPacket(commandBytes, commandBytes.length, destinationAddress, portNumber);
                    try {
                        if (controlChecks[0] || controlChecks[1]) {
                            datagramSocket.send(commandPayload[0]);

                            switch (commandBytes[0]){
                                case RESET_SYSTEM:
                                    commandBytes[0] = commandBytes[1] = NO_OPERATION;
                                    controlChecks[0] = controlChecks[1] = false;
                                    isConnected = false;
                                    break;

                                case TEST_DRIVE:
                                    commandBytes[0] = commandBytes[1] = NO_OPERATION;
                                    controlChecks[0] = controlChecks[1] = false;
                                    break;
                            }

                        } else {
                            try {
                                Thread.sleep(70);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        sender.start();
    }
}
