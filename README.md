LiteHttp consists of two parts currently, Imageloader and Httploader. The corresponding functions are image loading and network request. The data returned by the network request can be automatically converted to Json, JavaBean, File, Bitmap, etc. by setting. Litehttp is simple to use, and all network requests are asynchronous requests.
## Instructions for use
Imageloader can set the image displayed during the loading process and load the failed image. Httploader supports adding request headers, request bodies, setting request methods.
### Code Example
Initialize LiteHttp for HttpLoader.
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
Initialize LiteHttp for ImageLoader.
```Java
        LiteHttp.httpLoader()
                 .clazz(Bitmap.class)
                 .url(regular)
                 .method(Method.GET)
                 .listener(new IHttpListener<Bitmap>() {
                     @Override
                     public void onSuccess(Bitmap bitmap) {
                     }
        
                     @Override
                     public void onError(int code) {
                     }
                 }).execute();
```
or one can simply use 
```Java
        LiteHttp.imageLoader(getContext())
                 .url(url)
                 .scaleType(getScaleType())
                 .placeholder(placeholder)
                 .error(error)
                 .view(this)
                 .listener(new IImageListener() {
                       @Override
                       public void onSuccess(Bitmap bitmap) {
                       }
                           
                       @Override
                       public void onError(ErrorResponse errorCode) {
                       }
                 })
                 .execute();
```
or you can directly use the LiteImageView and load image as follow
```Java
        imageView.loadImage(url, 
                            placeholder, 
                            error, 
                            new IImageListener() {
                                 @Override
                                 public void onSuccess(Bitmap bitmap) {
                                 }
                                                                                
                                 @Override
                                 public void onError(ErrorResponse errorCode) {
                                 }
        })

```
