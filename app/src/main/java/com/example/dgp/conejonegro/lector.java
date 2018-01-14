package com.example.dgp.conejonegro;

        import android.Manifest;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.content.pm.PackageManager;
        import android.os.Build;
        import android.support.v4.app.ActivityCompat;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.util.SparseArray;
        import android.view.SurfaceHolder;
        import android.view.SurfaceView;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;

        import com.google.android.gms.vision.CameraSource;
        import com.google.android.gms.vision.Detector;
        import com.google.android.gms.vision.barcode.Barcode;
        import com.google.android.gms.vision.barcode.BarcodeDetector;
        import java.io.IOException;
        import java.util.regex.Pattern;

public class lector extends AppCompatActivity{
    private CameraSource cameraSource;
    private SurfaceView cameraView;
    private final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    //chicos aqui se guarda el string //
    private verElemento elemento = new verElemento();
    private String token = "";
    /////------------///
    private String tokenanterior = "";

    private Button botonListaSalas;
    private Button botonQR;
    private Button botonRutas;
    private Button botonConfig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lector);

        cameraView = (SurfaceView) findViewById(R.id.camera_view);
        initQR();

        crearBotones();
    }
    public void initQR() {

        // creo el detector qr
        BarcodeDetector barcodeDetector =
                new BarcodeDetector.Builder(this)
                        .setBarcodeFormats(Barcode.ALL_FORMATS)
                        .build();

        // creo la camara
        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1600, 1024)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        // listener de ciclo de vida de la camara
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                // verifico si el usuario dio los permisos para la camara
                if (ActivityCompat.checkSelfPermission(lector.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        // verificamos la version de ANdroid que sea al menos la M para mostrar
                        // el dialog de la solicitud de la camara
                        if (shouldShowRequestPermissionRationale(
                                Manifest.permission.CAMERA)) ;
                        requestPermissions(new String[]{Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_CAMERA);
                    }
                    return;
                } else {
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException ie) {
                        Log.e("CAMERA SOURCE", ie.getMessage());
                    }
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        // preparo el detector de QR
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }


            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() > 0) {

                    // obtenemos el token
                    token = barcodes.valueAt(0).displayValue.toString();

                    // verificamos que el token anterior no se igual al actual
                    // esto es util para evitar multiples llamadas empleando el mismo token
                    if (!token.equals(tokenanterior)) {

                        // guardamos el ultimo token proceado
                        tokenanterior = token;
                        String[] partes = token.split("/");
                        String separador = Pattern.quote(".");
                        partes = partes[partes.length-1].split(separador);



                        Intent shareIntent = new Intent(lector.this, elemento.getClass());
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.putExtra("id", partes[0]);
                        shareIntent.setType("text/plain");
                        startActivity(shareIntent);
                        finish();

                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    synchronized (this) {
                                        wait(5000);
                                        // limpiamos el token
                                        tokenanterior = "";
                                    }
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    Log.e("Error", "Waiting didnt work!!");
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                    }
                }
            }
        });

    }

    public void crearBotones(){

        botonListaSalas = (Button)findViewById(R.id.salaBotonSalas);

        botonQR = (Button)findViewById(R.id.salaBotonQR);

        botonRutas = (Button)findViewById(R.id.salasBotonRutas);

        botonConfig = (Button)findViewById(R.id.salasconfiguracionBoton);

        botonListaSalas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(lector.this, verSalas.class));
                finish();
            }
        });

        botonConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(lector.this, Configuracion.class));
                finish();
            }
        });

        botonQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(lector.this, lector.class));
                finish();
            }
        });

        botonRutas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(lector.this, verRutas.class));
                finish();
            }
        });

        traducirInterfaz();
    }

    public void traducirInterfaz(){
        SharedPreferences config = getSharedPreferences("traducciones", Context.MODE_PRIVATE);

        TextView mTextView = (TextView)findViewById(R.id.principalTexto);
        mTextView.setText(config.getString("principalTexto", "Configuración"));

        TextView mTextView2 = (TextView)findViewById(R.id.principalTextoQR);
        mTextView2.setText(config.getString("principalBotonQR", "Escanear QR"));

        TextView mTextView3 = (TextView)findViewById(R.id.principalTextoRutas);
        mTextView3.setText(config.getString("principalBotonRutas", "Ver Rutas"));

        TextView mTextView4 = (TextView)findViewById(R.id.principalTextoSalas);
        mTextView4.setText(config.getString("principalBotonSalas", "Ver Salas"));

    }

}
