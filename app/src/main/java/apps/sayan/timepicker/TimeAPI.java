package apps.sayan.timepicker;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.PUT;

/**
 * Created by SAYAN on 28-05-2016.
 */

//Full URL to USE
    //https://api.myjson.com/bins/1x18s
public interface TimeAPI {
    @GET("/bins/1x18s")
    public void getTime(Callback<POJO> response);

    @PUT("/bins/1x18s")
    public void postTime(@Body POJO pojo, Callback<POJO> callback);
}
