package com.monke.rental;

import com.monke.data.OnCompleteListener;
import com.monke.data.Result;

public interface ResponseRemoteDataSource {

    void uploadResponse(ResponseRemote response, OnCompleteListener<Result<?>> listener);

    void getResponseId(String responseId, OnCompleteListener<Result<ResponseRemote>> listener);
}
