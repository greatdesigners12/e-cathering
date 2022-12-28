package com.training.e_cathering.Utils

data class DataAPIWrapper<T, Boolean, Exception>(var data : T? = null, var loading : Boolean? = null, var e : Exception? = null)
