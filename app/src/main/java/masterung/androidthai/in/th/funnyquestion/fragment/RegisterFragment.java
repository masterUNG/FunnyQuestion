package masterung.androidthai.in.th.funnyquestion.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import masterung.androidthai.in.th.funnyquestion.MainActivity;
import masterung.androidthai.in.th.funnyquestion.R;
import masterung.androidthai.in.th.funnyquestion.ServiceActivity;
import masterung.androidthai.in.th.funnyquestion.utility.MyAlertDialog;

public class RegisterFragment extends Fragment {

    private ProgressDialog progressDialog;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Create Toolbar
        createToolbar();

    }   // Main Method

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.itemUpload) {

            registerController();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void registerController() {

        EditText nameEditText = getView().findViewById(R.id.edtName);
        EditText emailEditText = getView().findViewById(R.id.edtEmail);
        EditText passwordEditText = getView().findViewById(R.id.edtPassword);

        String nameString = nameEditText.getText().toString().trim();
        String emailString = emailEditText.getText().toString().trim();
        String passwordString = passwordEditText.getText().toString().trim();

        if (nameString.isEmpty() || emailString.isEmpty() || passwordString.isEmpty()) {
//            Have Space
            MyAlertDialog myAlertDialog = new MyAlertDialog(getActivity());
            myAlertDialog.normalDialog("Have Space",
                    "Please Fill All Every Blank");
        } else {
//            No Space
            uploadToFirebase(nameString, emailString, passwordString);

        }


    }

    private void uploadToFirebase(final String nameString, String emailString, String passwordString) {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Process Upload");
        progressDialog.setMessage("Please Wain Few Minus...");
        progressDialog.show();

        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(emailString, passwordString)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                            Success

                            UserProfileChangeRequest.Builder builder = new UserProfileChangeRequest.Builder();
                            builder.setDisplayName(nameString);
                            UserProfileChangeRequest userProfileChangeRequest = builder.build();

                            final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            firebaseUser.updateProfile(userProfileChangeRequest).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Toast.makeText(getActivity(), "Success Register " + firebaseUser.getDisplayName(),
                                            Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();

                                    startActivity(new Intent(getActivity(), ServiceActivity.class));
                                    getActivity().finish();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                }
                            });


                        } else {
//                            Non Success
                            MyAlertDialog myAlertDialog = new MyAlertDialog(getActivity());
                            myAlertDialog.normalDialog("Non Register",
                                    task.getException().getMessage());
                            progressDialog.dismiss();
                        }
                    }
                });


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_register, menu);
    }

    private void createToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarRegister);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Register");
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle("Please Fill Every Blank");
        ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        return view;
    }
}
