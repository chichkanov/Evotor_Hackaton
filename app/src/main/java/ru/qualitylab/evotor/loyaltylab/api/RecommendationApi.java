package ru.qualitylab.evotor.loyaltylab.api;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ru.qualitylab.evotor.loyaltylab.model.ProductUi;

public interface RecommendationApi {

    @POST("recommend")
    Single<ProductUi> getRecommendations(@Body String body);

}
