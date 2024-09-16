package com.example.composemoviebox.data

sealed class ApiResult<out T> {
    // Yükleme durumunu temsil eder, opsiyonel olarak veri alabilir
    data class Loading<out T>(
        val data: T? = null,
    ) : ApiResult<T>()

    // Başarıyla veri alındığında kullanılır
    data class Success<out T>(
        val data: T,
    ) : ApiResult<T>()

    // Hata meydana geldiğinde kullanılır, opsiyonel olarak önceki veri ve hata mesajı içerebilir
    data class Error<out T>(
        val exception: Throwable,
        val data: T? = null,
    ) : ApiResult<T>()
}
