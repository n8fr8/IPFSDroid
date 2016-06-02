package org.ligi.ipfsdroid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.List;
import javax.inject.Inject;
import org.ligi.tracedroid.sending.TraceDroidEmailSender;

public class MainActivity extends AppCompatActivity {


    @Inject
    IPFSExecutor ipfsBinaryController;

    @BindView(R.id.installButton)
    Button installButton;

    @BindViews({R.id.versionButton,
                R.id.gcButton,
                R.id.ipfsInitButton,
                R.id.daemonButton,
                R.id.swarmPeersButton,
                R.id.catReadmeButton,
                R.id.commandEdit,
                R.id.executeFreeCommand})
    List<View> actionViews;

    @BindView(R.id.commandEdit)
    EditText commandEdit;

    @OnClick(R.id.daemonButton)
    void onDaemonClick() {
        startService(new Intent(this, IPFSDaemonService.class));
    }

    @OnClick(R.id.executeFreeCommand)
    void onExec() {
        runCommandWithResultAlert(commandEdit.getText().toString());
    }

    @OnClick(R.id.versionButton)
    void onVersionClick() {
        final String command = "version";
        runCommandWithResultAlert(command);
    }

    private void runCommandWithResultAlert(final String command) {
        ipfsBinaryController.runWithAlert(this, command);
    }

    @OnClick(R.id.ipfsInitButton)
    void onInitClick() {

        runCommandWithResultAlert("init");
    }

    @OnClick(R.id.gcButton)
    void onGCClick() {
        runCommandWithResultAlert("repo gc");
    }

    @OnClick(R.id.swarmPeersButton)
    void onSwarmPeers() {
        runCommandWithResultAlert("swarm peers");
    }

    @OnClick(R.id.catReadmeButton)
    void catReadmeButton() {
        runCommandWithResultAlert("cat /ipfs/QmVtU7ths96fMgZ8YSZAbKghyieq7AjxNdcqyVzxTt3qVe/readme");
    }

    @OnClick(R.id.addButton)
    void addButton() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                final String foo = new IPFSAPI().addString("foo").getHash();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        new AlertDialog.Builder(MainActivity.this).setMessage(foo).show();
                    }
                });

            }
        }).start();
    }

    @BindView(R.id.textEdit)
    EditText textEdit;

    @OnClick(R.id.addTextCommand)
    void addTextButton() {
        final Intent intent = new Intent(this, AddIPFSContent.class);
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, textEdit.getText().toString());
        startActivity(intent);
    }

    @OnClick(R.id.exampleButton)
    void onButtonClick() {
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://ligi.de/ipfs/example_links2.html"));
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        App.component().inject(this);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        TraceDroidEmailSender.sendStackTraces("ligi@ligi.de", this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        installButton.setVisibility(ipfsBinaryController.isReady() ? View.GONE : View.VISIBLE);

        final int actionButtonsVisibility = ipfsBinaryController.isReady() ? View.VISIBLE : View.GONE;

        for (final View actionButton : actionViews) {
            actionButton.setVisibility(actionButtonsVisibility);
        }
    }

}
