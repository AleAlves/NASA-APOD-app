package br.com.aleson.nasa.apod.app.feature.profile.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import br.com.aleson.nasa.apod.app.common.callback.ResponseCallback;
import br.com.aleson.nasa.apod.app.feature.profile.api.ServiceVersionRequest;
import br.com.aleson.nasa.apod.app.feature.profile.api.ServiceVersionResponse;
import br.com.aleson.nasa.apod.app.feature.profile.model.ServiceVersionModel;
import br.com.aleson.nasa.apod.app.feature.profile.repository.ProfileRepository;
import br.com.aleson.nasa.apod.app.feature.profile.repository.ProfileRepositoryImpl;
import br.com.connector.aleson.android.connector.Connector;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends BaseViewModel {

    private MutableLiveData version;
    private MutableLiveData account;
    private ProfileRepository repository;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        repository = new ProfileRepositoryImpl();
    }

    public LiveData<ServiceVersionModel> getVersion() {
        if (version == null) {
            version = new MutableLiveData<ServiceVersionModel>();
            getServiceVersion();
        }
        return version;
    }

    public LiveData<Boolean> delete() {
        if (account == null) {
            account = new MutableLiveData<Boolean>();
            deleteAccount();
        }
        return account;
    }

    private void getServiceVersion() {

        this.setLoading(true);

        repository.getService(new ResponseCallback() {
            @Override
            public void onResponse(Object response) {

                processResponse(((ServiceVersionResponse) response));

                setLoading(false);

            }

            @Override
            public void onFailure(Object response) {
                setLoading(false);
            }
        });

    }

    private void processResponse(ServiceVersionResponse response) {

        ServiceVersionModel serviceVersionModel = new ServiceVersionModel();
        serviceVersionModel.setVersion(response.getVersion());

        this.version.postValue(serviceVersionModel);
    }

    private void deleteAccount() {

        repository.deleteAccount(new ResponseCallback() {
            @Override
            public void onResponse(Object response) {
                account.postValue(true);
            }

            @Override
            public void onFailure(Object response) {
                account.postValue(false);
            }
        });
    }
}
