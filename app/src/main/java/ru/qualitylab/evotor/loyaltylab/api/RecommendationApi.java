package ru.qualitylab.evotor.loyaltylab.api;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ru.qualitylab.evotor.loyaltylab.model.MyBody;
import ru.qualitylab.evotor.loyaltylab.model.ResponseProducts;

public interface RecommendationApi {

    @POST("recommend")
    Single<ResponseProducts> getRecommendations(@Body MyBody body);

}
