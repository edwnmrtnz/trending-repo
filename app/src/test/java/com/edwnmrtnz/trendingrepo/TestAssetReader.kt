package com.edwnmrtnz.trendingrepo

import java.io.File

object TestAssetReader {
    fun read(
        module: String,
        filenameWithExtension: String
    ): String {
        return File("../$module/src/main/assets/$filenameWithExtension")
            .readText(Charsets.UTF_8)
    }
}
