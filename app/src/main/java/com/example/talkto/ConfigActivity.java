package com.example.talkto;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.net.InetAddress;

public class ConfigActivity extends AppCompatActivity {

    private TextView latencyTextView;
    private TextView ipTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_config);

        ImageButton returnButton = findViewById(R.id.returnButton);
        Button saveButton = findViewById(R.id.saveButton);
        SwitchCompat darkModeSwitch = findViewById(R.id.darkModeSwitch);
        latencyTextView = findViewById(R.id.textView4);
        ipTextView = findViewById(R.id.textView6);

        boolean isDarkModeEnabled = (getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK)
                == android.content.res.Configuration.UI_MODE_NIGHT_YES;

        String ipAddress = getWifiIpAddress(this);
        ipTextView.setText(ipAddress);

        new Thread(() -> {
            long latency = getLatency("8.8.8.8");
            runOnUiThread(() -> {
                if (latency != -1) {
                    latencyTextView.setText(latency + " ms");
                } else {
                    latencyTextView.setText("Latência: Host não alcançável");
                }
            });
        }).start();

        darkModeSwitch.setChecked(isDarkModeEnabled);

        returnButton.setOnClickListener(v -> {
            Intent returnMenu = new Intent(ConfigActivity.this, MenuActivity.class);
            startActivity(returnMenu);
        });

        saveButton.setOnClickListener(v -> {
            if (darkModeSwitch.isChecked()) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }

            Intent returnChat = new Intent(ConfigActivity.this, ChatActivity.class);
            startActivity(returnChat);
        });
    }

    private long getLatency(String ipAddress) {
        try {
            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            long startTime = System.currentTimeMillis();
            boolean reachable = inetAddress.isReachable(5000); // Timeout de 5 segundos
            long endTime = System.currentTimeMillis();
            if (reachable) {
                return endTime - startTime;
            } else {
                return -1;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public String getWifiIpAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        return Formatter.formatIpAddress(ipAddress);
    }
}