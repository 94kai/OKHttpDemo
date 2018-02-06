/*
 * Copyright (C) 2014 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package okhttp3;

import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 网络操作流程就是，调用层传入url，请求方式等等信息，封装成Request，然后经过一系列的操作，从服务端拿到一个
 * Response，回传到调用层。拦截器在这个过程中可以拦截调用层发出的request，并且对他进行一下处理，比如篡改、打印reqeust信息等
 * ，也可一个在拿到response之后，拦截response，对他进行修改。
 * 拦截器可以有多个，像一条链似的，上游处理完，下游还可以继续处理。
 * okhttp的拦截器机制使得一些业务耦合性更弱，更灵活。例如，他把重试机制、缓存机制、打印日志等，甚至网络请求都封装成拦截器。我们也可以自定义拦截器
 * Observes, modifies, and potentially short-circuits requests going out and the corresponding
 * responses coming back in. Typically interceptors add, remove, or transform headers on the request
 * or response.
 */
public interface Interceptor {
  Response intercept(Chain chain) throws IOException;

  interface Chain {
    Request request();

    Response proceed(Request request) throws IOException;

    /**
     * Returns the connection the request will be executed on. This is only available in the chains
     * of network interceptors; for application interceptors this is always null.
     */
    @Nullable
    Connection connection();

    Call call();

    int connectTimeoutMillis();

    Chain withConnectTimeout(int timeout, TimeUnit unit);

    int readTimeoutMillis();

    Chain withReadTimeout(int timeout, TimeUnit unit);

    int writeTimeoutMillis();

    Chain withWriteTimeout(int timeout, TimeUnit unit);
  }
}
