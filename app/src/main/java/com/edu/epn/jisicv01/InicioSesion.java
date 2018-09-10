package com.edu.epn.jisicv01;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;


public class InicioSesion extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private GoogleApiClient googleApiClient;
    private final int CODERC=9001;
// FB
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button ingreso;
    EditText inputEmail,inputPassword;




    private OnFragmentInteractionListener mListener;

    public InicioSesion() {
        // Required empty public constructor
    }

    public static InicioSesion newInstance(String param1, String param2) {
        InicioSesion fragment = new InicioSesion();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // sha
 /*    FacebookSdk.sdkInitialize(getContext());

      try {
          PackageInfo info = getActivity().getPackageManager().getPackageInfo(
                  "com.edu.epn.jisicv01",
                  PackageManager.GET_SIGNATURES);
           Log.e("TAG",info.toString());
          for (Signature signature : info.signatures) {
              MessageDigest md = MessageDigest.getInstance("SHA");
               md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
           }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("TAG", e.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("TAG", e.toString());
        }

        // fin sha
*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio_sesion, container, false);
        /**********************************/
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) view.findViewById(R.id.btnFB);
        //loginButton.setReadPermissions("user_friends");
        loginButton.setFragment(this);

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {
            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                //nextActivity(newProfile);
                //logeoConFB(newProfile);
            }
        };
        accessTokenTracker.startTracking();
        profileTracker.startTracking();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
               // Profile profile = Profile.getCurrentProfile();
                //Toast.makeText(getActivity(), profile.getFirstName(), Toast.LENGTH_SHORT).show();
              //  Log.e("nombre",profile.getFirstName());
              //  Log.e("nombre",profile.getMiddleName());
              //  Log.e("nombre",profile.getLastName());
              //  Log.e("nombre",profile.getId().toString());
              //  Log.e("nombre",profile.getProfilePictureUri(500,500).toString());
              //  Toast.makeText(getActivity(), "Login successful", Toast.LENGTH_SHORT).show();
              //  logeoConFB(profile);
                Profile profile = Profile.getCurrentProfile();


                accessTokenTracker = new AccessTokenTracker() {
                    @Override
                    protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

                    }
                };
                profileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                        LogeoFacebookWeb(currentProfile);
                    }
                };
                accessTokenTracker.startTracking();
                profileTracker.startTracking();
                loginButton.setReadPermissions("user_friends");
                loginButton.setReadPermissions("public_profile");
                Toast.makeText(getActivity(), "User ID: "
                        + AccessToken.getCurrentAccessToken().getUserId(), Toast.LENGTH_SHORT).show();

        /*        Intent intents = new Intent(getContext(),Ingresado.class);
              //  intents.putExtra("loging", verificarUser = "User".toString());
                intents.putExtra("email", "dsa");
                intents.putExtra("password", "dsa");
                startActivity(intents);*/
          // logeoConFB(profile);

               LogeoFacebookWeb(profile);
            }

            @Override
            public void onCancel() {
                Toast.makeText(getActivity(), "Login canceled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(getActivity(), "Login error", Toast.LENGTH_SHORT).show();
            }
        });

        // obteniendo datos
        inputEmail = view.findViewById(R.id.inputEmail);
        inputPassword = view.findViewById(R.id.inputPassword);


            ingreso = view.findViewById(R.id.btnIngresar);
            ingreso.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final String username = inputEmail.getText().toString();
                    final String pass = inputPassword.getText().toString();
                    if(username.isEmpty() && pass.isEmpty()){
                        Toast.makeText(getContext(),"ingrese sus credenciales",Toast.LENGTH_LONG).show();
                    }else {
                        Log.e("TAG email:::::", "1" + inputEmail.getText().toString());
                        Log.e("TAG pass:::::", "2" + inputPassword.getText().toString());
                        LogeoWeb(username,pass);
                    }
                }
            });
            // logeo con google
            SignInButton btnGoogle = view.findViewById(R.id.btnGoogle);
            btnGoogle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logeoConGoogle();
                }
            });
        return view;
    }


    ////////////////////// fb

