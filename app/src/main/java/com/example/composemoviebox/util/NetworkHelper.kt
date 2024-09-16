package com.example.composemoviebox.util

//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.MutableStateFlow
//
//private fun <T> fetchData(
//    fetch: suspend () -> Flow<ApiResult<T>>,
//    state: MutableStateFlow<ApiResult<T>>
//) {
//    viewModelScope.launch {
//        fetch().collect { result ->
//            state.value = when (result) {
//                is ApiResult.Success -> ApiResult.Success(result.data)
//                is ApiResult.Error -> ApiResult.Error(result.exception)
//                is ApiResult.Loading -> ApiResult.Loading()
//            }
//        }
//    }
//}
