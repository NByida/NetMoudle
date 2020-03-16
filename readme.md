# 1 使用方法
 ```
 dependencies {
	        implementation 'com.github.NByida:NetMoudle:1.0'
}
```
#2 在application中初始化
```class App : Application() {
       override fun onCreate() {
           super.onCreate()
           RetrofitInstance.init(this, "https://xuyida.club")
                   .setLogLevel(okhttp3.logging.HttpLoggingInterceptor.Level.HEADERS)
                   .addCommonParams(map)
       }
   }
```

# 3 继承BaseViewModel
```使用launchOnlyResult发起请求
    
    fun getPoetryByTag() {
            launchOnlyResult({
                RetrofitInstance.create(GankApi::class.java).getPoetry(1)
            }, { list.value = it })
        }

```

# 4 在UI层处理请求结果
```
viewModel.list.observe(this, Observer {
            var iterator = it.iterator()
            while (iterator.hasNext()) {
                Log.e("text", iterator.next().content)
            }
            text.text = it.toString()
        })
    }
```