public void LogeoWeb(final String username,final String pass){
    Response.Listener<String> responseListener = new Response.Listener<String>() {

        @Override
        public void onResponse(String response) {
            try {
                JSONObject jsonResponse = new JSONObject(response);
                boolean success = jsonResponse.getBoolean("success");
                if (success) {
                    Toast.makeText(getContext(), username , Toast.LENGTH_LONG).show();
                    Toast.makeText(getContext(), pass , Toast.LENGTH_LONG).show();
                    Intent intentw = new Intent(getContext(),Ingresado.class);
                    intentw.putExtra("email", username+ " 1");
                    intentw.putExtra("password", pass+" 2");
                    intentw.putExtra("imgPerfil", "ads");
                   startActivity(intentw);
                     Toast.makeText(getContext(), "Logeado" , Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Error en el logueo, password y username no coinciden", Toast.LENGTH_LONG).show();

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Error en el logueo").setNegativeButton("Retry", null).create().show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    LoguinRequest loguinRequest = new LoguinRequest(username, pass, responseListener);
    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
    requestQueue.add(loguinRequest);
}

    public void LogeoFacebookWeb(final Profile profile){
        if(profile != null) {
            Response.Listener<String> responseListener = new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if (success) {
                               logeoConFB(profile);

                            Toast.makeText(getContext(), "Logeado", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "Error en el logueo,Usuario no registrado", Toast.LENGTH_LONG).show();

                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setMessage("Error en el logueo").setNegativeButton("Retry", null).create().show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            String facebook_id = profile.getName();

            Toast.makeText(getActivity(), "User ID: "
                    + facebook_id, Toast.LENGTH_SHORT).show();

        LoguinFacebookRequest loguinFacebookRequest = new LoguinFacebookRequest(facebook_id, responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(loguinFacebookRequest);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //Profile profile = Profile.getCurrentProfile();
        //logeoConFB(profile);
       if(accessTokenTracker.isTracking()&&profileTracker.isTracking()) {
           AppEventsLogger.activateApp(getContext());
           Profile profile = Profile.getCurrentProfile();
           LogeoFacebookWeb(profile);

        }

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    ///////////////////// fin ffb


        // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void logeoConGoogle(){
        if (googleApiClient != null)
            googleApiClient.disconnect();

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient
                .Builder(getContext())
                .addApi(Auth.GOOGLE_SIGN_IN_API,googleSignInOptions)
                .build();

        Intent intentGoogle = Auth
                .GoogleSignInApi
                .getSignInIntent(googleApiClient);
        // se usa el start activity for result
        startActivityForResult(intentGoogle,CODERC);
    }

    private void logeoConFB(Profile profile){
        if(profile != null){
            Intent intent = new Intent(getContext(),Ingresado.class);
            //intent.putExtra("idUsuario", profile.getFirstName() + " " + profile.getLastName());
           // intent.putExtra("email", profile.getFirstName());
            intent.putExtra("email", profile.getFirstName());

            System.out.println(profile.getLinkUri().toString());
            intent.putExtra("password", profile.getId().toString());
            intent.putExtra("imgPerfil", profile.getProfilePictureUri(250,250).toString());
            startActivity(intent);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


            if (requestCode == CODERC) {
                GoogleSignInResult result = Auth //resultado de la cesion
                        .GoogleSignInApi
                        .getSignInResultFromIntent(data);
                if (result.isSuccess()){

                    GoogleSignInAccount acc=result.getSignInAccount();
                    String token = acc.getIdToken();
                    Log.e("correo",acc.getEmail());
                    Log.e("nombre",acc.getDisplayName());
                    Log.e("id",acc.getId());
                    Log.e("foto",acc.getPhotoUrl().toString());
                    if (token != null){
                        Toast.makeText(getContext(),token,Toast.LENGTH_LONG).show();
                    }
                    // ingresadon ...
                    Intent intent = new Intent(getContext(),Ingresado.class);
                    intent.putExtra("email",acc.getEmail().toString());
                    intent.putExtra("password",acc.getId().toString());
                    intent.putExtra("imgPerfil",acc.getPhotoUrl().toString());
                    startActivity(intent);
                    Toast.makeText(getContext(),"Correcto",Toast.LENGTH_LONG).show();
                }

        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
