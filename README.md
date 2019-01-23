LiteHttp consists of two parts currently, Imageloader and Httploader. The corresponding functions are image loading and network request. The data returned by the network request can be automatically converted to Json, JavaBean, File, Bitmap, etc. by setting. Litehttp is simple to use, and all network requests are asynchronous requests.
## Instructions for use
Imageloader can set the image displayed during the loading process and load the failed image. Httploader supports adding request headers, request bodies, setting request methods.
### Code Example
Initialize LiteHttp before using it.
```Java
        LiteHttp.httpLoader(context)
                .url(url)
                .method(method)
                .clazz(classToReturn)
                .listener(new IHttpListener<Example>() {

                    @Override
                    public void onSuccess(Example o) { 
                    }

                    @Override
                    public void onError(ErrorResponse response) {
                    }
                }).execute()
```