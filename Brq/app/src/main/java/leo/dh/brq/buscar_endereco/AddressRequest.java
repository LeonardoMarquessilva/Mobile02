package leo.dh.brq.buscar_endereco;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.lang.ref.WeakReference;

import leo.dh.brq.activity.AdicionarClienteActivity;

public class AddressRequest extends AsyncTask<Void, Void, Address> {
    private WeakReference<AdicionarClienteActivity> activity;

    public AddressRequest( AdicionarClienteActivity activity ){
        this.activity = new WeakReference<>( activity );
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if( activity.get() != null ){
            activity.get().lockFields( true );
        }
    }

    @Override
    protected Address doInBackground(Void... voids) {
        try {
            String jsonString = JsonRequest.request( activity.get().getUriZipCode() );

            Gson gson = new Gson();
            return gson.fromJson( jsonString, Address.class );

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Address address) {
        super.onPostExecute(address);

        if( activity.get() != null ){
            activity.get().lockFields( false );

            if( address != null ){
               activity.get().setDataViews( address );
            }
        }
    }
}
