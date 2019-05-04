package br.com.aleson.nasa.apod.app.feature.profile.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import br.com.aleson.nasa.apod.app.feature.profile.api.ServiceVersionRequest;
import br.com.aleson.nasa.apod.app.feature.profile.api.ServiceVersionResponse;
import br.com.aleson.nasa.apod.app.feature.profile.model.ServiceVersionModel;
import br.com.connector.aleson.android.connector.Connector;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends BaseViewModel {

    private MutableLiveData version;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ServiceVersionModel> getVersion() {
        if (version == null) {
            version = new MutableLiveData<ServiceVersionModel>();
            getServiceVersion();
        }
        return version;
    }

    private void getServiceVersion() {

        this.setLoading(true);

        Connector.request().create(ServiceVersionRequest.class).getServiceVersion().enqueue(new Callback<ServiceVersionResponse>() {
            @Override
            public void onResponse(Call<ServiceVersionResponse> call, Response<ServiceVersionResponse> response) {

                if (response == null || response.body() == null) {

                    setLoading(false);
                } else {

                    processResponse(response);
                    setLoading(false);
                }
            }

            @Override
            public void onFailure(Call<ServiceVersionResponse> call, Throwable t) {
                setLoading(false);
            }
        });
    }

    private void processResponse(Response<ServiceVersionResponse> response) {

        ServiceVersionResponse versionResponse = response.body();
        ServiceVersionModel serviceVersionModel = new ServiceVersionModel();
        serviceVersionModel.setVersion(versionResponse.getVersion());

        this.version.postValue(serviceVersionModel);
    }
}
