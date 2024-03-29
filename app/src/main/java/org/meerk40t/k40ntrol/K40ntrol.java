package org.meerk40t.k40ntrol;

import android.content.Context;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.wch.multiport.MultiPortManager;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import org.meerk40t.k40ntrol.databinding.ActivityK40ntrolBinding;
import org.meerk40t.k40ntrol.ui.gallery.NavigateViewModel;

import java.util.Arrays;

public class K40ntrol extends AppCompatActivity {

    private static final String ACTION_USB_PERMISSION = "org.meerk40t.k40ntrol.USB_PERMISSION";
    MultiPortManager multiport;
    private NavigateViewModel navigateViewModel;

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityK40ntrolBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        navigateViewModel =
                new ViewModelProvider(this).get(NavigateViewModel.class);

        multiport = new MultiPortManager(
                (UsbManager) getSystemService(Context.USB_SERVICE), this,
                ACTION_USB_PERMISSION);

        binding = ActivityK40ntrolBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarK40ntrol.toolbar);
        binding.appBarK40ntrol.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_manager, R.id.nav_navigate, R.id.nav_host)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_k40ntrol);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.k40ntrol, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_k40ntrol);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    int[] crc_table = new int[]{
            0x00, 0x5E, 0xBC, 0xE2, 0x61, 0x3F, 0xDD, 0x83,
            0xC2, 0x9C, 0x7E, 0x20, 0xA3, 0xFD, 0x1F, 0x41,
            0x00, 0x9D, 0x23, 0xBE, 0x46, 0xDB, 0x65, 0xF8,
            0x8C, 0x11, 0xAF, 0x32, 0xCA, 0x57, 0xE9, 0x74};

    String RIGHT = "B";
    String LEFT = "T";
    String BOTTOM = "R";
    String TOP = "L";
    int step_distance = 197; //~5mm

    public void move_laser_n(View view) {
        String distance = lhymicro_distance(step_distance);
        String command = "I" + TOP + distance + "NS2P\n";
        send_data(command);
    }

    public void move_laser_e(View view) {
        String distance = lhymicro_distance(step_distance);
        String command = "I" + RIGHT + distance + "NS2P\n";
        send_data(command);
    }

    public void move_laser_w(View view) {
        String distance = lhymicro_distance(step_distance);
        String command = "I" + LEFT + distance + "NS2P\n";
        send_data(command);
    }

    public void move_laser_s(View view) {
        String distance = lhymicro_distance(step_distance);
        String command = "I" + BOTTOM + distance + "NS2P\n";
        send_data(command);
    }

    public void move_laser_sw(View view) {
        String distance = lhymicro_distance(step_distance);
        String command = "I" + LEFT + distance + BOTTOM + distance + "NS2P\n";
        send_data(command);
    }

    public void move_laser_nw(View view) {
        String distance = lhymicro_distance(step_distance);
        String command = "I" + LEFT + distance + TOP + distance + "NS2P\n";
        send_data(command);
    }

    public void move_laser_ne(View view) {
        String distance = lhymicro_distance(step_distance);
        String command = "I" + RIGHT + distance + TOP + distance + "NS2P\n";
        send_data(command);
    }

    public void move_laser_se(View view) {
        String distance = lhymicro_distance(step_distance);
        String command = "I" + RIGHT + distance + BOTTOM + distance + "NS2P\n";
        send_data(command);
    }

    public void fire_laser(View view) {
        try {
            send_data("IDNS2P\n");
            Thread.sleep(50);
            send_data("IUNS2P\n");
        } catch (InterruptedException e) {
            send_data("IUNS2P\n");
        }
    }

    public void execute_laser_commands(View view) {
        if (multiport.isConnected()) {
            multiport.CH341SystemInit();
            if (!multiport.CH341InitParallel((char) 1)) {
                Snackbar.make(view, "Could not set required mode", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else {
                Snackbar.make(view, "Set Mode Para19 on CH431 v" + multiport.ChipVersion, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
        else {
            Snackbar.make(view, "No Connection.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    public void send_data(String data) {
        String[] packets = data.split("\n");
        for (String packet : packets) {
            for (int i = 0, ie = packet.length(); i < ie; i += 30) {
                send_packet(packet.substring(i, Math.min(i + 30, ie)));
            }
        }
    }

    public byte[] make_packet(String packet) {
        byte[] pack = new byte[32];
        Arrays.fill(pack, (byte) 'F');
        pack[0] = 0;
        for (int i = 0, ie = packet.length(); i < ie; i++) {
            pack[i + 1] = (byte) packet.charAt(i);
        }
        pack[31] = onewire_crc_lookup(pack);
        return pack;
    }

    public void send_packet(String packet) {
        byte[] home = make_packet(packet);
        send_bytes(home);
    }

    public void send_bytes(byte[] home) {
        StringBuilder home_str = new StringBuilder();
        for (int i = 0; i < home.length; i++) {
            home_str.append(String.format("%02x ", home[i]));
        }
        String event;
        if (multiport.isConnected()) {
            multiport.CH341EppWriteData(home, home.length);
            event = "Sent: ";
            int[] status = new int[1];
            multiport.CH341GetInput(status);
//            this.status.setText("Status: " + status[0]);

        } else {
            event = "NOT SENT: ";
        }
//        this.message.setText(event + home_str.toString());
    }

    public byte onewire_crc_lookup(byte[] bytes) {
        //License: 2-clause "simplified" BSD license
        //Copyright (C) 1992-2017 Arjen Lentz
        //https://lentz.com.au/blog/calculating-crc-with-a-tiny-32-entry-lookup-table
        int crc = 0;
        for (int i = 1; i < 31; i++) {
            crc = bytes[i] ^ crc;
            crc = crc_table[crc & 0x0f] ^ crc_table[16 + ((crc >> 4) & 0x0f)];
        }
        return (byte) crc;
    }

    char[] distance_lookup = new char[]{
            '`', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
            'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
            't', 'u', 'v', 'w', 'x', 'y'
    };


    public String lhymicro_distance(int amount) {
        StringBuilder b = new StringBuilder();
        while (amount >= 255) {
            amount -= 255;
            b.append('z');
        }
        if (amount >= 52) {
            b.append(String.format("%03d", amount));
            return b.toString();
        }
        if (amount == 51) {
            b.append('|').append('z');
            return b.toString();
        }
        while (amount >= 25) {
            amount -= 25;
            b.append('y');
        }
        if (amount > 0) {
            b.append(distance_lookup[amount]);
        }
        return b.toString();
    }

}