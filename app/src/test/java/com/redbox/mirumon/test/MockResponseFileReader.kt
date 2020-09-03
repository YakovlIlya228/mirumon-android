package com.redbox.mirumon.test

import java.io.File


fun MockResponseFileReader( filePath: String): String {
    return File(filePath).readText(Charsets.UTF_8)
}